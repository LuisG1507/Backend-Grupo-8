package pe.edu.pe.smartrent_backend.DTOS.estateDTOS;

public class EstateDistrictAvailabilityDTO {
    private String district;
    private Long availableEstates;

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Long getAvailableEstates() {
        return availableEstates;
    }

    public void setAvailableEstates(Long availableEstates) {
        this.availableEstates = availableEstates;
    }
}
