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
    private Users user1;

    @ManyToOne
    @JoinColumn(name = "idUser2")
    private Users user2;

    @ManyToOne
    @JoinColumn(name = "idEstate")
    private Estate estate;

    public Conversation() {
    }

    public Conversation(Integer idConversation, Users user1, Users user2, Estate estate) {
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

    public Users getUser1() {
        return user1;
    }

    public void setUser1(Users user1) {
        this.user1 = user1;
    }

    public Users getUser2() {
        return user2;
    }

    public void setUser2(Users user2) {
        this.user2 = user2;
    }

    public Estate getEstate() {
        return estate;
    }

    public void setEstate(Estate estate) {
        this.estate = estate;
    }
}
