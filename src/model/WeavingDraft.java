package model;

public class WeavingDraft {
	
	/** Horizontal part of draft */
	private int[] _weft;
	/** Vertical part of draft */
	private int[] _warp;
	/** Weft and warp tie ins */
	private boolean[][] _ties;
	/** Pattern according to previous fields */
	private boolean[][] _pattern;
	
	public WeavingDraft(int[] weft, int[] warp, boolean[][] ties) {
		
		_weft = weft;
		_warp = warp;
		_ties = ties;
		_pattern = new boolean[_weft.length][_warp.length];
		
		generatePattern();
		
	}
	
	private void generatePattern() {
		
		for(int i = 0; i < _weft.length; i++) {
			for(int j = 0; j < _warp.length; j++) {
				
				if(_ties[_weft[i]][_warp[j]]) {
					
					_pattern[i][j] = true;
					
				}
				
			}
			
		}
		
	}

}
