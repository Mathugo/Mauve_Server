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

     private ArrayList<Socket> Clients = new ArrayList<Socket>();
     private ArrayList<Thread> recv_th = new ArrayList<Thread>();
  //   private ArrayList<Thread> send_th = new ArrayList<Thread>();

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
              Clients.add(client);
              current_cl=client;
              System.out.println("[*] Client connected");
        }
      catch (IOException e)
        {
            e.printStackTrace();
        }
      }

    public void recv(Socket cl)
      {
        BufferedReader in = null;
        String cl_buffer="";
        try
        {
          in = new BufferedReader(
          new InputStreamReader(cl.getInputStream()));
        }
        catch(Exception e)
        {
            System.out.println("[!] Error getting input ..");
            e.printStackTrace();
        }

        while (cl_buffer != "EXIT" || cl_buffer !="ERROR")
        {
          try
          {
             cl_buffer = in.readLine();
             Factory fact = new Factory(this,cl,cl_buffer); //
          }
          catch(Exception e)
          {
            // Client is off, we remove it from the dynamic list
            System.out.println("[!] Error can't recv data from the client : " + cl.getInetAddress().getHostName());
   	    	   e.printStackTrace();
             this.removeClient(cl);
             break;
          }
        }
        try
          {
          in.close();
          }
        catch(Exception e)
          {
          System.out.println("[!] Error closing socket ..");
          e.printStackTrace();
          }
      }
      public void send(Socket cl,String sbuffer)
      {
        try{
          PrintStream out = new PrintStream(cl.getOutputStream());
          out.println(sbuffer);
        }
        catch(Exception e)
        {
          System.out.println("[!] Error can't send data to client " + cl.getInetAddress().getHostName());
          e.printStackTrace();
          this.removeClient(cl);
        }
      }

      public void removeClient(Socket cl)
      {
        System.out.println("[*] Removing client .."+cl.getInetAddress().getHostName());
        try
        {
          Iterator itr = Clients.iterator();
          while (itr.hasNext())
            {
              Socket s = (Socket)itr.next();
              if (s == cl )
                itr.remove();
            }
            cl.close();
          System.out.println("[*] Done");
        }
        catch(Exception e)
        {
          System.out.println("[!] Error can't remove client");
          e.printStackTrace();
        }
      }
      public void printBuffer()
      {
        System.out.println(buffer);
      }

      // GETTERS
      public ArrayList<Socket> getClients()
      {
        return Clients;
      }
      public String getBuffer()
      {
        return buffer;
      }

      public void start()
      {
            while (ServerISRunning)
            {
                  this.AcceptClient();
                  System.out.println("[!] Creating thread ..");
                  Thread t_recv = new Thread(new Runnable()
                  {
                    public void run()
                    {
                        recv(current_cl);
                    }
                  });
                  recv_th.add(t_recv);
                  System.out.println("[*] Done");
                  t_recv.start();
                  System.out.println("[*] Recv thread started");
            }
          this.close_all();
      }

      public void close_all()
      {
        try{
          server.close();
          for (int i = 0; i < Clients.size(); i++)
          {
              Clients.get(i).close();
          }
        }catch (Exception e)
        {
          System.out.println("[!] Can't close the server");
          e.printStackTrace();
        }
        try{
          for (int i = 0; i < recv_th.size() ; i++)
          {
            recv_th.get(i).stop();
          }
        }catch (Exception e)
        {
          System.out.println("[!] Can't close all threads");
          e.printStackTrace();
        }

      }



}