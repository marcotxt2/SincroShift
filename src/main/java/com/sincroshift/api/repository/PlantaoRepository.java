package com.sincroshift.api.repository;

import com.sincroshift.api.model.Plantao;
import com.sincroshift.api.model.enums.StatusPlantao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantaoRepository extends JpaRepository<Plantao,Long> {
    List<Plantao> findByStatus(StatusPlantao status); //verifica status do plantão
    List<Plantao> findByUsuarioAtualId(Long usuarioId); //busca por ID
}
