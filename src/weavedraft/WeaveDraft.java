package weavedraft;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import model.Entry;

import controller.Controls;

public class WeaveDraft extends JPanel implements Serializable {
    private static final long serialVersionUID = -1275575997193739739L;
    private static final Color INITIAL_TOP_COLOR = Color.blue;
    private static final Color INITIAL_SIDE_COLOR = Color.white;
    private static final Dimension WEAVE_DIM = new Dimension(600, 600);
    private static final Color BACKGROUND_COLOR = new Color(105, 105, 105); //dim gray
    //new Color(112, 128, 144); slate gray
    private final DraftStructure my_draftStruct;
    private final Color[][] my_data;
    private final Color[] my_topColor, my_sideColor;
    private final int my_threadSize;
    private DraftRender warpR, tieupR, pedalsR, centerR, topColorR, sideColorR;
    private Color my_color;
    private JButton my_colorB;
    private JButton my_submitB;
    private JButton my_cancelB;
    private Entry my_entry;
    private Controls my_control;
    private JDialog my_dialog;
    /**
     * Constructs WeaveDraft based on parameters for grid size and tieup size. 
     * @param gridSize Integer for width and height of center draft space.
     * @param tieUpSize Integer for width and height of draft tieup. 
     */
    public WeaveDraft(int gridSize, int tieUpSize, Entry entry, Controls control, 
                      JDialog dialog) {
        my_dialog = dialog;
        my_entry = entry;
        my_control = control;
        my_draftStruct = new DraftStructure(gridSize, tieUpSize);
        my_topColor = new Color[my_draftStruct.my_gridSize];
        my_sideColor = new Color[my_draftStruct.my_gridSize];
        my_data = new Color[my_draftStruct.my_gridSize][my_draftStruct.my_gridSize];
        if (gridSize <= 20) {
            my_threadSize = 20;
        } else if (gridSize <= 28) {
            my_threadSize = 15;
        } else {
            my_threadSize = 10;
        }
        init();
    }
    /*public WeaveDraft(DraftStructure the_model) {
        this.my_draftStruct = the_model;
        init();
    }*/
    /**
     * Updates and repaints the center draft display.
     */
    public void update() {
        for (int i = 0; i < my_draftStruct.my_gridSize; i++) {
            for (int j = 0; j < my_draftStruct.my_gridSize; j++) {
                if (my_draftStruct.getValue(i, j)) {
                    my_data[i][j] = my_topColor[i];
                } else {
                    my_data[i][j] = my_sideColor[j];
                }
            }
        }
        warpR.invalidate();
        tieupR.invalidate();
        pedalsR.invalidate();
        centerR.invalidate();
        this.revalidate();
        this.repaint();
    }
    /**
     * Save a screenshot of the weavedraft to the specified file path
     * @param path Path to save image to
     * @return Image file
     * @throws IOException
     */
    public File saveScreenshot(String path) throws IOException {
        BufferedImage bufImage = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_RGB);
        paint(bufImage.createGraphics());

        File imageFile = new File(path);
        imageFile.createNewFile();
        ImageIO.write(bufImage, "jpeg", imageFile);

