package com.bykenyodarz.springboot_test.controllers;

import static org.springframework.http.HttpStatus.*;
import com.bykenyodarz.springboot_test.models.Cuenta;
import com.bykenyodarz.springboot_test.models.viewmodels.TransactionDTO;
import com.bykenyodarz.springboot_test.services.CuentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/cuentas")
public class CuentaController {

    private final CuentaService service;

    public CuentaController(CuentaService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public Cuenta detalle(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("/transferir")
    public ResponseEntity<?> transferir(@RequestBody TransactionDTO dto){
        service.transferir(dto.getCuentaOrigenId(), dto.getCuentaDestinoId(),
                dto.getMonto(), dto.getBancoId());

        Map<String, Object> response = new HashMap<>();

        response.put("date", LocalDate.now());
        response.put("status", "ACCEPTED");
        response.put("message", "Transferencia realizada con exito");
        response.put("transaction", dto);

        return ResponseEntity.status(ACCEPTED).body(response);

    }



}
