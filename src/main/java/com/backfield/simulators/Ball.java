package com.backfield.simulators;

import java.awt.*;

public class Ball {

    public double x = 0.0;
    public double y = 0.0;
    public double xVelocity = 0.0;
    public double yVelocity = 0.0;
    public int size = 10;
    public int radius = 0;
    public double bounceCoefficient = 0.5;

    public void draw(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillOval((int)x - radius, (int)y - radius, size, size);
    }

    public void integrate() {
        x += xVelocity;
        y += yVelocity;
    }

}
