package com.backfield.simulators;

import javax.annotation.Resource;
import java.security.SecureRandom;

public class BallGenerator {

    @Resource(name = "mainWindow")
    public MainWindow mainWindow;

    @Resource(name = "random")
    public SecureRandom random;

    @Resource(name = "gameState")
    public GameState gameState;

    public Ball get() {
        Ball ball = new Ball();
        ball.x = Math.abs(random.nextInt(mainWindow.getWidth() - (gameState.offset * 2))) + gameState.offset;
        ball.y = Math.abs(random.nextInt(mainWindow.getHeight() - (gameState.offset * 2))) + gameState.offset;
        ball.size = Math.abs(random.nextInt(10) + 5);
        ball.xVelocity = random.nextDouble();
        ball.yVelocity = random.nextDouble();
        ball.radius = (int)(ball.size / 2.0);
        ball.bounceCoefficient = random.nextDouble() * 0.8;
        return ball;
    }

}
