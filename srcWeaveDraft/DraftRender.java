import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

public class DraftRender extends JComponent {
    private static final long serialVersionUID = 6277669978587345503L;
    
    private int rows;
    private int columns;
    private boolean data[][];
    private Color draft[][];
    private Color colorChoice[];
    public int cellWidth = 20, cellHeight = 20;
    
    private Color light = new Color(200, 200, 200);
    private Color dark = new Color(140, 140, 140);
    
    public DraftRender(int rows, int columns, boolean data[][]) {
        this.rows = rows;
        this.columns = columns;
        this.data = data;
    }
    public DraftRender(int rows, int columns, Color[][] the_draft) {
    	this.rows = rows;
    	this.columns = columns;
    	this.draft = the_draft;
    }
    public DraftRender(int rows, int columns, Color[] the_colorChoice) {
    	this.rows = rows;
    	this.columns = columns;
    	this.colorChoice = the_colorChoice;
    }
    //get a column based on a mouse event
    public int eventToCellX(MouseEvent e) {
        return (int)Math.floor((float)e.getX() / (float)cellWidth);   
    }
    
    //get a row based on a mouse event
    public int eventToCellY(MouseEvent e) {
        return (int)Math.floor((float)e.getY() / (float)cellHeight);
    }
    
    //paint grid
    public void paintComponent(Graphics g2) {
        Graphics2D g = (Graphics2D) g2;
        
        for (int i = 0; i <= columns; i += 1) {
            for (int j = 0; j <= rows; j += 1) {
                int x = i * cellWidth - 1;
                int y = j * cellHeight - 1;
                
                g.setColor(light);
                g.drawRect(x, y, cellWidth, cellHeight);
                
                if (i % 4 == 0) {
                    g.setColor(dark);
                    g.drawLine(x, y, x, y + cellHeight);
                }
                
                if (j % 4 == 0) {
                    g.setColor(dark);
                    g.drawLine(x, y, x + cellWidth, y);
                }
                if (data != null) {
                	if (data[i][j]) {
                		g.setPaint(Color.black);
                	} else {
                		g.setPaint(Color.white);
                	}
                } else if (draft != null) {
                	g.setPaint(draft[i][j]);
                } else if (colorChoice != null) {
                	if (columns != 1) {
                		g.setPaint(colorChoice[i]);
                	} else {
                		g.setPaint(colorChoice[j]);
                	}
                }
                g.fillRect(x + 1, y + 1, cellWidth - 1, cellHeight - 1);
            }
        }     
    }
}