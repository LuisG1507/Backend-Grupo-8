package pe.edu.pe.smartrent_backend.DTOS.riskpointsDTOS;

import java.time.LocalDate;

public class RiskPointsDecisionDTO4 {

    private int id_modelo3d;
    private String title;
    private String city;
    private LocalDate create_date;

    public int getId_modelo3d() {
        return id_modelo3d;
    }

    public void setId_modelo3d(int id_modelo3d) {
        this.id_modelo3d = id_modelo3d;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getCreate_date() {
        return create_date;
    }

    public void setCreate_date(LocalDate create_date) {
        this.create_date = create_date;
    }
}
