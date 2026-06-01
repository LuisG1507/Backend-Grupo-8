package pe.edu.pe.smartrent_backend.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.pe.smartrent_backend.DTOS.models3DDTOs.*;
import pe.edu.pe.smartrent_backend.Entities.Models3D;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IModels3DRepository extends JpaRepository<Models3D, Integer> {}
