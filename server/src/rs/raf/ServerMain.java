package rs.raf;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class ServerMain {

    public static final int PORT = 9001;
    private List<String> censoredWords = new ArrayList<>();
    private List<Message> messages = new CopyOnWriteArrayList<>();
    private  List<String> users = new CopyOnWriteArrayList<>();
    private List<ClientWriter> clientWriters = new CopyOnWriteArrayList<>();

    public ServerMain() throws Exception{
        censoredWords.add("rat");
        censoredWords.add("korona");
        try {
            ServerSocket serverSocket = new ServerSocket(ServerMain.PORT);
            while (true) {
                System.out.println("Server ocekuje konekcije");
                Socket socket = serverSocket.accept();
                System.out.println("Server primio konekciju");
                Thread serverThread = new Thread(new ServerThread(socket, this));
                serverThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        try {
            new ServerMain();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ClientWriter> getClientWriters() {
        return clientWriters;
    }

    public List<String> getCensoredWords() {
        return censoredWords;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public List<String> getUsers() {
        return users;
    }
}
