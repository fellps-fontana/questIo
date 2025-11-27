package com.api.quesIo.questio.Repositorys;

import com.api.quesIo.questio.Models.QuestionarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionarioRepository extends JpaRepository<QuestionarioModel, Long> {
    // Note que mudou de UUID para Long ali no final ^
}