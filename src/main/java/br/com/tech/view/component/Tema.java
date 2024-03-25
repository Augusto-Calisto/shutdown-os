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
    FLATLAF_DARK("com.formdev.flatlaf.FlatDarkLaf");
    
    private String packagePath;
    
    private Tema(String packagePath) {
        this.packagePath = packagePath;
    }
    
    public static void mudar(Tema tema, JFrame frame) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        if(frame != null) {
            String caminhoPackageTema = tema.getPackagePath();

            UIManager.setLookAndFeel(caminhoPackageTema);

            SwingUtilities.updateComponentTreeUI(frame);
            
            frame.pack();
        }
    }
    
    public String getPackagePath() {
        return packagePath;
    }
}
