package com.api.quesIo.questio.services;

import com.api.quesIo.questio.Models.HospedeModel;
import com.api.quesIo.questio.Repositorys.HospedeRepository;
import com.api.quesIo.questio.dtos.HospedeDto;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils; // Dica pro

import java.util.List;
import java.util.UUID;

@Service
public class HospedeService {

    private final HospedeRepository hospedeRepository;

    public HospedeService(HospedeRepository hospedeRepository) {
        this.hospedeRepository = hospedeRepository;
    }

    public HospedeModel create(HospedeDto hospedeDto) {
        HospedeModel hospedeModel = new HospedeModel();
        // DICA DE OURO: BeanUtils copia propriedades com mesmo nome automaticamente!
        // Copia name -> name, email -> email, room -> room...
        BeanUtils.copyProperties(hospedeDto, hospedeModel);

        return hospedeRepository.save(hospedeModel);
    }

    public List<HospedeModel> findAll() {
        return hospedeRepository.findAll();
    }

    public void delete(UUID id) {
        if (!hospedeRepository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado");
        }
        hospedeRepository.deleteById(id);
    }

    public HospedeModel update(HospedeDto hospedeDto, UUID id) {
        HospedeModel existente = hospedeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Copia tudo do DTO para o Model existente, exceto o ID (para não quebrar)
        BeanUtils.copyProperties(hospedeDto, existente, "id");

        return hospedeRepository.save(existente);
    }
}