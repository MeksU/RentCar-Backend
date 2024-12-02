package pl.meksu.rentcar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserReservationDTO {
    private int id;
    private OfferDTO offer;
    private Date startDate;
    private Date endDate;
    private Double price;
    private String paymentStatus;
}