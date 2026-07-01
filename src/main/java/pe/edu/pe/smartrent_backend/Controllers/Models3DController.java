package pe.edu.pe.smartrent_backend.Controllers;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.models3DDTOs.*;
import pe.edu.pe.smartrent_backend.DTOS.reviewsDTOS.ReviewsIdDTO;
import pe.edu.pe.smartrent_backend.Entities.Estate;
import pe.edu.pe.smartrent_backend.Entities.Models3D;
import pe.edu.pe.smartrent_backend.Entities.Reviews;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IEstate;
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

    @Autowired
    private IEstate eI;

    @PostMapping("/Register")
    @PreAuthorize("hasAuthority('ARRENDADOR')")
    public ResponseEntity<?> registrar(@RequestBody Models3DDTO mD) {
        try {
            String validationError = validarModelo(
                    mD.getFileURL(), mD.getState(), mD.getCreateDate(), mD.getIdEstate());
            if (validationError != null) {
                return ResponseEntity.badRequest().body(validationError);
            }
            if (eI.listarId(mD.getIdEstate()).isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Inmueble no encontrado.");
            }

            Models3D mL = new Models3D();
            mL.setFileURL(mD.getFileURL());
            mL.setState(mD.getState());
            mL.setCreateDate(mD.getCreateDate());

            Estate estate = new Estate();
            estate.setIdEstate(mD.getIdEstate());
            mL.setEstate(estate);

            mI.registrar(mL);
            return ResponseEntity.ok("Registrado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar: " + e.getMessage());
        }
    }

    @PutMapping("/Update")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDADOR')")
    public ResponseEntity<String> actualizar(@RequestBody Models3DCompleteDTO model3D){
        Optional<Models3D> exist = mI.listarId(model3D.getIdModels3D());
        if (exist.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El valor no existe");
        }

        String validationError = validarModelo(
                model3D.getFileURL(), model3D.getState(),
                model3D.getCreateDate(), model3D.getIdEstate());
        if (validationError != null) {
            return ResponseEntity.badRequest().body(validationError);
        }
        if (eI.listarId(model3D.getIdEstate()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Inmueble no encontrado.");
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO')")
    public ResponseEntity<?> ListarModels(){
        ModelMapper m = new ModelMapper();
        List<Model3DIdDTO> list = mI.Listar().stream().map(y->m.map(y,Model3DIdDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    // Los modelos se filtran por el propietario del inmueble relacionado.
    @GetMapping("/my-models")
    @PreAuthorize("hasAuthority('ARRENDADOR')")
    public ResponseEntity<?> listarMisModelos(Authentication authentication) {
        ModelMapper m = new ModelMapper();
        List<Model3DIdDTO> list = mI.listarPorUsername(authentication.getName()).stream()
                .map(y -> m.map(y, Model3DIdDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDADOR')")
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        Optional<Models3D> exist = mI.listarId(id);
        if(exist.isPresent()){
            mI.eliminar(id);
            return ResponseEntity.ok("El valor ha sido eliminado");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el valor ingresado");
        }
    }

    //listar por id
    @GetMapping("/listId/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDADOR', 'ARRENDATARIO')")
    public ResponseEntity<?> listId(@PathVariable int id) {
        ModelMapper m = new ModelMapper();
        Models3D models3D = mI.listId(id);

        if (models3D != null) {
            Model3DIdDTO dto = m.map(models3D, Model3DIdDTO.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Modelos 3D no encontrado");
        }
    }

    private String validarModelo(
            String fileURL, String state, LocalDate createDate, int idEstate) {
        if (fileURL == null || fileURL.isBlank() || fileURL.length() > 2000
                || (!fileURL.startsWith("http://") && !fileURL.startsWith("https://"))) {
            return "El modelo debe tener una URL valida.";
        }
        if (!"ACTIVO".equals(state) && !"INACTIVO".equals(state)) {
            return "Seleccione un estado valido para el modelo.";
        }
        if (createDate == null || createDate.isAfter(LocalDate.now())) {
            return "La fecha de creacion no puede estar en el futuro.";
        }
        if (idEstate <= 0) {
            return "Seleccione un inmueble valido.";
        }
        return null;
    }

}
