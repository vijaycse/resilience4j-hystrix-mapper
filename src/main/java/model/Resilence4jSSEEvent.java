package model;

public class Resilence4jSSEEvent {

    public Resilence4jSSEEvent(String Id, String event, Resilence4jCBEvents data){
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

    public Resilence4jCBEvents getData() {
        return data;
    }

    public void setData(Resilence4jCBEvents data) {
        this.data = data;
    }

    private String id;
    private String event;
    private Resilence4jCBEvents data;

    @Override
    public String toString() {
        return "Resilence4jSSEEvent{" +
                "id='" + id + '\'' +
                ", event='" + event + '\'' +
                ", data=" + data +
                '}';
    }
}
