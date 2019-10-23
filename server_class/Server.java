package server_class;

import java.util.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {

     private int port;
     private ServerSocket server = null;
     private int MaxAcceptClient = 100;
//     private Socket Clients[MAX_CLIENT];
    private List<Socket> Clients = new ArrayList<Socket>();
    private int nbClients=0;

     public Server(int pport)
     {
       port=pport;
       System.out.println("[*] Starting server on :"+port);

       try
       {

        server = new ServerSocket(port);
        System.out.println("Socket done");
        }
      catch (UnknownHostException e)
      {
        e.printStackTrace();
      }
      catch (IOException e)
       {
        e.printStackTrace();
      }
     }

     public void AcceptClient()
     {
       try
       {
                System.out.println("[*] Waiting for clients ...");
                Socket client = server.accept();
                Clients.add(client);
                nbClients+=1;
                System.out.println("[*] Client connected");
        }
      catch (IOException e)
        {
            e.printStackTrace();
        }

      }
}
