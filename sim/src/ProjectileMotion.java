package src;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class ProjectileMotion extends JPanel{

    public Simulation physics = new Simulation();

    public void initComponents(){
        JFrame frame = new JFrame("Projectile Motion Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(this);
        frame.setVisible(true);
        physics.setup();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        physics.draw(g2);
    }

    public static void main(String[] args){
        new ProjectileMotion().initComponents();
    }
}
