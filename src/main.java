import serverPackage.*;

import java.util.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;

public class main
{
  public static void main(String[] args)
  {

    int port=32890;
    Server ser = new Server(port);
    ser.start();

  //  ser.recv(ser.getClients().get(0));
  //  ser.printBuffer();


  }

}
