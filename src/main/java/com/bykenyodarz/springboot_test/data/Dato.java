package com.bykenyodarz.springboot_test.data;

import com.bykenyodarz.springboot_test.models.Banco;
import com.bykenyodarz.springboot_test.models.Cuenta;

import java.math.BigDecimal;

public class Dato {
//    public static final Cuenta CUENTA_001 = new Cuenta(1L, "Andres", new BigDecimal("1000"));
//    public static final Cuenta CUENTA_002 = new Cuenta(2L, "Jhon", new BigDecimal("2000"));
//    public static final Banco BANCO = new Banco(1L, "El Banco del Estado", 0);

    public static Cuenta crearCuenta001() {
        return new Cuenta(1L, "Andres", new BigDecimal("1000"));
    }

    public static Cuenta crearCuenta002() {
        return new Cuenta(2L, "Jhon", new BigDecimal("2000"));
    }

    public static Banco crearBanco() {
        return new Banco(1L, "El Banco del Estado", 0);
    }




}
