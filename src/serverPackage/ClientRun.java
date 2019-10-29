package serverPackage;

import java.util.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientRun extends Thread {

  private Socket sock=null;
  private Server ser=null;
  private BufferedReader in=null;
  private PrintStream out=null;
  private String buffer="";
  private String name="";

  public ClientRun(Socket psock,Server pser)
  {
    sock=psock;
    ser=pser;
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
  public String getClName(){return name;}

  public void recv()
  {
    while (buffer != "EXIT" || buffer != "ERROR")
    {
      try
      {
      buffer=in.readLine();
      Factory fact = new Factory(this);
      }
      catch(Exception e)
      {
        // Client is off, we remove it from the dynamic list
        System.out.println("[!] Error can't recv data from the client : " + sock.getInetAddress().getHostName());
        e.printStackTrace();
        ser.removeClient(this);
        break;
      }
    }
  }
  public void send(String pbuffer)
  {
      out.println(pbuffer);
  }

  @Override
  public void run()
  {
      this.recv();
  }

}
