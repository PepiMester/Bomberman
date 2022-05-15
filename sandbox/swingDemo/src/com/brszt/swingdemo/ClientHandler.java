package com.brszt.swingdemo;

public class ClentHandler extends Runnable {
    //ez csinálja meg minden egyes kliens számára a kommunikációt, ill. annak funkcionális részét

    private Socket sock;
    private BufferedReader reader;
    private PrintWriter writer;

    public ClientHandler(Socket socket) {
        //hasonlóan mint a kliensnél
        this.sock = socket;
        try {
            reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            writer = new PrintWriter(sock.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        try {
            String text = reader.readLine();    //beolvassa a klienstől kapott üzenetet
            while (!text.equals("bye")){        //ha az az elköszönés, akkor kilép
                // funkcionális kiszolgáló rész ide jön
                String reverseText = new StringBuilder(text).reverse().toString();  //megfordítja a stringet
                writer.println("Response: " + reverseText);                         // és visszaküldi
                text = reader.readLine();       //olvassa a következő üzenetet a bemenetről
            }

            sock.close();

        } catch (IOException ex) {
            System.out.println("TCPServer exception: " + ex.getMessage());
            ex.printStackTrace();
        }

        System.out.println("Returning from ClientHandler thread...");
    }
}
