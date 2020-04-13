/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bangmaple.jdbcgenerator;

/**
 *
 * @author bangmaple
 */
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class DraggableTabbedPane extends JTabbedPane {

    private boolean dragging;
    private Image tabImage;
    private Point currentMouseLocation;
    private int draggedTabIndex;

    public DraggableTabbedPane(final JFrame parent) {
        super();
        initVar();
        mouseMotionAdapterActionListener(parent);
        mouseAdapterActionListener();
    }

    private void initVar() {
        dragging = false;
        tabImage = null;
        currentMouseLocation = null;
        draggedTabIndex = 0;
    }

    private void mouseMotionAdapterActionListener(final JFrame parent) {
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (!dragging) {
                    int tabNumber = getUI().tabForCoordinate(DraggableTabbedPane.this, e.getX(), e.getY());
                    if (tabNumber >= 0) {
                        draggedTabIndex = tabNumber;
                        Rectangle bounds = getUI().getTabBounds(DraggableTabbedPane.this, tabNumber);
                        Image totalImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
                        Graphics totalGraphics = totalImage.getGraphics();
                        totalGraphics.setClip(bounds);
                        setDoubleBuffered(false);
                        paintComponent(totalGraphics);
                        tabImage = new BufferedImage(bounds.width, bounds.height, BufferedImage.TYPE_INT_ARGB);
                        Graphics graphics = tabImage.getGraphics();
                        graphics.drawImage(totalImage, 0, 0, bounds.width, bounds.height, bounds.x, bounds.y, bounds.x + bounds.width, bounds.y + bounds.height, DraggableTabbedPane.this);
                        dragging = true;
                        repaint();
                    }
                    int thisX = parent.getLocation().x;
                    int thisY = parent.getLocation().y;
                    int xMoved = e.getX() - currentMouseLocation.x;
                    int yMoved = e.getY() - currentMouseLocation.y;
                    int X = thisX + xMoved;
                    int Y = thisY + yMoved;
                    parent.setLocation(X, Y);
                } else {
                    currentMouseLocation = e.getPoint();
                    repaint();
                }
                super.mouseDragged(e);
            }
        });
    }

    private void mouseAdapterActionListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                currentMouseLocation = e.getPoint();
                getComponentAt(currentMouseLocation);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (dragging) {
                    int tabNumber = getUI().tabForCoordinate(DraggableTabbedPane.this, e.getX(), 10);

                    if (tabNumber >= 0) {
                        Component comp = getComponentAt(draggedTabIndex);
                        String title = getTitleAt(draggedTabIndex);
                        removeTabAt(draggedTabIndex);
                        insertTab(title, null, comp, null, tabNumber);
                    }
                }
                dragging = false;
                tabImage = null;
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (dragging && currentMouseLocation != null && tabImage != null) {
            g.drawImage(tabImage, currentMouseLocation.x, currentMouseLocation.y, this);
        }
    }
}
