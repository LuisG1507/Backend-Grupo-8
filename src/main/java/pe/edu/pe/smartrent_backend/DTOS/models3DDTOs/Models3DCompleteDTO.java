package pe.edu.pe.smartrent_backend.DTOS.models3DDTOs;

import pe.edu.pe.smartrent_backend.Entities.Estate;

import java.time.LocalDate;

public class Models3DCompleteDTO {
    private Integer idModels3D;
    private String fileURL;
    private String state;
    private LocalDate createDate;
    private int idEstate;

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

    public int getIdEstate() {
        return idEstate;
    }

    public void setIdEstate(int idEstate) {
        this.idEstate = idEstate;
    }
}
