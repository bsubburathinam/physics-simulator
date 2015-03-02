package com.backfield.simulators;

import javax.annotation.Resource;
import javax.swing.*;
import java.awt.*;

public class SimulatorPanel extends JPanel {

    @Resource(name = "mainWindow")
    public MainWindow mainWindow;

    @Resource(name = "gameState")
    public GameState gameState;

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.clearRect(0, 0, mainWindow.getWidth(), mainWindow.getHeight());
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, mainWindow.getWidth(), mainWindow.getHeight());
        for (int i = 0; i < gameState.ballCount; i++) {
            gameState.balls[i].draw(g);
        }
        g.setColor(Color.WHITE);
        g.drawLine(
                gameState.offset,
                mainWindow.getHeight() - gameState.offset,
                mainWindow.getWidth() - gameState.offset,
                mainWindow.getHeight() - gameState.offset
        );
        g.drawLine(
                gameState.offset,
                gameState.offset,
                gameState.offset,
                mainWindow.getHeight() - gameState.offset
        );
        g.drawLine(
                mainWindow.getWidth() - gameState.offset,
                gameState.offset,
                mainWindow.getWidth() - gameState.offset,
                mainWindow.getHeight() - gameState.offset
        );
        g.drawLine(
                gameState.offset,
                gameState.offset,
                mainWindow.getWidth() - gameState.offset,
                gameState.offset
        );
    }

}
