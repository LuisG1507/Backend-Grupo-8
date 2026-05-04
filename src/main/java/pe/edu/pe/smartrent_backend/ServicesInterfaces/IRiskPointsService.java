package pe.edu.pe.smartrent_backend.ServicesInterfaces;
import pe.edu.pe.smartrent_backend.Entities.RiskPoints;
import java.util.List;

public interface IRiskPointsService {
    public void insert(RiskPoints riskPoint);
    public List<RiskPoints> list();
    public void delete(Integer id);
    public RiskPoints listId(Integer id);
    public void update(RiskPoints riskPoint);


    //Toma decisiones

    public List<Object[]> RPDecision1(); //Modelos 3D con mayor cantidad de puntos de riesgo registrados
    public List<Object[]> RPDecision2(); //Distribución de puntos de riesgo por severidad con porcentaje
    public List<Object[]> RPDecision3(); //Inmuebles con más puntos de severidad crítica (prioridad de intervención)
    public List<Object[]> RPDecision4(); // Modelos 3D sin ningún punto de riesgo registrado (posiblemente sin inspección)




}