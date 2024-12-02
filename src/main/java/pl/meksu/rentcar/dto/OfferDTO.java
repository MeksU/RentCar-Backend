package pl.meksu.rentcar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.meksu.rentcar.models.Car;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferDTO {
    private int id;
    private Car car;
    private double price;
    private String description;
    private double promotion;
}
