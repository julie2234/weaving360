package weavedraft;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class WeaveDraft extends JPanel {
    private static final long serialVersionUID = -1275575997193739739L;
    private DraftStructure model;
    private boolean[][] data;
    private DraftRender warp, tieup, pedals, center;

    public void update() {
        for (int i = 0; i < model.gridSize; i += 1) {
            for (int j = 0; j < model.gridSize; j += 1) {
                data[i][j] = model.getValue(i, j);
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
        data = new boolean[model.gridSize][model.gridSize];

        for (int i = 0; i < model.gridSize; i += 1) {
            for (int j = 0; j < model.gridSize; j += 1) {
                data[i][j] = model.getValue(i, j);
            }
        }

        warp = new DraftRender(model.tieUpSize - 1, model.gridSize - 1, model.warp);
        tieup = new DraftRender(model.tieUpSize - 1, model.tieUpSize - 1, model.tieup);
        pedals = new DraftRender(model.gridSize - 1, model.tieUpSize - 1, model.pedals);
        center = new DraftRender(model.gridSize - 1, model.gridSize - 1, data);

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

        int width = model.tieUpSize * 20, height = model.gridSize * 20;
        warp.setPreferredSize(new Dimension(height, width));
        tieup.setPreferredSize(new Dimension(width, width));
        pedals.setPreferredSize(new Dimension(width, height));
        center.setPreferredSize(new Dimension(height, height));

        JSplitPane left = new JSplitPane(JSplitPane.VERTICAL_SPLIT, warp, center);
        JSplitPane right = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tieup, pedals);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);

        splitPane.setBorder(BorderFactory.createEmptyBorder());
        left.setBorder(BorderFactory.createEmptyBorder());
        right.setBorder(BorderFactory.createEmptyBorder());

        splitPane.setEnabled(false);
        left.setEnabled(false);
        right.setEnabled(false);

        this.add(splitPane);
    }
}