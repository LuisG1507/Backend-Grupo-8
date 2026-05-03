package pe.edu.pe.smartrent_backend.Entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Models3D")
public class Models3D {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idModels3D;

    @Column(name = "fileURL", length = 200, nullable = false)
    private String fileURL;

    @Column(name = "state", length = 100, nullable = false)
    private String state;

    @Column(name = "createDate", nullable = false)
    private LocalDate createDate;

    @OneToOne
    @JoinColumn(name = "idEstate")
    private Estate estate;

    @OneToMany(mappedBy = "models3D", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RiskPoints> riskPoints;

    public Models3D() {
    }

    public Models3D(Integer idModels3D, String fileURL, String state, LocalDate createDate, Estate estate) {
        this.idModels3D = idModels3D;
        this.fileURL = fileURL;
        this.state = state;
        this.createDate = createDate;
        this.estate = estate;
    }

    public Integer getIdModels3D() {
        return idModels3D;
    }

    public void setIdModels3D(Integer idModels3D) {
        this.idModels3D = idModels3D;
    }

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public Estate getEstate() {
        return estate;
    }

    public void setEstate(Estate estate) {
        this.estate = estate;
    }
}
