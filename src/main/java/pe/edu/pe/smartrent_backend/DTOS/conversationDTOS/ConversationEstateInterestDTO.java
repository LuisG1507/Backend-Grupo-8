package pe.edu.pe.smartrent_backend.DTOS.conversationDTOS;

public class ConversationEstateInterestDTO {
    private String title;
    private String city;
    private Long totalConversations;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getTotalConversations() {
        return totalConversations;
    }

    public void setTotalConversations(Long totalConversations) {
        this.totalConversations = totalConversations;
    }
}
