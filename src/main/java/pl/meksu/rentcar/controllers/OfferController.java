package pl.meksu.rentcar.controllers;

import org.springframework.amqp.rabbit.annotation.Argument;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.meksu.rentcar.dto.AssignPromotionDTO;
import pl.meksu.rentcar.dto.CreateOfferDTO;
import pl.meksu.rentcar.dto.OfferDTO;
import pl.meksu.rentcar.models.Offer;
import pl.meksu.rentcar.models.Promotion;
import pl.meksu.rentcar.services.OfferService;
import pl.meksu.rentcar.services.PromotionService;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

    private final OfferService offerService;
    private final PromotionService promotionService;

    public OfferController(OfferService offerService, PromotionService promotionService) {
        this.offerService = offerService;
        this.promotionService = promotionService;
    }

    @GetMapping
    public ResponseEntity<List<OfferDTO>> getAllOffers() {
        return ResponseEntity.ok(offerService.getAllOffers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferDTO> getOfferById(@PathVariable int id) {
        return ResponseEntity.ok(offerService.getOfferById(id));
    }

    @PostMapping
    public ResponseEntity<CreateOfferDTO> createOffer(@RequestBody CreateOfferDTO offer) {
        CreateOfferDTO createdOffer = offerService.createOffer(offer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOffer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOffer(@PathVariable int id) {
        offerService.deleteOffer(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/promotion-create")
    public ResponseEntity<Promotion> createPromotion(@RequestBody Promotion promotion) {
        Promotion createdPromotion = promotionService.createPromotion(promotion);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPromotion);
    }

    @PutMapping("/promotion-assign")
    public ResponseEntity<?> assignPromotionToOffer(@RequestBody AssignPromotionDTO assignPromotion) {
        promotionService.assignPromotionToOffer(assignPromotion.getOfferId(), assignPromotion.getPromotionId());
        return ResponseEntity.ok().build();
    }
}
