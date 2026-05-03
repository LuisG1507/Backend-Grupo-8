package pe.edu.pe.smartrent_backend.DTOS.models3DDTOs;

public class ModelEstateDTO {
    private String fileURL;
    private String city;
    private String district;

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
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
