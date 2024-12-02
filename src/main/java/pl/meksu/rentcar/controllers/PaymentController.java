package pl.meksu.rentcar.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.meksu.rentcar.dto.PaymentDTO;
import pl.meksu.rentcar.models.Payment;
import pl.meksu.rentcar.services.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/add")
    public ResponseEntity<Payment> addPaymentToReservation(@RequestBody PaymentDTO paymentDTO) {
        Payment payment = paymentService.addPaymentToReservation(paymentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }
}