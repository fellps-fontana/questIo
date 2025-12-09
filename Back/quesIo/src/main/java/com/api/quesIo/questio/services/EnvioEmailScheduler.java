package com.api.quesIo.questio.services;

import com.api.quesIo.questio.Models.HospedeModel;
import com.api.quesIo.questio.Repositorys.HospedeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnvioEmailScheduler {

    private final HospedeRepository hospedeRepository;
    private final EmailService emailService;

    // URL do Front
    private final String FRONTEND_URL = "http://localhost:4200";

    @Scheduled(cron = "*/10 * * * * *")
    @EventListener(ApplicationReadyEvent.class)
    public void enviarEmailsCheckoutVencido() {

        LocalDateTime agora = LocalDateTime.now();

        // Comentei o log de hora para não poluir tanto, já que roda a cada 10s
        // System.out.println("⏰ [Scheduler] Hora: " + agora);

        List<HospedeModel> listaPendentes =
                hospedeRepository.findByCheckOutBeforeAndEmailSentFalse(agora);

        if (listaPendentes.isEmpty()) return;

        System.out.println("[Scheduler] Verificando " + listaPendentes.size() + " pendentes...");

        for (HospedeModel hospede : listaPendentes) {
            try {
                // --- CORREÇÃO DE LÓGICA ---
                // Se não tem questionário, APENAS PULA.
                // Não marcamos como enviado, assim ele tenta de novo na próxima rodada.
                if (hospede.getAssignedQuestionnaire() == null) {
                    // Log opcional (pode comentar se ficar enchendo o saco no console)
                    System.out.println("⏳ Hóspede " + hospede.getName() + " sem questionário. Aguardando atribuição...");
                    continue;
                }

                // Se chegou aqui, tem questionário!
                String link = FRONTEND_URL + "/responder/" + hospede.getId();

                String assunto = "Pesquisa de Satisfação - Questio";
                String mensagem = String.format(
                        "Olá %s,\n\n" +
                                "Obrigado por sua estadia! Esperamos que tenha sido excelente.\n" +
                                "Por favor, responda nossa pesquisa de satisfação sobre o formulário '%s':\n\n" +
                                "%s\n\n" +
                                "Atenciosamente,\nEquipe Questio",
                        hospede.getName(),
                        hospede.getAssignedQuestionnaire().getTitle(),
                        link
                );

                emailService.enviarEmail(hospede.getEmail(), assunto, mensagem);

                // Agora sim, enviou com sucesso, marca como true para não enviar mais
                hospede.setEmailSent(true);
                hospedeRepository.save(hospede);

                System.out.println("✅ E-mail enviado para: " + hospede.getEmail());

            } catch (Exception e) {
                System.err.println("❌ Erro ao enviar para " + hospede.getEmail() + ": " + e.getMessage());
            }
        }
    }
}