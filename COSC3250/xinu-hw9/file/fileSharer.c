/* fileSharer.c - fileSharer */
/* Copyright (C) 2007, Marquette University.  All rights reserved. */

#include <kernel.h>
#include <xc.h>
#include <file.h>
#include <fileshare.h>
#include <ether.h>
#include <network.h>
#include <nvram.h>

struct fishnode school[SCHOOLMAX];
char   fishlist[DIRENTRIES][FNAMLEN];

static uchar bcast[] = {0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF};
static uchar myMAC[ETH_ADDR_LEN];

//respond to announcement
int fishAnnounce(uchar *packet)
{
	struct ethergram *eg = (struct ethergram *)packet;
	int i = 0;

	for (i = 0; i < SCHOOLMAX; i++)
	{
		/* Check to see if node already known. */
		if (school[i].used &&
			(0 == memcmp(school[i].mac, eg->src, ETH_ADDR_LEN)))
			return OK;
	}
	for (i = 0; i < SCHOOLMAX; i++)
	{
		/* Else find an unused slot and store it. */
		if (!school[i].used)
		{
			school[i].used = 1;
			memcpy(school[i].mac, eg->src, ETH_ADDR_LEN);
			memcpy(school[i].name, eg->data + 1, FISH_MAXNAME);
			//printf("Node %s\n", school[i].name);
			return OK;
		}
	}
	return SYSERR;
}

//respond to dir list
int fishDirList(uchar *packet)
{
	struct ethergram *eg = (struct ethergram *)packet;

	memcpy(fishlist, eg->data + 1, DIRENTRIES * FNAMLEN);

	int i, j;
	for(i = 0; i < DIRENTRIES; i++)
	{
		for(j = 0; j < FNAMLEN; j++)
		{
			printf("%s,", fishlist[i][j]);
		}
	}

	return OK;
}

//respond to have file
int fishHaveFile(uchar *packet)
{
	struct ethergram *eg = (struct ethergram *)packet;

	char name[FNAMLEN];
	memcpy(name, eg->data + 1, FNAMLEN);
	devcall file = fileCreate(name);
	if(file != SYSERR)
	{
		char data[DISKBLOCKLEN];
		memcpy(data, eg->data + 1 + FNAMLEN, DISKBLOCKLEN);
		int i;
		for(i = 0; i < DISKBLOCKLEN; i++)
		{
			filePutChar(file, data[i]);
		}
		return OK;
	}
	return SYSERR;
}

//respond to no file
int fishNoFile(uchar *packet)
{
	struct ethergram *eg = (struct ethergram *)packet;

	return SYSERR;
}


