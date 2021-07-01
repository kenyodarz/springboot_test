package com.bykenyodarz.springboot_test.controllers;

import com.bykenyodarz.springboot_test.models.Cuenta;
import com.bykenyodarz.springboot_test.models.viewmodels.TransactionDTO;
import com.bykenyodarz.springboot_test.services.CuentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    private final CuentaService service;

    public CuentaController(CuentaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public Cuenta detalle(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Cuenta cuenta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cuenta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        var cuenta = service.findById(id);
        if (cuenta == null) {
         return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body("La cuenta ha sido eliminada correctamente");

    }

    @PostMapping("/transferir")
    public ResponseEntity<?> transferir(@RequestBody TransactionDTO dto) {
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
