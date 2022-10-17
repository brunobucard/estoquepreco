package com.microservice.estoquepreco.controller;

import com.microservice.estoquepreco.service.RabbitMQService;
import org.librabbitmq.constantes.RabbitMQConstantes;
import org.librabbitmq.dto.PrecoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/preco")
public class PrecoController {

    @Autowired
    private RabbitMQService rabbitMQService;

    @PutMapping
    public ResponseEntity alteraPreco(@RequestBody PrecoDTO precoDTO) {
        this.rabbitMQService.enviaMensagem(RabbitMQConstantes.FILA_PRECO, precoDTO);
        return new ResponseEntity(HttpStatus.OK);
    }
}
