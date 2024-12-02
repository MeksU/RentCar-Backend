package pl.meksu.rentcar.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MessageDTO {
    private String content;
    private String title;
    private int user;
    private Date sentDate;
}