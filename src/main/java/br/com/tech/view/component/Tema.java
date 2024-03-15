package br.com.tech.view.component;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Tema {
       
    public static void mudarTema(String caminhoPackageTema, JFrame frame) {
        try {
            UIManager.setLookAndFeel(caminhoPackageTema);
            
            if(frame != null) {
                SwingUtilities.updateComponentTreeUI(frame);
                frame.pack();
            }
            
        } catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
