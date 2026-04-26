package pe.edu.pe.smartrent_backend.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "contract")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idContract;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "monthly_amount")
    private Double monthlyAmount;

    @Column(name = "status")
    private boolean status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "id_estate")
    private Estate estate;

    @ManyToOne
    @JoinColumn(name = "id_arrendador")
    private Users lessor;

    @ManyToOne
    @JoinColumn(name = "id_arrendatario")
    private Users lessee;

    public Contract() {
    }

    public Contract(int idContract, LocalDateTime startDate, LocalDateTime endDate, Double monthlyAmount,
                    boolean status, LocalDateTime createdAt, Estate estate, Users lessor, Users lessee) {
        this.idContract = idContract;
        this.startDate = startDate;
        this.endDate = endDate;
        this.monthlyAmount = monthlyAmount;
        this.status = status;
        this.createdAt = createdAt;
        this.estate = estate;
        this.lessor = lessor;
        this.lessee = lessee;
    }

    public int getIdContract() {
        return idContract;
    }

    public void setIdContract(int idContract) {
        this.idContract = idContract;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Double getMonthlyAmount() {
        return monthlyAmount;
    }

    public void setMonthlyAmount(Double monthlyAmount) {
        this.monthlyAmount = monthlyAmount;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Estate getEstate() {
        return estate;
    }

    public void setEstate(Estate estate) {
        this.estate = estate;
    }

    public Users getLessor() {
        return lessor;
    }

    public void setLessor(Users lessor) {
        this.lessor = lessor;
    }

    public Users getLessee() {
        return lessee;
    }

    public void setLessee(Users lessee) {
        this.lessee = lessee;
    }
}
