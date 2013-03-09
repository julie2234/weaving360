import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

public class WeaveDraft extends JPanel {
    private static final long serialVersionUID = -1275575997193739739L;
    private static final Color INITIAL_TOP_COLOR = Color.blue;
    private static final Color INITIAL_SIDE_COLOR = Color.white;
    private static final Dimension WEAVE_DIM = new Dimension(600, 600);
    private static final Color BACKGROUND_COLOR = new Color(105, 105, 105); //dim gray
    //new Color(112, 128, 144); slate gray
    private DraftStructure model;
    private Color[][] data;
    private DraftRender warp, tieup, pedals, center, topColorR, sideColorR;
    private Color my_color;
    private Color[] my_topColor, my_sideColor;
    private JButton my_colorB;
    private JButton my_submitB;
    private JButton my_cancelB;

    public void update() {
        for (int i = 0; i < model.gridSize; i += 1) {
            for (int j = 0; j < model.gridSize; j += 1) {
            	if (model.getValue(i, j)) {
            		data[i][j] = my_topColor[i];
            	} else {
            		data[i][j] = my_sideColor[j];
            	}
                //data[i][j] = model.getValue(i, j);
            }
        }

        warp.invalidate();
        tieup.invalidate();
        pedals.invalidate();
        center.invalidate();
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

    public DraftStructure getModel() {
        return model;
    }

    public WeaveDraft(int gridSize, int tieUpSize) {
        model = new DraftStructure(gridSize, tieUpSize);
        init();
    }

    public WeaveDraft(DraftStructure model) {
        this.model = model;
        init();
    }
    private void init() {
    	this.setPreferredSize(WEAVE_DIM);
    	this.setBackground(BACKGROUND_COLOR);
    	this.setLayout(new GridBagLayout());
        my_color = INITIAL_TOP_COLOR;
        my_topColor = new Color[model.gridSize];
        my_sideColor = new Color[model.gridSize];
        data = new Color[model.gridSize][model.gridSize];
        setColors();
        createButtons();
        for (int i = 0; i < model.gridSize; i++) {
            for (int j = 0; j < model.gridSize; j++) {
            	if (model.getValue(i, j)) {
            		data[i][j] = my_topColor[i];
            	} else {
            		data[i][j] = my_sideColor[j];
            	}
                //data[i][j] = model.getValue(i, j);
            }
        }
        topColorR = new DraftRender(1, model.gridSize - 1, my_topColor);
        sideColorR = new DraftRender(model.gridSize - 1, 1, my_sideColor);
        warp = new DraftRender(model.tieUpSize - 1, model.gridSize - 1, model.warp);
        tieup = new DraftRender(model.tieUpSize - 1, model.tieUpSize - 1, model.tieup);
        pedals = new DraftRender(model.gridSize - 1, model.tieUpSize - 1, model.pedals);
        center = new DraftRender(model.gridSize - 1, model.gridSize - 1, data);
        addMouseListeners();


        int width = model.tieUpSize * 20;
        int height = model.gridSize * 20;
        topColorR.setPreferredSize(new Dimension(height, 20));
        JPanel topColorPanel = new JPanel();
        topColorPanel.add(topColorR);
        //topColorPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        sideColorR.setPreferredSize(new Dimension(20, height));
        warp.setPreferredSize(new Dimension(height, width));
        tieup.setPreferredSize(new Dimension(width, width));
        pedals.setPreferredSize(new Dimension(width, height));
        center.setPreferredSize(new Dimension(height, height));
        
        //JSplitPane top = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topColorR, warp);
        JSplitPane left = new JSplitPane(JSplitPane.VERTICAL_SPLIT, warp, center);
        JSplitPane right = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tieup, pedals);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        //top.setBorder(BorderFactory.createEmptyBorder());
        splitPane.setBorder(BorderFactory.createEmptyBorder(15, 5, 0, 20));
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
        //this.add(splitPane);
    }
    private void setColors() {
    	for (int i = 0; i < model.gridSize; i++) {
    		my_topColor[i] = INITIAL_TOP_COLOR;
    		my_sideColor[i] = INITIAL_SIDE_COLOR;
    	}
    }
    private void addMouseListeners() {
    	topColorR.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) { }
            public void mouseEntered(MouseEvent e) { }
            public void mouseExited(MouseEvent e) { }
            public void mousePressed(MouseEvent e) {
                int cellX = topColorR.eventToCellX(e);
                //int cellY = topColorR.eventToCellY(e);
                my_topColor[cellX] = my_color;
                update();
            }
            public void mouseReleased(MouseEvent e) { }
        });
    	sideColorR.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) { }
            public void mouseEntered(MouseEvent e) { }
            public void mouseExited(MouseEvent e) { }
            public void mousePressed(MouseEvent e) {
                //int cellX = sideColorR.eventToCellX(e);
                int cellY = sideColorR.eventToCellY(e);
                my_sideColor[cellY] = my_color;
                update();
            }
            public void mouseReleased(MouseEvent e) { }
        });
        warp.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) { }
            public void mouseEntered(MouseEvent e) { }
            public void mouseExited(MouseEvent e) { }
            public void mousePressed(MouseEvent e) {
                int cellX = warp.eventToCellX(e);
                int cellY = warp.eventToCellY(e);
                model.toggleWarp(cellX, cellY);
                update();
            }
            public void mouseReleased(MouseEvent e) { }
        });

        tieup.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) { }
            public void mouseEntered(MouseEvent e) { }
            public void mouseExited(MouseEvent e) { }
            public void mousePressed(MouseEvent e) {
                int cellX = tieup.eventToCellX(e);
                int cellY = tieup.eventToCellY(e);
                model.toggleTieUp(cellX, cellY);
                update();
            }
            public void mouseReleased(MouseEvent e) { }
        });

        pedals.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) { }
            public void mouseEntered(MouseEvent e) { }
            public void mouseExited(MouseEvent e) { }
            public void mousePressed(MouseEvent e) {
                int cellX = pedals.eventToCellX(e);
                int cellY = pedals.eventToCellY(e);
                model.togglePedals(cellX, cellY);
                update();
            }
            public void mouseReleased(MouseEvent e) { }
        });	
    }
    private void buildGridBag(JSplitPane the_pane, JPanel the_topColorP) {
		GridBagConstraints c = new GridBagConstraints();
		/*c.fill = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 0;
		//c.ipady = 20;
		this.add(my_colorB, c);*/
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.WEST;
		//c.ipady = 0;
		c.gridx = 0;
		c.gridy = 1;
		this.add(the_topColorP, c);
		c.anchor = GridBagConstraints.BELOW_BASELINE_TRAILING;
		c.fill = GridBagConstraints.CENTER;
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(my_colorB);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 14));
		buttonPanel.setBackground(BACKGROUND_COLOR);
		c.gridx = 0;
		c.gridy = 1;
		this.add(buttonPanel, c);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.ipady= 0;
		c.gridx = 0;
		c.gridy = 2;
		this.add(the_pane, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.SOUTH;
		c.gridx = 1;
		c.gridy = 2;
		this.add(sideColorR, c);
		JPanel buttonGrid = new JPanel();
        GridLayout grid = new GridLayout(1, 0);
        grid.setHgap(5);
        buttonGrid.setLayout(grid);
		buttonGrid.add(my_submitB);
		buttonGrid.add(my_cancelB);
		buttonGrid.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		buttonGrid.setBackground(BACKGROUND_COLOR);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 3;
		this.add(buttonGrid, c);
    }
    private void createButtons() {
    	my_colorB = new JButton("Change Color");
    	my_colorB.setForeground(Color.white);
    	my_colorB.setBackground(my_color);
    	my_colorB.setMargin(new Insets(1, 1, 1, 1));
    	my_colorB.setFocusPainted(false);
    	//my_colorB.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
    	final JPanel weavePanel = this;
		my_colorB.addActionListener(new ActionListener() {
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
		my_submitB.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent the_event) {
			}
		});
       	my_cancelB = new JButton("Cancel");
    	my_cancelB.addActionListener(new ActionListener() {
    		public void actionPerformed(final ActionEvent the_event) {
   			}
   		});
    }
    private void changeSplitPaneDivider(JSplitPane the_pane) {
        the_pane.setUI(new BasicSplitPaneUI() {
            public BasicSplitPaneDivider createDefaultDivider() {
                return new BasicSplitPaneDivider(this) {
                    public void setBorder(Border b) {
                    }
                };
            }
        });
        //splitPane.setBorder(null);
    }
}
