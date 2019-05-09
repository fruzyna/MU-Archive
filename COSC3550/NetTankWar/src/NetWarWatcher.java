// NetWarWatcher.java
// written by mike slattery - mar 2007
//
// Based on FBFWatcher.java by
// Andrew Davison, April 2005, ad@fivedots.coe.psu.ac.th

/* NetWarWatcher monitors the stream coming from the server
   which will contain messages that must be processed by
   the client (a NetTankWar object)

   Incoming Messages
   -----------------
   From server:
    begin                   -- start new game. You are RED tank (player 0)
                                  and should build rock field.
   From other client:
    rocks <rock-string>     -- description of rock field. You are BLUE tank (player 1).
    turnL <boolean> <x> <y> <angle>
    turnR <boolean> <x> <y> <angle> -- begin or end turn
    forth <boolean> <x> <y> <angle> -- begin or end forward movement
*/

import java.io.*;


public class NetWarWatcher extends Thread
{
  private NetTankWar ntw;    // ref back to client
  private BufferedReader in;

  public NetWarWatcher(NetTankWar ntw, BufferedReader i)
  {  this.ntw = ntw;
     in = i;
  }


  public void run()
  // Read messages and act on them
  {
    String line;
    try {
      while ((line = in.readLine()) != null) {
		  System.out.println("Watcher: player "+ntw.getPlayerID()+ " got "+line);
        if (line.startsWith("begin")){
          ntw.setPlayerID(0);
          ntw.sendRocks();
		}
        else if (line.startsWith("rocks")){
		  ntw.setPlayerID(1);
          ntw.setRocks(line.substring(6));
	    }
        else if (line.startsWith("turn") || line.startsWith("forth") || line.startsWith("fire"))
          ntw.processMove(line);
        else if (line.startsWith("rock") || line.startsWith("tank"))
        	ntw.processHit(line);
        else   // anything else
          System.out.println("ERR: " + line + "\n");
      }
    }
    catch(Exception e)    // socket closure will cause termination of while
    { System.out.println("NetWarWatcher: Socket closed");
    }
  }  // end of run()

}  // end of NetWarWatcher

