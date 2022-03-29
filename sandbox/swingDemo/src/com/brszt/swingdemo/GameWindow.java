package com.brszt.swingdemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameWindow extends JFrame implements ActionListener {

    private Container contentframe = null;
    private JPanel mainmenu = new JPanel();
    //ide meg lehetne adni többféle dolgot is, ha pl. az ablak tartalmát szeretnénk cserélgetni

    public GameWindow(String title){
        super(title);
        Initialize();
    }

    private void Initialize(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     //így lehet bezárni az ablakot X-szel
        setSize(600,400);

        contentframe = getContentPane();                    //menü felépítése...
        contentframe.setBackground(Color.black);
        contentframe.setLayout(new CardLayout(150, 100));

        mainmenu.setBackground(null);
        mainmenu.setLayout(new GridLayout(3, 1, 30, 20));

        JButton btnStartServer = new JButton("Új játék indítása");
        JButton btnJoinButton = new JButton("Csatlakozás");
        JButton btnExtra = new JButton("Ez a gomb pedig eltünteti a menüt");

        btnStartServer.setBackground(Color.darkGray);
        btnJoinButton.setBackground(Color.darkGray);
        btnExtra.setBackground(Color.darkGray);

        btnStartServer.setForeground(Color.lightGray);
        btnJoinButton.setForeground(Color.lightGray);
        btnExtra.setForeground(Color.lightGray);

        btnStartServer.setFocusPainted(false);
        btnJoinButton.setFocusPainted(false);
        btnExtra.setFocusPainted(false);

        btnStartServer.setActionCommand("startCommand");    //ide bármilyen szöveget meg lehet adni
        btnJoinButton.setActionCommand("joinCommand");
        btnExtra.setActionCommand("hallod tesa");

        btnStartServer.addActionListener(this);
        btnJoinButton.addActionListener(this);
        btnExtra.addActionListener(this);

        mainmenu.add(btnStartServer);
        mainmenu.add(btnJoinButton);
        mainmenu.add(btnExtra);

        contentframe.add(mainmenu);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {  //itt kapódik el a gombok megnyomása

        System.out.println(actionEvent.paramString());

        //demó, az ablak tartalmát változtatja meg
        if(actionEvent.getActionCommand().equals("hallod tesa")){
            this.contentframe.remove(mainmenu);
            JOptionPane.showMessageDialog(this, "nahát mi történt");
        }
    }
}
