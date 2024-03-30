package br.com.tech.model;

import java.io.IOException;

public class PromptCommand {
    private static final String SISTEMA_OPERACIONAL = System.getProperty("os.name");
	
    private ISistemaOperacional sistemaOperacional;
	
    public PromptCommand() {
        if(SISTEMA_OPERACIONAL.contains("Windows")) {
            sistemaOperacional = new Windows();
        }

        if(SISTEMA_OPERACIONAL.contains("Linux")) {
           sistemaOperacional = new Linux();
        }
    }
	
    public long desligarComputador(long tempo) throws IOException {
        if(tempo < 0) {
            throw new IllegalArgumentException("A data e/ou hora passada é menor que o horário atual do computador");
        }

        return sistemaOperacional.desligar(tempo);
    }
    
    public long reiniciarComputador(long tempo) throws IOException {
        if(tempo < 0) {
            throw new IllegalArgumentException("A data e/ou hora passada é menor que o horário atual do computador");
        }

        return sistemaOperacional.reiniciar(tempo);
    }

    public void cancelarProcesso(long pidProcesso) throws IOException {
        sistemaOperacional.cancelar(pidProcesso);
    }

    public long hibernarComputador(long tempo) throws IOException {
        if(tempo < 0) {
            throw new IllegalArgumentException("A data e/ou hora passada é menor que o horário atual do computador");
        }
        
        return sistemaOperacional.hibernar(tempo);
    }
}