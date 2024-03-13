package br.com.tech.model;

import java.io.IOException;

public interface ISistemaOperacional {
    long desligar(long segundos) throws IOException;
    long cancelar() throws IOException;
    long hibernar(long segundos) throws IOException;
    long reiniciar(long segundos) throws IOException;
}