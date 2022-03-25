package hu.brszt.tcpdemo;

import java.io.*;

public class main {
    /* Multithread tcp szerver demó */

    private static Thread serverThread;

    public static void main(String[] args){

        String host = "localhost";  //szerver címe
        boolean servermode = true;

        if(args.length == 1){       //ha megadjuk a szerver címét paraméterként, több klienssel is beléphetünk a demóba
            host = args[0];
            servermode = false;

        }else{                      //paraméter nélkül indítva a sima demó szerver indul el, egy klienssel
            StartServer();
            try {                  //biztonsági várakozás, hogy a szervez biztosan elinduljon thread módban
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //létrejön a kliens, csatlakozik a szerverhez
        TCPClient client = new TCPClient("localhost", 65535);

        //ez a standard inputról való beolvasáshoz kell
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

        try {
            //itt lehet üzengetni a szervernek, amit visszaküld, azt kiírja a program
            String response = "Írjál ide valamit, a végén pedig köszönj el (bye)";
            do{
                System.out.println(response);
                System.out.print(">");
                client.SendText(buffer.readLine());
                response = client.ReadResponse();
            }while(response != null);
            //ha nem érkezik válasz, a szerver bezárta a kapcsolatot
            //TODO ezt biztos meg lehet nézni kultúrált módon is
        } catch (IOException e) {
            e.printStackTrace();
        }

        //kliens kilép
        System.out.println("Client quitting.");
        client.close();

        //szerver leáll
        if(servermode) {
            serverThread.interrupt();
        }
    }

    private static void StartServer(){  //itt indul a szerver, ebből elég egy példány
        System.out.println("Starting new game server...");
        TCPServer GameServer = new TCPServer(65535);
        serverThread = new Thread(GameServer);
        serverThread.start();
    }
}
