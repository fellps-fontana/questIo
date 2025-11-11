package com.api.quesIo.clientes.services;

import com.api.quesIo.clientes.Models.FuncaoModel;
import com.api.quesIo.clientes.Repositorys.FuncaoRepository;
import com.api.quesIo.clientes.dtos.FuncaoDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FuncaoService {

    private final FuncaoRepository funcaoRepository;

    public FuncaoService(FuncaoRepository funcaoRepository) {
        this.funcaoRepository = funcaoRepository;
    }

    // Criar nova função
    public FuncaoModel create(FuncaoDto funcaoDto) {
        FuncaoModel funcao = new FuncaoModel();
        funcao.setNome(funcaoDto.getNome());
        return funcaoRepository.save(funcao);
    }

    // Listar todas as funções
    public List<FuncaoModel> findAll() {
        return funcaoRepository.findAll();
    }

    // Atualizar função existente
    public FuncaoModel update(FuncaoDto funcaoDto, UUID id) {
        FuncaoModel existente = funcaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Função não encontrada"));
        existente.setNome(funcaoDto.getNome());
        return funcaoRepository.save(existente);
    }

    // Deletar função
    public void delete(UUID id) {
        FuncaoModel existente = funcaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Função não encontrada"));
        funcaoRepository.deleteById(existente.getId());
    }
}
