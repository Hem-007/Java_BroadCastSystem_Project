import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    String clientId;
    Socket clientSocket;
    Server server;
    BufferedReader reader ;
    PrintWriter writer ;
    public ClientHandler(Socket clientSocket,Server server){
        this.server = server;
        this.clientSocket = clientSocket;
        clientId = clientSocket.getInetAddress().getHostName() + "-" + clientSocket.getPort();
    }
    @Override
    public void run() {

        try {
            reader =new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(),true);
            String message;
            while(true){

                if((message = reader.readLine()) != null){
                    if(message.equalsIgnoreCase("exit")){
                        System.out.println("Received from - " + clientId +" : " + "wants to exit...!");
                        System.out.println("Client-" + clientId + " : Disconnected..!");
                        server.broadcastMessage("Client-" + clientId + " : Disconnected..!",this);
                        server.removeClient(this);
                        break;
                    }
                    System.out.println("Received from - " + clientId +" : " +message);
                    server.broadcastMessage(message,this);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    void sendMessage(String message){
        if(writer != null){
            writer.println(message);
        }
    }
    public String getClientId(){return clientId;}
}
