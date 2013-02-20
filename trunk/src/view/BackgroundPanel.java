/*
 * Matthew Adams
 * 
 * TCSS 305A Spring 2012
 * Tetris Project
 */
package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 *  Support custom painting on a panel in the form of
 *
 *  a) images - that can be scaled, tiled or painted at original size
 *  b) non solid painting - that can be done by using a Paint object
 *
 *  Also, any component added directly to this panel will be made
 *  non-opaque so that the custom painting can show through.
 *  
 * source: 
 * http://tips4java.wordpress.com/2008/10/12/background-panel/
 * Date of Original: October 12, 2008
 * @author Rob Camick
 * @author Matthew Adams
 * @version updated 5/28/12
 */
@SuppressWarnings("serial")
public class BackgroundPanel extends JPanel {
  /**
   * Integer value representing a scaled image.
   */
  public static final int SCALED = 0;
  /**
   * Integer value representing a tiled image.
   */
  public static final int TILED = 1;
  /**
   * Integer value representing an actual sized image. 
   */
  public static final int ACTUAL = 2;
  /**
   * Paint object.
   */
  private Paint my_painter;
  /**
   * Image being processed.
   */
  private Image my_image;
  /**
   * Integer representing style of image (scaled, tiled, actual).
   */
  private int my_style;
  /**
   * x coordinate alignment.
   */
  private float my_alignment_x;
  /**
   * y coordinate alignment.
   */
  private float my_alignment_y;
  /**
   * current transparency state.
   */
  private boolean my_transparent_add = true;

  /**
   * Set image as the background with the SCALED style.
   * 
   * @param the_image Image to be set as background.
   */
  public BackgroundPanel(final Image the_image) {
    this(the_image, SCALED);
  }

  /**
   * Set image as the background with the specified style.
   * 
   * @param the_image Image object.
   * @param the_style Integer representing style of background image.
   */
  public BackgroundPanel(final Image the_image, final int the_style) {
    super();
    setImage(the_image);
    setStyle(the_style);
    setLayout(new BorderLayout());
  }

  /**
   * Set image as the background with the specified style and alignment.
   * 
   * @param the_image Image object.
   * @param the_style Integer representing style of background image.
   * @param the_alignment_x X axis float alignment value.
   * @param the_alignment_y Y axis float alignment value.
   */
  public BackgroundPanel(final Image the_image, final int the_style, 
                         final float the_alignment_x, final float the_alignment_y) {
    super();
    setImage(the_image);
    setStyle(the_style);
    setImageAlignmentX(the_alignment_x);
    setImageAlignmentY(the_alignment_y);
    setLayout(new BorderLayout());
  }
  /**
   * Use the Paint interface to paint a background.
   * @param the_painter Paint object used to paint background.
   */
  public BackgroundPanel(final Paint the_painter) {
    super();
    setPaint(the_painter);
    setLayout(new BorderLayout());
  }

  /**
   * Set the image used as the background.
   * @param the_image Image used to set as background.
   */
  private void setImage(final Image the_image) {
    my_image = the_image;
    repaint();
  }

  /**
   * Set the style used to paint the background image.
   * @param the_style Integer representing style of image.
   */
  private void setStyle(final int the_style) {
    my_style = the_style;
    repaint();
  }

  /**
   * Set the Paint object used to paint the background.
   * @param the_painter Paint object used to paint background.
   */
  private void setPaint(final Paint the_painter) {
    my_painter = the_painter;
    repaint();
  }

  /**
   * Specify the horizontal alignment of the image when using ACTUAL style.
   * @param the_alignment_x Float value for x alignment.
   */
  private void setImageAlignmentX(final float the_alignment_x) {
    if (the_alignment_x > 1.0f) {
      my_alignment_x = 1.0f; 
    } else if (the_alignment_x < 0.0f) {
      my_alignment_x = 0.0f;
    } else {
      my_alignment_x = the_alignment_x;
    }
    //my_alignment_x = the_alignment_x > 1.0f ? 1.0f : 
    //the_alignment_x < 0.0f ? 0.0f : the_alignment_x;
    repaint();
  }

  /**
   * Specify the horizontal alignment of the image when using ACTUAL style.
   * @param the_alignment_y Float value for y alignment.
   */
  private void setImageAlignmentY(final float the_alignment_y) {
    if (the_alignment_y > 1.0f) {
      my_alignment_y = 1.0f; 
    } else if (the_alignment_y < 0.0f) {
      my_alignment_y = 0.0f;
    } else {
      my_alignment_y = the_alignment_y;
    }
    //my_alignment_y = the_alignment_y > 1.0f ? 1.0f : 
    //the_alignment_y < 0.0f ? 0.0f : the_alignment_y;
    repaint();
  }

