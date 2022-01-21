package datastucture;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;

public class SplashScreen extends javax.swing.JFrame {

    Font IronShark;
    // LOGO 
    public SplashScreen() {
        initComponents();

        // CENTER POPUP WINDOW
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);

        // COMPANY LOGO
        ImageIcon CompLogo = new ImageIcon(getClass().getClassLoader().getResource("res/Brion-Tactical-Systems.png"));
        Image GetLogo = CompLogo.getImage();
        Image LogoPost = GetLogo.getScaledInstance(BTSLogo.getWidth(), BTSLogo.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon ScaledLogo = new ImageIcon(LogoPost);
        BTSLogo.setIcon(ScaledLogo);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Splash = new javax.swing.JPanel();
        BTSLogo = new javax.swing.JLabel();
        Loading = new javax.swing.JProgressBar();
        MainText = new javax.swing.JLabel();
        ShadowText = new javax.swing.JLabel();
        BTSTitleMainText = new javax.swing.JLabel();
        BTSShadowText = new javax.swing.JLabel();
        Status = new javax.swing.JLabel();
        BTSShadowText1 = new javax.swing.JLabel();
        ShadowText1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        Splash.setBackground(new java.awt.Color(0, 0, 51));
        Splash.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Splash.setMinimumSize(new java.awt.Dimension(490, 290));
        Splash.setName(""); // NOI18N
        Splash.setPreferredSize(new java.awt.Dimension(500, 300));
        Splash.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        Splash.add(BTSLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 250, 240));

        Loading.setBackground(new java.awt.Color(0, 0, 32));
        Loading.setForeground(new java.awt.Color(0, 0, 32));
        Loading.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Splash.add(Loading, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 480, 10));

        MainText.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        MainText.setForeground(new java.awt.Color(255, 255, 255));
        MainText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MainText.setText("STACK IMPLEMENTATION");
        Splash.add(MainText, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210, 270, 30));

        ShadowText.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        ShadowText.setForeground(new java.awt.Color(0, 0, 0));
        ShadowText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ShadowText.setText("POLICE DATABASE");
        Splash.add(ShadowText, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210, 270, 40));

        BTSTitleMainText.setFont(new java.awt.Font("Iron Shark", 0, 14)); // NOI18N
        try{
            IronShark = Font.createFont(Font.TRUETYPE_FONT, new File("src\\res\\Iron-Shark.ttf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,new File("src\\res\\Iron-Shark.ttf")));

        }catch(IOException | FontFormatException e){

        }
        BTSTitleMainText.setForeground(new java.awt.Color(255, 208, 0));
        BTSTitleMainText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BTSTitleMainText.setText("Brion Tactical Systems");
        Splash.add(BTSTitleMainText, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, 290, 30));

        BTSShadowText.setFont(new java.awt.Font("Iron Shark", 0, 14)); // NOI18N
        try{
            IronShark = Font.createFont(Font.TRUETYPE_FONT, new File("src\\res\\Iron-Shark.ttf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,new File("src\\res\\Iron-Shark.ttf")));

        }catch(IOException | FontFormatException e){

        }
        BTSShadowText.setForeground(new java.awt.Color(0, 0, 0));
        BTSShadowText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BTSShadowText.setText("Brion Tactical Systems");
        Splash.add(BTSShadowText, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, 290, 40));

        Status.setFont(new java.awt.Font("Lucida Console", 0, 14)); // NOI18N
        Status.setForeground(new java.awt.Color(255, 255, 255));
        Status.setToolTipText("");
        Splash.add(Status, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 260, 240, 30));

        BTSShadowText1.setFont(new java.awt.Font("Iron Shark", 0, 14)); // NOI18N
        BTSShadowText1.setForeground(new java.awt.Color(0, 0, 0));
        BTSShadowText1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BTSShadowText1.setText("Brion Tactical Systems");
        Splash.add(BTSShadowText1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, 290, 40));

        ShadowText1.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        ShadowText1.setForeground(new java.awt.Color(0, 0, 0));
        ShadowText1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ShadowText1.setText("STACK IMPLEMENTATION");
        Splash.add(ShadowText1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210, 270, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Splash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Splash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel BTSLogo;
    public javax.swing.JLabel BTSShadowText;
    public javax.swing.JLabel BTSShadowText1;
    public javax.swing.JLabel BTSTitleMainText;
    public javax.swing.JProgressBar Loading;
    public javax.swing.JLabel MainText;
    public javax.swing.JLabel ShadowText;
    public javax.swing.JLabel ShadowText1;
    private javax.swing.JPanel Splash;
    public javax.swing.JLabel Status;
    // End of variables declaration//GEN-END:variables
}
