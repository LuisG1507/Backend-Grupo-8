package pe.edu.pe.smartrent_backend.DTOS.roleDTOS;

public class RoleDecisionDTO2 {

    private String name;
    private String last_name;
    private int cantidad_roles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getCantidad_roles() {
        return cantidad_roles;
    }

    public void setCantidad_roles(int cantidad_roles) {
        this.cantidad_roles = cantidad_roles;
    }
}
