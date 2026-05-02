package pe.edu.pe.smartrent_backend.DTOS.userbackgorundDTOS;

import java.time.LocalDateTime;

public class UserBackgroundMonthlyDTO {
    private String month;
    private int totalBackground;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getTotalBackground() {
        return totalBackground;
    }

    public void setTotalBackground(int totalBackground) {
        this.totalBackground = totalBackground;
    }
}
