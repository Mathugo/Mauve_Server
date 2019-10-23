import server_class.*;
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

    int port=32889;
    String host = "127.0.0.1";
    Server ser = new Server(port,host);
    ser.AcceptClient();


  }

}
