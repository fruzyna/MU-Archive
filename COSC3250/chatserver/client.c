#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <netdb.h>

#define BUF_SIZE 500
#define MAXCLIENTNAME 16
#define TRUE 1
#define FALSE 0

//Liam Fruzyna, Weihua Liu

int main (int argc, char *argv[])
{
	int i, sockfd, serverport, server_addr_len, nwrite, nread, pid;
	char c, name[MAXCLIENTNAME], buf_in[BUF_SIZE], buf_out[BUF_SIZE];
	struct sockaddr_in server_addr, my_addr;
	struct hostent* server;

	if (argc != 4)
	{
		fprintf (stderr, "Usage: %s server portnum username\n", argv[0]);
		exit (EXIT_FAILURE);
	}

  	//init buffers
	bzero(buf_in, BUF_SIZE);
	bzero(buf_out, BUF_SIZE);
	
	//setup addresses
	server = gethostbyname(argv[1]);
	serverport = atoi(argv[2]);
	strcpy(name, argv[3]);
	sockfd = socket (AF_INET, SOCK_DGRAM, 0);
	if (server == NULL)
	{
		fprintf(stderr, "ERROR, no such host");
		exit(0);
	}
	bzero((char *) &server_addr, server_addr_len);
	server_addr.sin_family = AF_INET;
	bcopy((char*)server->h_addr, (char*)&server_addr.sin_addr.s_addr, server->h_length);
	server_addr.sin_port = htons(serverport);
	
	//connect to server
	if (connect(sockfd,(struct sockaddr*)&server_addr, sizeof(server_addr)) < 0)
	{
		printf("ERROR connecting");
	}
	
	//for into 2 processes
	if ( (pid = fork()) < 0)
	{
		fprintf(stderr, "ERROR, fork failed");
	}
	else if (pid == 0)
	{
		//send user name to server
		if ((nwrite = write(sockfd, name, sizeof(name))) < 0)
		{
			fprintf(stderr, "ERROR, giving server name");
		}
		
		c = 0;
		i = 0;
		//input listener
		while ((c = getchar()) != EOF)
		{			
			//send buffer if new line
			if (c == '\n')
			{
				//replace new line with end char
				buf_out[i] = '\0';
				
				//send buffer
				i++;
				nwrite = write(sockfd, buf_out, i);
				
				//if close exit the program
				if (0 == strncmp(buf_out, "close", 5))
				{
					printf("Leaving Server");
					break;
				}
				
				//reset input
				bzero(buf_out, BUF_SIZE);
				i = 0;
				printf("\x1B[A");
				continue;
			}
			
			//add input to buffer
			buf_out[i] = c;
			i++;
		}
	}
	else
	{
		//server listener
		while(1)
		{
			//reads new message from server
			if ((nread = read(sockfd, buf_in, BUF_SIZE)) == 0)
			{
				fprintf(stderr, "ERROR, reading from server");
			}
			
			//print what the server sends
			buf_in[nread] = '\0';
			printf("%s\n", buf_in);
			
			//if exit exit the program
			if (0 == strncmp(buf_in, "===SERVER IS SHUTTING DOWN===", 30))
			{
				break;
			}
		}
	}
	
	//only reached if EOF is sent or server shuts down
	close(sockfd);
	return 0;
}
