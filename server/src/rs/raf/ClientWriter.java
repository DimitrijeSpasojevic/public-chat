package rs.raf;

import java.io.PrintWriter;

public class ClientWriter {
    private String name;
    private PrintWriter printWriter;


    public ClientWriter(String name, PrintWriter printWriter) {
        this.name = name;
        this.printWriter = printWriter;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public String getName() {
        return name;
    }
}
