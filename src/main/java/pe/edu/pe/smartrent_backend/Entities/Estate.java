package pe.edu.pe.smartrent_backend.Entities;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "Estate")
public class Estate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEstate;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "description", length = 200, nullable = false)
    private String description;

    @Column(name = "adress", length = 200, nullable = false)
    private String adress;

    @Column(name = "district", length = 200, nullable = false)
    private String district;

    @Column(name = "city", length = 200, nullable = false)
    private String city;

    @Column(name = "monthlyPrice", nullable = false)
    private Double monthlyPrice;

    @Column(name = "type", length = 100, nullable = false)
    private String type;

    @Column(name = "state", nullable = false)
    private Boolean state;

    @Column(name = "rooms", nullable = false)
    private Integer rooms;

    @Column(name = "bathrooms", nullable = false)
    private Integer bathrooms;

    @Column(name = "areaM2", nullable = false)
    private Double areaM2;

    @Column(name = "creationDate", nullable = false)
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private Users users;

    public Estate() {
    }

    public Estate(Integer idEstate, String title, String description, String adress, String district, String city, Double monthlyPrice, String type, Boolean state, Integer rooms, Integer bathrooms, Double areaM2, LocalDate creationDate, Users users) {
        this.idEstate = idEstate;
        this.title = title;
        this.description = description;
        this.adress = adress;
        this.district = district;
        this.city = city;
        this.monthlyPrice = monthlyPrice;
        this.type = type;
        this.state = state;
        this.rooms = rooms;
        this.bathrooms = bathrooms;
        this.areaM2 = areaM2;
        this.creationDate = creationDate;
        this.users = users;
    }

    public Integer getIdEstate() {
        return idEstate;
    }

    public void setIdEstate(Integer idEstate) {
        this.idEstate = idEstate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(Double monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public Integer getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
    }

    public Double getAreaM2() {
        return areaM2;
    }

    public void setAreaM2(Double areaM2) {
        this.areaM2 = areaM2;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