/*------------------------------------------------------------------------
 * fishPing - Reply to a broadcast FISH request.
 *------------------------------------------------------------------------
 */
 void fishPing(uchar *packet)
 {
 	uchar *ppkt = packet;
 	struct ethergram *eg = (struct ethergram *)packet;

 	/* Source of request becomes destination of reply. */
 	memcpy(eg->dst, eg->src, ETH_ADDR_LEN);
 	/* Source of reply becomes me. */
 	memcpy(eg->src, myMAC, ETH_ADDR_LEN);
 	/* Zero out payload. */
 	bzero(eg->data, ETHER_MINPAYLOAD);
 	/* FISH type becomes ANNOUNCE. */
 	eg->data[0] = FISH_ANNOUNCE;
 	strncpy(&eg->data[1], nvramGet("hostname\0"), FISH_MAXNAME-1);
 	write(ETH0, packet, ETHER_SIZE + ETHER_MINPAYLOAD);
 }

 //respond to dir ask
 void fishDirAsk(uchar *packet)
 {
 	uchar *ppkt = packet;
 	struct ethergram *eg = (struct ethergram *)packet;

 	/* Source of request becomes destination of reply. */
 	memcpy(eg->dst, eg->src, ETH_ADDR_LEN);
 	/* Source of reply becomes me. */
 	memcpy(eg->src, myMAC, ETH_ADDR_LEN);
 	/* Zero out payload. */
 	bzero(eg->data, ETHER_MINPAYLOAD);
 	/* FISH type becomes DIRLIST. */
 	eg->data[0] = FISH_DIRLIST;

 	strncpy(&eg->data[1], fishlist, DIRENTRIES * FNAMLEN);
	int length;
	if(DIRENTRIES * FNAMLEN < ETHER_MINPAYLOAD)
	{
		length = ETHER_MINPAYLOAD;
	}
	else
	{
		length = DIRENTRIES * FNAMLEN;
	}
 	write(ETH0, packet, ETHER_SIZE + length);
 }

 //respond to get file
 void fishGetFile(uchar *packet)
 {
 	uchar *ppkt = packet;
 	struct ethergram *eg = (struct ethergram *)packet;

 	/* Source of request becomes destination of reply. */
 	memcpy(eg->dst, eg->src, ETH_ADDR_LEN);
 	/* Source of reply becomes me. */
 	memcpy(eg->src, myMAC, ETH_ADDR_LEN);
 	/* Zero out payload. */
 	bzero(eg->data, ETHER_MINPAYLOAD);

	devcall file = fileOpen(eg->data);

	if(file != SYSERR)
	{
 		eg->data[0] = FISH_HAVEFILE;
	 	bzero(eg->data, ETHER_MINPAYLOAD);
		strncpy(&eg->data[1], filetab[file].fn_data, DISKBLOCKLEN);
		write(ETH0, packet, ETHER_SIZE + DISKBLOCKLEN);
	}
	else
	{
		eg->data[0] = FISH_NOFILE;
	 	bzero(eg->data, ETHER_MINPAYLOAD);
		write(ETH0, packet, ETHER_SIZE + ETHER_MINPAYLOAD);
	}
 }

 void printPkt(uchar *pkt, int len)
 {
 	int i;
 	printf("[");
 	for(i = 0; i < len; i++)
 	{
 		if(i == 0)
 			printf("Dest MAC:");
 		else if(i == 6)
 			printf("\nSrc MAC:");
 		else if(i == 12)
 			printf("\n3250:");
 		else if(i == 14)
 			printf("\nType:");
 		else if(i == 15)
 			printf("\nPayload:");
 		printf("%02X ", pkt[i]); //*(pkt + i) also works
 	}
 	printf("]\n");
 }

/*------------------------------------------------------------------------
 * fileSharer - Process that shares files over the network.
 *------------------------------------------------------------------------
 */
int fileSharer(int dev)
{
	uchar packet[PKTSZ];
	int size;
	struct ethergram *eg = (struct ethergram *)packet;


	enable();
	/* Zero out the packet buffer. */
	bzero(packet, PKTSZ);
	bzero(school, sizeof(school));
	bzero(fishlist, sizeof(fishlist));

	/* Lookup canonical MAC in NVRAM, and store in ether struct */
 	colon2mac(nvramGet("et0macaddr"), myMAC);

	while (SYSERR != (size = read(dev, packet, PKTSZ)))
	{
		/* Check packet to see if fileshare type with
		   destination broadcast or me. */
		if ((ntohs(eg->type) == ETYPE_FISH)
			&& ((0 == memcmp(eg->dst, bcast, ETH_ADDR_LEN))
				|| (0 == memcmp(eg->dst, myMAC, ETH_ADDR_LEN))))
		{
			//printPkt(packet, size);

			switch (eg->data[0])
			{
			case FISH_ANNOUNCE:
			//store received fish
				fishAnnounce(packet);
				break;

			case FISH_PING:
			//announce yourself
				fishPing(packet);
				break;

		// TODO: All of the cases below.

			case FISH_DIRASK:
			//return directories
				fishDirAsk(packet);
				break;
			case FISH_DIRLIST:
				fishDirList(packet);
				break;
			//save list
			case FISH_GETFILE:
			//send file or none
				fishGetFile(packet);
				break;
			case FISH_HAVEFILE:
			//save file
				fishHaveFile(packet);
				break;
			case FISH_NOFILE:
			//give failure
				fishNoFile(packet);
				break;
			default:
				printf("ERROR: Got unhandled FISH type %d\n", eg->data[0]);
			}
		}
	}

	return OK;
}
