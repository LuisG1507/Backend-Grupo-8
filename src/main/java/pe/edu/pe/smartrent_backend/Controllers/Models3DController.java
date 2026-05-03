package pe.edu.pe.smartrent_backend.Controllers;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.models3DDTOs.*;
import pe.edu.pe.smartrent_backend.Entities.Estate;
import pe.edu.pe.smartrent_backend.Entities.Models3D;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IModels3D;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Models3D")
public class Models3DController {

    @Autowired
    private IModels3D mI;

    @PostMapping("/Register")
    private ResponseEntity<?> registrar(@RequestBody Models3DDTO mD) {
        ModelMapper m = new ModelMapper();
        try {
            Models3D mL = m.map(mD, Models3D.class);
            mI.registrar(mL);
            return ResponseEntity.ok("Registrado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar: " + e.getMessage());
        }
    }

    @PutMapping("/Update")
    public ResponseEntity<String> actualizar(@RequestBody Models3DCompleteDTO model3D){
        Optional<Models3D> exist = mI.listarId(model3D.getIdModels3D());
        if (exist.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El valor no existe");
        }

        Models3D m = exist.get();
        m.setState(model3D.getState());
        m.setFileURL(model3D.getFileURL());
        m.setCreateDate(model3D.getCreateDate());

        Estate auxEstate = new Estate();
        auxEstate.setIdEstate(model3D.getIdEstate());

        m.setEstate(auxEstate);

        mI.actualizar(m);
        return ResponseEntity.ok("Actualizado correctamente");
    }

    @GetMapping("/ListModels3D")
    public ResponseEntity<?> ListarModels(){
        ModelMapper m = new ModelMapper();
        List<Models3DDTO> list = mI.Listar().stream().map(y->m.map(y,Models3DDTO.class))
                .collect(Collectors.toList());
        if(list.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay datos en este objeto");
        }else{
            return ResponseEntity.ok(list);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        Optional<Models3D> exist = mI.listarId(id);
        if(exist.isPresent()){
            mI.eliminar(id);
            return ResponseEntity.ok("El valor ha sido eliminado");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el valor ingresado");
        }
    }

    @GetMapping("/estado/{state}")
    public List<Models3DCompleteDTO> buscarEstado(@PathVariable String state){
        ModelMapper m = new ModelMapper();
        List<Models3D> entidades = mI.buscarPorEstado(state);
        return entidades.stream()
                .map(entity -> {
                    Models3DCompleteDTO dto = m.map(entity, Models3DCompleteDTO.class);
                    if (entity.getEstate() != null) {
                        dto.setIdEstate(entity.getEstate().getIdEstate());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/fecha/{date}")
    public List<Models3DCompleteDTO> buscarFecha(@PathVariable String date){
        ModelMapper m = new ModelMapper();
        LocalDate fechaBusqueda = LocalDate.parse(date);
        List<Models3D> entidades = mI.buscarPorFecha(fechaBusqueda);
        return entidades.stream()
                .map(entity -> {
                    Models3DCompleteDTO dto = m.map(entity, Models3DCompleteDTO.class);
                    if (entity.getEstate() != null) {
                        dto.setIdEstate(entity.getEstate().getIdEstate());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/ubicacion")
    public List<Model3DLocation> ubicacion(){
        List<Object[]> list = mI.modelosConUbicacion();
        List<Model3DLocation> listLoc = new ArrayList<>();
        for(Object[] row : list){
            Model3DLocation dt = new Model3DLocation();
            dt.setFileURL((String) row[0]);
            dt.setState((String) row[1]);
            dt.setCity((String) row[2]);
            dt.setDistrict((String) row[3]);

            listLoc.add(dt);
        }
        return listLoc;
    }

    @GetMapping("/estateModels")
    public List<ModelEstateDTO> estateModel(){
        List<Object[]> resultados = mI.inmueblesConModelo();

        List<ModelEstateDTO> listaMapeada = new ArrayList<>();

        for (Object[] fila : resultados) {
            ModelEstateDTO dto = new ModelEstateDTO();

            dto.setCity((String) fila[0]);      // e.city
            dto.setDistrict((String) fila[1]);  // e.district
            dto.setFileURL((String) fila[2]);   // m.fileURL
            listaMapeada.add(dto);
        }
        return listaMapeada;
    }

    //Inmuebles sin modelo 3D (sin presentación visual)
    @GetMapping("/no-model-estates")
    public ResponseEntity<?> noModelEstates() {
        List<Object[]> resultados = mI.findEstatesWithoutModel();
        List<Models3DNoModelEstateDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            Models3DNoModelEstateDTO dto = new Models3DNoModelEstateDTO();
            dto.setIdEstate(((Number) row[0]).intValue());
            dto.setTitle(row[1].toString());
            dto.setCity(row[2].toString());
            dto.setMonthlyPrice(((Number) row[3]).doubleValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }

    //Ciudades con mayor cantidad de modelos 3D activos
    @GetMapping("/active-by-city")
    public ResponseEntity<?> activeByCity() {
        List<Object[]> resultados = mI.findCitiesWithMostActiveModels();
        List<Models3DActiveByCityDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            Models3DActiveByCityDTO dto = new Models3DActiveByCityDTO();
            dto.setCity(row[0] != null ? row[0].toString() : "Sin Ciudad");
            dto.setActiveModels(row[1] != null ? ((Number) row[1]).longValue() : 0L);

            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }

    // Tasa de modelos activos vs inactivos con porcentaje
    @GetMapping("/state-rate")
    public ResponseEntity<?> stateRate() {
        List<Object[]> resultados = mI.findStateRate();
        List<Models3DStateRateDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            Models3DStateRateDTO dto = new Models3DStateRateDTO();
            dto.setState(row[0].toString());
            dto.setTotal(((Number) row[1]).longValue());
            dto.setPercentage(((Number) row[2]).doubleValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }

    // Inmuebles con modelo 3D activo que además tienen puntos de riesgo críticos
    @GetMapping("/critical-risk")
    public ResponseEntity<?> criticalRisk() {
        List<Object[]> resultados = mI.findEstatesWithCriticalRiskPoints();
        List<Models3DCriticalRiskDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            Models3DCriticalRiskDTO dto = new Models3DCriticalRiskDTO();
            dto.setTitle(row[0] != null ? row[0].toString() : "Sin título");
            dto.setCity(row[1] != null ? row[1].toString() : "Sin ciudad");
            dto.setCriticalPoints(row[2] != null ? ((Number) row[2]).intValue() : 0);

            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }

}
