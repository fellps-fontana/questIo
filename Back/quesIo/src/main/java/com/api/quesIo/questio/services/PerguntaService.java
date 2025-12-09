package com.api.quesIo.questio.services;

import com.api.quesIo.questio.Models.PerguntaModel;
import com.api.quesIo.questio.Models.QuestionarioModel;
import com.api.quesIo.questio.Repositorys.PerguntaRepository;
import com.api.quesIo.questio.Repositorys.QuestionarioRepository;
import com.api.quesIo.questio.dtos.PerguntaDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PerguntaService {

    private final PerguntaRepository perguntaRepository;
    private final QuestionarioRepository questionarioRepository; // Precisamos disso para vincular

    public PerguntaService(PerguntaRepository perguntaRepository, QuestionarioRepository questionarioRepository) {
        this.perguntaRepository = perguntaRepository;
        this.questionarioRepository = questionarioRepository;
    }

    // Adiciona uma pergunta a um formulário existente
    public PerguntaModel addQuestion(Long questionarioId, PerguntaDto dto) {
        QuestionarioModel questionario = questionarioRepository.findById(questionarioId)
                .orElseThrow(() -> new RuntimeException("Questionário não encontrado"));

        PerguntaModel pergunta = new PerguntaModel();
        BeanUtils.copyProperties(dto, pergunta);
        pergunta.setQuestionnaire(questionario); // O Vínculo acontece aqui!

        return perguntaRepository.save(pergunta);
    }

    public PerguntaModel update(UUID id, PerguntaDto dto) {
        PerguntaModel existente = perguntaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pergunta não encontrada"));

        // Atualiza texto, tipo, obrigatoriedade, etc.
        BeanUtils.copyProperties(dto, existente, "id", "questionario");

        return perguntaRepository.save(existente);
    }

    public void delete(UUID id) {
        if (!perguntaRepository.existsById(id)) {
            throw new RuntimeException("Pergunta não encontrada");
        }
        perguntaRepository.deleteById(id);
    }
}