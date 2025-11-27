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

    @Transactional // Garante que salva TUDO ou NADA (Rollback se der erro)
    public List<RespostaModel> salvarRespostas(UUID hospedeId, List<RespostaDto> listaRespostas) {

        // 1. Acha o hóspede
        HospedeModel hospede = hospedeRepository.findById(hospedeId)
                .orElseThrow(() -> new RuntimeException("Hóspede não encontrado"));

        List<RespostaModel> salvas = new ArrayList<>();

        // 2. Itera sobre cada resposta vinda do front
        for (RespostaDto dto : listaRespostas) {

            // Acha a pergunta no banco pelo ID que veio do front
            PerguntaModel pergunta = perguntaRepository.findById(dto.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Pergunta inválida ID: " + dto.getQuestionId()));

            // Cria o objeto para salvar
            RespostaModel modelo = new RespostaModel();
            modelo.setHospede(hospede);
            modelo.setPergunta(pergunta);
            modelo.setValor(dto.getValue()); // Salva como String

            salvas.add(respostaRepository.save(modelo));
        }

        return salvas;
    }
}