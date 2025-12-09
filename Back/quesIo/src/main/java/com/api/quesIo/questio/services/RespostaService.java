package com.api.quesIo.questio.services;

import com.api.quesIo.questio.Models.HospedeModel;
import com.api.quesIo.questio.Models.PerguntaModel;
import com.api.quesIo.questio.Models.RespostaModel;
import com.api.quesIo.questio.Repositorys.HospedeRepository;
import com.api.quesIo.questio.Repositorys.PerguntaRepository;
import com.api.quesIo.questio.Repositorys.RespostaRepository;
import com.api.quesIo.questio.dtos.RespostaDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RespostaService {

    private final RespostaRepository respostaRepository;
    private final HospedeRepository hospedeRepository;
    private final PerguntaRepository perguntaRepository;

    public RespostaService(RespostaRepository rr, HospedeRepository hr, PerguntaRepository pr) {
        this.respostaRepository = rr;
        this.hospedeRepository = hr;
        this.perguntaRepository = pr;
    }

    // --- NOVO MÉTODO: O QUE FALTAVA ---
    // Esse é o método que o Controller /lote está chamando
// No método salvarRespostasEmLote
    @Transactional
    public void salvarRespostasEmLote(List<RespostaDto> dtos) {
        if (dtos == null || dtos.isEmpty()) return;

        UUID guestId = dtos.get(0).getGuestId();

        // 1. Salva as respostas (Lógica antiga)
        salvarRespostas(guestId, dtos);

        // 2. ATUALIZA O STATUS DO HÓSPEDE (NOVO)
        HospedeModel hospede = hospedeRepository.findById(guestId)
                .orElseThrow(() -> new RuntimeException("Hóspede não encontrado"));

        hospede.setResponded(true); // Marca como respondido
        hospedeRepository.save(hospede);
    }

    // --- SEU MÉTODO EXISTENTE (Mantido) ---
    @Transactional
    public List<RespostaModel> salvarRespostas(UUID hospedeId, List<RespostaDto> listaRespostas) {
        HospedeModel hospede = hospedeRepository.findById(hospedeId)
                .orElseThrow(() -> new RuntimeException("Hóspede não encontrado"));

        List<RespostaModel> salvas = new ArrayList<>();

        for (RespostaDto dto : listaRespostas) {
            PerguntaModel pergunta = perguntaRepository.findById(dto.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Pergunta inválida ID: " + dto.getQuestionId()));

            RespostaModel modelo = new RespostaModel();
            modelo.setGuest(hospede);
            modelo.setQuestion(pergunta);
            modelo.setValue(dto.getValue());

            salvas.add(respostaRepository.save(modelo));
        }
        return salvas;
    }

    public List<RespostaDto> listarPorFormulario(Long formId) {
        List<RespostaModel> respostas = respostaRepository.buscarRespostasPorFormulario(formId);

        List<RespostaDto> listaRetorno = new ArrayList<>();

        for (RespostaModel r : respostas) {
            RespostaDto dto = new RespostaDto();

            // Preenche o guestId para o admin saber de quem é a resposta
            if (r.getGuest() != null) {
                dto.setGuestId(r.getGuest().getId());
            }

            PerguntaModel p = r.getQuestion();
            if (p != null) {
                dto.setQuestionId(p.getId());
            }

            dto.setValue(r.getValue());
            listaRetorno.add(dto);
        }

        return listaRetorno;
    }
}