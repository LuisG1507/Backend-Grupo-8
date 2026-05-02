package pe.edu.pe.smartrent_backend.ServicesInterfaces;

import pe.edu.pe.smartrent_backend.Entities.RiskReport;

import java.util.List;

public interface IRiskReport {

    public void Register(RiskReport r); //Registrar

    public void Update(RiskReport r); //Modificar

    public RiskReport listId(Integer id); //ListarId

    public List<RiskReport> list(); //Listartodo

    public void Delete(Integer id); //Eliminar


    //QUERY TOMA DECISION

    public List<Object[]> RRDecision1(); // Inmuebles con más reportes de riesgo
    public List<Object[]> RRDecision2(); // Distribución de reportes por nivel de riesgo con porcentaje
    public List<Object[]> RRDecision3(); // Usuarios que más reportes han generado
    public List<Object[]> RRDecision4(); //Inmuebles con nivel de riesgo ALTO que aún tienen contrato activo (situación crítica)


}
