package pe.edu.pe.smartrent_backend.DTOS.Models3DDTOs;

public class ModelEstateDTO {
    private String fileURL;
    private String state;
    private String city;
    private String district;

    public ModelEstateDTO(String fileURL, String state, String city, String district) {
        this.fileURL = fileURL;
        this.state = state;
        this.city = city;
        this.district = district;
    }

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
