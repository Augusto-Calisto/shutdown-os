package br.com.tech;

import br.com.tech.view.TelaPrincipal;
import br.com.tech.view.component.IconeBandeja;
import br.com.tech.view.component.Tema;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

public class RodarAplicacao {
    public static final Image ICONE_DA_APLICACAO_DESKTOP = Toolkit.getDefaultToolkit().getImage(RodarAplicacao.class.getResource("/image/icon.png"));
    public static final Image ICONE_BANDEJA = Toolkit.getDefaultToolkit().getImage(RodarAplicacao.class.getResource("/image/icon_tray.png"));
    public static final String NOME_DA_APLICACAO_DESKTOP = "ShutdownOS";
    
    public static void main(String[] args) {
        try {
            TelaPrincipal tela = new TelaPrincipal();

            Tema.mudar(Tema.WINDOWS, tela);
        
            IconeBandeja.getSingleton(tela);

            EventQueue.invokeLater(() -> {
                tela.setIconImage(ICONE_DA_APLICACAO_DESKTOP);
                tela.setVisible(true);
            });
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), NOME_DA_APLICACAO_DESKTOP, JOptionPane.ERROR_MESSAGE);
        }
    }
}