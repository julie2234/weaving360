import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

public class DraftRender extends JComponent {
    private static final long serialVersionUID = 6277669978587345503L;
    
    private final int my_rows;
    private final int my_cols;
    private boolean my_data[][];
    private Color my_draft[][];
    private Color my_colorChoice[];
    protected int my_cellSize;
    
    private Color light = new Color(200, 200, 200);
    private Color dark = new Color(140, 140, 140);
    /**
     * Constructs render for warp, tie-up, and pedal panels.
     * @param the_rows Number of rows.
     * @param the_cols Number of columns.
     * @param the_data Data for how to paint warp, tie-up, or pedals.
     */
    public DraftRender(final int the_threadSize, final int the_rows, 
                       final int the_cols, boolean[][] the_data) {
        my_rows = the_rows;
        my_cols = the_cols;
        my_cellSize = the_threadSize;
        my_data = the_data;
    }
    /**
     * Constructs render for center display of WeaveDraft.
     * @param rows Number of rows.
     * @param columns Number of columns.
     * @param the_draft Data for how to paint center grid of WeaveDraft.
     */
    public DraftRender(final int the_threadSize, final int rows, 
                       final int columns, Color[][] the_draft) {
    	my_rows = rows;
    	my_cols = columns;
    	my_cellSize = the_threadSize;
    	my_draft = the_draft;
    }
    /**
     * Constructs render for color choosing displays.
     * @param rows Number of rows.
     * @param columns Number of columns.
     * @param the_colorChoice Data for how to paint color display grid.
     */
    public DraftRender(final int the_threadSize, final int rows, 
                       final int columns, Color[] the_colorChoice) {
    	my_rows = rows;
    	my_cols = columns;
    	my_cellSize = the_threadSize;
    	my_colorChoice = the_colorChoice;
    }
    //get a column based on a mouse event
    public int eventToCellX(MouseEvent e) {
        return (int)Math.floor((float)e.getX() / (float)my_cellSize);   
    }
    
    //get a row based on a mouse event
    public int eventToCellY(MouseEvent e) {
        return (int)Math.floor((float)e.getY() / (float)my_cellSize);
    }
    /**
     * Paint grids.
     */
    public void paintComponent(Graphics g2) {
        Graphics2D g = (Graphics2D) g2;
        
        for (int i = 0; i <= my_cols; i += 1) {
            for (int j = 0; j <= my_rows; j += 1) {
                int x = i * my_cellSize - 1;
                int y = j * my_cellSize - 1;
                
                g.setColor(light);
                g.drawRect(x, y, my_cellSize, my_cellSize);
                
                if (i % 4 == 0) {
                    g.setColor(dark);
                    g.drawLine(x, y, x, y + my_cellSize);
                }
                
                if (j % 4 == 0) {
                    g.setColor(dark);
                    g.drawLine(x, y, x + my_cellSize, y);
                }
                //Logic to paint based on active data structure: my_data, my_draft, or my_colorChoice.
                if (my_data != null) {
                	if (my_data[i][j]) {
                		g.setPaint(Color.black);
                	} else {
                		g.setPaint(Color.white);
                	}
                } else if (my_draft != null) {
                	g.setPaint(my_draft[i][j]);
                } else if (my_colorChoice != null) {
                	if (my_cols != 1) {
                		g.setPaint(my_colorChoice[i]);
                	} else {
                		g.setPaint(my_colorChoice[j]);
                	}
                }
                g.fillRect(x + 1, y + 1, my_cellSize - 1, my_cellSize - 1);
            }
        }     
    }
}