package com.api.quesIo.questio.services;

import com.api.quesIo.questio.Models.PerguntaModel;
import com.api.quesIo.questio.Models.QuestionarioModel;
import com.api.quesIo.questio.Repositorys.QuestionarioRepository;
import com.api.quesIo.questio.Repositorys.RespostaRepository;
import com.api.quesIo.questio.dtos.QuestionarioDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionarioService {

    private final QuestionarioRepository repository;
    private final RespostaRepository respostaRepository;

    public QuestionarioService(QuestionarioRepository repository, RespostaRepository respostaRepository) {
        this.repository = repository;
        this.respostaRepository = respostaRepository;
    }

    public QuestionarioModel create(QuestionarioDto dto) {
        QuestionarioModel questionario = new QuestionarioModel();
        BeanUtils.copyProperties(dto, questionario, "questions");

        if (dto.getQuestions() != null) {
            List<PerguntaModel> perguntas = dto.getQuestions().stream().map(pDto -> {
                PerguntaModel p = new PerguntaModel();
                BeanUtils.copyProperties(pDto, p);
                p.setQuestionario(questionario);
                return p;
            }).collect(Collectors.toList());

            questionario.setQuestions(perguntas);
        }

        return repository.save(questionario);
    }

    // --- CORREÇÃO 1: CHAMA O MÉTODO PARA CADA ITEM NA LISTA ---
    public List<QuestionarioModel> findAll() {
        List<QuestionarioModel> list = repository.findAll();
        // Para cada formulário, calcula as estatísticas
        list.forEach(this::preencherEstatisticas);
        return list;
    }

    // --- CORREÇÃO 2: CHAMA O MÉTODO NO ITEM INDIVIDUAL ---
    public Optional<QuestionarioModel> findById(Long id) {
        Optional<QuestionarioModel> opt = repository.findById(id);
        // Se achou o formulário, calcula os totais dele antes de devolver
        opt.ifPresent(this::preencherEstatisticas);
        return opt;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }


    private void preencherEstatisticas(QuestionarioModel q) {
        try {
            // --- CORREÇÃO FINAL: CHAMAMOS AGORA O MÉTODO DE CONTAGEM TOTAL ---
            // Este é o método que conta todas as linhas (ex: 8), não apenas os participantes (ex: 1).
            Long total = respostaRepository.contarTotalRespostas(q.getId());

            q.setTotalAnswers(total != null ? total.intValue() : 0);

            q.setLastAnswerAt(respostaRepository.ultimaRespostaData(q.getId()));
        } catch (Exception e) {
            // Se der erro, printa o problema para debug e seta para 0
            System.err.println("ERRO AO CALCULAR TOTAIS PARA FORM ID " + q.getId() + ": " + e.getMessage());
            q.setTotalAnswers(0);
        }
    }
}