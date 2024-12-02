package pl.meksu.rentcar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.meksu.rentcar.dto.OfferDTO;
import pl.meksu.rentcar.dto.ReservationDTO;
import pl.meksu.rentcar.dto.UserReservationDTO;
import pl.meksu.rentcar.models.Reservation;
import pl.meksu.rentcar.models.User;
import pl.meksu.rentcar.models.Offer;
import pl.meksu.rentcar.repo.ReservationRepository;
import pl.meksu.rentcar.repo.UserRepository;
import pl.meksu.rentcar.repo.OfferRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OfferRepository offerRepository;

    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Reservation getReservationById(int id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

    public List<UserReservationDTO> getReservationsByUserId(int userId) {
        return reservationRepository.findByUserId(userId).stream()
                .map(this::convertToUserReservationDTO)
                .collect(Collectors.toList());
    }

    public List<ReservationDTO> getReservationsByOfferId(int offerId) {
        LocalDate localDate = LocalDate.now();
        Date today = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return reservationRepository.findByOfferId(offerId).stream()
                .filter(reservation -> !reservation.getEndDate().before(today))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Reservation createReservation(ReservationDTO reservationDTO) {
        User user = userRepository.findById(reservationDTO.getUser())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Offer offer = offerRepository.findById(reservationDTO.getOffer())
                .orElseThrow(() -> new RuntimeException("Offer not found"));

        LocalDate startLocalDate = reservationDTO.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endLocalDate = reservationDTO.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long daysBetween = ChronoUnit.DAYS.between(startLocalDate, endLocalDate) + 1;

        double price;
        if (offer.getPromotion() != null) {
            price = (offer.getPrice() - offer.getPrice() * offer.getPromotion().getDiscountPercentage() / 100.0) * daysBetween;
        } else {
            price = offer.getPrice() * daysBetween;
        }


        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setOffer(offer);
        reservation.setPrice(price);
        reservation.setStartDate(reservationDTO.getStartDate());
        reservation.setEndDate(reservationDTO.getEndDate());
        reservation.setPaymentStatus("Niezapłacona");

        return reservationRepository.save(reservation);
    }

    public void deleteReservation(int id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Reservation not found");
        }
    }

    private ReservationDTO convertToDTO(Reservation reservation) {
        int offerId = reservation.getOffer().getId();
        int userId = reservation.getUser().getId();
        Date startDate = reservation.getStartDate();
        Date endDate = reservation.getEndDate();

        return new ReservationDTO(userId, offerId, startDate, endDate);
    }

    private UserReservationDTO convertToUserReservationDTO(Reservation reservation) {
        OfferDTO offerDTO = convertToDTO(reservation.getOffer());
        return new UserReservationDTO(
               reservation.getId(),
               offerDTO,
               reservation.getStartDate(),
               reservation.getEndDate(),
               reservation.getPrice(),
               reservation.getPaymentStatus()
        );
    }

    private OfferDTO convertToDTO(Offer offer) {
        double price = offer.getPrice();
        boolean hasPromotion = offer.getPromotion() != null;
        double oldPrice = 0.0;

        if (hasPromotion) {
            oldPrice = price;
            price = price - (price * offer.getPromotion().getDiscountPercentage() / 100.0);
        }

        return new OfferDTO(
                offer.getId(),
                offer.getCar(),
                price,
                offer.getDescription(),
                oldPrice
        );
    }
}
