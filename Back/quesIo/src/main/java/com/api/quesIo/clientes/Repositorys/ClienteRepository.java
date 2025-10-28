package com.api.quesIo.clientes.Repositorys;

import com.api.quesIo.clientes.Models.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClienteRepository extends JpaRepository<ClienteModel, UUID>{

}
