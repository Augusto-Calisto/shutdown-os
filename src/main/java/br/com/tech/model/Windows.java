package br.com.tech.model;

import java.io.IOException;

public class Windows implements ISistemaOperacional {
	
    @Override
    public long desligar(long segundos) throws IOException {		
        Process process = Runtime.getRuntime().exec("shutdown /s /t " + segundos);

        return process.pid();
    }

    @Override
    public long cancelar() throws IOException {
        Process process = Runtime.getRuntime().exec("shutdown /a");

        return process.pid();
    }

    @Override
    public long reiniciar(long segundos) throws IOException {
        Process process = Runtime.getRuntime().exec("shutdown /r /t " + segundos);

        return process.pid();
    }

    @Override
    public long hibernar(long segundos) throws IOException {
        Process process = Runtime.getRuntime().exec("shutdown /h /t " + segundos);

        return process.pid();
    }
}