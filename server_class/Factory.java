package server_class;

import java.util.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class Factory{

  private String buffer;
  private Socket sock;

  public Factory(Socket sock,String pbuffer)
  {
    buffer=pbuffer;
    System.out.println(pbuffer);
    this.compare();
  }

  public void compare()
  {
    String[] arrayBuffer = buffer.split(",",5);

    switch (arrayBuffer[0]) // <command> <arg1> <args2> ...
    {
        case "download":
          download(sock, arrayBuffer[1]); // we send filename
          break;
        case "upload":
            upload(sock, arrayBuffer[1]);
            break;
        default:
            System.out.println(buffer);

    }
  }
  public void download(Socket sock, String filename)
  {
    try
    {
      System.out.println("Filename : "+ filename);
      File f = new File("..\\musics\\"+ filename);

      if (f.exists())
      {
        System.out.println("Opened");
      }
    }
    catch (Exception e)
    {
      System.out.println("Can't recv filename");
      e.printStackTrace();
    }
  }
  public void upload(Socket sock, String filename){}

}
