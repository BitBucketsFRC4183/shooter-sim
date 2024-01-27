package src;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class Simulation{

    private double x;
    private double y;
    private double initialX;
    private double initialY;
    private double angle;
    private double velocity;
    private double xVelocity;
    private double yVelocity;
    private double time;
    private final double gravity = 9.81;

    public void setup(){
        x = 0;
        y = 400;
        initialX = x;
        initialY = y;
        angle = 45;
        velocity = 80;
        xVelocity = velocity * Math.cos(Math.toRadians(angle));
        yVelocity = velocity * Math.sin(Math.toRadians(angle));
        time = 0;
    }

    public void draw(Graphics2D g){
        for(int i = 0; i < 1000; i++){
            g.fill(new Ellipse2D.Double(x, y, 5, 5));
            time += 0.1;
            x = initialX + xVelocity * time;
            y = initialY - (yVelocity * time - (gravity / 2) * time * time);

        }

        g.draw(new Line2D.Double(1, 5, 1, 400));
    }
}