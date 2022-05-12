package com.brszt.swingdemo;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GraphicContent {

    private int x = 0;
    public BufferedImage mapcontent;

    public GraphicContent(){
        mapcontent = new BufferedImage(32*15, 32*15, BufferedImage.TYPE_INT_RGB);
    }

    public void update(){
        Graphics2D g2d = mapcontent.createGraphics();
        g2d.clearRect(0, 0, 32*15, 32*15);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(64 + x, 64 + x, 32, 32);
        x = (x+1)%(100);
    }
}
