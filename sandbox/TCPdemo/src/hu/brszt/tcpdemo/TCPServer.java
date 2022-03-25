package hu.brszt.tcpdemo;

import java.io.*;
import java.net.*;

public class TCPServer implements Runnable{
    private int port;

    public TCPServer(int port){     //a megadott porton fog figyelni a cucc
        this.port = port;
    }

    @Override
    public void run() {

        ServerSocket serverSocket;

        try{
            serverSocket = new ServerSocket(port);      //kinyitja a portot
            serverSocket.setSoTimeout(1000);            //timeout azért kell, hogy le lehessen állítani a szervert,
                                                        // ne várakozzon az accept() végtelen ideig (ld alább)
            System.out.println("Server is listening on port " + port);

            while(!Thread.currentThread().isInterrupted()) {    //itt nézzük meg, hogy le akarjuk-e állítani a threadet kívülről
                try{
                    Socket socket = serverSocket.accept();      //várunk az új beérkező kapcsolatra
                    System.out.println("New client connected: " + socket.getRemoteSocketAddress().toString());
                    new ClientHandler(socket).start();          //nyitunk neki egy új szálat innen
                } catch (SocketTimeoutException ex) {
                    //itt nincs baj, csak senki sem csatlakozott az adott idő alatt (lejárt az accept timeout)
                }
            }

        }catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
            // itt kapunk el minden más pl. végzetes hibát, ami nem timeout
        }

        System.out.println("Server stopping...");
    }
}
