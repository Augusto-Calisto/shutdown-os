package br.com.tech;

import br.com.tech.view.TelaPrincipal;
import br.com.tech.view.component.IconeBandeja;
import br.com.tech.view.component.Tema;

import java.awt.EventQueue;

public class RodarAplicacao {
    public static final String NOME_DA_APLICACAO_DESKTOP = "ShutdownOS";
    
    public static void main(String[] args) {
        TelaPrincipal tela = new TelaPrincipal();
        
        Tema.mudarTema("com.sun.java.swing.plaf.windows.WindowsLookAndFeel", tela);
                
        IconeBandeja.getSingleton(tela);
                
        EventQueue.invokeLater(() -> {
            tela.setVisible(true);
        });
    }
}