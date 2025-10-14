package com.api.quesIo.clientes.services;

import com.api.quesIo.clientes.Models.ClienteModel;
import com.api.quesIo.clientes.Repositorys.ClienteRepository;
import com.api.quesIo.clientes.dtos.ClienteDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {
    private ClienteRepository clienteRepository;
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ClienteModel create (ClienteDto clienteDto) {
        ClienteModel clienteModel = new ClienteModel();
        clienteModel.setNome(clienteDto.getNome());
        clienteModel.setEmail(clienteDto.getEmail());
        clienteModel.setTelefone(clienteDto.getTelefone());
        clienteModel.setEndereco(clienteDto.getEndereco());
        clienteModel.setCnpj(clienteDto.getCnpj());
        return clienteRepository.save(clienteModel);
    }

    public List<ClienteModel> findAll() { return clienteRepository.findAll(); }

    public void delete (UUID id) {
        ClienteModel existente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        clienteRepository.deleteById(existente.getId());
    }


    public ClienteModel update (ClienteDto clienteDto, UUID id) {
        ClienteModel existente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        existente.setNome(clienteDto.getNome());
        existente.setEmail(clienteDto.getEmail());
        existente.setTelefone(clienteDto.getTelefone());
        existente.setEndereco(clienteDto.getEndereco());
        return clienteRepository.save(existente);

    }
}
