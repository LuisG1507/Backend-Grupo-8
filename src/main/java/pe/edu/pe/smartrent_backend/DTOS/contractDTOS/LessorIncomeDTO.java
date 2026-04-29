package pe.edu.pe.smartrent_backend.DTOS.contractDTOS;

public class LessorIncomeDTO {

    private Integer idLessor;
    private String lessorName;
    private Long contractCount;
    private Double totalMonthlyIncome;


    public Integer getIdLessor() {
        return idLessor;
    }

    public void setIdLessor(Integer idLessor) {
        this.idLessor = idLessor;
    }

    public String getLessorName() {
        return lessorName;
    }

    public void setLessorName(String lessorName) {
        this.lessorName = lessorName;
    }

    public Long getContractCount() {
        return contractCount;
    }

    public void setContractCount(Long contractCount) {
        this.contractCount = contractCount;
    }

    public Double getTotalMonthlyIncome() {
        return totalMonthlyIncome;
    }

    public void setTotalMonthlyIncome(Double totalMonthlyIncome) {
        this.totalMonthlyIncome = totalMonthlyIncome;
    }
}
