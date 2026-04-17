package pe.edu.pe.smartrent_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.pe.smartrent_backend.Entities.RiskReport;

public interface IRiskReportRepository extends JpaRepository<RiskReport,Integer> {
}
