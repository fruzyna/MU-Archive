
// NetWarServer.java
// Written by mike slattery - mar 2007
// Based on FBFServer by
// Andrew Davison, April 2005, ad@fivedots.coe.psu.ac.th

/* A threaded server specialised to deal with at most two
   concurrent links from the two players involved in the
   NetTankWar game.

   This is a thin server -- all the game logic is located
   on the client-side.
*/

import java.net.*;


public class NetWarServer
{
  private static final int PORT = 1234;

  private static final int MAX_PLAYERS = 2;   // two-person game

  // data structures shared by the handlers
  private PlayerServerHandler[] handlers;     // handlers for players
  private int numPlayers;


  public NetWarServer()
  // Concurrently process players
  {
    handlers = new PlayerServerHandler[MAX_PLAYERS];
    handlers[0] = null;
    handlers[1] = null;
    numPlayers = 0;

    try {
      @SuppressWarnings("resource")
      ServerSocket serverSock = new ServerSocket(PORT);
      Socket clientSock;
      while (true) {
        System.out.println("Waiting for a client...");
        clientSock = serverSock.accept();
        handlers[numPlayers] = new PlayerServerHandler(numPlayers, clientSock, this);
        handlers[numPlayers].start();
        numPlayers++;
        if (numPlayers == MAX_PLAYERS){
			handlers[0].sendMessage("begin");
			break;
		}
      }
    }
    catch(Exception e)
    {  System.out.println(e);  }
  }  // end of NetWarServer()


  // methods for child threads to access shared data structures

  synchronized public void tellOther(int playerID, String msg)
  // send msg to the other player
  { int otherID = 1 - playerID;
    handlers[otherID].sendMessage(msg);
  }  // end of tellOther()


  // -----------------------------------

  public static void main(String args[])
  {  new NetWarServer();  }

} // end of NetWarServer class



