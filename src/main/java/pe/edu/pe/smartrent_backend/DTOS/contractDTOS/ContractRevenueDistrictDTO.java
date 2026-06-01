package pe.edu.pe.smartrent_backend.DTOS.contractDTOS;

public class ContractRevenueDistrictDTO {
    private String district;
    private Double totalRevenue;

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
