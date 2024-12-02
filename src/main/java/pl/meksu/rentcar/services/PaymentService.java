package pl.meksu.rentcar.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.meksu.rentcar.dto.PaymentDTO;
import pl.meksu.rentcar.models.Payment;
import pl.meksu.rentcar.models.Reservation;
import pl.meksu.rentcar.repo.PaymentRepository;
import pl.meksu.rentcar.repo.ReservationRepository;

import java.util.Date;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;

    public PaymentService(PaymentRepository paymentRepository, ReservationRepository reservationRepository) {
        this.paymentRepository = paymentRepository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public Payment addPaymentToReservation(PaymentDTO paymentDTO) {
        Reservation reservation = reservationRepository.findById(paymentDTO.getReservationId())
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found for reservationId: " + paymentDTO.getReservationId()));

        Payment payment = new Payment();
        payment.setReservation(reservation);
        payment.setPaymentNumber(paymentDTO.getPaymentNumber());

        payment.setPaymentDate(new Date());

        reservation.setPaymentStatus("Zapłacona");
        reservationRepository.save(reservation);

        return paymentRepository.save(payment);
    }
}
