package pe.edu.pe.smartrent_backend.DTOS.userDTOS;

import jakarta.persistence.*;
import pe.edu.pe.smartrent_backend.Entities.Role;

import java.time.LocalDate;
import java.util.List;

public class UserIdDTO {
    private Integer idUser;
    private String name;
    private String lastName;
    private Integer dni;
    private String username;
    private Boolean statusVerification;
    private String profilePhoto;
    private String phoneNumber;
    private LocalDate createdDate;
    private LocalDate updateDate;
    private Boolean enabled;
    private List<RolesP> roles;

    public UserIdDTO() {
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

    public UserIdDTO(List<RolesP> roles) {
        this.roles = roles;
    }

    public static class RolesP{
        private Integer id;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }
}
