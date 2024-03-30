package br.com.tech.model;

import java.io.IOException;

public class Windows implements ISistemaOperacional {
    
    @Override
    public long desligar(long segundos) throws IOException {		
        Process process = Runtime.getRuntime().exec("shutdown /s /t " + segundos);

        return process.pid();
    }

    @Override
    public void cancelar(long pid) throws IOException {        
        Runtime.getRuntime().exec("taskkill /F /PID " + pid);
        
        Runtime.getRuntime().exec("shutdown /a");
    }

    @Override
    public long reiniciar(long segundos) throws IOException {
        Process process = Runtime.getRuntime().exec("shutdown /r /t " + segundos);

        return process.pid();
    }

    @Override
    public long hibernar(long segundos) throws IOException {        
        String[] comandos = {"powershell.exe", "-Command", "sleep " + segundos + " | shutdown /h"};

        ProcessBuilder processBuilder = new ProcessBuilder(comandos);  
        
        Process process = processBuilder.start(); 
                
        return process.pid();
    }
}