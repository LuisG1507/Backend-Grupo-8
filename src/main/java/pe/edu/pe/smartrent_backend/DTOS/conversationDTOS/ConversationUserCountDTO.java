package pe.edu.pe.smartrent_backend.DTOS.conversationDTOS;

public class ConversationUserCountDTO {
    private String name;
    private String lastName;
    private Long totalConversations;

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

    public Long getTotalConversations() {
        return totalConversations;
    }

    public void setTotalConversations(Long totalConversations) {
        this.totalConversations = totalConversations;
    }
}
