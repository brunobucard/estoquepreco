package com.microservice.estoquepreco.controller;

import com.microservice.estoquepreco.service.RabbitMQService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.librabbitmq.dto.PrecoDTO;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Preco")
class PrecoControllerTest {

    @Mock
    private RabbitMQService rabbitMQService;

    @InjectMocks
    private PrecoController precoController;

    PrecoDTO precoDTO;

    @Test
    @DisplayName("Deve alterar preço")
    void deveAlteraPreco() {
        precoDTO = mockPreDTO();

        Mockito.doNothing().when(rabbitMQService).enviaMensagem(Mockito.anyString(), ArgumentMatchers.eq(precoDTO));

        ResponseEntity response = precoController.alteraPreco(precoDTO);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Mockito.verify(rabbitMQService, Mockito.times(1))
                .enviaMensagem(Mockito.anyString(), ArgumentMatchers.eq(precoDTO));
    }

    private PrecoDTO mockPreDTO() {
        precoDTO = new PrecoDTO();
        precoDTO.preco = 10.00;
        precoDTO.codigoProduto = "10";

        return precoDTO;
    }
}