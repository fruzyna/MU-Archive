
// PlayerServerHandler.java
// written by mike slattery - mar 2007
// Based on code by
// Andrew Davison, April 2005, ad@fivedots.coe.psu.ac.th

/* Relay messages from the client
   to the other player.
*/

import java.net.*;
import java.io.*;

public class PlayerServerHandler extends Thread
{
  private NetWarServer server;
  private Socket clientSock;
  private BufferedReader in;
  private PrintWriter out;

  private int playerID;     // this player id is assigned by Server


  public PlayerServerHandler(int pid, Socket s, NetWarServer serv)
  {
	  playerID = pid;
    clientSock = s;
    server = serv;
    System.out.println("Player connected");
    try {
      in  = new BufferedReader(
			    new InputStreamReader( clientSock.getInputStream() ) );
      out = new PrintWriter( clientSock.getOutputStream(), true );  // autoflush
    }
    catch(Exception e)
    {  System.out.println(e);  }
  }


  public void run()
  /*
     start processing client-side input
  */
  {
      processPlayerInput();

    try {     // close socket from player
      clientSock.close();
      System.out.println("Handler: Player " + playerID + " connection closed\n");
    }
    catch(Exception e)
    {  System.out.println(e);  }

  }  // end of run()


   private void processPlayerInput()
   /* Stop when the input stream closes (is null).
      Otherwise send the message to the other player. */
   {
     String line;
     boolean done = false;
     try {
       while (!done) {
         if((line = in.readLine()) == null)
           done = true;
         else {
           System.out.println("Player " + playerID + " got msg: " + line);
           server.tellOther(playerID, line); // pass on message
         }
       }
     }
     catch(IOException e)
     {  System.out.println("Player " + playerID + " closed the connection"); }
   }  // end of processPlayerInput()


  synchronized public void sendMessage(String msg)
  // called by handler and top-level server
  {
	System.out.println("Handler: Msg to player "+playerID+" "+msg);
	try {
        out.println(msg);
    }
    catch(Exception e)
    {  System.out.println("Handler for player " + playerID + "\n" + e);  }
  }  // end of sendMessage()


}  // end of PlayerServerHandler class
