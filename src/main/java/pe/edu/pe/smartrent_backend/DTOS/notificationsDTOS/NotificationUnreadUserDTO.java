package pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS;

public class NotificationUnreadUserDTO {
    private String name;
    private String lastName;
    private Long pending;

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

    public Long getPending() {
        return pending;
    }

    public void setPending(Long pending) {
        this.pending = pending;
    }
}
