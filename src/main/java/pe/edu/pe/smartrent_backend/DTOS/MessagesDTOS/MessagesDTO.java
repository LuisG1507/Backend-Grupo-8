package pe.edu.pe.smartrent_backend.DTOS.MessagesDTOS;

import pe.edu.pe.smartrent_backend.Entities.Conversation;
import pe.edu.pe.smartrent_backend.Entities.Users;

import java.time.LocalDate;

public class MessagesDTO {

    private Integer idMessage;
    private String content;
    private String status;
    private LocalDate dateSent;
    private Conversation conversation;
    private Users user;

    public Integer getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(Integer idMessage) {
        this.idMessage = idMessage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDateSent() {
        return dateSent;
    }

    public void setDateSent(LocalDate dateSent) {
        this.dateSent = dateSent;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
