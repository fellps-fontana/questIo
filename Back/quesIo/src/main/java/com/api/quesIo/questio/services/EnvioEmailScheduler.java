package com.api.quesIo.questio.services;

import com.api.quesIo.questio.Models.HospedeModel;
import com.api.quesIo.questio.Repositorys.HospedeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnvioEmailScheduler {

    private final HospedeRepository hospedeRepository;
    private final EmailService emailService;

    // Roda a cada 1 minuto
    @Scheduled(cron = "0 */1 * * * *")
    public void enviarEmailsCheckoutVencido() {

        List<HospedeModel> hospedes =
                hospedeRepository.buscarCheckoutVencidoNaoEnviado(LocalDateTime.now());

        for (HospedeModel h : hospedes) {
            try {
                emailService.enviarEmail(
                        h.getEmail(),
                        "Questionário de Avaliação",
                        "Olá " + h.getName() + ",\n\nObrigado pela hospedagem! ..."
                );

                h.setEmailSent(true);
                h.setEmailSent(true);
                hospedeRepository.save(h);

            } catch (Exception e) {
                System.out.println("Erro ao enviar email para: " + h.getEmail());
            }
        }
    }
}