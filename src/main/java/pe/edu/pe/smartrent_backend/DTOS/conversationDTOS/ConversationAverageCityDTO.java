package pe.edu.pe.smartrent_backend.DTOS.conversationDTOS;

public class ConversationAverageCityDTO {
    private String city;
    private Long totalConversations;
    private Long totalEstates;
    private Double average;

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

    public Long getTotalEstates() {
        return totalEstates;
    }

    public void setTotalEstates(Long totalEstates) {
        this.totalEstates = totalEstates;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }
}