        return imageFile;
    }
    /**
     * Returns DraftStructure object containing the warp, tieup, and pedals
     * of a weave draft.
     * @return Returns DraftStructure of this WeaveDraft
     */
    public DraftStructure getModel() {
        return my_draftStruct;
    }
    /**
     * Initializes a new WeaveDraft (Helper to constructor).
     */
    private void init() {
        this.setMinimumSize(WEAVE_DIM);
        this.setBackground(BACKGROUND_COLOR);
        this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED), 
                                                                                             BorderFactory.createBevelBorder(BevelBorder.LOWERED)), 
                                                          BorderFactory.createEmptyBorder(30, 30, 30, 30)));
        this.setLayout(new GridBagLayout());
        my_color = INITIAL_TOP_COLOR;
        setColors();
        createButtons();
        //Set color of center draft grid.
        for (int i = 0; i < my_draftStruct.my_gridSize; i++) {
            for (int j = 0; j < my_draftStruct.my_gridSize; j++) {
                if (my_draftStruct.getValue(i, j)) {
                    my_data[i][j] = my_topColor[i];
                } else {
                    my_data[i][j] = my_sideColor[j];
                }
            }
        }
        topColorR = new DraftRender(my_threadSize, 1, my_draftStruct.my_gridSize - 1, my_topColor);
        sideColorR = new DraftRender(my_threadSize, my_draftStruct.my_gridSize - 1, 1, my_sideColor);
        warpR = new DraftRender(my_threadSize, my_draftStruct.my_tieUpSize - 1, 
                                my_draftStruct.my_gridSize - 1, my_draftStruct.my_warp);
        tieupR = new DraftRender(my_threadSize, my_draftStruct.my_tieUpSize - 1, 
                                 my_draftStruct.my_tieUpSize - 1, my_draftStruct.my_tieup);
        pedalsR = new DraftRender(my_threadSize, my_draftStruct.my_gridSize - 1, 
                                  my_draftStruct.my_tieUpSize - 1, my_draftStruct.my_pedals);
        centerR = new DraftRender(my_threadSize, my_draftStruct.my_gridSize - 1, 
                                  my_draftStruct.my_gridSize - 1, my_data);
        addMouseListeners();

        int width = my_draftStruct.my_tieUpSize * my_threadSize;
        int height = my_draftStruct.my_gridSize * my_threadSize;
        topColorR.setPreferredSize(new Dimension(height, my_threadSize));
        JPanel topColorPanel = new JPanel();
        topColorPanel.add(topColorR);
        //topColorPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        sideColorR.setPreferredSize(new Dimension(my_threadSize, height));
        warpR.setPreferredSize(new Dimension(height, width));
        tieupR.setPreferredSize(new Dimension(width, width));
        pedalsR.setPreferredSize(new Dimension(width, height));
        centerR.setPreferredSize(new Dimension(height, height));

        //JSplitPane top = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topColorR, warp);
        JSplitPane left = new JSplitPane(JSplitPane.VERTICAL_SPLIT, warpR, centerR);
        JSplitPane right = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tieupR, pedalsR);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        //top.setBorder(BorderFactory.createEmptyBorder());
        splitPane.setBorder(BorderFactory.createEmptyBorder(my_threadSize - 5, 5, 0, my_threadSize));
        left.setBorder(BorderFactory.createEmptyBorder());
        right.setBorder(BorderFactory.createEmptyBorder());

        splitPane.setEnabled(false);
        //top.setEnabled(false);
        left.setEnabled(false);
        right.setEnabled(false);
        changeSplitPaneDivider(left);
        changeSplitPaneDivider(right);
        changeSplitPaneDivider(splitPane);
        left.setBackground(BACKGROUND_COLOR);
        right.setBackground(BACKGROUND_COLOR);
        splitPane.setBackground(BACKGROUND_COLOR);
        topColorPanel.setBackground(BACKGROUND_COLOR);
        buildGridBag(splitPane, topColorPanel);
        //this.setMinimumSize(WEAVE_DIM);
        //this.add(splitPane);
    }
    /**
     * Sets colors of top and side bars.
     */
    private void setColors() {
        for (int i = 0; i < my_draftStruct.my_gridSize; i++) {
            my_topColor[i] = INITIAL_TOP_COLOR;
            my_sideColor[i] = INITIAL_SIDE_COLOR;
        }
    }
    /**
     * Adds mouse listeners to all renderer panels.
     */
    private void addMouseListeners() {
        topColorR.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { }
            @Override
            public void mouseEntered(MouseEvent e) { }
            @Override
            public void mouseExited(MouseEvent e) { }
            @Override
            public void mousePressed(MouseEvent e) {
                int cellX = topColorR.eventToCellX(e);
                //int cellY = topColorR.eventToCellY(e);
                my_topColor[cellX] = my_color;
                update();
            }
            @Override
            public void mouseReleased(MouseEvent e) { }
        });
        topColorR.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int cellX = topColorR.eventToCellX(e);
                if (cellX >= 0 && cellX < my_draftStruct.my_gridSize) {
                    my_topColor[cellX] = my_color;
                    update();
                }
            }
            @Override
            public void mouseMoved(MouseEvent e) {}
        });
        sideColorR.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { }
            @Override
            public void mouseEntered(MouseEvent e) { }
            @Override
            public void mouseExited(MouseEvent e) { }
            @Override
            public void mousePressed(MouseEvent e) {
                //int cellX = sideColorR.eventToCellX(e);
                int cellY = sideColorR.eventToCellY(e);
                my_sideColor[cellY] = my_color;
                update();
            }
            @Override
            public void mouseReleased(MouseEvent e) { }
        });
        sideColorR.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int cellY = sideColorR.eventToCellY(e);
                if (cellY >= 0 && cellY < my_draftStruct.my_gridSize) {
                    my_sideColor[cellY] = my_color;
                    update();
                }
            }
            @Override
            public void mouseMoved(MouseEvent e) {}
        });
        warpR.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { }
            @Override
            public void mouseEntered(MouseEvent e) { }
            @Override
            public void mouseExited(MouseEvent e) { }
            @Override
            public void mousePressed(MouseEvent e) {
                int cellX = warpR.eventToCellX(e);
                int cellY = warpR.eventToCellY(e);
                my_draftStruct.toggleWarp(cellX, cellY);
                update();
            }
            @Override
            public void mouseReleased(MouseEvent e) { }
        });
        warpR.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int cellX = warpR.eventToCellX(e);
                int cellY = warpR.eventToCellY(e);
                if (cellX >= 0 && cellX < my_draftStruct.my_gridSize && 
                        cellY >= 0 && cellY < my_draftStruct.my_tieUpSize) {
                    my_draftStruct.toggleWarp(cellX, cellY);
                    update();
                }
            }
            @Override
            public void mouseMoved(MouseEvent e) {}
        });
        tieupR.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { }
            @Override
            public void mouseEntered(MouseEvent e) { }
            @Override
            public void mouseExited(MouseEvent e) { }
            @Override
            public void mousePressed(MouseEvent e) {
                int cellX = tieupR.eventToCellX(e);
                int cellY = tieupR.eventToCellY(e);
                my_draftStruct.toggleTieUp(cellX, cellY);
                update();
            }
            @Override
            public void mouseReleased(MouseEvent e) { }
        });
        pedalsR.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { }
            @Override
            public void mouseEntered(MouseEvent e) { }
            @Override
            public void mouseExited(MouseEvent e) { }
            @Override
            public void mousePressed(MouseEvent e) {
                int cellX = pedalsR.eventToCellX(e);
                int cellY = pedalsR.eventToCellY(e);
                my_draftStruct.togglePedals(cellX, cellY);
                update();
            }
            @Override
            public void mouseReleased(MouseEvent e) { }
        });
        pedalsR.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int cellX = warpR.eventToCellX(e);
                int cellY = warpR.eventToCellY(e);
                if (cellX >= 0 && cellX < my_draftStruct.my_tieUpSize && 
                        cellY >= 0 && cellY < my_draftStruct.my_gridSize) {
                    my_draftStruct.togglePedals(cellX, cellY);
                    update();
                }
            }
            @Override
            public void mouseMoved(MouseEvent e) {}
        });
    }
    /**
     * Builds GridBagLayout for WeaveDraft panel. 
     * 
     * @param the_pane JSplitPane containing warp, tieup, pedals, and center panel.
     * @param the_topColorP Top color panel.
     */
    private void buildGridBag(JSplitPane the_pane, JPanel the_topColorP) {
        GridBagConstraints c = new GridBagConstraints();
        /*c.fill = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 0;
		//c.ipady = 20;
		this.add(my_colorB, c);*/
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 1;
        this.add(the_topColorP, c);
        c.anchor = GridBagConstraints.BELOW_BASELINE_TRAILING;
        c.fill = GridBagConstraints.CENTER;
        //c.gridwidth = 2;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(my_colorB);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, my_threadSize - 5));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        c.gridx = 0;
        c.gridy = 1;
        this.add(buttonPanel, c);
        //c.gridwidth = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        this.add(the_pane, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.SOUTH;
        c.gridx = 1;
        c.gridy = 2;
        this.add(sideColorR, c);
        //JPanel with GridLayout for submit and cancel buttons
        JPanel buttonGrid = new JPanel();
        GridLayout grid = new GridLayout(1, 0);
        grid.setHgap(5);
        buttonGrid.setLayout(grid);
        buttonGrid.add(my_submitB);
        buttonGrid.add(my_cancelB);
        buttonGrid.setBorder(BorderFactory.createEmptyBorder(my_threadSize, 0, 0, 0));
        buttonGrid.setBackground(BACKGROUND_COLOR);
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 3;
        this.add(buttonGrid, c);
    }
    /**
     * Creates all JButtons for WeaveDraft.
     */
    private void createButtons() {
        my_colorB = new JButton("Color");
        my_colorB.setForeground(Color.white);
        my_colorB.setBackground(my_color);
        my_colorB.setMargin(new Insets(1, my_threadSize / 3, 1, my_threadSize / 3));
        my_colorB.setFocusPainted(false);
        final JPanel weavePanel = this;
        my_colorB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent the_event) {
                final Color variable_color = JColorChooser.showDialog(weavePanel, "Color Chooser", my_color);
                if (variable_color != null) {
                    my_color = variable_color;
                    if (variable_color.equals(Color.white)) {
                        my_colorB.setForeground(Color.black);
                    } else {
                        my_colorB.setForeground(Color.white);
                    }
                    my_colorB.setBackground(my_color);
                }
            }
        });
        
        my_submitB = new JButton("Submit");
        my_submitB.setBackground(new Color(169, 169, 169));
        my_submitB.setMargin(new Insets(2, 12, 2, 12));
        my_submitB.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), 
                                                                BorderFactory.createEmptyBorder(0, 4, 0, 4)));
        my_submitB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent the_event){
                
                BufferedImage image = null;
                
                try {
                    image = new Robot().createScreenCapture(new Rectangle(
                                    my_dialog.getLocation(), my_dialog.getSize()));
                } catch (HeadlessException e) {
                    e.printStackTrace();
                } catch (AWTException e) {
                    e.printStackTrace();
                }
                
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {
                    ImageIO.write(image, "gif", baos);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    baos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                byte[] imageInByte = baos.toByteArray();
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                my_control.submitEntryFromDraft(my_entry, imageInByte,
                                                /*draft,*/ my_dialog);
            }
        });
        my_cancelB = new JButton("Cancel");
        my_cancelB.setBackground(new Color(169, 169, 169));
        my_cancelB.setMargin(new Insets(2, 8, 2, 8));
        my_cancelB.setBorder(BorderFactory.createRaisedBevelBorder());
        my_cancelB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent the_event) {
                my_control.cancelFromDialog(my_dialog);     
            }
        });
    }
    /**
     * Helper method that removes standard border when using a JSplitPane.
     * @param the_pane JSplitPane to have border removed from.
     */
    private void changeSplitPaneDivider(JSplitPane the_pane) {
        the_pane.setUI(new BasicSplitPaneUI() {
            @Override
            public BasicSplitPaneDivider createDefaultDivider() {
                return new BasicSplitPaneDivider(this) {
                    /** Serializable ID*/
                    private static final long serialVersionUID = 7456462406148070891L;

                    @Override
                    public void setBorder(Border b) {
                    }
                };
            }
        });
        //splitPane.setBorder(null);
    }
}
