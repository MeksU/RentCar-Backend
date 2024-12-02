package pl.meksu.rentcar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignPromotionDTO {
    private int offerId;
    private int promotionId;
}