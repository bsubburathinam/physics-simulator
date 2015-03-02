package com.backfield.simulators;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.Resource;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;

public class MainWindow extends JFrame {

    @Resource(name = "threadPoolTaskScheduler")
    public ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Resource(name = "simulatorPanel")
    public SimulatorPanel simulatorPanel;

    @Resource(name = "gameState")
    public GameState gameState;

    public JMenuItem createReloadMenuItem() {
        JMenuItem reloadMenuItem = new JMenuItem("Reset");
        reloadMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameState.initialize();
            }
        });
        reloadMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
        return reloadMenuItem;
    }

    public JMenuItem createExitMenuItem() {
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(-1);
            }
        });
        return exitMenuItem;
    }

    public JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(createReloadMenuItem());
        fileMenu.add(createExitMenuItem());
        return fileMenu;
    }

    public JMenuBar createMenu() {
        JMenuBar menu = new JMenuBar();
        menu.add(createFileMenu());
        return menu;
    }

    public MainWindow(int width, int height) {
        super("Universe Simulator");

        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //this.setResizable(false);
        this.setJMenuBar(createMenu());
        this.setLocation(40, 40);
        this.setSize(width, height);

        this.setVisible(true);
    }

    public void start() {
        this.add(simulatorPanel);

        gameState.lastIntegration = Calendar.getInstance().getTimeInMillis();
        threadPoolTaskScheduler.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                gameState.integrate();
                simulatorPanel.repaint();
                gameState.lastIntegration = Calendar.getInstance().getTimeInMillis();
            }
        }, 50);
    }
}
