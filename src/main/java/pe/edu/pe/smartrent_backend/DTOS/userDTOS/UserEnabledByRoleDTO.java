package pe.edu.pe.smartrent_backend.DTOS.userDTOS;

public class UserEnabledByRoleDTO {
    private String role;
    private Long enabled;
    private Long disabled;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getEnabled() {
        return enabled;
    }

    public void setEnabled(Long enabled) {
        this.enabled = enabled;
    }

    public Long getDisabled() {
        return disabled;
    }

    public void setDisabled(Long disabled) {
        this.disabled = disabled;
    }
}
