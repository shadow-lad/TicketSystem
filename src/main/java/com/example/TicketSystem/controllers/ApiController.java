package com.example.TicketSystem.controllers;

import com.example.TicketSystem.dao.MovieDAO;
import com.example.TicketSystem.dao.TimingDAO;
import com.example.TicketSystem.entities.Movie;
import com.example.TicketSystem.entities.Timings;
import com.example.TicketSystem.payload.request.BuyTicketsRequest;
import com.example.TicketSystem.payload.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ApiController {

    private static final long MOVIE_INTERVAL = 15 * 60 * 100;

    @Autowired
    MovieDAO movieDAO;

    @Autowired
    TimingDAO timingDAO;

    @GetMapping("/movies")
    public ResponseEntity<?> getMovies() {
        return ResponseEntity.ok(movieDAO.findAll());
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<?> getMovies(@PathVariable Long id) {
        Optional<Movie> movie = movieDAO.findById(id);
        return ResponseEntity.of(movie);
    }

    @GetMapping("/times")
    public ResponseEntity<?> getTimings() {
        List<Timings> timings = timingDAO.findAll();
        return ResponseEntity.ok(timings);
    }

    @PostMapping(value = "/movies", consumes = "application/json")
    public ResponseEntity<?> addMovie(@Valid @RequestBody Movie movie) {
        Movie savedMovie = movieDAO.save(movie);
        return ResponseEntity.ok(savedMovie);
    }

    @PostMapping(value = "/movies/{id}/time")
    public ResponseEntity<?> addTime(@PathVariable Long id, @Valid @RequestBody Timings time) {
        Optional<Movie> movieOpt = movieDAO.findById(id);

        if (movieOpt.isPresent()) {
            Movie movie = movieOpt.get();
            long startTime = time.getStartTime();
            long endTime = time.getEndTime();

            if (endTime - startTime != movie.getDuration()) {
                return ResponseEntity.badRequest().body(new MessageResponse("The duration of the movie is " +
                        "" + movie.getDuration() + " and not " + (endTime - startTime)));
            }

            Optional<Timings> startTimeOptional = timingDAO.findByStartTimeBetween(endTime - MOVIE_INTERVAL, endTime);
            Optional<Timings> endTimeOptional = timingDAO.findByEndTimeBetween(startTime, startTime + MOVIE_INTERVAL);

            if (startTimeOptional.isPresent() || endTimeOptional.isPresent()) {
                return ResponseEntity.badRequest().body(new MessageResponse("That slot is not free"));
            } else {
                movie.setTimings(time);
                timingDAO.save(time);
                movieDAO.save(movie);
                return ResponseEntity.ok(new MessageResponse("Successful"));
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/times/{id}/buy")
    public ResponseEntity<?> buyTickets(@PathVariable Long id, @Valid @RequestBody BuyTicketsRequest buyTickets) {
        Optional<Timings> timings = timingDAO.findById(id);

        if (timings.isPresent()) {
            Timings time = timings.get();

            int available = time.getNumberOfTickets();
            if (available >= buyTickets.getNoOfTickets()) {
                available -= buyTickets.getNoOfTickets();
                time.setNumberOfTickets(available);
                timingDAO.save(time);
                return ResponseEntity.ok(new MessageResponse("Tickets booked"));
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("There are only " + available + " ticket(s) available"));
            }

        } else {
            return ResponseEntity.notFound().build();
        }

    }

}
