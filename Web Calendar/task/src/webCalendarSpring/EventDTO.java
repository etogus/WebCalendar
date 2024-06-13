package webCalendarSpring;

import java.time.LocalDate;

public class EventDTO {

    private int id;
    private String event;
    private LocalDate date;

    public EventDTO(int id, String event, LocalDate date) {
        this.id = id;
        this.event = event;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
