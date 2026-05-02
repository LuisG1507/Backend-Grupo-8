package pe.edu.pe.smartrent_backend.DTOS.messagesDTOS;

public class MessagesUrgentConversationDTO {

    private Integer idConversation;
    private String estateTitle;
    private Long urgentMessages;

    public Integer getIdConversation() {
        return idConversation;
    }

    public void setIdConversation(Integer idConversation) {
        this.idConversation = idConversation;
    }

    public String getEstateTitle() {
        return estateTitle;
    }

    public void setEstateTitle(String estateTitle) {
        this.estateTitle = estateTitle;
    }

    public Long getUrgentMessages() {
        return urgentMessages;
    }

    public void setUrgentMessages(Long urgentMessages) {
        this.urgentMessages = urgentMessages;
    }
}
