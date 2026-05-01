package pe.edu.pe.smartrent_backend.DTOS.favoriteDTOS;

public class FavoriteNoContractDTO {
        private String title;
        private String city;
        private Long favorites;

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

    public Long getFavorites() {
        return favorites;
    }

    public void setFavorites(Long favorites) {
        this.favorites = favorites;
    }
}
