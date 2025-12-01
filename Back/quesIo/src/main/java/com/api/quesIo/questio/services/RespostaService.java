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

    @Transactional
    public List<RespostaModel> salvarRespostas(UUID hospedeId, List<RespostaDto> listaRespostas) {
        HospedeModel hospede = hospedeRepository.findById(hospedeId)
                .orElseThrow(() -> new RuntimeException("Hóspede não encontrado"));

        List<RespostaModel> salvas = new ArrayList<>();

        for (RespostaDto dto : listaRespostas) {
            PerguntaModel pergunta = perguntaRepository.findById(dto.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Pergunta inválida ID: " + dto.getQuestionId()));

            RespostaModel modelo = new RespostaModel();
            modelo.setHospede(hospede);
            modelo.setPergunta(pergunta);
            modelo.setValor(dto.getValue());

            salvas.add(respostaRepository.save(modelo));
        }
        return salvas;
    }

    public List<RespostaDto> listarPorFormulario(Long formId) {
        // CORREÇÃO AQUI: Use o nome do método com @Query que criamos no Repository
        List<RespostaModel> respostas = respostaRepository.buscarRespostasPorFormulario(formId);

        List<RespostaDto> listaRetorno = new ArrayList<>();

        for (RespostaModel r : respostas) {
            RespostaDto dto = new RespostaDto();

            PerguntaModel p = r.getPergunta();
            if (p != null) {
                dto.setQuestionId(p.getId());
            }

            dto.setValue(r.getValor());
            listaRetorno.add(dto);
        }

        return listaRetorno;
    }
}