package com.api.quesIo.clientes.services;

import com.api.quesIo.clientes.Models.EmpresaModel;
import com.api.quesIo.clientes.Repositorys.EmpresaRepository;
import com.api.quesIo.clientes.dtos.EmpresaDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    // Criar nova empresa
    public EmpresaModel create(EmpresaDto empresaDto) {
        EmpresaModel empresaModel = new EmpresaModel();
        empresaModel.setNome(empresaDto.getNome());
        empresaModel.setCnpj(empresaDto.getCnpj());
        empresaModel.setEmail(empresaDto.getEmail());
        empresaModel.setTelefone(empresaDto.getTelefone());
        return empresaRepository.save(empresaModel);
    }

    // Listar todas as empresas
    public List<EmpresaModel> findAll() {
        return empresaRepository.findAll();
    }

    // Atualizar empresa existente
    public EmpresaModel update(EmpresaDto empresaDto, UUID id) {
        EmpresaModel existente = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        existente.setNome(empresaDto.getNome());
        existente.setCnpj(empresaDto.getCnpj());
        existente.setEmail(empresaDto.getEmail());
        existente.setTelefone(empresaDto.getTelefone());

        return empresaRepository.save(existente);
    }

    // Excluir empresa
    public void delete(UUID id) {
        EmpresaModel existente = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        empresaRepository.deleteById(existente.getId());
    }
}
