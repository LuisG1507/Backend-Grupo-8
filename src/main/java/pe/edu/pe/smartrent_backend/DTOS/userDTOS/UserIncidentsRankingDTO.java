package pe.edu.pe.smartrent_backend.DTOS.userDTOS;

public class UserIncidentsRankingDTO {

    private String nombre;
    private Integer cantidad;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
