package br.com.tech.view.component;

import static br.com.tech.RodarAplicacao.ICONE_BANDEJA;
import static br.com.tech.RodarAplicacao.NOME_DA_APLICACAO_DESKTOP;

import br.com.tech.view.TelaPrincipal;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import java.io.IOException;

import java.time.Duration;
import java.time.LocalDateTime;

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
            
            MenuItem menuTraySair = new MenuItem("Fechar");

            menuTraySair.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        if(tela.isProcessoAtivado()) {
                            long pid = tela.getPidProcesso();
                            tela.getPromptCommand().cancelarProcesso(pid);
                        }

                        System.exit(0);

                    } catch(IOException ex) {
                        System.out.println(ex.getMessage());
                    }             
                }
            });

            popMenu.add(menuTraySair);
            
            trayIcon = new TrayIcon(ICONE_BANDEJA, NOME_DA_APLICACAO_DESKTOP, popMenu);

            trayIcon.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(tela.isProcessoAtivado()) {
                        String mensagemPeriodoFalta = buscarTextoQuantidadeTempoRestante();

                        trayIcon.displayMessage(NOME_DA_APLICACAO_DESKTOP, mensagemPeriodoFalta, TrayIcon.MessageType.INFO); // Notificação do Sistema Operacional
                    }

                    tela.setVisible(true);
                }
            });
            
            trayIcon.addMouseMotionListener(new MouseMotionListener(){
                @Override
                public void mouseDragged(MouseEvent e) {}

                @Override
                public void mouseMoved(MouseEvent e) {
                    if(tela.isProcessoAtivado()) {
                        String mensagemPeriodoFalta = buscarTextoQuantidadeTempoRestante();
                        trayIcon.setToolTip(mensagemPeriodoFalta);
                    } else {
                        trayIcon.setToolTip(NOME_DA_APLICACAO_DESKTOP);
                    }
                }
            });
            
            SystemTray tray = SystemTray.getSystemTray();

            tray.add(trayIcon);
            
        } catch(AWTException awtException) {
            System.out.println(awtException.getMessage()); 
        }
    }
    
    private String buscarTextoQuantidadeTempoRestante() {
        LocalDateTime dataHoraAcao = tela.getDateTimePicker().getDateTimeStrict();

        LocalDateTime agora = LocalDateTime.now();

        Duration duracao = Duration.between(agora, dataHoraAcao);

        long dias = duracao.toDays();

        duracao = duracao.minusDays(dias);

        long horas = duracao.toHours();

        duracao = duracao.minusHours(horas);

        long minutos = duracao.toMinutes();
        
        duracao = duracao.minusMinutes(minutos);
        
        long segundos = duracao.toSeconds();
        
        minutos = minutos + segundos / 60;
        
        String opcaoSelecionada = tela.getComboBoxOpcao().getSelectedItem().toString();
        
        String mensagemPeriodoFalta = "Falta(m) ";

        if(dias > 0) {
            mensagemPeriodoFalta += (dias + " dia(s) ");
        } 
        
        if(horas > 0) {
            mensagemPeriodoFalta += (horas + " hora(s) ");
        } 
        
        if(minutos > 0) {
            mensagemPeriodoFalta += (minutos + " minuto(s) ");
        } 
        
        if(segundos > 0) {
            mensagemPeriodoFalta += (segundos + " segundo(s)");
        }
        
        mensagemPeriodoFalta += (" para " + opcaoSelecionada);
        
        return mensagemPeriodoFalta;
    }

    public TrayIcon getTrayIcon() {
        return trayIcon;
    }
}