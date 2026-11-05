package com.example;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import domain_layer.Cane;
import domain_layer.Cliente;
import domain_layer.PeriodoAffido;

/**
 * Unit tests for foster-period registration, conclusion, and subscription checks.
 */
class PeriodoAffidoTest {
    static PeriodoAffido pa;
    static Cane cn;

    @BeforeAll
    public static void initTest() {
        pa = new PeriodoAffido(1, LocalDate.now(), LocalDate.now().plusWeeks(2), 150.0f);
        cn = new Cane(0, "Dog", "Chihuahua");
    }

    @Test
    void testRegistraAffido() {
        // registraAffido performs the following steps:
        // 1) adds the dog to elencoCaniAffido and increases the list length;
        // 2) updates numPosti by decrementing it.

        // Retrieves the current values.
        int lunghezzaElencoCaniAffido = pa.getCaniAffido().size();
        int numPostiPrecedente = pa.getNumeroPosti();

        pa.registraAffido(cn);

        // Expected: the list length increases by one.
        assertEquals(lunghezzaElencoCaniAffido + 1, pa.getCaniAffido().size());

        // Expected: the number of available places decreases by one.
        assertEquals(numPostiPrecedente - 1, pa.getNumeroPosti());
    }

    @Test
    void testConculdiAffido() {
        // concludiAffido performs the following step:
        // 1) removes the dog from elencoCaniAffido and decreases the list length.

        // Adds a dog to a foster period.
        pa.registraAffido(cn);

        // Retrieves the current values.
        int lunghezzaPrecedente = pa.getCaniAffido().size();

        pa.concludiAffido(cn);

        // Expected: the list size decreases by one.
        assertEquals(lunghezzaPrecedente - 1, pa.getCaniAffido().size());
    }

    @Test
    void testVerificaIscrizione() {
        // verificaIscrizione checks
        // whether a dog owned by the customer is already
        // in foster care for this period.

        // Associates a dog with a new customer.
        Cliente cl = new Cliente("Giuseppe2", "Giuseppe", "Leocata", "0000", "0123456789");
        cl.registraCane("Doggino", "Carlino", 0);

        // Expected: false because cn is not in the period.

        assertFalse(pa.verificaIscrizione(cl));

        // Adds cn to the foster period.
        pa.registraAffido(cl.getCane(0));

        // Expected: true.
        assertTrue(pa.verificaIscrizione(cl));
    }
}
