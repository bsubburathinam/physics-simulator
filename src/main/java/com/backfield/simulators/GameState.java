package com.backfield.simulators;

import javax.annotation.Resource;
import java.util.Calendar;

public class GameState {

    @Resource(name = "ballGenerator")
    public BallGenerator ballGenerator;

    @Resource(name = "mainWindow")
    public MainWindow mainWindow;

    public int ballCount = 50;

    public Ball[] balls = new Ball[ballCount];

    public long lastIntegration = 0;

    public long dts = 1;

    public long dtCount = 1;

    public int offset = 50;

    public double collision(Ball a, Ball b) {
        return Math.sqrt(Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2));
    }

    public void initialize() {
        int i = 0;
        while (i < ballCount) {
            Ball newBall = ballGenerator.get();
            boolean collides = false;
            for (int x = 0; x < i; x++) {
                if (balls[i] != null && (collision(balls[i], newBall) < (balls[i].radius + newBall.radius))) {
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
        for (int x = 0; x < ballCount; x++) {
            for (int y = x + 1; y < ballCount; y++) {
                double d = collision(balls[x], balls[y]);
                double mind = balls[x].radius + balls[y].radius;
                if (d < mind) {
                    double ratio = (mind / d);
                    double ux1 = balls[x].xVelocity;
                    double uy1 = balls[x].yVelocity;
                    double m1 = balls[x].size;

                    double ux2 = balls[y].xVelocity;
                    double uy2 = balls[y].yVelocity;
                    double m2 = balls[y].size;

                    balls[x].x = balls[x].x + balls[x].xVelocity - (ratio * balls[x].xVelocity);
                    balls[x].y = balls[x].y + balls[x].yVelocity - (ratio * balls[x].yVelocity);

                    balls[x].xVelocity = (ux1*(m1-m2)+2*m2*ux2)/(m1+m2);
                    balls[x].yVelocity = (uy1*(m1-m2)+2*m2*uy2)/(m1+m2);
                    balls[y].xVelocity = (ux2*(m2-m1)+2*m1*ux1)/(m1+m2);
                    balls[y].yVelocity = (uy2*(m2-m1)+2*m1*uy1)/(m1+m2);
                }
            }
        }
        int floor = mainWindow.getHeight() - offset;
        for (int i = 0; i < ballCount; i++) {
            if ((balls[i].y + balls[i].radius) >= floor) {
                balls[i].y = floor - balls[i].radius;
                balls[i].yVelocity *= -balls[i].bounceCoefficient;
                balls[i].xVelocity *= 0.8;
            } else if((balls[i].y - balls[i].radius) <= offset) {
                balls[i].y = offset + balls[i].radius;
                balls[i].yVelocity *= -balls[i].bounceCoefficient;
                balls[i].xVelocity *= 0.8;
            }
        }
        int wall = mainWindow.getWidth() - offset;
        for (int i = 0; i < ballCount; i++) {
            if ((balls[i].x + balls[i].radius) >= wall) {
                balls[i].x = wall - balls[i].radius;
                balls[i].xVelocity *= -balls[i].bounceCoefficient;
                balls[i].yVelocity *= 0.8;
            } else if ((balls[i].x - balls[i].radius) <= offset) {
                balls[i].x = offset + balls[i].radius;
                balls[i].xVelocity *= -balls[i].bounceCoefficient;
                balls[i].yVelocity *= 0.8;
            }
        }
        dts += (Calendar.getInstance().getTimeInMillis() - lastIntegration);
        dtCount++;
    }

}