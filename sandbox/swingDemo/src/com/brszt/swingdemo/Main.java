package com.brszt.swingdemo;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        GraphicContent map = new GraphicContent();
        GameWindow gw = new GameWindow("Bomberman yeeték", map);

        long timer = System.currentTimeMillis();
        long prev = timer;
        while(true){
            timer = System.currentTimeMillis();
            if(gw.ingame && timer - prev > 10) {
                map.update();
                gw.repaint();
                prev = timer;
            }
        }
    }
}
