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
    private double theta1;
    private double theta2;
    private double velocity;
    private double xVelocity;
    private double yVelocity;
    private double time;
    private final double gravity = -9.81;


    public void setup() {
        //config
        mass = 0.25;
        area_cross_section = 0.4;
        note_radius = 0.1778;
        air_density = 1.225;
        x = 100; //change
        y = 400;
        Ax = -(Math.sqrt(Vxi * Vxi + Vyi * Vyi) / mass) * (drag_coefficient * Vxi + magnus_coefficient * Vyi);
        Ay = (Math.sqrt(Vxi * Vxi + Vyi * Vyi) / mass) * (magnus_coefficient * Vxi - drag_coefficient * Vyi) + gravity;
        initialX = x;
        initialY = y;

        //tune
        drag_coefficient = 0.001;
        magnus_coefficient = 0.001;
        theta1 = 15;
        theta2 = 60;
        velocity = 50;
        Vxi = getVxi(theta1);
        Vyi = getVyi(theta1);
        time = 0;

    }

    public double getVxi(double theta) {
        return velocity * Math.cos(Math.toRadians(theta1));
    }

    public double getVyi(double theta) {
        return -velocity * Math.sin(Math.toRadians(theta1));
    }

    public double findMagnitude(double x_component, double y_component) {
        return Math.sqrt(x_component * x_component + y_component * y_component);
    }

    public double trapezoidalIntegrationOfPosition(double initial_position, double initial_velocity, double final_velocity) {
        return initial_position + (initial_velocity + final_velocity) / 2;
    }

    public void updateVelocityAndAcceleration() {
        if (y >= 0) {
            double Vxf = Vxi + Ax * time;
            double Vyf = Vyi + Ay * time;

            x = trapezoidalIntegrationOfPosition(x, Vxi, Vxf);
            y = trapezoidalIntegrationOfPosition(y, Vyi, Vyf);

            Vxi = Vxf;
            Vyi = Vyf;

            double v = findMagnitude(Vxi, Vyi);

            Ax = -(v / mass) * (drag_coefficient * Vxi + magnus_coefficient * Vyi);
            Ay = -gravity + (v / mass) * (magnus_coefficient * Vxi - drag_coefficient * Vyi);

        }
    }



    public void draw(Graphics2D g) {
        for (int i = 0; i < 10000; i++) {
            g.fill(new Ellipse2D.Double(x, y, 10, 10));
            //g.fill(new Ellipse2D.Double(50, 70, 10, 10));
            time += 0.2;
            updateVelocityAndAcceleration();
        }
        //g.draw(new Line2D.Double(1, 5, 1, 400));
        //g.draw(new Line2D.Double(1, 5, 1, 400));


        g.setColor(Color.GREEN);

        x = 100;
        y = 400;
        theta1 = theta2;
        Vxi = getVxi(theta1);
        Vyi = getVyi(theta1);
        Ax = -(Math.sqrt(Vxi * Vxi + Vyi * Vyi) / mass) * (drag_coefficient * Vxi + magnus_coefficient * Vyi);
        Ay = (Math.sqrt(Vxi * Vxi + Vyi * Vyi) / mass) * (magnus_coefficient * Vxi - drag_coefficient * Vyi) + gravity;
        time = 0;

        for (int i = 0; i < 10000; i++) {
            g.fill(new Ellipse2D.Double(x, y, 10, 10));
            time += 0.02;
            updateVelocityAndAcceleration();
        }
        //g.draw(new Line2D.Double(1, 5, 1, 400));


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




