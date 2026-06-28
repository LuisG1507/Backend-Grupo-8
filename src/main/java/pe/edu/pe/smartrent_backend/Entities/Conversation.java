package pe.edu.pe.smartrent_backend.Entities;


import jakarta.persistence.*;

@Entity
@Table(name = "Conversation")
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idConversation;

    @ManyToOne
    @JoinColumn(name = "idUser1")
    private User user1;

    @ManyToOne
    @JoinColumn(name = "idUser2")
    private User user2;

    @ManyToOne
    @JoinColumn(name = "idEstate")
    private Estate estate;

    public Conversation() {
    }

    public Conversation(Integer idConversation, User user1, User user2, Estate estate) {
        this.idConversation = idConversation;
        this.user1 = user1;
        this.user2 = user2;
        this.estate = estate;
    }

    public Integer getIdConversation() {
        return idConversation;
    }

    public void setIdConversation(Integer idConversation) {
        this.idConversation = idConversation;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public Estate getEstate() {
        return estate;
    }

    public void setEstate(Estate estate) {
        this.estate = estate;
    }
}
