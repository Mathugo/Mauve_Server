package serverPackage;

import java.util.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientRun extends Thread {

  private Socket sock;
  private BufferedReader in;
  private PrintStream out;
  private String buffer;
  private String name;

  public ClientRun(Socket psock)
  {
    sock=psock;
    name=sock.getInetAddress().getHostName();
    try
    {
      out = new PrintStream(sock.getOutputStream());
      in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
    }
    catch(Exception e)
    {
      System.out.println("[!] Error while creating stream of : "+name);
      e.printStackTrace();
    }
  }
  public Socket getSock(){return sock;}
  public String getBuffer(){return buffer;}

  public void recv()
  {
    try
    {
    buffer=in.readLine();
    }
    catch(Exception e)
    {
      System.out.println("[!] Error getting input stream of : "+name);
    }
  //  Factory fact = new Factory(this,sock,buffer);
  }
  public void send(String pbuffer)
  {
      out.println(pbuffer);
  }
  @Override
  public void run()
  {
      recv();
  }

}
