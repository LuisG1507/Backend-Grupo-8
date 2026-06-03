package pe.edu.pe.smartrent_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.pe.smartrent_backend.Entities.RiskReport;

import java.util.List;

@Repository
public interface IRiskReportRepository extends JpaRepository<RiskReport,Integer> {}
