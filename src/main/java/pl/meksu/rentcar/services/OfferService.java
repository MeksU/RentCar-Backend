package pl.meksu.rentcar.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.meksu.rentcar.dto.CreateOfferDTO;
import pl.meksu.rentcar.dto.OfferDTO;
import pl.meksu.rentcar.models.Car;
import pl.meksu.rentcar.models.Offer;
import pl.meksu.rentcar.repo.CarRepository;
import pl.meksu.rentcar.repo.OfferRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OfferService {

    private final OfferRepository offerRepository;
    private final CarRepository carRepository;

    public OfferService(OfferRepository offerRepository, CarRepository carRepository) {
        this.offerRepository = offerRepository;
        this.carRepository = carRepository;
    }

    public List<OfferDTO> getAllOffers() {
        return offerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public OfferDTO getOfferById(int id) {
        Offer offer = offerRepository.findById(id).orElseThrow(() -> new RuntimeException("Offer not found"));
        return convertToDTO(offer);
    }

    public CreateOfferDTO createOffer(CreateOfferDTO offerDTO) {
        Car car = carRepository.findById(offerDTO.getCarId())
                .orElseThrow(() -> new RuntimeException("Car not found"));

        Offer offer = new Offer();
        offer.setCar(car);
        offer.setPrice(offerDTO.getPrice());
        offer.setDescription(offerDTO.getDescription());
        offer.setPromotion(null);

        Offer savedOffer = offerRepository.save(offer);

        return new CreateOfferDTO(
                offer.getCar().getId(),
                savedOffer.getPrice(),
                savedOffer.getDescription()
        );
    }

    public void deleteOffer(int id) {
        if (offerRepository.existsById(id)) {
            offerRepository.deleteById(id);
        } else {
            throw new RuntimeException("Offer not found");
        }
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
