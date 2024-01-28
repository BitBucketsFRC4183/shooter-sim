package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ProjectileMotion extends JPanel{

    public Simulation physics = new Simulation();

    public void initComponents(){
        JFrame frame = new JFrame("Projectile Motion Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 900);
        frame.setResizable(true);
        frame.setTitle("Projectile Motion Simulation");
        frame.setLocationRelativeTo(null);
        frame.add(this);
        frame.setVisible(true);

        physics.setup();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(Color.red);
        ((Graphics2D) g).scale(1, 1);

        physics.draw(g2);
    }


    public static void main(String[] args){
        new ProjectileMotion().initComponents();
    }
}
