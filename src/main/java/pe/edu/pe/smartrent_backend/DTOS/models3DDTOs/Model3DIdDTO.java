package pe.edu.pe.smartrent_backend.DTOS.models3DDTOs;

import java.time.LocalDate;

public class Model3DIdDTO {
    private Integer idModels3D;
    private String fileURL;
    private String state;
    private LocalDate createDate;
    private EstateP estate;

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

    public EstateP getEstate() {
        return estate;
    }

    public void setEstate(EstateP estate) {
        this.estate = estate;
    }

    public static class EstateP{
        private Integer idEstate;

        public Integer getIdEstate() {
            return idEstate;
        }

        public void setIdEstate(Integer idEstate) {
            this.idEstate = idEstate;
        }
    }

}
