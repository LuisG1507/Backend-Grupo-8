package pe.edu.pe.smartrent_backend.DTOS.favoriteDTOS;

public class FavoriteMonthlyTrendDTO {

    private String month;
    private Long favoritesAdded;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Long getFavoritesAdded() {
        return favoritesAdded;
    }

    public void setFavoritesAdded(Long favoritesAdded) {
        this.favoritesAdded = favoritesAdded;
    }
}
