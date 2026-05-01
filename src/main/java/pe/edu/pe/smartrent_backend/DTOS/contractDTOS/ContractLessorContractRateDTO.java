package pe.edu.pe.smartrent_backend.DTOS.contractDTOS;

public class ContractLessorContractRateDTO {
    private String name;
    private String lastName;
    private Long active;
    private Long inactive;
    private Long total;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getActive() {
        return active;
    }

    public void setActive(Long active) {
        this.active = active;
    }

    public Long getInactive() {
        return inactive;
    }

    public void setInactive(Long inactive) {
        this.inactive = inactive;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
