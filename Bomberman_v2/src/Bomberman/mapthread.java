package Bomberman;

import Network.Server;

import static Model.Main.Game;

public class mapthread implements Runnable{
    public void run() {
        for(;;) {
            Server.sendMap(Game);
        }
    }
}