  /**
   * Override method so we can make the component transparent.
   * @param the_component Component to be made transparent.
   */
  public void add(final JComponent the_component) {
    add(the_component, null);
  }

  /**
   * Override to provide a preferred size equal to the image size.
   * @return Returns dimension of preferredSize().
   */
  /*@Override
  public Dimension getPreferredSize() {
    Dimension variable_dim = new Dimension();
    if (my_image == null) {
      variable_dim = super.getPreferredSize();
    } else {
      variable_dim = new Dimension(my_image.getWidth(null), my_image.getHeight(null));
    }
    return variable_dim;
  }*/

  /**
   * Override method so we can make the component transparent.
   * @param the_component Component to be made transparent.
   * @param the_constraints Constraints of creating transparency.
   */
  public void add(final JComponent the_component, final Object the_constraints) {
    if (my_transparent_add) {
      makeComponentTransparent(the_component);
    }

    super.add(the_component, the_constraints);
  }

  /**
   * Controls whether components added to this panel should automatically be
   * made transparent. That is, setOpaque(false) will be invoked. The default is
   * set to true.
   * @param the_transparent_add Whether or not component should be made transparent.
   */
  public void setTransparentAdd(final boolean the_transparent_add) {
    this.my_transparent_add = the_transparent_add;
  }

  /**
   * Try to make the component transparent. For components that use renderers,
   * like JTable, you will also need to change the renderer to be transparent.
   * An easy way to do this it to set the background of the table to a Color
   * using an alpha value of 0.
   * @param the_component Component to be made transparent.
   */
  private void makeComponentTransparent(final JComponent the_component) {
    the_component.setOpaque(false);

    if (the_component instanceof JScrollPane) {
      final JScrollPane variable_scroll_pane = (JScrollPane) the_component;
      final JViewport viewport = variable_scroll_pane.getViewport();
      viewport.setOpaque(false);
      final Component variable_c = viewport.getView();

      if (variable_c instanceof JComponent) {
        ((JComponent) variable_c).setOpaque(false);
      }
    }
  }

  /**
   * Add custom painting.
   * @param the_g Graphics for paintComponent.
   */
  @Override
  protected void paintComponent(final Graphics the_g) {
    super.paintComponent(the_g);

    // Invoke the painter for the background

    if (my_painter != null) {
      final Dimension d = getSize();
      final Graphics2D g2d = (Graphics2D) the_g;
      g2d.setPaint(my_painter);
      g2d.fill(new Rectangle(0, 0, d.width, d.height));
    }

    // Draw the image

    if (my_image == null) {
      return;
    }
    switch (my_style) {
      case SCALED:
        drawScaled(the_g);
        break;

      case TILED:
        drawTiled(the_g);
        break;

      case ACTUAL:
        drawActual(the_g);
        break;

      default:
        drawScaled(the_g);
    }
  }

  /**
   * Custom painting code for drawing a SCALED image as the background.
   * @param the_g Graphics to draw image.
   */
  private void drawScaled(final Graphics the_g) {
    final Dimension variable_d = getSize();
    the_g.drawImage(my_image, 0, 0, variable_d.width, variable_d.height, null);
  }

  /**
   * Custom painting code for drawing TILED images as the background.
   * @param the_g Graphics to draw tiled image.
   */
  private void drawTiled(final Graphics the_g) {
    final Dimension d = getSize();
    final int width = my_image.getWidth(null);
    final int height = my_image.getHeight(null);

    for (int x = 0; x < d.width; x += width) {
      for (int y = 0; y < d.height; y += height) {
        the_g.drawImage(my_image, x, y, null, null);
      }
    }
  }

  /**
   * Custom painting code for drawing the ACTUAL image as the background. The
   * image is positioned in the panel based on the horizontal and vertical
   * alignments specified.
   * @param the_g Graphics to draw image actual size.
   */
  private void drawActual(final Graphics the_g) {
    final Dimension variable_d = getSize();
    final Insets variable_insets = getInsets();
    final int width = variable_d.width - variable_insets.left - variable_insets.right;
    final int height = variable_d.height - variable_insets.top - variable_insets.left;
    final float variable_x = (width - my_image.getWidth(null)) * my_alignment_x;
    final float variable_y = (height - my_image.getHeight(null)) * my_alignment_y;
    the_g.drawImage(my_image, (int) variable_x + variable_insets.left, 
                    (int) variable_y + variable_insets.top, this);
  }
}