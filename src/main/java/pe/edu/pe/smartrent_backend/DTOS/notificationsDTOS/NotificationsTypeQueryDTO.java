package pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS;

import java.time.LocalDate;

public class NotificationsTypeQueryDTO {
    //QueryToma
    private String title;
    private String type;
    private LocalDate createDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }
}
