package com.bykenyodarz.springboot_test.controllers;

import com.bykenyodarz.springboot_test.models.Cuenta;
import com.bykenyodarz.springboot_test.models.viewmodels.TransactionDTO;
import com.bykenyodarz.springboot_test.services.CuentaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bykenyodarz.springboot_test.data.Dato.crearCuenta001;
import static com.bykenyodarz.springboot_test.data.Dato.crearCuenta002;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CuentaController.class)
class CuentaControllerTest {

    ObjectMapper mapper;
    @Autowired
    private MockMvc mvc;
    @MockBean
    private CuentaService cuentaService;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void testDetalle() throws Exception {
        // Given Context
        when(cuentaService.findById(1L)).thenReturn(crearCuenta001().orElseThrow());

        // When
        mvc.perform(get("/api/cuentas/1").contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.persona").value("Andres"))
                .andExpect(jsonPath("$.saldo").value("1000"));
    }

    @Test
    void testTransferir() throws Exception {
        // Given
        var dto = new TransactionDTO();
        dto.setCuentaOrigenId(1L);
        dto.setCuentaDestinoId(2L);
        dto.setMonto(new BigDecimal("100"));
        dto.setBancoId(1L);
        Map<String, Object> response = new HashMap<>();

        response.put("date", LocalDate.now());
        response.put("status", "ACCEPTED");
        response.put("message", "Transferencia realizada con exito");
        response.put("transaction", dto);

        // When
        mvc.perform(post("/api/cuentas/transferir")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                //Then
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Transferencia realizada con exito"))
                .andExpect(jsonPath("$.transaction.cuentaOrigenId").value(dto.getCuentaOrigenId()));
    }

    @Test
    void testListar() throws Exception {
        // Given
        List<Cuenta> cuentas = Arrays.asList(
                crearCuenta001().orElseThrow(),
                crearCuenta002().orElseThrow()
        );
        when(cuentaService.findAll()).thenReturn(cuentas);

        // When
        mvc.perform(get("/api/cuentas").contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].persona").value("Andres"))
                .andExpect(jsonPath("$[0].saldo").value("1000"))
                .andExpect(jsonPath("$[1].persona").value("Jhon"))
                .andExpect(jsonPath("$[1].saldo").value("2000"))
                .andExpect(jsonPath("$", hasSize(2)));
    }


    @Test
    void testGuardar() throws Exception {
        // Given
        var cuenta = new Cuenta(null, "Jane", new BigDecimal("3000"));
        when(cuentaService.save(any())).thenReturn(cuenta);
        //When
        mvc.perform(post("/api/cuentas").contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(cuenta)))
                // Then
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.persona").value("Jane"))
                .andExpect(jsonPath("$.saldo").value("3000"));
    }
}