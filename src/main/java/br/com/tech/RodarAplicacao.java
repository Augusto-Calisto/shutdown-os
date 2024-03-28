package br.com.tech;

import br.com.tech.view.TelaPrincipal;
import br.com.tech.view.component.IconeBandeja;
import br.com.tech.view.component.Tema;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class RodarAplicacao {
    public static final Image ICONE_DA_APLICACAO_DESKTOP = Toolkit.getDefaultToolkit().getImage(RodarAplicacao.class.getResource("/image/icon.png"));
    public static final Image ICONE_BANDEJA = Toolkit.getDefaultToolkit().getImage(RodarAplicacao.class.getResource("/image/icon_tray.png"));
    public static final String NOME_DA_APLICACAO_DESKTOP = "ShutdownOS";
    
    public static void main(String[] args) {
        try {
            if(!isRunningApp()) {
                TelaPrincipal tela = new TelaPrincipal();

                Tema.mudar(Tema.FLATLAF_LIGHT, tela);
                
                IconeBandeja.getSingleton(tela);

                EventQueue.invokeLater(() -> {
                    tela.setIconImage(ICONE_DA_APLICACAO_DESKTOP);
                    tela.setVisible(true);
                });                
            } else {
                JOptionPane.showMessageDialog(null, "O aplicativo já está rodando!!!", NOME_DA_APLICACAO_DESKTOP, JOptionPane.WARNING_MESSAGE);
                
                System.exit(0);
            }
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            
            JOptionPane.showMessageDialog(null, ex.getMessage(), NOME_DA_APLICACAO_DESKTOP, JOptionPane.ERROR_MESSAGE);
            
            System.exit(0);
        }
    }
    
    private static boolean isRunningApp() throws IOException, InterruptedException {        
        boolean isRunning = false;
        
        List<String> comandosSistemaOperacional = getCommandForOS();
        
        comandosSistemaOperacional.add("jps -m");
                
        ProcessBuilder builder = new ProcessBuilder(comandosSistemaOperacional);
        
        String javaHome = System.getProperty("java.home"); // C:\Program Files\Java\jdk-11.0.12 (Windows)   |    /usr/bin/java (Linux)
                
        String pathFolderBinJava = javaHome + File.separator + "bin";
        
        builder.directory(new File(pathFolderBinJava));
        
        builder.redirectErrorStream();

        Process subProcess = builder.start();

        BufferedReader subProcessInputReader = new BufferedReader(new InputStreamReader(subProcess.getInputStream()));

        String linha = "";

        int quantidadeDeApps = 0;
        
        String classeComPacote = RodarAplicacao.class.getName();
        
        while((linha = subProcessInputReader.readLine()) != null) {
            if(linha.contains(classeComPacote) || linha.contains("shutdownOS.jar")) {
                quantidadeDeApps++;
            }
        }
        
        if(quantidadeDeApps >= 2) {
            isRunning = true;
        }

        subProcessInputReader.close();
                
        return isRunning;
    }
    
    private static List<String> getCommandForOS() {
        String os = System.getProperty("os.name").toLowerCase();
        
        List<String> commandList = new ArrayList<>();

        if(os.contains("win")) { // Windows
            commandList.add("cmd");
            commandList.add("/c");
            
        } else if(os.contains("nix") || os.contains("nux") || os.contains("aix")) { // Linux ou Unix
            commandList.add("/bin/sh");
            commandList.add("-c");
            
        } else if(os.contains("mac")) { // MacOS
            commandList.add("/bin/sh");
            commandList.add("-c");
        }
        
        return commandList;
    }
}