package pe.edu.pe.smartrent_backend.DTOS.userbackgorundDTOS;

public class UserBackgroundMonthlyDTO {
    private String month;
    private Long totalBackground;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Long getTotalBackground() {
        return totalBackground;
    }

    public void setTotalBackground(Long totalBackground) {
        this.totalBackground = totalBackground;
    }
}
