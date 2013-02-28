package model;

/**
 * Creates a weaving draft object.
 * 
 * @author James Marquardt
 *
 */
public class WeavingDraft {
	
	/** Horizontal part of draft */
	private int[] _weft;
	/** Vertical part of draft */
	private int[] _warp;
	/** Weft and warp tie ins */
	private boolean[][] _ties;
	/** Pattern according to previous fields */
	private boolean[][] _pattern;
	
	/**
	 * Constructor for a weaving draft object.
	 * 
	 * @param weft Settings of weft.
	 * @param warp Settings of warp.
	 * @param ties Settings of tie togethers.
	 */
	public WeavingDraft(int[] weft, int[] warp, boolean[][] ties) {
		
		_weft = weft;
		_warp = warp;
		_ties = ties;
		_pattern = new boolean[_weft.length][_warp.length];
		
		generatePattern();
		
	}
	
	public int[] getWeft() {
		
		return _weft;
		
	}
	
	public void setWeft(int[] weftsettings) {
		
		_weft = weftsettings;
		
	}
	
	public int[] getWarp() {
		
		return _warp;
		
	}
	
	public void setWarp(int[] warpsettings) {
		
		_warp = warpsettings;
		
	}
	
	/**
	 * Generates a weaving pattern based on the warp, weft, and ties.
	 */
	public void generatePattern() {
		
		for(int i = 0; i < _weft.length; i++) {
			for(int j = 0; j < _warp.length; j++) {
				
				if(_ties[_weft[i]][_warp[j]]) {
					
					_pattern[i][j] = true;
					
				}
				
			}
			
		}
		
	}

}
