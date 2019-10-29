package serverPackage;

import java.util.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class Factory{

  private String buffer="";
  private ClientRun cl=null;
  private Socket clSock=null;
  private int BUFFER_FILE_SIZE=4096;
  private String OriginPath="../src/musics/";

  public Factory(ClientRun pcl)
  {
    cl = pcl;
    buffer = cl.getBuffer();
    clSock=cl.getSock();
    this.compare();
  }

  public void compare()
  {
    String[] arrayBuffer = buffer.split(",",5);

    switch (arrayBuffer[0]) // <command> <arg1> <args2> ...
    {
        case "download":
          download(arrayBuffer[1]); // we send filename
          break;
        case "upload":
          upload(arrayBuffer[1]);
          break;
        case "list_musics":
          list_musics();
        default:
            System.out.println(buffer);
    }
  }

  public void list_musics()
  {
    File f = new File(OriginPath);
    FilenameFilter filter = new FilenameFilter()
    {
      @Override // Tell the compilator we extend accept() method
      public boolean accept(File f, String name)
      {
        return name.endsWith(".mp3");
      }
    };
    String[] files = f.list(filter);
    String file_str="";
    for (int i = 0; i < files.length; i++)
    {
      file_str=file_str+files[i]+" ";
    }
    System.out.println(file_str);
  }


  public void download(String filename)
  {
    try
    {
      System.out.println("Filename : "+ filename);
        FileInputStream fis = null;
        File f = new File(OriginPath + filename);
        boolean test = f.exists();

      if (test)
      {
        InputStream in = null;
        OutputStream out = null;

        long size = f.length();
        System.out.println("Size : "+size);
        in = new FileInputStream(OriginPath + filename);
        System.out.println("Opened");

        cl.send("OK"); // We continue

        try
        {
          out = clSock.getOutputStream();
        }
        catch (Exception e)
        {
          System.out.println("Error creating out stream");
          e.printStackTrace();
        }
        byte[] buf = new byte[BUFFER_FILE_SIZE];
        int count=1;

        while (count > 0)
	        {
	        	try
	        	{
		        	count = in.read(buf);
              if (count == -1) {break;} // To not reach the ArrayOutOfBounds Exception
		        	out.write(buf, 0, count);
              System.out.println("[*] Uploading ..");
	        	}
	        	catch(Exception e)
	        	{
	        		System.out.println("[!] Error during the transfer");
	        		e.printStackTrace();
	        		break;
	        	}
	        }
        try
	        {
	        	out.close();
		        in.close();
            System.out.println("[*] Done uploading : "+filename);
	        }
	        catch(Exception e)
	        {
	        	System.out.print("Error while closing the stream");
	        	e.printStackTrace();
	        }
      }
      else
      {
        cl.send("ERROR");
      }
    }
    catch (Exception e)
    {
      System.out.println("Can't recv filename");
      e.printStackTrace();
    }
  }


  public void upload(String filename){}



















}
