package pe.edu.pe.smartrent_backend.ServicesInterfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import pe.edu.pe.smartrent_backend.Entities.RiskPoints;
import java.util.List;

public interface IRiskPointsService {
    public void insert(RiskPoints riskPoint);
    public List<RiskPoints> list();
    public void delete(Integer id);
    public RiskPoints listId(Integer id);
    public void update(RiskPoints riskPoint);
}