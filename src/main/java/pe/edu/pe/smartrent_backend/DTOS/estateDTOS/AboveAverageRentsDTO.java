package pe.edu.pe.smartrent_backend.DTOS.estateDTOS;

public class AboveAverageRentsDTO {

    private String title;
    private String district;
    private  Double montlhy_price;
    private Integer rooms;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Double getMontlhy_price() {
        return montlhy_price;
    }

    public void setMontlhy_price(Double montlhy_price) {
        this.montlhy_price = montlhy_price;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }
}
