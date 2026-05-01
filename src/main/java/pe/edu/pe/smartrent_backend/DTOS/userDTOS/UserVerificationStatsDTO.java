package pe.edu.pe.smartrent_backend.DTOS.userDTOS;

public class UserVerificationStatsDTO {
    private Long verified;
    private Long notVerified;
    private Double verifiedPercentage;

    public Long getVerified() {
        return verified;
    }

    public void setVerified(Long verified) {
        this.verified = verified;
    }

    public Long getNotVerified() {
        return notVerified;
    }

    public void setNotVerified(Long notVerified) {
        this.notVerified = notVerified;
    }

    public Double getVerifiedPercentage() {
        return verifiedPercentage;
    }

    public void setVerifiedPercentage(Double verifiedPercentage) {
        this.verifiedPercentage = verifiedPercentage;
    }
}
