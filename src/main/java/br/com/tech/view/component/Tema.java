package br.com.tech.view.component;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public enum Tema {
    WINDOWS("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"),
    METAL("javax.swing.plaf.metal.MetalLookAndFeel"),
    NIMBUS("javax.swing.plaf.nimbus.NimbusLookAndFeel"),
    FLATLAF_LIGHT("com.formdev.flatlaf.FlatLightLaf"),
    FLATLAF_DARK("com.formdev.flatlaf.FlatDarkLaf"),
    FLATLAF_INTELLIJ("com.formdev.flatlaf.FlatIntelliJLaf"),
    FLATLAF_DARCULA("com.formdev.flatlaf.FlatDarculaLaf"),
    FLATLAF_MAC_LIGHT("com.formdev.flatlaf.themes.FlatMacLightLaf"),
    FLATLAF_MAC_DARK("com.formdev.flatlaf.themes.FlatMacDarkLaf");
    
    private String caminhoPackageTema;
    
    private Tema(String caminhoPackageTema) {
        this.caminhoPackageTema = caminhoPackageTema;
    }
    
    public static void mudar(Tema tema, JFrame frame) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        if(frame != null) {
            UIManager.setLookAndFeel(tema.getCaminhoPackageTema());

            SwingUtilities.updateComponentTreeUI(frame);
            
            frame.pack();
        }
    }

    public String getCaminhoPackageTema() {
        return caminhoPackageTema;
    }
}
