package pl.meksu.rentcar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.meksu.rentcar.dto.MessageDTO;
import pl.meksu.rentcar.models.Message;
import pl.meksu.rentcar.models.User;
import pl.meksu.rentcar.services.MessageProducer;
import pl.meksu.rentcar.services.MessageService;
import pl.meksu.rentcar.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private final MessageService messageService;

    @Autowired
    private final UserService userService;

    @Autowired
    private final MessageProducer messageProducer;

    public MessageController(MessageService messageService, UserService userService, MessageProducer messageProducer) {
        this.messageService = messageService;
        this.userService = userService;
        this.messageProducer = messageProducer;
    }

    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int id) {
        return ResponseEntity.ok(messageService.getMessageById(id));
    }

    @PostMapping
    public ResponseEntity<?> createMessage(@RequestBody MessageDTO messageDTO) {
        try {
            User user = userService.getUserById(messageDTO.getUser()).orElseThrow(() -> new RuntimeException("User not found"));
            Message message = new Message();
            message.setContent(messageDTO.getContent());
            message.setTitle(messageDTO.getTitle());
            message.setUser(user);
            message.setSentDate(messageDTO.getSentDate());
            Message createdMessage = messageService.createMessage(message);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable int id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}