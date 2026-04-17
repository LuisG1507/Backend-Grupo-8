package pe.edu.pe.smartrent_backend.Entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProperty;

    @Column(name = "litle", length = 100, nullable = false)
    private String litle;

    @Column(name = "description", length = 200, nullable = false)
    private String description;

    @Column(name = "adress", length = 200, nullable = false)
    private String adress;

    @Column(name = "district", length = 200, nullable = false)
    private String district;

    @Column(name = "city", length = 200, nullable = false)
    private String city;

    @Column(name = "monthlyPrice", nullable = false)
    private double monthlyPrice;

    @Column(name = "type", length = 100, nullable = false)
    private String type;

    @Column(name = "state", nullable = false)
    private boolean state;

    @Column(name = "rooms", nullable = false)
    private int rooms;

    @Column(name = "bathrooms", nullable = false)
    private int bathrooms;

    @Column(name = "areaM2", nullable = false)
    private double areaM2;

    @Column(name = "creationDate", nullable = false)
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private Users users;

    public Property() {
    }

    public Property(int idProperty, String litle, String description, String adress, String district, String city, double monthlyPrice, String type, boolean state, int rooms, int bathrooms, double areaM2, LocalDate creationDate, Users users) {
        this.idProperty = idProperty;
        this.litle = litle;
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

    public int getIdProperty() {
        return idProperty;
    }

    public void setIdProperty(int idProperty) {
        this.idProperty = idProperty;
    }

    public String getLitle() {
        return litle;
    }

    public void setLitle(String litle) {
        this.litle = litle;
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

    public double getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(double monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }

    public double getAreaM2() {
        return areaM2;
    }

    public void setAreaM2(double areaM2) {
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
