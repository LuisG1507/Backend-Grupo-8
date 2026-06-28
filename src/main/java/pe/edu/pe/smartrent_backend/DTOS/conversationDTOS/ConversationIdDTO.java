package pe.edu.pe.smartrent_backend.DTOS.conversationDTOS;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import pe.edu.pe.smartrent_backend.Entities.Estate;
import pe.edu.pe.smartrent_backend.Entities.User;

public class ConversationIdDTO {
    private Integer idConversation;
    private UserD user1;
    private UserD user2;
    private EstateD estate;

    public Integer getIdConversation() {
        return idConversation;
    }

    public void setIdConversation(Integer idConversation) {
        this.idConversation = idConversation;
    }

    public UserD getUser1() {
        return user1;
    }

    public void setUser1(UserD user1) {
        this.user1 = user1;
    }

    public UserD getUser2() {
        return user2;
    }

    public void setUser2(UserD user2) {
        this.user2 = user2;
    }

    public EstateD getEstate() {
        return estate;
    }

    public void setEstate(EstateD estate) {
        this.estate = estate;
    }

    public static class UserD {
        private Integer idUser;

        public Integer getIdUser() {
            return idUser;
        }

        public void setIdUser(Integer idUser) {
            this.idUser = idUser;
        }
    }

    public static class EstateD {
        private Integer idEstate;

        public Integer getIdEstate() {
            return idEstate;
        }

        public void setIdEstate(Integer idEstate) {
            this.idEstate = idEstate;
        }
    }
}
