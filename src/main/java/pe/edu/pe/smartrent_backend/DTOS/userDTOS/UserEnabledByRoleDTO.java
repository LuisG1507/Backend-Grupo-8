package pe.edu.pe.smartrent_backend.DTOS.userDTOS;

public class UserEnabledByRoleDTO {
    private String role;
    private int enabled;
    private int disabled;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public int getDisabled() {
        return disabled;
    }

    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }
}
