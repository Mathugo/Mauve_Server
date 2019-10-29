package serverPackage;

import java.util.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class Server {

     private int port;
     private ServerSocket server = null;
     private Socket current_cl = null;

     private ArrayList<ClientRun> Clients = new ArrayList<ClientRun>();

     private String buffer;
     private boolean ServerISRunning = false;

     public Server(int pport) // Bind TCP Connection to a port
     {
       port=pport;
       ServerISRunning=true;
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
              current_cl=client;
              System.out.println("[*] Client connected");
        }
      catch (IOException e)
        {
            e.printStackTrace();
        }
      }

      public void removeClient(ClientRun cl)
      {
        System.out.println("[*] Removing client .."+cl.getClName());
        try
        {
          Iterator itr = Clients.iterator();
          while (itr.hasNext())
            {
              ClientRun c = (ClientRun)itr.next();
              if (c == cl )
                itr.remove();
            }
            cl.getSock().close();
          System.out.println("[*] Done");
        }
        catch(Exception e)
        {
          System.out.println("[!] Error can't remove client");
          e.printStackTrace();
        }
      }

      // GETTERS
      public ArrayList<ClientRun> getClients()
      {
        return Clients;
      }

      public void start()
      {
            while (ServerISRunning) // While server on we accept client and launch thread
            {
                  this.AcceptClient();
                  System.out.println("[!] Creating thread ..");
                  ClientRun cl = new ClientRun(current_cl,this); // We create object ClienRun which extends class Thread
                  Clients.add(cl);
                  System.out.println("[*] Done");
                  cl.start();
                  System.out.println("[*] Client "+cl.getClName()+" started");
            }
          this.close_all();
      }

      public void close_all()
      {
        try{
          for (int i = 0; i < Clients.size(); i++)
          {
              Clients.get(i).getSock().close(); // We close connection
              Clients.get(i).stop(); // We stop the thread
          }
          server.close();
        }catch (Exception e)
        {
          System.out.println("[!] Can't close the server");
          e.printStackTrace();
        }

      }



}
