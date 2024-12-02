package pl.meksu.rentcar.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.meksu.rentcar.models.Offer;
import pl.meksu.rentcar.models.Promotion;
import pl.meksu.rentcar.repo.OfferRepository;
import pl.meksu.rentcar.repo.PromotionRepository;

@Service
@Transactional
public class PromotionService {

    private final PromotionRepository promotionRepository;
    private final OfferRepository offerRepository;

    public PromotionService(PromotionRepository promotionRepository, OfferRepository offerRepository) {
        this.promotionRepository = promotionRepository;
        this.offerRepository = offerRepository;
    }

    public Promotion createPromotion(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    public void assignPromotionToOffer(int offerId, int promotionId) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));

        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));

        offer.setPromotion(promotion);
        offerRepository.save(offer);
    }
}
