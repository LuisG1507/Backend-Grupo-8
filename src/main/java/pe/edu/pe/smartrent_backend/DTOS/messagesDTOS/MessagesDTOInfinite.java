package pe.edu.pe.smartrent_backend.DTOS.messagesDTOS;

import java.time.LocalDate;

public class MessagesDTOInfinite {
    private String content;
    private String status;
    private LocalDate dateSent;
    private ConversationBasicDTO conversation;
    private UserBasicDTO user;

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

    public ConversationBasicDTO getConversation() {
        return conversation;
    }

    public void setConversation(ConversationBasicDTO conversation) {
        this.conversation = conversation;
    }

    public UserBasicDTO getUser() {
        return user;
    }

    public void setUser(UserBasicDTO user) {
        this.user = user;
    }

    public static class UserBasicDTO {
        private Integer idUser;
        private String name;
        private String lastName;
        private String username;
        private String profilePhoto;
        private String phoneNumber;

        public Integer getIdUser() {
            return idUser;
        }

        public void setIdUser(Integer idUser) {
            this.idUser = idUser;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getProfilePhoto() {
            return profilePhoto;
        }

        public void setProfilePhoto(String profilePhoto) {
            this.profilePhoto = profilePhoto;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
    }

    public static class ConversationBasicDTO {
        private Integer idConversation;

        public Integer getIdConversation() {
            return idConversation;
        }

        public void setIdConversation(Integer idConversation) {
            this.idConversation = idConversation;
        }
    }

}
