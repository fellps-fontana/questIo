package com.api.quesIo.questio.Repositorys;

import com.api.quesIo.questio.Models.HospedeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface HospedeRepository extends JpaRepository<HospedeModel, UUID>{

    @Query("SELECT h FROM HospedeModel h " +
            "WHERE h.checkout < :agora " +
            "AND h.xEnviadoEmail = false")
    List<HospedeModel> buscarCheckoutVencidoNaoEnviado(LocalDateTime agora);

}
