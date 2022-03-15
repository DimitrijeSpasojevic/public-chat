package rs.raf;

import java.time.LocalDateTime;

public class Message {

    private LocalDateTime time;
    private String name;
    private String content;


    public Message(LocalDateTime time, String name, String content) {
        this.time = time;
        this.name = name;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "time=" + time +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                "}";
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
