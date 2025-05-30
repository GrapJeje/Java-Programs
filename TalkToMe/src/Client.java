import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket s;
    private DataInputStream in;
    private DataOutputStream out;

    public Client(String ip, int port) {
        try {
            s = new Socket(ip, port);
            System.out.println("Connected to server.");

            in = new DataInputStream(s.getInputStream());
            out = new DataOutputStream(s.getOutputStream());

            Scanner scanner = new Scanner(System.in);
            System.out.print("Vul je naam in: ");
            String name = scanner.nextLine();
            out.writeUTF("join|" + name);

            new Thread(() -> {
                try {
                    while (true) {
                        String message = in.readUTF();
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    System.out.println("Verbinding met server verloren.");
                }
            }).start();

            while (true) {
                String text = scanner.nextLine();
                out.writeUTF(name + "|" + text);
            }

        } catch (IOException ex) {
            System.out.println("Fout bij verbinden: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new Client("127.0.0.1", 8080);
    }
}
