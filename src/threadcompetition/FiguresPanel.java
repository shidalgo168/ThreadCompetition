/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadcompetition;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;
/**
 *
 * @author Sergio Hidalgo
 */
public class FiguresPanel extends JPanel {
    private static final long serialVersionUID = -6291233936414618049L;

    private ArrayList<Road> gameObjectsArray = new ArrayList<>();

    public FiguresPanel(int width) {
        setPreferredSize(new Dimension(width, width));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        
        for (int i = 0; i < gameObjectsArray.size(); i++) {
            gameObjectsArray.get(i).draw(g);
        }
    }
    
    public void updateFigures (ArrayList<Road> pRoadList) {
        this.gameObjectsArray.clear();
        pRoadList.forEach((Road currentRoad) -> {
            this.gameObjectsArray.add(currentRoad);
            });
        
    }
}
