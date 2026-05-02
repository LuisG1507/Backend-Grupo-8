package pe.edu.pe.smartrent_backend.DTOS.userDTOS;

public class UserMonthlyGrowthDTO {
    private String month;
    private Long newUsers;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Long getNewUsers() {
        return newUsers;
    }

    public void setNewUsers(Long newUsers) {
        this.newUsers = newUsers;
    }
}
