package br.com.tech;

import br.com.tech.view.TelaPrincipal;
import br.com.tech.view.component.Tema;

import java.awt.AWTException;
import java.awt.EventQueue;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;

import java.net.MalformedURLException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class RodarAplicacao {
    public static final String NOME_DA_APLICACAO_DESKTOP = "ShutdownOS";
    
    private static TelaPrincipal tela;

    public static void main(String[] args) throws MalformedURLException, AWTException, UnsupportedLookAndFeelException {
        Tema.mudarTema("com.sun.java.swing.plaf.windows.WindowsLookAndFeel", null);
        
        tela = new TelaPrincipal();
        
        //Image image = Toolkit.getDefaultToolkit().createImage(RodarAplicacao.class.getResource("icon_tray.png"));
        
        criacaoTrayIcon();
        
        EventQueue.invokeLater(() -> {
            tela.setVisible(true);
        });
    }
    
    private static void criacaoTrayIcon() throws AWTException {
        if(SystemTray.isSupported()) {
            tela.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            
            PopupMenu popMenu = new PopupMenu();
            
            MenuItem menuTraySair = new MenuItem("Sair");
            
            menuTraySair.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        if(tela.isProcessoAtivado()) {
                            tela.getPromptCommand().cancelarDesligamento();
                        }
                        
                        System.exit(0);
                        
                    } catch(IOException ex) {
                        System.out.println(ex);
                    }             
                }
            });
            
            popMenu.add(menuTraySair);
            
            String path = System.getProperty("user.dir");
            
            ImageIcon imageIcon = new ImageIcon(path.concat("/resource/icon_tray.png"));
            
            TrayIcon iconeBandeja = new TrayIcon(imageIcon.getImage(), "ShutdownOS", popMenu);
                        
            iconeBandeja.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(tela.isProcessoAtivado()) {
                        LocalDateTime dataHoraAcao = tela.getDateTimePicker().getDateTimeStrict();
                        
                        LocalDateTime agora = LocalDateTime.now();

                        long minutos = ChronoUnit.MINUTES.between(agora, dataHoraAcao);

                        JOptionPane.showMessageDialog(null, "Falta(m) " + minutos + " minutos", NOME_DA_APLICACAO_DESKTOP, JOptionPane.INFORMATION_MESSAGE);
                    }
                    
                    // iconeBandeja.displayMessage("Caption", "Info", TrayIcon.MessageType.INFO); // Notificação do Windows
                    
                    tela.setVisible(true);
                }
            });

            iconeBandeja.setImageAutoSize(true);
            
            SystemTray tray = SystemTray.getSystemTray();
            
            tray.add(iconeBandeja);
        }
    }
}