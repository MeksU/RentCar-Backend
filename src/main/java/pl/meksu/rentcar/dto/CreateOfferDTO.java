package pl.meksu.rentcar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOfferDTO {
    private int carId;
    private double price;
    private String description;
}
