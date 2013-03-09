import java.io.Serializable;

public class DraftStructure implements Serializable {
    private static final long serialVersionUID = -7309399360699214636L;
    protected int my_gridSize = 0;
    protected int my_tieUpSize = 0;
    protected boolean my_warp[][], my_tieup[][], my_pedals[][];
    
    /**
     * Constructs WeaveDraft components consisting of warp, tie-up, and pedals.
     * 
     * @param the_gridSize Integer for width and height of center grid.
     * @param the_tieUpSize Integer for width and height of tie-up.
     */
    public DraftStructure(final int the_gridSize, final int the_tieUpSize) {
        my_gridSize = the_gridSize;
        my_tieUpSize = the_tieUpSize;
        
        // [columns][rows]
        my_warp = new boolean[my_gridSize][my_tieUpSize];
        my_tieup = new boolean[my_tieUpSize][my_tieUpSize];
        my_pedals = new boolean[my_tieUpSize][my_gridSize];
        for (int i = 0; i < my_gridSize; i++) {
        	my_warp[i][0] = true;
        	my_pedals[my_tieUpSize - 1][i] = true;
        }
    }
    /**
     * Toggles tie-up bothe_xes on and off.
     * @param the_x the_x-coordinate.
     * @param the_y y-coordinate.
     */
    public void toggleTieUp(final int the_x, final int the_y) {
        setTieUp(the_x, the_y, !my_tieup[the_x][the_y]);
    }
    /**
     * Activates new warp thread and deactivates all other threads within that column.
     * Only one thread per column of the warp is active at a time.
     * @param the_x the_x-coordinate of thread.
     * @param the_y y-coordinate of thread.
     */
    public void toggleWarp(final int the_x, final int the_y) {
        setWarp(the_x, the_y, !my_warp[the_x][the_y]);
    }
    /**
     * Activates new pedal thread and deactivates all other threads within that row.
     * Only one thread per row of the pedals is active at a time.
     * @param the_x the_x-coordinate of thread.
     * @param the_y y-coordinate of thread.
     */
    public void togglePedals(final int the_x, final int the_y) {
        setPedals(the_x, the_y, !my_pedals[the_x][the_y]);
    }
    /**
     * Sets tie-up value given an the_x an y coordinate of its location.
     * @param the_x the_x-coordinate.
     * @param the_y y-coordinate.
     * @param the_val True for active, false for inactive.
     */
    public void setTieUp(final int the_x, final int the_y, final boolean the_val) {
        my_tieup[the_x][the_y] = the_val;
    }
    /**
     * Sets which threads are active in a warp column. One thread per
     * column is always active. 
     * @param the_x the_x-coordinate.
     * @param the_y y-coordinate.
     * @param the_val True for active, false for inactive.
     */
    public void setWarp(final int the_x, final int the_y, final boolean the_val) {
    	if (the_val) {
    	    for (int i = 0; i < my_tieUpSize; i++) {
    	        my_warp[the_x][i] = false; 
    	    }
    	    my_warp[the_x][the_y] = the_val;
    	}
    }
    /**
     * Sets which threads are active in a pedal row. One thread per
     * row is always active.
     * @param the_x the_x-coordinate.
     * @param the_y y-coordinate.
     * @param the_val True for active, false for inactive.
     */
    public void setPedals(final int the_x, final int the_y, final boolean the_val) {
    	if (the_val) {
    	    for (int i = 0; i < my_tieUpSize; i++) {
    	        my_pedals[i][the_y] = false; 
    	    }
    	    my_pedals[the_x][the_y] = the_val;
    	}
    }
    /**
     * Returns the row containing the active thread of a specific column
     * in the warp.
     * @param the_col Column number.
     * @return Returns row of active thread.
     */
    private int isInWarp(final int the_col) {
        for (int i = 0; i < my_tieUpSize; i++) {
            if (my_warp[the_col][i]) {
                return i;
            }
        }
        return -1;
    }
    /**
     * Returns the column containing the active thread of a specific row
     * in the pedals.
     * @param the_row Row number.
     * @return Returns row of active thread.
     */
    private int isInPedal(final int the_row) {
        for (int i = 0; i < my_tieUpSize; i++) {
            if (my_pedals[i][the_row]) {
                return i;
            }
        }
        return -1;
    }
    /**
     * Returns whether a thread in the center grid should be activated based
     * on the warp, tie-up, and pedal.
     * @param the_col Column number.
     * @param the_row Row number.
     * @return Returns true to activate center grid thread.
     */
    public boolean getValue(final int the_col, final int the_row) {
        int warp = isInWarp(the_col), pedal = isInPedal(the_row);
        if (warp > -1 && pedal > -1) {
            if (my_tieup[pedal][warp]) {
                return true;
            }
        }
        return false;
    }
}