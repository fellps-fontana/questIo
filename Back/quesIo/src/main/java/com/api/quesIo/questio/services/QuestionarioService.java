package com.api.quesIo.questio.services;

import com.api.quesIo.questio.Models.PerguntaModel;
import com.api.quesIo.questio.Models.QuestionarioModel;
import com.api.quesIo.questio.Repositorys.QuestionarioRepository;
import com.api.quesIo.questio.dtos.QuestionarioDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionarioService {

    private final QuestionarioRepository repository;

    public QuestionarioService(QuestionarioRepository repository) {
        this.repository = repository;
    }

    public QuestionarioModel create(QuestionarioDto dto) {
        QuestionarioModel questionario = new QuestionarioModel();
        BeanUtils.copyProperties(dto, questionario, "questions"); // Copia titulo e descricao

        // Se tiver perguntas no DTO, convertemos e associamos
        if (dto.getQuestions() != null) {
            List<PerguntaModel> perguntas = dto.getQuestions().stream().map(pDto -> {
                PerguntaModel p = new PerguntaModel();
                BeanUtils.copyProperties(pDto, p);
                p.setQuestionario(questionario); // Vínculo Bidirecional
                return p;
            }).collect(Collectors.toList());

            questionario.setQuestions(perguntas);
        }

        return repository.save(questionario); // O CascadeType.ALL salva as perguntas automagicamente
    }

    public List<QuestionarioModel> findAll() {
        return repository.findAll();
    }
}