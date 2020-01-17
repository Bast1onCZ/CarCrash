package utility;

import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JPanel;

public class GraphicsUtility {
    
    public static double getPosForMiddleObject(double parentWidth, double objectWidth) {
        return (parentWidth - objectWidth) / 2;
    }
    
    public static int getPosForMiddleObject(int parentWidth, int objectWidth) {
        return (parentWidth - objectWidth) / 2;
    }
    
    /**
     * Prints text to the center
     * @param panel JPanel - current panel
     * @param g Graphics - given in overrided paintComponent(Graphics g) method
     * @param text String - text to print
     * @param y int - Y coord
     */
    public static void printCenter(JPanel panel, Graphics g, String text, int y) {
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        
        int x = GraphicsUtility.getPosForMiddleObject(panel.getWidth(), textWidth);
        
        g.drawString(text, x, y);
    }
    
}
