import javax.swing.JFrame;


public class DraftMain {

  /**
   * @param args
   */
  public static void main(String[] args) {
    JFrame my_frame = new JFrame();
    WeaveDraft weave = new WeaveDraft(16, 4);
    my_frame.add(weave);
    my_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //my_frame.setPreferredSize(FRAME_DIM);
    my_frame.pack();
    my_frame.setLocationRelativeTo(null);
    my_frame.setVisible(true);

  }

}
