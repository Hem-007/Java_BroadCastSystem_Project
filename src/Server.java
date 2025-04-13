import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    private BufferedReader reader;
    private PrintWriter writer;
    private Socket clientSocket;
    private static final int PORT = 12345;
    private Set<ClientHandler> clientHandlerSet = new HashSet<>();

    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server Started...!");


            while (true) {
                clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                System.out.println("New Client Connected: " + clientHandler.getClientId());
                clientHandlerSet.add(clientHandler);
                new Thread(clientHandler).start();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            serverClose();
        }
    }

    void broadcastMessage(String message,ClientHandler sender){
        for (ClientHandler client: clientHandlerSet) {
            if(client != sender ){
                client.sendMessage(message);
            }
        }
    }
    void removeClient(ClientHandler client){
        clientHandlerSet.remove(client);
    }

    private void serverClose() {
        try {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (clientSocket != null) {
                clientSocket.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
