import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {

    int PORT = 12345;
    String clientId;

    String serverAddress = "192.168.225.125";
    Socket serverSocket = null;
    BufferedReader reader = null;
    PrintWriter writer = null;
    Scanner input = new Scanner(System.in);

    @Override
    public void run() {


        try {
            serverSocket = new Socket(serverAddress, PORT);
            reader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            writer = new PrintWriter(serverSocket.getOutputStream(), true);
            clientId = serverSocket.getInetAddress().getHostName() +"-"+ serverSocket.getLocalPort();


            System.out.println(clientId +" Connected to the server...");

            Thread readThread = new Thread(this::readMessages);
            Thread writeThread = new Thread(this::writeMessages);

            readThread.start();
            writeThread.start();


        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
        }

        public void readMessages(){
            try {
                while (true) {
                    String serverMessage;
                    if ((serverMessage = reader.readLine()) != null) {
                        System.out.println(serverMessage);
                    }
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }finally {
                clientClose();
            }
        }

        public void writeMessages(){
            try {
                while (true) {
                    String message = input.nextLine();
                    if(message.equalsIgnoreCase("exit")){
                        System.out.println("Disconnected...!");
                        writer.println("exit");
                        clientClose();
                        break;
                    }
                    writer.println("Client-" + clientId + " : " + message);
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }finally {
                clientClose();
            }
        }

        void clientClose(){
            try {
                if (reader != null) reader.close();
                if (writer != null) writer.close();
                if (serverSocket != null) serverSocket.close();
                input.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
        }
    }
}
