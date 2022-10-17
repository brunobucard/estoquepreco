package com.microservice.estoquepreco.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("RabbitMQService")
class RabbitMQServiceTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private RabbitMQService rabbitMQService;

    @Test
    @DisplayName("Deve enviar a mensagem com sucesso")
    void deveEnviarMensagemComSucesso() {
        String mensagem = "Teste";
        String nomeFila = "FILA_ESTOQUE";

        Mockito.doNothing().when(rabbitTemplate).convertAndSend(ArgumentMatchers.eq(nomeFila) , ArgumentMatchers.eq(mensagem));

        rabbitMQService.enviaMensagem(nomeFila, mensagem);

        Mockito.verify(rabbitTemplate, Mockito.times(1)).convertAndSend(ArgumentMatchers.eq(nomeFila) , ArgumentMatchers.eq(mensagem));
    }
}