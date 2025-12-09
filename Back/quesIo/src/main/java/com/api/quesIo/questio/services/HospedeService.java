package com.api.quesIo.questio.services;

import com.api.quesIo.questio.Models.HospedeModel;
import com.api.quesIo.questio.Models.QuestionarioModel;
import com.api.quesIo.questio.Repositorys.HospedeRepository;
import com.api.quesIo.questio.Repositorys.QuestionarioRepository;
import com.api.quesIo.questio.dtos.HospedeDto;
import com.api.quesIo.questio.dtos.HospedeResponseDto;
import com.api.quesIo.questio.dtos.PerguntaDto;
import com.api.quesIo.questio.dtos.QuestionarioDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HospedeService {

    // URL do seu Front (Angular). Hoje é localhost, no futuro será seu site.
    private final String FRONT_URL = "http://localhost:4200/public/form?id=";

    private final HospedeRepository hospedeRepository;
    private final QuestionarioRepository questionarioRepository;


    public HospedeService(HospedeRepository hospedeRepository,  QuestionarioRepository questionarioRepository) {
        this.hospedeRepository = hospedeRepository;
        this.questionarioRepository = questionarioRepository;
    }

    // LISTAR TODOS (Agora devolve DTO com link)
    public List<HospedeResponseDto> findAll() {
        return hospedeRepository.findAll().stream()
                .map(this::converterParaDto) // Chama o método lá de baixo
                .collect(Collectors.toList());
    }

    // CRIAR (Agora devolve DTO com link)
    public HospedeResponseDto create(HospedeDto dto) {
        HospedeModel model = new HospedeModel();
        BeanUtils.copyProperties(dto, model);

        // Garante que o ID seja gerado se não vier (o banco gera, mas o save retorna preenchido)
        HospedeModel salvo = hospedeRepository.save(model);

        return converterParaDto(salvo);
    }

    // Deletar
    public void delete(UUID id) {
        hospedeRepository.deleteById(id);
    }

    // --- O CONVERSOR MÁGICO ---
// ... imports

    // MÉTODO NOVO: ATRIBUIR FORMULÁRIO
    public void atribuirQuestionario(UUID hospedeId, Long questionarioId) {
        HospedeModel hospede = hospedeRepository.findById(hospedeId)
                .orElseThrow(() -> new RuntimeException("Hóspede não encontrado"));

        // Se questionarioId for nulo, desatribui
        if (questionarioId == null) {
            hospede.setAssignedQuestionnaire(null);
        } else {
            // Busca o questionário no banco (precisa injetar o QuestionarioRepository)
            QuestionarioModel questionario = questionarioRepository.findById(questionarioId)
                    .orElseThrow(() -> new RuntimeException("Questionário não encontrado"));

            hospede.setAssignedQuestionnaire(questionario);
        }

        hospedeRepository.save(hospede);
    }

    // AJUSTE NO CONVERSOR (para mandar o ID pro front)
    private HospedeResponseDto converterParaDto(HospedeModel model) {
        HospedeResponseDto dto = new HospedeResponseDto();
        BeanUtils.copyProperties(model, dto);
        dto.setLinkAcesso(FRONT_URL + model.getId());

        // Manda o ID do form atribuído (se tiver)
        if (model.getAssignedQuestionnaire() != null) {
            dto.setAssignedFormId(model.getAssignedQuestionnaire().getId());
        }

        return dto;
    }
    // ATUALIZAR
    public HospedeResponseDto update(UUID id, HospedeDto dto) {
        // 1. Busca o hóspede existente. Se não achar, estoura erro.
        HospedeModel existente = hospedeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hóspede não encontrado com ID: " + id));

        // 2. Copia os dados novos do DTO para o Model existente
        // IMPORTANTE: Ignoramos a propriedade "id" para não alterar a chave primária
        BeanUtils.copyProperties(dto, existente, "id");

        // 3. Salva as alterações
        HospedeModel salvo = hospedeRepository.save(existente);

        // 4. Retorna o DTO com o link gerado
        return converterParaDto(salvo);
    }
    public QuestionarioDto buscarQuestionarioDoHospede(UUID hospedeId) {
        HospedeModel hospede = hospedeRepository.findById(hospedeId)
                .orElseThrow(() -> new RuntimeException("Link inválido ou hóspede não encontrado."));

        // Se quiser travar por data, descomente a linha abaixo:
        // validarAcessoAoFormulario(hospedeId);

        QuestionarioModel questionario = hospede.getAssignedQuestionnaire();
        if (questionario == null) {
            throw new RuntimeException("Nenhuma pesquisa foi atribuída para você.");
        }

        QuestionarioDto formDto = new QuestionarioDto();
        formDto.setTitle(questionario.getTitle());
        formDto.setDescription(questionario.getDescription());

        // Mapeia as perguntas
        List<PerguntaDto> perguntasDto = questionario.getQuestions().stream().map(p -> {
            PerguntaDto pDto = new PerguntaDto();
            pDto.setId(p.getId());
            pDto.setPrompt(p.getPrompt());
            pDto.setType(p.getType());
            pDto.setOrderIndex(p.getOrderIndex());
            pDto.setRequired(p.getRequired());
            return pDto;
        }).collect(Collectors.toList());

        formDto.setQuestions(perguntasDto);
        return formDto;
    }
}