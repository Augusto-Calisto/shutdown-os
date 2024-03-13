package br.com.tech.model;

import java.io.IOException;

public class Linux implements ISistemaOperacional {

    @Override
    public long desligar(long segundos) throws IOException {
        long minutos = segundos / 60;

        Process process = Runtime.getRuntime().exec("sudo shutdown -h " + minutos);

        return process.pid();
    }

    @Override
    public long cancelar() throws IOException {
        Process process = Runtime.getRuntime().exec("sudo shutdown -c");

        return process.pid();
    }

    @Override
    public long reiniciar(long segundos) throws IOException {
        long minutos = segundos / 60;

        Process process = Runtime.getRuntime().exec("sudo shutdown -r +" + minutos);

        return process.pid();
    }

    @Override
    public long hibernar(long segundos) throws IOException {
        Process process = Runtime.getRuntime().exec("sudo systemctl hibernate");

        return process.pid();
    }
}