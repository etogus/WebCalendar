package webCalendarSpring;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByDate(LocalDate localDate);
    List<Event> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
