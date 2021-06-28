package com.bykenyodarz.springboot_test;

import com.bykenyodarz.springboot_test.exceptions.DineroInsuficienteException;
import com.bykenyodarz.springboot_test.models.Banco;
import com.bykenyodarz.springboot_test.models.Cuenta;
import com.bykenyodarz.springboot_test.repositories.BancoRepository;
import com.bykenyodarz.springboot_test.repositories.CuentaRepository;
import com.bykenyodarz.springboot_test.services.CuentaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static com.bykenyodarz.springboot_test.data.Dato.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class SpringbootTestApplicationTests {

    @MockBean
    CuentaRepository cuentaRepository;
    @MockBean
    BancoRepository bancoRepository;

    @Autowired
    CuentaService service;

//    @BeforeEach
//    void setUp() {
//        cuentaRepository = mock(CuentaRepository.class);
//        bancoRepository = mock(BancoRepository.class);
//        service = new CuentaServiceImpl(cuentaRepository, bancoRepository);
//    }

    @Test
    void contextLoads() {
        // Given
        when(cuentaRepository.findById(1L)).thenReturn(crearCuenta001());
        when(cuentaRepository.findById(2L)).thenReturn(crearCuenta002());
        when(bancoRepository.findById(1L)).thenReturn(crearBanco());

        var saldoOrigen = service.revisarSaldo(1L);
        var saldoDestino = service.revisarSaldo(2L);

        assertAll(
                () -> assertEquals("1000", saldoOrigen.toPlainString()),
                () -> assertEquals("2000", saldoDestino.toPlainString())
        );

        service.transferir(1L, 2L, new BigDecimal("100"), 1L);

        var saldoOrigen2 = service.revisarSaldo(1L);
        var saldoDestino2 = service.revisarSaldo(2L);
        var total = service.revisarTotalTransactions(1L);

        assertAll(
                () -> assertEquals("900", saldoOrigen2.toPlainString()),
                () -> assertEquals("2100", saldoDestino2.toPlainString()),
                () -> assertEquals(1, total)
        );

        /* All Verifies */
        verify(cuentaRepository, times(3)).findById(1L);
        verify(cuentaRepository, times(3)).findById(2L);
        verify(cuentaRepository, times(2)).save(any(Cuenta.class));

        verify(bancoRepository, times(2)).findById(1L);
        verify(bancoRepository).save(any(Banco.class));
        /* Verify never */

        verify(cuentaRepository, never()).findAll();
        verify(cuentaRepository, times(6)).findById(anyLong());
    }

    @Test
    void contextLoads2() {
        // Given
        when(cuentaRepository.findById(1L)).thenReturn(crearCuenta001());
        when(cuentaRepository.findById(2L)).thenReturn(crearCuenta002());
        when(bancoRepository.findById(1L)).thenReturn(crearBanco());

        var saldoOrigen = service.revisarSaldo(1L);
        var saldoDestino = service.revisarSaldo(2L);

        assertAll(
                () -> assertEquals("1000", saldoOrigen.toPlainString()),
                () -> assertEquals("2000", saldoDestino.toPlainString()),
                () -> assertThrows(DineroInsuficienteException.class, () -> {
                    service.transferir(1L, 2L, new BigDecimal("1200"), 1L);
                })
//                () -> assertThrows(DineroInsuficienteException.class, () -> {
//                    service.findById(1L);
//                })
        );

        var saldoOrigen2 = service.revisarSaldo(1L);
        var saldoDestino2 = service.revisarSaldo(2L);
        var total = service.revisarTotalTransactions(1L);

        assertAll(
                () -> assertEquals("1000", saldoOrigen2.toPlainString()),
                () -> assertEquals("2000", saldoDestino2.toPlainString()),
                () -> assertEquals(0, total)
        );

        /* All Verifies */
        verify(cuentaRepository, times(3)).findById(1L);
        verify(cuentaRepository, times(2)).findById(2L);
        verify(cuentaRepository, never()).save(any(Cuenta.class));

        verify(bancoRepository, times(1)).findById(1L);
        verify(bancoRepository, never()).save(any(Banco.class));

        /* Verify never */

        verify(cuentaRepository, never()).findAll();
        verify(cuentaRepository, times(5)).findById(anyLong());
    }

    @Test
    void contextLoads3() {
        // Given
        when(cuentaRepository.findById(1L)).thenReturn(crearCuenta001());

        var cuenta1 = service.findById(1L);
        var cuenta2 = service.findById(1L);

        assertSame(cuenta1, cuenta2);
        verify(cuentaRepository, times(2)).findById(1L);

    }
}
