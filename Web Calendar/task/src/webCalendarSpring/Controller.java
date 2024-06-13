package webCalendarSpring;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class Controller {
    private final EventRepository eventRepository;

    public Controller(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping("/event")
    public ResponseEntity<List<EventDTO>> getEvents(@RequestParam(required = false) String start_time,
                                                    @RequestParam(required = false) String end_time) {
        List<Event> eventList;

        if(start_time != null && end_time != null) {
            LocalDate start = LocalDate.parse(start_time);
            LocalDate end = LocalDate.parse(end_time);
            eventList = eventRepository.findByDateBetween(start, end);
        } else {
            eventList = eventRepository.findAll();
        }

        if(eventList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<EventDTO> allEventDTOs = eventList.stream()
                .map(event -> new EventDTO(event.getId(), event.getEvent(), event.getDate()))
                .toList();

        return new ResponseEntity<>(allEventDTOs, HttpStatus.OK);
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable("id") int id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()) {
            throw new IdNotFoundException("The event doesn't exist!");
        }
        return new ResponseEntity<>(new EventDTO(
                event.get().getId(),
                event.get().getEvent(),
                event.get().getDate()),
                HttpStatus.OK);
    }

    @GetMapping("/event/today")
    public ResponseEntity<List<EventDTO>> getTodayEvents() {
        LocalDate today = LocalDate.now();
        List<Event> todayEvents = eventRepository.findByDate(today);
        List<EventDTO> todayEventDTOs = todayEvents.stream()
                .map(event -> new EventDTO(event.getId(), event.getEvent(), event.getDate()))
                .toList();
        return new ResponseEntity<>(todayEventDTOs, HttpStatus.OK);
    }

    @PostMapping("/event")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        if(event.getDate() == null || event.getEvent() == null || Objects.equals(event.getEvent().trim(), "")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        event.setMessage("The event has been added!");
        eventRepository.save(event);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @DeleteMapping("/event/{id}")
    public ResponseEntity<EventDTO> deleteEvent(@PathVariable("id") int id) {
        Optional<Event> event = eventRepository.findById(id);
        if(event.isEmpty()) {
            throw new IdNotFoundException("The event doesn't exist!");
        }
        eventRepository.delete(event.get());
        return new ResponseEntity<>(new EventDTO(
                event.get().getId(),
                event.get().getEvent(),
                event.get().getDate()),
                HttpStatus.OK);
    }

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<CustomErrorMessage> handleIdNotFound(IdNotFoundException e) {
        CustomErrorMessage body = new CustomErrorMessage(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
