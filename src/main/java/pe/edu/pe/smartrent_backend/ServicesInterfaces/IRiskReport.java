package pe.edu.pe.smartrent_backend.ServicesInterfaces;

import pe.edu.pe.smartrent_backend.Entities.RiskReport;

import java.util.List;

public interface IRiskReport {

    public void Register(RiskReport r); //Registrar

    public void Update(RiskReport r); //Modificar

    public RiskReport listId(Integer id); //ListarId

    public List<RiskReport> list(); //Listartodo

    public void Delete(Integer id);

}
