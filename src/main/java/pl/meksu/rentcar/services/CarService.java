package pl.meksu.rentcar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.meksu.rentcar.dto.CarDTO;
import pl.meksu.rentcar.models.Car;
import pl.meksu.rentcar.models.CarDetails;
import pl.meksu.rentcar.repo.CarDetailsRepository;
import pl.meksu.rentcar.repo.CarRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class CarService {

    private final String UPLOAD_DIR = "images/";

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarDetailsRepository carDetailsRepository;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car addCarWithDetails(CarDTO carDTO) throws IOException {

        if (carDTO.getImage() == null || carDTO.getImage().isEmpty()) {
            throw new IllegalArgumentException("Please select an image file to upload.");
        }
        String uniqueFileName = generateUniqueFileName(carDTO.getImage().getOriginalFilename());
        String filePath = UPLOAD_DIR + uniqueFileName;
        Path path = Paths.get(filePath);
        Files.write(path, carDTO.getImage().getBytes());

        Car car = new Car();
        car.setBrand(carDTO.getBrand())
                .setModel(carDTO.getModel())
                .setImage(uniqueFileName);

        car = carRepository.save(car);

        CarDetails carDetails = new CarDetails();
        carDetails.setEngine(carDTO.getEngine())
                .setPower(carDTO.getPower())
                .setType(carDTO.getType())
                .setSeats(carDTO.getSeats())
                .setTransmission(carDTO.getTransmission())
                .setFuelType(carDTO.getFuelType())
                .setCar(car);

        carDetails = carDetailsRepository.save(carDetails);

        car.setCarDetails(carDetails);
        carRepository.save(car);

        return car;
    }


    private String generateUniqueFileName(String originalFilename) {
        String timeStamp = String.valueOf(System.currentTimeMillis());
        return timeStamp + "_" + originalFilename;
    }
}
