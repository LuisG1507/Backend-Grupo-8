package pe.edu.pe.smartrent_backend.DTOS.contractDTOS;

public class ContractAverageDurationDTO {
    private String name;
    private String lastName;
    private Double averageDays;

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

    public Double getAverageDays() {
        return averageDays;
    }

    public void setAverageDays(Double averageDays) {
        this.averageDays = averageDays;
    }
}
