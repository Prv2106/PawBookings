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


class PeriodoAffidoTest {
    static PeriodoAffido pa;
    static Cliente cl;
    static Cane cn;

    @BeforeAll
    public static void initTest() {
        pa = new PeriodoAffido(1, LocalDate.now(), LocalDate.now().plusWeeks(2), 150.0f);
        cl = new Cliente("Giuseppe2", "Giuseppe", "Leocata", "0000", "0123456789");
        cn = new Cane(0, "Dog", "Chihuahua");
    }


    @Test
    void testRegistraAffido() {
        // il metodo 'registraAffido' si preoccupa di:
        // 1) aggiungere un cane alla lista 'elencoCaniAffido' -> incremento lunghezza LinkedList
        // 2) aggiornare l'attributo 'numPosti' della classe -> decremento intero
                
        // recuperiamo i valori attuali (che saranno precedenti)
        int lunghezzaElencoCaniAffido = pa.getCaniAffido().size();
        int numPostiPrecedente = pa.getNumeroPosti();
       
        // invochiamo il metodo sotto test
        pa.registraAffido(cn);

        // ci aspettiamo, quindi, che la lunghezza della lista sia maggiorata di 1
        assertEquals(lunghezzaElencoCaniAffido + 1, pa.getCaniAffido().size());

        // e che sia diminuito il numero di posti
        assertEquals(numPostiPrecedente - 1, pa.getNumeroPosti());
    }

    @Test
    void testConculdiAffido() {
        // il metodo 'concludiAffido' si preoccupa di:
        // 1) rimuovere un cane dalla lista 'elencoCaniAffido' -> decremento lunghezza della lista
        
        // aggiungiamo un cane ad un periodo di affido
        pa.registraAffido(cn);

        // recuperiamo il valori attuale
        int lunghezzaPrecedente = pa.getCaniAffido().size();

        // invochiamo il metodo da testare
        pa.concludiAffido(cn);

        // ci aspettiamo che la lista sia decrementata in dimensione
        assertEquals(lunghezzaPrecedente - 1, pa.getCaniAffido().size());
    }

    @Test
    void testVerificaIscrizione() {
        // il metodo 'verificaIscrizione' si occupa di controllare
        // se uno tra i cani posseduti da un cliente sia già 
        // in affido per questo periodo di affido

        // associamo un cane ad un cliente
        cl.addCane(cn);

        // invocando ora il metodo, ci aspettiamo che il metodo restituisca false
        // in quanto cn non è nell'elenco dei cani in affido in pa
        assertFalse(pa.verificaIscrizione(cl));

        // ora aggiungiamo cn al periodo affido
        pa.registraAffido(cn);

        // ci aspettiamo adesso, invece, true
        assertTrue(pa.verificaIscrizione(cl));
    }
}
