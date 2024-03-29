package src;

import javax.swing.*;
import java.awt.*;

public class ProjectileMotion extends JPanel{

    public Simulation physics = new Simulation();

    public void initComponents(){
        JFrame frame = new JFrame("Projectile Motion Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080); //100 pixels = 1 meter, 900 meters by 900 meters
        frame.setResizable(true);
        frame.setTitle("Projectile Motion Simulation");
        frame.setLocationRelativeTo(null);
        frame.add(this);
        frame.setVisible(true);
        physics.setup();
    }

    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.translate(300, -3400);
        g2.scale(9.5, 9.5); //9.5, 9.5
        physics.draw(g2);
    }


    public static void main(String[] args){
        new ProjectileMotion().initComponents();
    }
}
