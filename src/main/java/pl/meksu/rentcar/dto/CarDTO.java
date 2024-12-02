package pl.meksu.rentcar.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CarDTO {
    private String brand;
    private String model;
    private MultipartFile image;
    private String engine;
    private String fuelType;
    private String transmission;
    private int power;
    private String type;
    private int seats;
}
