package pe.edu.pe.smartrent_backend.DTOS.userDTOS;

public class UserUnverifiedWithBackgroundDTO {
    private String name;
    private String lastName;
    private Long totalBackgrounds;

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

    public Long getTotalBackgrounds() {
        return totalBackgrounds;
    }

    public void setTotalBackgrounds(Long totalBackgrounds) {
        this.totalBackgrounds = totalBackgrounds;
    }
}
