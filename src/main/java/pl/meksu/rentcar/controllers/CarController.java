package pl.meksu.rentcar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.meksu.rentcar.dto.CarDTO;
import pl.meksu.rentcar.models.Car;
import pl.meksu.rentcar.services.CarService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping
    public ResponseEntity<String> addCar(@ModelAttribute CarDTO carDTO) {
        try {
            carService.addCarWithDetails(carDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Car added successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = carService.getAllCars();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/images/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) {
        try {
            Path imagePath = Paths.get("images/" + fileName);
            byte[] imageBytes = Files.readAllBytes(imagePath);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(imageBytes);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private String generateUniqueFileName(String originalFilename) {
        String timeStamp = String.valueOf(System.currentTimeMillis());
        return timeStamp + "_" + originalFilename;
    }
}