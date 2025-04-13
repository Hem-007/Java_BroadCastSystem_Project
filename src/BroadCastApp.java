public class BroadCastApp {
    public static void main(String[] var0) {
        if (var0.length < 1) {
            System.out.println("Usage : [server]-starts server \n\t\t[connect]-starts new client");
        } else {
            switch (var0[0]) {
                case "server":
                    (new Server()).startServer();
                    break;
                case "connect":
                    (new Thread(new Client())).start();
                    break;
                default:
                    System.out.println("Invalid command. Use 'server' or 'connect'.");
            }

        }
    }
}
