package hello.websocket;

public class Message {

//    private String from;
    private String name;

    public String getText() {
        return name;
    }

//    public String getFrom() {
//        return from;
//    }
//
//    public void setFrom(String from) {
//        this.from = from;
//    }

    public void setText(String name) {
        this.name = name;
    }

}
