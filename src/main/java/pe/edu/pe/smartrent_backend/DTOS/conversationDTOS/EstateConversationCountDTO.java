package pe.edu.pe.smartrent_backend.DTOS.conversationDTOS;

public class EstateConversationCountDTO {
    private String estateTitle;
    private int conversationCount;

    public String getEstateTitle() {
        return estateTitle;
    }

    public void setEstateTitle(String estateTitle) {
        this.estateTitle = estateTitle;
    }

    public int getConversationCount() {
        return conversationCount;
    }

    public void setConversationCount(int conversationCount) {
        this.conversationCount = conversationCount;
    }
}