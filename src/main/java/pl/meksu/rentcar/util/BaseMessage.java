package pl.meksu.rentcar.util;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import pl.meksu.rentcar.models.Message;

@Data
@JsonDeserialize
public class BaseMessage {
    private String type;

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = ResetPasswordMessage.class, name = "reset_code"),
            @JsonSubTypes.Type(value = Message.class, name = "contact")
    })
    private Object data;
}