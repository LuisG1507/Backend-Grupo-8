package pe.edu.pe.smartrent_backend.Entities;


import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column (name = "lastName", length = 50, nullable = false)
    private String lastName;

    @Column(name = "dni", nullable = false)
    private Integer dni;

    @Column(name = "username", length = 20, nullable = false)
    private String username;

    @Column(name = "password", length = 50, nullable = false)
    private String password;

    @Column(name = "statusVerification", nullable = false)
    private Boolean statusVerification;

    @Column(name = "profilePhoto", length = 1000, nullable = false)
    private String profilePhoto;

    @Column(name = "phoneNumber", length = 20, nullable = false)
    private String phoneNumber;

    @Column(name = "createdDate", nullable = false)
    private LocalDate createdDate;

    @Column(name = "updateDate", nullable = false)
    private LocalDate updateDate;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Role> roles;


    public User() {
    }

    public User(Integer idUser, String name, String lastName, Integer dni, String username, String password, Boolean statusVerification, String profilePhoto, String phoneNumber, LocalDate createdDate, LocalDate updateDate, Boolean enabled, List<Role> roles) {
        this.idUser = idUser;
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        this.username = username;
        this.password = password;
        this.statusVerification = statusVerification;
        this.profilePhoto = profilePhoto;
        this.phoneNumber = phoneNumber;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
        this.enabled = enabled;
        this.roles = roles;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

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

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getStatusVerification() {
        return statusVerification;
    }

    public void setStatusVerification(Boolean statusVerification) {
        this.statusVerification = statusVerification;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }


}
