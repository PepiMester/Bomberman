package Bomberman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

public class Window extends JFrame implements ActionListener {
    private JPanel mainmenu;
    private Map map;
    private boolean isClient;
    private String hostAddress;

    public CountDownLatch gameModeSelected = new CountDownLatch(1);

    public boolean gameModeIsClient(){
        return isClient;
    }

    public String getHostAddress(){
        return hostAddress;
    }

    public Window(String title){
        super(title);
        Initialize();

        //uncomment this to bypass main menu:
        //getContentPane().remove(mainmenu);
        //addKeyListener(Map.Players.get(0));
        //requestFocusInWindow();
        //gameStarted.countDown();
    }

    public void setMap(Map map){
        this.map = map;
    }

    private void Initialize(){

        getContentPane().setBackground(Color.black);
        getContentPane().setLayout(new CardLayout(50, 180));

        mainmenu = new JPanel();

        mainmenu.setBackground(null);
        mainmenu.setLayout(new GridLayout(2, 1, 30, 20));

        JButton btnStartServer = new JButton("Új játék indítása");
        JButton btnJoinButton = new JButton("Csatlakozás");

        btnStartServer.setBackground(Color.darkGray);
        btnJoinButton.setBackground(Color.darkGray);

        btnStartServer.setForeground(Color.lightGray);
        btnJoinButton.setForeground(Color.lightGray);

        btnStartServer.setFocusPainted(false);
        btnJoinButton.setFocusPainted(false);

        btnStartServer.setActionCommand("startCommand");    //ide bármilyen szöveget meg lehet adni
        btnJoinButton.setActionCommand("joinCommand");

        btnStartServer.addActionListener(this);
        btnJoinButton.addActionListener(this);

        mainmenu.add(btnStartServer);
        mainmenu.add(btnJoinButton);

        getContentPane().add(mainmenu);

        setIconImage(new ImageIcon("./src/Sprites/player1_.png").getImage());

        setDefaultCloseOperation(EXIT_ON_CLOSE);     //így lehet bezárni az ablakot X-szel
        getContentPane().setPreferredSize(new Dimension(map.getWidth() * 32 + getInsets().left + getInsets().right, map.getHeight() * 32 + getInsets().top + getInsets().bottom));
        setResizable(false);
        pack();
        setLocationRelativeTo(null);    //ablak középre igazítása a képernyőn
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {  //itt kapódik el a gombok megnyomása
        if(actionEvent.getActionCommand().equals("startCommand"))
        {
            getContentPane().remove(mainmenu);
            addKeyListener(Map.Players.get(0));
            requestFocusInWindow();
            this.isClient = false;
            gameModeSelected.countDown();
        }
        else if(actionEvent.getActionCommand().equals("joinCommand"))
        {
            getContentPane().remove(mainmenu);
            this.hostAddress = JOptionPane.showInputDialog(this, "HOVA");
            addKeyListener(Map.Players.get(1));
            requestFocusInWindow();
            this.isClient = true;
            gameModeSelected.countDown();
        }
    }

    @Override
    public void paint(Graphics g){
        if(gameModeSelected.getCount()==0) {
            if(this.map!=null) {
                g.drawImage(map.MapContent, getInsets().left, getInsets().top, null);
            }else{
                g.setColor(Color.lightGray);
                g.setFont(g.getFont().deriveFont(20f));
                String msg = "Várakozás a másik játékosra...";
                g.drawString(msg, getWidth() / 2 - (int) g.getFontMetrics().getStringBounds(msg, g).getCenterX(), getHeight()/2);
            }
        }
    }
}
