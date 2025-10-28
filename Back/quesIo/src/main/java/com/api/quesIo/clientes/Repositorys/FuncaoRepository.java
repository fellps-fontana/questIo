package com.api.quesIo.clientes.Repositorys;

import com.api.quesIo.clientes.Models.FuncaoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FuncaoRepository extends JpaRepository<FuncaoModel, UUID> {
}
