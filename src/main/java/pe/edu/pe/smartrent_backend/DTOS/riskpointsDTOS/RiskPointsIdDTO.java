package pe.edu.pe.smartrent_backend.DTOS.riskpointsDTOS;

public class RiskPointsIdDTO {
        private Integer id;
        private String description;
        private Double cordX;
        private Double cordY;
        private Double cordZ;
        private String severity;
        private Models3Dp idModel3D;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCordX() {
        return cordX;
    }

    public void setCordX(Double cordX) {
        this.cordX = cordX;
    }

    public Double getCordY() {
        return cordY;
    }

    public void setCordY(Double cordY) {
        this.cordY = cordY;
    }

    public Double getCordZ() {
        return cordZ;
    }

    public void setCordZ(Double cordZ) {
        this.cordZ = cordZ;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public Models3Dp getIdModel3D() {
        return idModel3D;
    }

    public void setIdModel3D(Models3Dp idModel3D) {
        this.idModel3D = idModel3D;
    }

    public static class Models3Dp {
            private Integer idModels3D;

            public Integer getIdModels3D() {
                return idModels3D;
            }

            public void setIdModels3D(Integer idModels3D) {
                this.idModels3D = idModels3D;
            }
        }
}
