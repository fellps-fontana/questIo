package com.api.quesIo.questio.services;

import com.api.quesIo.questio.Models.HospedeModel;
import com.api.quesIo.questio.Models.QuestionarioModel;
import com.api.quesIo.questio.Repositorys.HospedeRepository;
import com.api.quesIo.questio.Repositorys.QuestionarioRepository;
import com.api.quesIo.questio.dtos.HospedeDto;
import com.api.quesIo.questio.dtos.HospedeResponseDto;
import com.api.quesIo.questio.dtos.PerguntaDto;
import com.api.quesIo.questio.dtos.QuestionarioDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HospedeService {

    // URL para onde o hóspede vai quando clica no link (tem que bater com a rota do Angular)
    // No email scheduler definimos como /responder/, então vamos manter padrão aqui
    private final String FRONT_URL = "http://localhost:4200/responder/";

    private final HospedeRepository hospedeRepository;
    private final QuestionarioRepository questionarioRepository;

    public HospedeService(HospedeRepository hospedeRepository, QuestionarioRepository questionarioRepository) {
        this.hospedeRepository = hospedeRepository;
        this.questionarioRepository = questionarioRepository;
    }

    // --- LISTAR TODOS ---
    public List<HospedeResponseDto> findAll() {
        return hospedeRepository.findAll().stream()
                .map(this::converterParaDto)
                .collect(Collectors.toList());
    }

    // --- CRIAR (BLINDADO) ---
    public HospedeResponseDto create(HospedeDto dto) {
        HospedeModel model = new HospedeModel();

        // Mapeamento MANUAL (Garante que CheckIn/Out sejam salvos)
        model.setName(dto.getName());
        model.setEmail(dto.getEmail());
        model.setPhone(dto.getPhone());
        model.setRoom(dto.getRoom());
        model.setCheckIn(dto.getCheckIn());   // Crítico para o agendador
        model.setCheckOut(dto.getCheckOut()); // Crítico para o agendador

        // Inicializa como false, pois acabou de criar
        model.setEmailSent(false);

        HospedeModel salvo = hospedeRepository.save(model);
        return converterParaDto(salvo);
    }

    // --- ATUALIZAR ---
    public HospedeResponseDto update(UUID id, HospedeDto dto) {
        HospedeModel existente = hospedeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hóspede não encontrado com ID: " + id));

        // Atualiza os campos
        existente.setName(dto.getName());
        existente.setEmail(dto.getEmail());
        existente.setPhone(dto.getPhone());
        existente.setRoom(dto.getRoom());
        existente.setCheckIn(dto.getCheckIn());
        existente.setCheckOut(dto.getCheckOut());

        // Não alteramos o emailSent no update (geralmente), a não ser que queira reenviar

        HospedeModel salvo = hospedeRepository.save(existente);
        return converterParaDto(salvo);
    }

    // --- DELETAR ---
    public void delete(UUID id) {
        hospedeRepository.deleteById(id);
    }

    // --- ATRIBUIR QUESTIONÁRIO ---
    public void atribuirQuestionario(UUID hospedeId, Long questionarioId) {
        HospedeModel hospede = hospedeRepository.findById(hospedeId)
                .orElseThrow(() -> new RuntimeException("Hóspede não encontrado"));

        if (questionarioId == null) {
            hospede.setAssignedQuestionnaire(null);
        } else {
            QuestionarioModel questionario = questionarioRepository.findById(questionarioId)
                    .orElseThrow(() -> new RuntimeException("Questionário não encontrado"));

            hospede.setAssignedQuestionnaire(questionario);
        }
        hospedeRepository.save(hospede);
    }

    // --- BUSCAR QUESTIONÁRIO DO HÓSPEDE (Para o Front responder) ---
    public QuestionarioDto buscarQuestionarioDoHospede(UUID hospedeId) {
        HospedeModel hospede = hospedeRepository.findById(hospedeId)
                .orElseThrow(() -> new RuntimeException("Link inválido ou hóspede não encontrado."));

        // --- TRAVA DE SEGURANÇA ---
        if (hospede.isResponded()) {
            // Lança um erro específico que vamos pegar no Front
            throw new RuntimeException("RESPONDIDO");
        }
        QuestionarioModel questionario = hospede.getAssignedQuestionnaire();

        if (questionario == null) {
            // Se não tiver específico, pode buscar um padrão ou lançar erro
            throw new RuntimeException("Nenhuma pesquisa foi atribuída para você.");
        }

        if (!questionario.isActive()) {
            throw new RuntimeException("Este questionário não está mais ativo.");
        }

        // Converte para DTO
        QuestionarioDto formDto = new QuestionarioDto();
        formDto.setId(questionario.getId()); // Importante mandar o ID do form
        formDto.setTitle(questionario.getTitle());
        formDto.setDescription(questionario.getDescription());

        List<PerguntaDto> perguntasDto = questionario.getQuestions().stream().map(p -> {
            PerguntaDto pDto = new PerguntaDto();
            pDto.setId(p.getId());
            pDto.setPrompt(p.getPrompt()); // Enunciado
            pDto.setType(p.getType());     // Enum ou String
            pDto.setOrderIndex(p.getOrderIndex());
            pDto.setRequired(p.getRequired());
            return pDto;
        }).collect(Collectors.toList());

        formDto.setQuestions(perguntasDto);
        return formDto;
    }

    // --- CONVERSOR INTERNO ---
    private HospedeResponseDto converterParaDto(HospedeModel model) {
        HospedeResponseDto dto = new HospedeResponseDto();

        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setEmail(model.getEmail());
        dto.setPhone(model.getPhone());
        dto.setRoom(model.getRoom());
        dto.setCheckIn(model.getCheckIn());
        dto.setCheckOut(model.getCheckOut());
        dto.setEmailSent(model.isEmailSent());

        // Gera o link usando o ID do hóspede
        // Ex: http://localhost:4200/responder/550e8400-e29b...
        dto.setLinkAcesso(FRONT_URL + model.getId());

        if (model.getAssignedQuestionnaire() != null) {
            dto.setAssignedFormId(model.getAssignedQuestionnaire().getId());
        }

        return dto;
    }
}