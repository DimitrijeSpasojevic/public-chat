package rs.raf;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static final int PORT = 9001;

    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        String msgFromServer;
        String ime = null;
        try {
            socket = new Socket("127.0.0.1", PORT);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            Scanner scanner = new Scanner(System.in);
            msgFromServer = in.readLine();

            while (!msgFromServer.equals("ACCEPT")) {
                System.out.println("[server]: " + msgFromServer);
                ime = scanner.nextLine();
                out.println(ime);
                msgFromServer = in.readLine();
            }
            msgFromServer = in.readLine();
            System.out.println(msgFromServer);

            ClientReader clientReader = new ClientReader(in);
            Thread thread = new Thread(clientReader);
            thread.start();

            while (true){
                System.out.println("Unesite poruku!");
                String msg = scanner.nextLine();
                out.println(msg);
                if (msg.equals("KRAJ"))
                    break;
                System.out.println("poslata poruka!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Zatovrena konekcija [" + ime + "]");
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (out != null) {
                out.close();
            }

            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
