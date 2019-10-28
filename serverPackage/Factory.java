package serverPackage;

import java.util.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class Factory{

  private String buffer;
  private Socket cl;
  private Server ser;
  private int BUFFER_FILE_SIZE=4096;

  public Factory(Server pser, Socket pcl, String pbuffer)
  {
    buffer = pbuffer;
    pser = ser;
    cl = pcl;
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
    File f = new File("musics");
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
        File f = new File("musics/"+ filename);
        boolean test = f.exists();

      if (test)
      {
        InputStream in = null;
        OutputStream out = null;

        long size = f.length();
        System.out.println("Size : "+size);
        in = new FileInputStream("musics/"+filename);
        System.out.println("Opened");

        ser.send(cl,"OK"); // We continue

        try
        {
          out = cl.getOutputStream();
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
		        	out.write(buf, 0, count);
	        	}
	        	catch(Exception e)
	        	{
	        		System.out.println("Error during the transfer");
	        		e.printStackTrace();
	        		break;
	        	}
	        }
        try
	        {
	        	out.close();
		        in.close();
	        }
	        catch(Exception e)
	        {
	        	System.out.print("Error while closing the stream");
	        	e.printStackTrace();
	        }
      }
      else
      {
        ser.send(cl,"ERROR");
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
