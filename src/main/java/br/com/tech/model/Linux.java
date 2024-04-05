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
    public void cancelar(long pid) throws IOException {
        Runtime.getRuntime().exec("sudo kill -9 " + pid);

        Runtime.getRuntime().exec("sudo shutdown -c");
    }

    @Override
    public long reiniciar(long segundos) throws IOException {
        long minutos = segundos / 60;

        Process process = Runtime.getRuntime().exec("sudo shutdown -r +" + minutos);

        return process.pid();
    }

    @Override
    public long hibernar(long segundos) throws IOException {
        Process process = Runtime.getRuntime().exec("sudo sleep " + segundos + " && systemctl hibernate");
                
        return process.pid();
    }
}