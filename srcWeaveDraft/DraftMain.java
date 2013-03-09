import java.awt.Dimension;

import javax.swing.JFrame;

public class DraftMain {

  /**
   * Main to test WeaveDraft.
   * @param args Main arguments.
   */
  public static void main(String[] args) {
      //Change to test resizing
      int gridSize = 24;
      int tieUp = 4;
      
      JFrame my_frame = new JFrame();
      WeaveDraft weave = new WeaveDraft(gridSize, tieUp);
      my_frame.add(weave);
      //my_frame.setMinimumSize(new Dimension((int) (gridSize*37.5), tieUp * 150));
      my_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //my_frame.setPreferredSize(FRAME_DIM);
      my_frame.pack();
      my_frame.setMinimumSize(my_frame.getSize());
      my_frame.setLocationRelativeTo(null);
      my_frame.setVisible(true);
  }
}
