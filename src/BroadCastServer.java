import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BroadCastServer implements Runnable {
    int PORT = 12345;
    String hostName = "192.168.225.125";
    ServerSocket serverSocket;
    BufferedReader reader;
    PrintWriter writer;
    Set<ClientHandler> clientSet = new HashSet<>();

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);

            System.out.println("Server Started......!");
            while (true) {

                Socket clientSocket = serverSocket.accept();
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                writer = new PrintWriter(clientSocket.getOutputStream(),true);
//                clientSet.add();
//                System.out.println("New Client Connected......! : Client-" + clientSet.size());

            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
