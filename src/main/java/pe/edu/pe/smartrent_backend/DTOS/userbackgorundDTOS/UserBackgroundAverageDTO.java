package pe.edu.pe.smartrent_backend.DTOS.userbackgorundDTOS;

public class UserBackgroundAverageDTO {
    private String name;
    private String lastName;
    private Long totalBackground;

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

    public Long getTotalBackground() {
        return totalBackground;
    }

    public void setTotalBackground(Long totalBackground) {
        this.totalBackground = totalBackground;
    }
}
