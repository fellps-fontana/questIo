package com.api.quesIo.questio.Repositorys;

import com.api.quesIo.questio.Models.HospedeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HospedeRepository extends JpaRepository<HospedeModel, UUID>{

}
