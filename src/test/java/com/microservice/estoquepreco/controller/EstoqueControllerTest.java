package com.microservice.estoquepreco.controller;

import com.microservice.estoquepreco.service.RabbitMQService;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.librabbitmq.dto.EstoqueDTO;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("EstoqueController")
class EstoqueControllerTest {

    @Mock
    private RabbitMQService rabbitMQService;

    @InjectMocks
    private EstoqueController estoqueController;

    EstoqueDTO estoqueDTO;

    @Test
    @DisplayName("Deve alterar estoque")
    void deveAlterarEstoque() {
        estoqueDTO = mockEstoqueDTO();

        Mockito.doNothing().when(rabbitMQService).enviaMensagem(Mockito.anyString(), ArgumentMatchers.eq(estoqueDTO));

        ResponseEntity response = estoqueController.alteraEstoque(estoqueDTO);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Mockito.verify(rabbitMQService, Mockito.times(1))
                .enviaMensagem(Mockito.anyString(), ArgumentMatchers.eq(estoqueDTO));

    }

    private EstoqueDTO mockEstoqueDTO() {
        estoqueDTO = new EstoqueDTO();
        estoqueDTO.codigoProduto = "10";
        estoqueDTO.quantidade = 100;

        return estoqueDTO;
    }

}