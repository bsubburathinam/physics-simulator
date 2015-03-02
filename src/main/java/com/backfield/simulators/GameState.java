package com.backfield.simulators;

import javax.annotation.Resource;
import java.util.Calendar;

public class GameState {

    @Resource(name = "ballGenerator")
    public BallGenerator ballGenerator;

    @Resource(name = "mainWindow")
    public MainWindow mainWindow;

    public Ball[] balls = new Ball[100];

    public int ballCount = 50;

    public long lastIntegration = 0;

    public int offset = 50;

    public boolean collision(Ball a, Ball b) {
        if (a == null || b == null) { return false; }
        return Math.sqrt(Math.pow(b.x - a.x, 2) + Math.pow(b.y - b.y, 2)) < (a.radius + b.radius);
    }

    public void initialize() {
        int i = 0;
        while (i < ballCount) {
            Ball newBall = ballGenerator.get();
            boolean collides = false;
            for (int x = 0; x < i; x++) {
                if (collision(balls[i], newBall)) {
                    collides = true;
                    break;
                }
            }
            if(!collides) {
                balls[i] = ballGenerator.get();
                i++;
            }
        }
    }

    public void applyGravity(Ball ball, double dt) {
        ball.yVelocity += (9.8 * dt);
    }

    public void integrate() {
        double dt = (Calendar.getInstance().getTimeInMillis() - lastIntegration) / 1000.0;
        for (int i = 0; i < ballCount; i++) {
            applyGravity(balls[i], dt);
        }
        for (int i = 0; i < ballCount; i++) {
            balls[i].integrate();
        }
        int floor = mainWindow.getHeight() - offset;
        for (int i = 0; i < ballCount; i++) {
            if ((balls[i].y + balls[i].radius) >= floor) {
                balls[i].y = floor - balls[i].radius;
                balls[i].yVelocity *= -0.5;
                balls[i].xVelocity *= 0.8;
            } else if((balls[i].y - balls[i].radius) <= offset) {
                balls[i].y = offset + balls[i].radius;
                balls[i].yVelocity *= -0.5;
                balls[i].xVelocity *= 0.8;
            }
        }
        int wall = mainWindow.getWidth() - offset;
        for (int i = 0; i < ballCount; i++) {
            if ((balls[i].x + balls[i].radius) >= wall) {
                balls[i].x = wall - balls[i].radius;
                balls[i].xVelocity *= -0.5;
            } else if ((balls[i].x - balls[i].radius) <= offset) {
                balls[i].x = offset + balls[i].radius;
                balls[i].xVelocity *= -0.5;
            }
        }
        for (int x = 0; x < ballCount; x++) {
            for (int y = x + 1; y < ballCount; y++) {
                if (collision(balls[x], balls[y])) {
                    balls[x].xVelocity += (-0.5 * balls[y].xVelocity) / balls[x].size;
                    balls[x].yVelocity += (-0.5 * balls[y].yVelocity) / balls[x].size;
                    balls[y].xVelocity += (-0.5 * balls[x].xVelocity) / balls[y].size;
                    balls[y].yVelocity += (-0.5 * balls[x].yVelocity) / balls[y].size;
                }
            }
        }
    }

}
