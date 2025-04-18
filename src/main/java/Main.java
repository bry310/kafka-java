import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Main {
  public static void main(String[] args){
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.err.println("Logs from your program will appear here!");

    // Uncomment this block to pass the first stage
    // 
     ServerSocket serverSocket = null;
     Socket clientSocket = null;
     int port = 9092;
     try {
       serverSocket = new ServerSocket(port);
       // Since the tester restarts your program quite often, setting SO_REUSEADDR
       // ensures that we don't run into 'Address already in use' errors
       serverSocket.setReuseAddress(true);
       // Wait for connection from client.
       clientSocket = serverSocket.accept();

       OutputStream out = clientSocket.getOutputStream();
       InputStream in = clientSocket.getInputStream();

       var message_size = in.readNBytes(4);
       var request_api_key = in.readNBytes(2);
       var request_api_version = in.readNBytes(2);
       var correlation_id = in.readNBytes(4);
       KafkaRespanseMessage kafkaRespanseMessage = new KafkaRespanseMessage(
               message_size,
               correlation_id
       );


       out.write(kafkaRespanseMessage.message_size());
       out.write(kafkaRespanseMessage.correlation_id());
     } catch (IOException e) {
       System.out.println("IOException: " + e.getMessage());
     } finally {
       try {
         if (clientSocket != null) {
           clientSocket.close();
         }
       } catch (IOException e) {
         System.out.println("IOException: " + e.getMessage());
       }
     }
  }
}
