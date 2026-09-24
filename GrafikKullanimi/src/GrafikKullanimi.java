
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;


public class GrafikKullanimi extends JPanel{

    public GrafikKullanimi() {
        setBackground(Color.BLACK);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        g.setColor(Color.RED);
        g.drawRect(100, 200, 50, 10);
        
        g.setColor(Color.white);
        g.drawLine(500, 400, 50, 90);
        
        
        
    }
    
}
