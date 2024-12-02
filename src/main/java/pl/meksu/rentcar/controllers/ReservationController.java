package pl.meksu.rentcar.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.meksu.rentcar.dto.ReservationDTO;
import pl.meksu.rentcar.dto.UserReservationDTO;
import pl.meksu.rentcar.models.Reservation;
import pl.meksu.rentcar.services.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable int id) {
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserReservationDTO>> getReservationsByUserId(@PathVariable int userId) {
        System.out.println("Siema");
        return ResponseEntity.ok(reservationService.getReservationsByUserId(userId));
    }

    @GetMapping("/offer/{offerId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByOfferId(@PathVariable int offerId) {
        return ResponseEntity.ok(reservationService.getReservationsByOfferId(offerId));
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDTO reservationDTO) {
        Reservation createdReservation = reservationService.createReservation(reservationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable int id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}