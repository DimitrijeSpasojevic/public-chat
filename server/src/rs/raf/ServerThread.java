package rs.raf;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

public class ServerThread implements Runnable {

    private Socket socket;
    private String msgFromClient;
    private ServerMain serverMain;
    public ServerThread(Socket socket, ServerMain main) {
        this.socket = socket;
        this.serverMain = main;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            out.println("Unesite korisnicko ime");
            msgFromClient = in.readLine();
            while (serverMain.getUsers().contains(msgFromClient)) {
                out.println("Zauzeto, unesite drugo");
                msgFromClient = in.readLine();
            }
            serverMain.getUsers().add(msgFromClient);
            ClientWriter clientWriter = new ClientWriter(msgFromClient,out);
            serverMain.getClientWriters().add(clientWriter);
            out.println("ACCEPT");
            out.println("Poruka dobrodoslice");
            out.println(serverMain.getMessages());

            for(ClientWriter writer : serverMain.getClientWriters()){
                if(writer!=clientWriter)
                    writer.getPrintWriter().println("Konektovao se novi korisnik [" + clientWriter.getName() + "]");
            }

            while (true){
                String msg = in.readLine();
                if(serverMain.getCensoredWords().contains(msg)){
                    msg = censor(msg);
                }
                if(serverMain.getMessages().size() >= 100){
                    serverMain.getMessages().remove(0);
                }
                if(msg == null){
                    break;
                }
                if(msg.equals("KRAJ")){
                    clientWriter.getPrintWriter().println(msg);
                    break;
                }
                serverMain.getMessages().add(new Message(LocalDateTime.now(),msgFromClient,msg));
                for(ClientWriter writer : serverMain.getClientWriters()){
                    if(writer!=clientWriter)
                        writer.getPrintWriter().println(serverMain.getMessages());
                }
            }

        } catch (IOException e) {
            System.out.println("IO exception");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    System.out.println("Neuspelo zatvaranje in-a");

                }
            }

            if (out != null) {
                out.close();
            }

            if (this.socket != null) {
                try {
                    socket.close();
                    System.out.println("Zatvorena konekcija sa " + msgFromClient);
                } catch (IOException e) {
                    System.out.println("Nije uspelo zatvarnje sa " + msgFromClient);
                    e.printStackTrace();
                }
            }

        }
    }

    private String censor(String msg) {
        String censored = "";
        Character first = msg.charAt(0);
        Character last = msg.charAt(msg.length()-1);
        censored += String.valueOf(first);
        for (int i = 1; i < msg.length()-1 ; i++) {
            censored += "*";
        }
        censored += String.valueOf(last);
        return censored;
    }
}
