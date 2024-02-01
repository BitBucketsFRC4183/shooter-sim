package src;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;


public class Simulation {

    double mass;
    double area_cross_section;
    double note_radius;
    double air_density;
    double drag_coefficient;
    double magnus_coefficient;
    double x;
    double y;
    double holdX;
    double holdY;
    int goodPath;
    int badPath;
    int numCurves;
    double Vxi;
    double Vyi;
    double Ax;
    double Ay;
    double initialX;
    double initialY;
    double theta;
    List<Double> thetaList = new ArrayList<>();
    double delta_theta;
    double bestTheta;
    double velocity;
    double time;
    final double gravity = 9.81;
    double dt;

    //ugly code below but oh well
    double startX = 32; //20, 4.72
    double startY = 361.6566; //400, 598.12
    double hypotenuse = 20.567;
    double endX = startX + hypotenuse * Math.cos(28.93*Math.PI/180);
    double endY = startY - hypotenuse * Math.sin(28.93*Math.PI/180);
    Line2D backSpeaker = new Line2D.Double(50,374.6316,50,452.6316);
    Line2D topSpeaker = new Line2D.Double(startX, startY, endX, endY);
    Line2D frontSpeaker = new Line2D.Double(startX, startY, startX, startY+8.1);
    Line2D openingLine = new Line2D.Double(startX, startY+8.1, 50, 374.6316);


    public void setup() {
        //config
        mass = 0.25;
        area_cross_section = 0.4;
        note_radius = 0.1778;
        air_density = 1.225;
        x = 0; //change,0
        y = 400; //400
        initialX = 0; //starting X of shooter
        initialY = 400; //starting Y of shooter
        goodPath = 0;
        badPath = 0;
        bestTheta = 0;

        //tune
        drag_coefficient = 0;
        magnus_coefficient = 0;
        theta = 15;
        delta_theta = 8;
        numCurves = 8; //8
        velocity = 45;
        Vxi = getVxi(theta);
        Vyi = getVyi(theta);
        time = 0;
        dt = 0.02; //0.02

    }

    public void resetSetup()
    {
        x = initialX;
        y = initialY;
        Vxi = getVxi(theta);
        Vyi = getVyi(theta);
        Ax = -(Math.sqrt(Vxi * Vxi + Vyi * Vyi) / mass) * (drag_coefficient * Vxi + magnus_coefficient * Vyi);
        Ay = (Math.sqrt(Vxi * Vxi + Vyi * Vyi) / mass) * (magnus_coefficient * Vxi - drag_coefficient * Vyi) + gravity;
        time = 0;
        goodPath = 0;
        badPath = 0;
    }

    public double getVxi(double theta) {
        return velocity * Math.cos(Math.toRadians(theta));
    }

    public double getVyi(double theta) {
        return -velocity * Math.sin(Math.toRadians(theta));
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


    public void drawOneCurve(Graphics2D g) {
        for (int i = 0; i < 10000; i++) {
            g.fill(new Ellipse2D.Double(x, y, 1, 1));
            time += dt;
            holdX = x;
            holdY = y;
            updateVelocityAndAcceleration();
            Line2D newLine = new Line2D.Double(holdX, holdY, x, y);
            if (newLine.intersectsLine(frontSpeaker) || newLine.intersectsLine(topSpeaker) || newLine.intersectsLine(backSpeaker)) {
               badPath += 1;
            }
            else if (newLine.intersectsLine(openingLine)) {
                goodPath += 1;
                g.setColor(Color.RED);
            }
        }
        if (goodPath == 0){
            g.setColor(Color.WHITE);
        }
    }

    public void draw(Graphics2D g) {
        for (int k = 0; k < numCurves; k++) {
            drawOneCurve(g);
            g.setColor(Color.WHITE);
            if (goodPath >= 1){
                thetaList.add(theta); //use if you want middle angle
                //bestTheta = theta; //use if you want max angle
            }
            resetSetup();
            theta += delta_theta;
        }


        g.setColor(Color.GREEN);
        g.draw(backSpeaker); //vertical wall
        g.setColor(Color.BLUE);
        g.draw(topSpeaker);
        g.setColor(Color.YELLOW);
        g.draw(frontSpeaker); //shorter vertical wall
        g.setColor(Color.ORANGE);
        g.draw(openingLine); //opening line

        double sum = 0;
        for (Double aDouble : thetaList) {
            sum += aDouble;
        }
        //System.out.println("Best theta: " + bestTheta);
        System.out.println("Best theta: " + sum/thetaList.size());
        System.out.println("Current velocity: " + velocity);
        System.out.println("run!");
    }


}