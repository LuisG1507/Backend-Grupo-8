package pe.edu.pe.smartrent_backend.DTOS.favoriteDTOS;

public class FavoriteEstateDTO {
    private String title;
    private String city;
    private String district;
    private Double monthlyPrice;
    private Long timesFavorite;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Double getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(Double monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public Long getTimesFavorite() {
        return timesFavorite;
    }

    public void setTimesFavorite(Long timesFavorite) {
        this.timesFavorite = timesFavorite;
    }
}
