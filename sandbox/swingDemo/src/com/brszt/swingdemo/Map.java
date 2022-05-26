package com.brszt.swingdemo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

public class Map implements Serializable {

    public BufferedImage content;
    public static ArrayList<Objektum> Objects = new ArrayList<Objektum>();

    //konstansok, hogy itt egyszerre lehessen paraméterezni a mapot, ne kelljen mindenhol átírni
    // ha változtatni akarunk esetleg rajta
    public final int width = 15;
    public final int height = 15;

    public Map(){
        content = new BufferedImage(width * 32, height * 32, BufferedImage.TYPE_INT_RGB);
        Objects.add(new Objektum(64, 64));
    }

    public void update(){

        for(Objektum o : Objects){
            o.Update();
        }

        Graphics2D g2d = content.createGraphics();
        g2d.setColor(Color.gray);
        g2d.fillRect(0, 0, width * 32, height * 32);
        g2d.setColor(Color.BLACK);
        g2d.fillRect(Objects.get(0).getPosition()[0], Objects.get(0).getPosition()[1], 32, 32);
    }
}
