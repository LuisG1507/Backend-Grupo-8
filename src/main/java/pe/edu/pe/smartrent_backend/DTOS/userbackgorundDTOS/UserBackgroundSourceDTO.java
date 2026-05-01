package pe.edu.pe.smartrent_backend.DTOS.userbackgorundDTOS;

public class UserBackgroundSourceDTO {
    private String source;
    private Long totalReported;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getTotalReported() {
        return totalReported;
    }

    public void setTotalReported(Long totalReported) {
        this.totalReported = totalReported;
    }
}
