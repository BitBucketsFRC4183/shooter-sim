package src;

/*
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

     */

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class Simulation {

    double mass;
    double area_cross_section;
    double note_radius;
    double air_density;
    double drag_coefficient;
    double magnus_coefficient;
    double x;
    double y;
    double Vxi;
    double Vyi;
    double Ax;
    double Ay;
    private double initialX;
    private double initialY;
    private double theta;
    private double theta2;
    private double theta3;
    private double theta4;
    private double theta5;
    private double theta6;
    private double theta7;
    private double velocity;
    private double xVelocity;
    private double yVelocity;
    private double time;
    private final double gravity = 9.81;
    private double dt;


    public void setup() {
        //config
        mass = 0.25;
        area_cross_section = 0.4;
        note_radius = 0.1778;
        air_density = 1.225;
        x = 0; //change
        y = 400;
        initialX = x;
        initialY = y;

        //tune
        drag_coefficient = 0;
        magnus_coefficient = 0;
        theta = 15;
        theta2 = 20;
        theta3 = 25;
        theta4 = 30;
        theta5 = 35;
        theta6 = 40;
        theta7 = 45;

        velocity = 15;
        Vxi = getVxi(theta);
        Vyi = getVyi(theta);
        time = 0;
        dt = 0.02;

    }

    public void resetSetup()
    {
        x = 0;
        y = 400;

        Vxi = getVxi(theta);
        Vyi = getVyi(theta);
        Ax = -(Math.sqrt(Vxi * Vxi + Vyi * Vyi) / mass) * (drag_coefficient * Vxi + magnus_coefficient * Vyi);
        Ay = (Math.sqrt(Vxi * Vxi + Vyi * Vyi) / mass) * (magnus_coefficient * Vxi - drag_coefficient * Vyi) + gravity;
        time = 0;
    }

    public double getVxi(double theta) {
        return velocity * Math.cos(Math.toRadians(this.theta));
    }

    public double getVyi(double theta) {
        return -velocity * Math.sin(Math.toRadians(this.theta));
    }

    public double setXAcceleration(double Vxi, double Vyi){
        Ax = -(Math.sqrt(Vxi * Vxi + Vyi * Vyi) / mass) * (drag_coefficient * Vxi + magnus_coefficient * Vyi);
        return Ax;
    }
    public double setYAcceleration(double Vxi, double Vyi){
        Ay = (Math.sqrt(Vxi * Vxi + Vyi * Vyi) / mass) * (magnus_coefficient * Vxi - drag_coefficient * Vyi) + gravity;
        return Ay;
    }

    public void updateVelocityAndAcceleration() {

            double dVx1 = Ax * dt;
            double dVy1 = Ay * dt;

            double dVx2 = dt * setXAcceleration(dVx1/2+Vxi, dVy1/2+Vyi);
            double dVy2 = dt * setYAcceleration(dVx1/2+Vxi, dVy1/2 + Vyi);

            x += dt*(dVx2/2+Vxi);
            y += dt*(dVy2/2+Vyi);

            Vxi += dVx2;
            Vyi += dVy2;

            Ax = setXAcceleration(Vxi,Vyi);
            Ay = setYAcceleration(Vxi,Vyi);



    }

    public void drawOneCurve(Graphics2D g)
    {
        for (int i = 0; i < 10000; i++) {
            g.fill(new Ellipse2D.Double(x, y, 3, 3));
            //g.fill(new Ellipse2D.Double(50, 70, 10, 10));
            time += dt;
            updateVelocityAndAcceleration();
        }
    }



    public void draw(Graphics2D g) {
        drawOneCurve(g);

        g.setColor(Color.GREEN);
        theta = theta2;
        resetSetup();

        drawOneCurve(g);

        g.setColor(Color.YELLOW);
        theta = theta3;
        resetSetup();

        drawOneCurve(g);

        g.setColor(Color.BLACK);
        theta = theta4;
        resetSetup();

        drawOneCurve(g);

        g.setColor(Color.CYAN);
        theta = theta5;
        resetSetup();

        drawOneCurve(g);

        g.setColor(Color.MAGENTA);
        theta = theta5;
        resetSetup();

        drawOneCurve(g);

        g.setColor(Color.PINK);
        theta = theta6;
        resetSetup();

        drawOneCurve(g);

        g.setColor(Color.ORANGE);
        theta = theta7;
        resetSetup();

        drawOneCurve(g);



        g.setColor(Color.BLUE);

        double x = 20;
        double y = 400;
        double endX   = x + 40 * Math.sin(14);
        double endY   = y + 40 * Math.cos(14);


        g.draw(new Line2D.Double(50,400,50,450)); //vertical
        g.draw(new Line2D.Double(x, y, endX, endY)); //horizontal

        //g.draw(new Line2D.Double(5, 0, 5, 1.98));
        //g.draw(new Line2D.Double(5.54,2.11,5,2.11));


    }


}

    /*
    public double calculateAngle ()
    {
        double v = findMagnitude(Vxi, Vyi);
        double a = findMagnitude(Ax, Ay);

        return Math.atan(1/(Math.sqrt((2* shooterCalculatorComponent.speaker_height()*gravity*gravity+gravity*v*v)/(2*a*Math.pow(v,4)+gravity*v*v))));
    }

     */




