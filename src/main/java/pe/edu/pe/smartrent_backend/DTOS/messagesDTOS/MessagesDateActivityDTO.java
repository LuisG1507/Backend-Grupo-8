package pe.edu.pe.smartrent_backend.DTOS.messagesDTOS;

public class MessagesDateActivityDTO {

    private String fecha;
    private Long cantidadMensajes;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Long getCantidadMensajes() {
        return cantidadMensajes;
    }

    public void setCantidadMensajes(Long cantidadMensajes) {
        this.cantidadMensajes = cantidadMensajes;
    }
}
