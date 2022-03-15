package rs.raf;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientReader implements Runnable{

    private BufferedReader reader;

    public ClientReader(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        while (true){
            try {
                String fromServer = reader.readLine();
                if(fromServer.equals("KRAJ"))
                    break;
                System.out.println(fromServer);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("greska sa citanjem iz ClientReadera");
            }
        }
    }
}
