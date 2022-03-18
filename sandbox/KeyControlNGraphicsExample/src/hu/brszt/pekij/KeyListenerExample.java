package hu.brszt.pekij;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

//csak demó, ezt illik majd külön szedni az interenet, a billenytűzet- és a játéktér grafikus ill. logikai kezelésére
public class KeyListenerExample extends Frame implements KeyListener, Runnable {

    //játékos helye
    private Point location = new Point(200, 200);

    //egyszerre lenyomott billentyűk kódjainak "tömbje"
    private HashMap<Integer, Boolean> control = new HashMap<Integer, Boolean>();

    KeyListenerExample() {

        //ezeket a billentyűket nézzük, lenyomás inicializálása false-ra
        control.put(KeyEvent.VK_UP, false);
        control.put(KeyEvent.VK_DOWN, false);
        control.put(KeyEvent.VK_LEFT, false);
        control.put(KeyEvent.VK_RIGHT, false);

        //eseményvezérelt dolog beállítása
        addKeyListener(this);

        //ablak cicomázás
        setTitle("Szevasztok gyerekek");
        setSize(400, 400);
        setLayout(null);
        setVisible(true);

        //zsa
        run();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //nyilvántartjuk, mit nyomtak meg
        if(control.containsKey(e.getKeyCode())){
            control.replace(e.getKeyCode(), true);
        }else{
            System.out.println("Ezt a billentyűt nyomták le: " + e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //nyilvántartjuk, mit engedtek el
        if(control.containsKey(e.getKeyCode())){
            control.replace(e.getKeyCode(), false);
        }else{
            System.out.println("Ezt a billentyűt engedték el: " + e.getKeyCode());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //ide nem kell semmi
    }

    //kirajzoló dolog, itt kell majd mindent kirajzolni
    @Override
    public void paint(Graphics g) {
        //ide kell egy kettős bufferelés azt jólesz, és nem fog villogni az objektum
        g.fillRect(location.x, location.y, 10, 10);
    }

    //módosítjuk a mindenfélét, itt még pl. csak a játékos helyzete izgalmas
    private void update(){
        if(control.get(KeyEvent.VK_DOWN)==true){
            location.y++;
        }
        if(control.get(KeyEvent.VK_UP)==true){
            location.y--;
        }
        if(control.get(KeyEvent.VK_LEFT)==true){
            location.x--;
        }
        if(control.get(KeyEvent.VK_RIGHT)==true){
            location.x++;
        }
    }

    //főciklus
    @Override
    public void run() {

        //időzítős dolgok
        double delta = 0;
        long lastTime = System.nanoTime();
        final double NS = 1000000000.0 / 60.0;  //??? nemtudommi, úgy találtam stackoverflow-n

        //indul a mandula
        System.out.println("DADADADA DA! run");

        while(true){
            //tick kiszámoló algoritmus
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / NS;
            lastTime = currentTime;

            //ha kell frissíteni, frissíti
            if (delta >= 1) {
                update();
                delta--;
            }

            //kivéve a játékteret, mert azt gyakrabban
            repaint();
        }
    }
}

//TODO: még nem lehet bezárni a játékban az ablakot, érdemes megoldani később