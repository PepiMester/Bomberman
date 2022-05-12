package com.brszt.swingdemo;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Map map = new Map();
        GameWindow gw = new GameWindow("Demo játék", map);

        long timer = System.currentTimeMillis();
        long prev = timer;
        while(true){
            timer = System.currentTimeMillis();
            if(gw.isInGame() && (timer - prev) > 10) {
                map.update();
                gw.repaint();
                prev = timer;
            }
        }
    }
}
