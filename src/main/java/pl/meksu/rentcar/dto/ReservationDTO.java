package pl.meksu.rentcar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private int user;
    private int offer;
    private Date startDate;
    private Date endDate;
}