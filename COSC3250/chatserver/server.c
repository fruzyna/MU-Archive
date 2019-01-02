#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <netdb.h>

#define BUF_SIZE 500
#define MAX_CLIENT 5
#define MAX_ROOM 5

//Liam Fruzyna, Weihua Liu

struct client {
	struct sockaddr_in their_addr;
	int their_addr_len;
	char name[16];
	int namelen;
	int room;
};


int main (int argc, char *argv[])
{
	int sockfd, myport, nread, their_addr_len;
	struct sockaddr_in my_addr, their_addr;
	char buf[BUF_SIZE];

	struct client *clients;
	clients = (struct client*) malloc(MAX_CLIENT * MAX_ROOM * sizeof(struct client));

	int used = 0;

	if (argc != 2)
	{
		fprintf (stderr, "Usage: %s portnum\n", argv[0]);
		exit (EXIT_FAILURE);
	}

	myport = atoi (argv[1]);

	sockfd = socket (AF_INET, SOCK_DGRAM, 0);

	my_addr.sin_family = AF_INET;
	my_addr.sin_port = htons (myport);
	my_addr.sin_addr.s_addr = INADDR_ANY;
	memset (&(my_addr.sin_zero), '\0', 8);

	if (bind (sockfd, (struct sockaddr *) &my_addr, sizeof (struct sockaddr)))
	{
		close (sockfd);
		fprintf (stderr, "Failed to bind socket!\n");
		exit (EXIT_FAILURE);
	}
	else
	{
		printf ("Server listening on port %d\n", myport);
	}

	while(1)
	{
		int exit = 0;
	
		their_addr_len = sizeof (struct sockaddr_in);
		nread = recvfrom (sockfd, buf, BUF_SIZE, 0, (struct sockaddr *) &their_addr, &their_addr_len);
		
		int i;
		
		char host[NI_MAXHOST], service[NI_MAXSERV];
		int result;
		result = getnameinfo ((struct sockaddr *) &their_addr, their_addr_len, host, NI_MAXHOST, service, NI_MAXSERV, NI_NUMERICSERV);

		if (result == 0)
			printf ("Received %d bytes from %s:%s\n", nread, host, service);
		else
			fprintf (stderr, "getnameinfo: %s\n", gai_strerror (result));


		//determine if client is new or not
		int new = 1;
		for(i = 0; i < used; i++)
		{
			struct client *c = clients + i;
			if(memcmp(&(c->their_addr), &their_addr, sizeof(struct sockaddr_in)) == 0)
			{
				//printf("Existing client\n");
				new = 0;
				break;
			}
		}
		
		//current user
		struct client *c = clients + i;
		
		int oldRoom = -1;
		
		if(used < MAX_CLIENT * MAX_ROOM && new)
		{
			//add new user
			memcpy(&(c->their_addr), &their_addr, sizeof(struct sockaddr_in));
			c->their_addr_len = their_addr_len;
			c->namelen = nread-1;
			c->room = 1;
			if(buf[nread-1] == '\n')
			{
				buf[nread-1] = '\0';
			}
			strncpy(c->name, buf, 16);
			
			//build message welcoming new user
			strncpy(buf, "Welcome ", BUF_SIZE);
			strcat(buf, c->name);
			nread = nread + 8;
			used++;
			
			printf("Adding client %s\n", c->name);
		}
		else
		{
			int skip = 0;
			
			buf[nread-1] = '\0';
			if(strcmp(buf, "exit") == 0)
			{
				//if exit is called, set message to announcing and set exit flag
				exit = 1;
				strncpy(buf, "===SERVER IS SHUTTING DOWN===", BUF_SIZE);
				nread = 30;
				skip = 1;
			}
			else if(strncmp(buf, "goto", 4) == 0)
			{
				int room = buf[5] - '0';
				if(room > 0 && room <= MAX_ROOM && c->room != room)
				{
					oldRoom = c->room;
					c->room = room;
					strncpy(buf, c->name, BUF_SIZE);
					strcat(buf, " has moved to ");
					char room[2];
					room[0] = '0' + c->room;
					room[1] = '\0';
					strcat(buf, room);
					nread = c->namelen + 16;
				}
				else
				{
					strncpy(buf, "Invalid room number", BUF_SIZE);
					nread = 20;
				}
				skip = 1;
			}
			else if(strcmp(buf, "room") == 0)
			{
				strncpy(buf, "This is room ", BUF_SIZE);
				char room[2];
				room[0] = '0' + c->room;
				room[1] = '\0';
				strcat(buf, room);
				nread = 15;
				skip = 1;
			}
			else if(strcmp(buf, "users") == 0)
			{
				//if users if called, set message to list of users
				strncpy(buf, "CURRENT USERS[", BUF_SIZE);
				char count[2];
				count[0] = '0' + used;
				count[1] = '\0';
				strcat(buf, count);
				strcat(buf, "]: ");
				nread = 18;
				for(i = 0; i < used; i++)
				{
					struct client *c = clients + i;
					strcat(buf, c->name);
					strcat(buf, ":");
					char room[2];
					room[0] = '0' + c->room;
					room[1] = '\0';
					strcat(buf, room);
					strcat(buf, ", ");
					nread += c->namelen + 2;
				}
				skip = 1;
			}
			else if(strcmp(buf, "close") == 0)
			{
				//if close is called remove current user
				printf("Removing user %s", c->name);
				
				//build message
				strncpy(buf, "User ", BUF_SIZE);
				strcat(buf, c->name);
				strcat(buf, " has left the chat");
				nread = c->namelen + 25;
				
				//remove user
				used--;
				int j;
				for(j = i; j < used; j++)
				{
					struct client *cc = clients + j;
					struct client *cn = clients + j + 1;
					memcpy(&(cc->their_addr), &(cn->their_addr), sizeof(struct sockaddr_in));
					cc->their_addr_len = cn->their_addr_len;
					cc->namelen = cn->namelen;
					strncpy(cc->name, cn->name, 16);
				}
				skip = 1;
			}
		
			if(!skip)
			{
				//build reply
				char userString[BUF_SIZE];
				userString[0] = '[';
				userString[1] = '\0';
				strcat(userString, c->name);
				strcat(userString, "] ");
				strcat(userString, buf);
				strncpy(buf, userString, BUF_SIZE);
				
				//calculate length of output
				nread = nread + 3 + c->namelen;
			}
			
		}

		//send message to all clients
		for(i = 0; i < used; i++)
		{
			struct client *cl = clients + i;
			if(cl->room == c->room || cl->room == oldRoom)
			{
				if (sendto (sockfd, buf, nread, 0, (struct sockaddr *) &(cl->their_addr), cl->their_addr_len) != nread)
				{
					fprintf (stderr, "Error sending response\n");
				}
			}
		}
		
		//close the program when exit
		if(exit)
		{
			break;
		}
	}
	close(sockfd);
	return 0;
}
