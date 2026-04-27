package pe.edu.pe.smartrent_backend.DTOS.conversationDTOS;

public class EstateConversationCountDTO {
    private String estateTitle;
    private Long conversationCount;

    public EstateConversationCountDTO() {}

    public EstateConversationCountDTO(String estateTitle, Long conversationCount) {
        this.estateTitle = estateTitle;
        this.conversationCount = conversationCount;
    }

    public String getEstateTitle() { return estateTitle; }
    public void setEstateTitle(String estateTitle) { this.estateTitle = estateTitle; }
    public Long getConversationCount() { return conversationCount; }
    public void setConversationCount(Long conversationCount) { this.conversationCount = conversationCount; }
}