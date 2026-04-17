package pe.edu.pe.smartrent_backend.Entities;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Messages")
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMessage;

    @Column(name = "content", length = 1000, nullable = false)
    private String content;

    @Column(name = "status", length = 50, nullable = false)
    private String status;

    @Column(name = "dateSent", nullable = false)
    private LocalDate dateSent;

    @ManyToOne
    @JoinColumn(name = " idConversation")
    private Conversation conversation;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private Users user;

    public Messages() {
    }

    public Messages(Integer idMessage, String content, String status, LocalDate dateSent, Conversation conversation, Users user) {
        this.idMessage = idMessage;
        this.content = content;
        this.status = status;
        this.dateSent = dateSent;
        this.conversation = conversation;
        this.user = user;
    }

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
