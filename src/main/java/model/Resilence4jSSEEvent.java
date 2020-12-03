package model;

public class Resilence4jSSEEvent {

    public Resilence4jSSEEvent(String Id, String event, Resilience4jCBEvent data){
        this.data = data;
        this.event = event;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Resilience4jCBEvent getData() {
        return data;
    }

    public void setData(Resilience4jCBEvent data) {
        this.data = data;
    }

    private String id;
    private String event;
    private Resilience4jCBEvent data;

    @Override
    public String toString() {
        return "Resilence4jSSEEvent{" +
                "id='" + id + '\'' +
                ", event='" + event + '\'' +
                ", data=" + data +
                '}';
    }
}
