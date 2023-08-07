/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pikachu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;


/**
 *
 * @author ADMIN
 */
public class Pikachu extends JFrame implements KeyListener{

    static int width = 1500; // chiều rộng của ứng dụng
    static int height = 800; // chiều dài của ứng dụng
    static String PLAYER1 = "", PLAYER2 = "";
    boolean isPaused = false; // Trạng thái tạm dừng
    
    static Player player1 = null;
    static Player player2 = null;
    
    private TrangChu trangChu;
    
    public Pikachu() {
        setTitle("Pikachu hai người chơi"); //  đặt tên app
        ImageIcon logo = new ImageIcon("./src/images/pikachu-logo.png");
        setIconImage(logo.getImage());
        setSize(width,height);         // set chiều dài, chiều rộng của app
        setLocationRelativeTo(null);         
        setResizable(false);   // Không được phép chỉnh chiều dài, chiều rộng của app. VD: Kéo hoặc mở rộng/thu nhỏ.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        trangChu = new TrangChu();
        add(trangChu);
//        add(new Settings());
        addKeyListener(this); // Thêm sự kiện KeyListener   
        
       
    }
    
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Pikachu game = new Pikachu();
        game.setVisible(true);
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) { // Kiểm tra nút "SPACE" được nhấn
            isPaused = true;
            JOptionPane.showMessageDialog(null, "Ứng dụng đã tạm dừng!");           
        }
    }
    


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
