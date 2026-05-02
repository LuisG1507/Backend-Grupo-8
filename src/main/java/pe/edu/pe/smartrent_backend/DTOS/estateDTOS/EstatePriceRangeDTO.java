package pe.edu.pe.smartrent_backend.DTOS.estateDTOS;

public class EstatePriceRangeDTO {
    private String type;
    private Long lowRange;
    private Long midRange;
    private Long highRange;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getLowRange() {
        return lowRange;
    }

    public void setLowRange(Long lowRange) {
        this.lowRange = lowRange;
    }

    public Long getMidRange() {
        return midRange;
    }

    public void setMidRange(Long midRange) {
        this.midRange = midRange;
    }

    public Long getHighRange() {
        return highRange;
    }

    public void setHighRange(Long highRange) {
        this.highRange = highRange;
    }
}
