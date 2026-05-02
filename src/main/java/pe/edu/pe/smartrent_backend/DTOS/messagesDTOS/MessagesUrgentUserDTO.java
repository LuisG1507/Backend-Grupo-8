package pe.edu.pe.smartrent_backend.DTOS.messagesDTOS;

public class MessagesUrgentUserDTO {
    private String name;
    private String lastName;
    private Long urgentMessages;

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

    public Long getUrgentMessages() {
        return urgentMessages;
    }

    public void setUrgentMessages(Long urgentMessages) {
        this.urgentMessages = urgentMessages;
    }
}
