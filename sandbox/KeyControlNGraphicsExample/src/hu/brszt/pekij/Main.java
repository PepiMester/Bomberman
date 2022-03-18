package hu.brszt.pekij;

class Main{
    public static void main(String[] args) {
        //thread módban megy a game, mert a főciklus egy bazinagy while, és nem szeretném ha zabálná a processzoridőt
        new Thread(new KeyListenerExample()).start();
    }
}