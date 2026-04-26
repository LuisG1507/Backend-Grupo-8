package pe.edu.pe.smartrent_backend.Controllers;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.Models3DDTOs.ModelEstateDTO;
import pe.edu.pe.smartrent_backend.DTOS.Models3DDTOs.Models3DCompleteDTO;
import pe.edu.pe.smartrent_backend.DTOS.Models3DDTOs.Models3DDTO;
import pe.edu.pe.smartrent_backend.Entities.Models3D;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IModels3D;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Models3D")
public class Models3DController {

    @Autowired
    private IModels3D mI;

    @PostMapping
    private void registrar(@RequestBody Models3DDTO mD) {
        ModelMapper m = new ModelMapper();
        Models3D mL = m.map(mD, Models3D.class);
        mI.registrar(mL);
    }

    @PutMapping
    public ResponseEntity<String> actualizar(@RequestBody Models3DCompleteDTO model3D){
        Optional<Models3D> exist = mI.listarId(model3D.getIdModels3D());
        if(exist.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El valor no existe");
        }
        Models3D m = exist.get();
        m.setState(model3D.getState());
        m.setFileURL(model3D.getFileURL());
        m.setCreateDate(model3D.getCreateDate());
        m.setEstate(model3D.getEstate());

        mI.actualizar(m);

        return ResponseEntity.ok("Su valor ha sido actualizado");
    }

    @GetMapping
    public ResponseEntity<?> ListarModels(){
        ModelMapper m = new ModelMapper();
        List<Models3DDTO> list = mI.Listar().stream().map(y->m.map(y,Models3DDTO.class))
                .collect(Collectors.toList());
        if(list.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay listas en este objeto");
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
    public List<Models3D> buscarEstado(@PathVariable String state){
        return mI.buscarPorEstado(state);
    }

    @GetMapping("/fecha/{date}")
    public List<Models3D> buscarFecha(@PathVariable String date){
        return mI.buscarPorFecha(date);
    }

    @GetMapping("/ubicacion")
    public List<Object[]> ubicacion(){
        return mI.modelosConUbicacion();
    }

    @GetMapping("/estateModels")
    public List<Object[]> estateModel(){
        return mI.inmueblesConModelo();
    }
}
