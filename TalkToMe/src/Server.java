import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    private ServerSocket ss;
    private final Set<ClientHandler> clients = Collections.synchronizedSet(new HashSet<>());

    public Server(int port) {
        try {
            ss = new ServerSocket(port);
            System.out.println("Server started with IP: " + Inet4Address.getLocalHost().getHostAddress() + " on port: " + port);

            while (true) {
                Socket socket = ss.accept();
                System.out.println("New client connected: " + socket.getLocalAddress());

                ClientHandler handler = new ClientHandler(socket);
                clients.add(handler);
                new Thread(handler).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server(8080);
    }

    class ClientHandler implements Runnable {
        private final Socket socket;
        private DataInputStream in;
        private DataOutputStream out;
        private String username = "Unknown";

        public ClientHandler(Socket socket) {
            this.socket = socket;
            try {
                in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                out = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                System.out.println("Error setting up streams: " + e.getMessage());
            }
        }

        public void run() {
            String message;
            try {
                while (true) {

                    message = in.readUTF();
                    String[] parts = message.split("\\|", 2);
                    if (parts.length == 2) {
                        username = parts[0];
                        if (username.equals("join")) {
                            username = parts[1];
                            System.out.println("Client joined: " + username);
                            synchronized (clients) {
                                for (ClientHandler client : clients) {
                                    this.sendMessage(client, username + " has joined the chat.");
                                }
                            }
                            continue;
                        }
                        String text = parts[1];

                        synchronized (clients) {
                            for (ClientHandler client : clients) {
                                if (client != this) this.sendMessage(client, username + ": " + text);
                            }
                        }
                    } else System.out.println("Broken or empty message: " + message);
                }
            } catch (IOException e) {
                System.out.println("Client disconnected: " + socket.getInetAddress());
            } finally {
                try {
                    in.close();
                    out.close();
                    socket.close();
                    clients.remove(this);
                    synchronized (clients) {
                        for (ClientHandler client : clients) {
                            this.sendMessage(client, username + " has left the chat.");
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Error closing connection: " + e.getMessage());
                }
            }
        }

        private void sendMessage(ClientHandler client, String msg) {
            try {
                client.out.writeUTF(msg);
            } catch (IOException e) {
                System.out.println("Error sending message to " + client.username + ": " + e.getMessage());
            }
        }
    }
}
