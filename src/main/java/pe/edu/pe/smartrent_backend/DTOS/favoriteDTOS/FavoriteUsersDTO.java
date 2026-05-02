package pe.edu.pe.smartrent_backend.DTOS.favoriteDTOS;

public class FavoriteUsersDTO {
    private String name;
    private String lastName;
    private Long totalFavorites;

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

    public Long getTotalFavorites() {
        return totalFavorites;
    }

    public void setTotalFavorites(Long totalFavorites) {
        this.totalFavorites = totalFavorites;
    }
}
