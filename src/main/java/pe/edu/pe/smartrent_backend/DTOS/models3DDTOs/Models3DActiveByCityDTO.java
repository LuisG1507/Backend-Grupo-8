package pe.edu.pe.smartrent_backend.DTOS.models3DDTOs;

public class Models3DActiveByCityDTO {
    private String city;
    private Long activeModels;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getActiveModels() {
        return activeModels;
    }

    public void setActiveModels(Long activeModels) {
        this.activeModels = activeModels;
    }
}
