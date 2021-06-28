package com.bykenyodarz.springboot_test;

import com.bykenyodarz.springboot_test.models.Cuenta;
import com.bykenyodarz.springboot_test.repositories.CuentaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class IntegrationJpaTest {

    @Autowired
    CuentaRepository cuentaRepository;

    @Test
    void testFindById() {
        var cuenta = cuentaRepository.findById(1L);
        assertAll(
                () -> assertTrue(cuenta.isPresent()),
                () -> assertEquals("Andres", cuenta.orElseThrow().getPersona())
        );
    }

    @Test
    void testFindByPersona() {
        var cuenta = cuentaRepository.findByPersona("Jhon");
        assertAll(
                () -> assertTrue(cuenta.isPresent()),
                () -> assertEquals(2L, cuenta.orElseThrow().getId()),
                () -> assertEquals("2000.00", cuenta.orElseThrow().getSaldo().toPlainString())
        );
    }

    @Test
    void testFindByPersonaThrowException() {
        var cuenta = cuentaRepository.findByPersona("Juan");
        assertAll(
                () -> assertThrows(NoSuchElementException.class, cuenta::orElseThrow),
                () -> assertFalse(cuenta.isPresent())
        );
    }

    @Test
    void testFindAll() {
        var cuentas = cuentaRepository.findAll();
        assertAll(
                () -> assertFalse(cuentas.isEmpty()),
                () -> assertEquals(2, cuentas.size())
        );
    }

    @Test
    void testSave() {
        // Given
        var cuentaJorge = new Cuenta(null, "Jorge", new BigDecimal("3000"));

        cuentaRepository.save(cuentaJorge);

        // When
        var cuenta = cuentaRepository.findByPersona("Jorge").orElseThrow();

        assertAll(
                () -> assertEquals("Jorge", cuenta.getPersona()),
                () -> assertEquals("3000", cuenta.getSaldo().toPlainString()),
                () -> assertNotNull(cuenta.getId())
        );

    }

    @Test
    void testUpdateAndDelete() {
        // Given
        var cuentaJorge = new Cuenta(null, "Jorge", new BigDecimal("3000"));

        cuentaRepository.save(cuentaJorge);

        // When
        var cuenta = cuentaRepository.findByPersona("Jorge").orElseThrow();

        assertAll(
                () -> assertEquals("Jorge", cuenta.getPersona()),
                () -> assertEquals("3000", cuenta.getSaldo().toPlainString()),
                () -> assertNotNull(cuenta.getId())
        );

        cuenta.setPersona("Juan");
        cuenta.setSaldo(new BigDecimal("4000"));

        var nuevaCuenta = cuentaRepository.save(cuenta);

        assertAll(
                () -> assertEquals("Juan", nuevaCuenta.getPersona()),
                () -> assertEquals("4000", nuevaCuenta.getSaldo().toPlainString()),
                () -> assertNotNull(nuevaCuenta.getId())
        );

        cuentaRepository.delete(nuevaCuenta);

        var cuentaBorrada = cuentaRepository.findByPersona("Juan");

        assertAll(
                () -> assertThrows(NoSuchElementException.class, cuentaBorrada::orElseThrow),
                () -> assertFalse(cuentaBorrada.isPresent())
        );

    }
}
