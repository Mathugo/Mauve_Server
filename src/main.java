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
  // Server rooted normaly on port 32890
  public static void main(String[] args)
  {
    int port=433; // default port

    if (args.length == 1)
    {
      port = Integer.parseInt(args[0]);
    }

    Server ser = new Server(port);

  }

}
