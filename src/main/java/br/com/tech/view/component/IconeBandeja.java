package br.com.tech.view.component;

import static br.com.tech.RodarAplicacao.NOME_DA_APLICACAO_DESKTOP;

import br.com.tech.view.TelaPrincipal;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.swing.ImageIcon;
import javax.swing.WindowConstants;

public class IconeBandeja {
    private static IconeBandeja iconeBandeja;
    
    private TelaPrincipal tela;
    private TrayIcon trayIcon;
    
    private IconeBandeja(TelaPrincipal telaPrincipal) {
        tela = telaPrincipal;
        
        if(SystemTray.isSupported()) {
            tela.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            criar();
        }
    }
    
    public static IconeBandeja getSingleton(TelaPrincipal telaPrincipal) {
        if(iconeBandeja == null) {
            iconeBandeja = new IconeBandeja(telaPrincipal);
        }
        
        return iconeBandeja;
    }
    
    private void criar() {
        try {
            PopupMenu popMenu = new PopupMenu();

            MenuItem menuTraySair = new MenuItem("Fechar Aplicativo");

            menuTraySair.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        if(tela.isProcessoAtivado()) {
                            tela.getPromptCommand().cancelarProcesso();
                        }

                        System.exit(0);

                    } catch(IOException ex) {
                        System.out.println(ex.getMessage());
                    }             
                }
            });

            popMenu.add(menuTraySair);

            String path = System.getProperty("user.dir");

            ImageIcon imageIcon = new ImageIcon(path.concat("/resource/icon_tray.png"));

            trayIcon = new TrayIcon(imageIcon.getImage(), NOME_DA_APLICACAO_DESKTOP, popMenu);

            trayIcon.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(tela.isProcessoAtivado()) {
                        LocalDateTime dataHoraAcao = tela.getDateTimePicker().getDateTimeStrict();

                        LocalDateTime agora = LocalDateTime.now();

                        Duration duracao = Duration.between(agora, dataHoraAcao);

                        long dias = duracao.toDays();

                        duracao = duracao.minusDays(dias);

                        long horas = duracao.toHours();

                        duracao = duracao.minusHours(horas);

                        long minutos = duracao.toMinutes();
                        
                        duracao = duracao.minusSeconds(minutos);
                        
                        long segundos = duracao.toSeconds();

                        String mensagemPeriodoFalta = "Falta(m) " + dias + " dia(s), " + horas + " hora(s) e " + minutos + " minuto(s) e " + segundos + " segundo(s)";

                        trayIcon.displayMessage(NOME_DA_APLICACAO_DESKTOP, mensagemPeriodoFalta, TrayIcon.MessageType.INFO); // Notificação do Sistema Operacional
                    }

                    tela.setVisible(true);
                }
            });

            trayIcon.setImageAutoSize(true);

            SystemTray tray = SystemTray.getSystemTray();

            tray.add(trayIcon);
            
        } catch(AWTException awtException) {
            System.out.println(awtException.getMessage()); 
        }
    }

    public TrayIcon getTrayIcon() {
        return trayIcon;
    }
}