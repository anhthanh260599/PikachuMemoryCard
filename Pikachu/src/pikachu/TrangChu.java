/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pikachu;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import static pikachu.Pikachu.PLAYER1;
import static pikachu.Pikachu.PLAYER2;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.Border;

/**
 *
 * @author ADMIN
 */



public class TrangChu extends JPanel implements ActionListener{
    Image backgroundTrangchu;
    JButton startButton;
    JButton xemHuongDanButton;
    JButton thoatButton;
    private Clip backgroundMusic;
    private Clip buttonClickSound;
    private Player player1;
    private Player player2;
    
    
    public TrangChu() 
    {
        
        // tạo background
        try {
            backgroundTrangchu = ImageIO.read(new File("./src/images/trangchu.png"));
        } catch (IOException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }    
        
        // âm thanh click button
        try {
            File buttonClickSoundFile = new File("./src/music/clickbutton.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(buttonClickSoundFile);
            buttonClickSound = AudioSystem.getClip();
            buttonClickSound.open(audioStream);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
        
        setLayout(null); // Bỏ layout của JPanel để tự điều chỉnh vị trí của các thành phần
//        setLayout(new FlowLayout(FlowLayout.LEFT, 100, 400)); // Sử dụng FlowLayout và căn giữa nút

        Color lighterRed = makeColorLighter(Color.RED, -70);

        startButton = new JButton("Bắt đầu chơi");
        startButton.addActionListener(this);   
        startButton.setBounds(100, 400, 150, 50);      
        startButton.setBackground(lighterRed);
        startButton.setForeground(Color.WHITE);
        add(startButton);
        
        xemHuongDanButton = new JButton("Xem hướng dẫn");
        xemHuongDanButton.addActionListener(this);
        xemHuongDanButton.setBounds(100, 460, 150, 50);
        xemHuongDanButton.setBackground(lighterRed);
        xemHuongDanButton.setForeground(Color.WHITE);
        
        add(xemHuongDanButton);

        thoatButton = new JButton("Thoát");
        thoatButton.addActionListener(this);
        thoatButton.setBounds(100, 520, 150, 50);
        thoatButton.setBackground(lighterRed);
        thoatButton.setForeground(Color.WHITE);
        add(thoatButton);
        playTrangChuMusic();
    }
    
    public void playTrangChuMusic() {
        try {
            File musicFile = new File("./src/music/trangchu.wav"); // Đường dẫn tới tệp nhạc
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);

            // Tạo Clip và mở tệp âm thanh
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioStream);

            // Lặp lại nhạc vô hạn
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);

            // Bắt đầu chơi nhạc
            backgroundMusic.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public void stopTrangChuMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }
    
    // Hàm giảm độ đậm của màu (đối với R, G, B)
    private Color makeColorLighter(Color color, int factor) {
        int r = Math.max(0, Math.min(255, color.getRed() - factor));
        int g = Math.max(0, Math.min(255, color.getGreen() - factor));
        int b = Math.max(0, Math.min(255, color.getBlue() - factor));
        return new Color(r, g, b);
    }
    
    @Override
    protected void paintComponent(Graphics g) { 
        super.paintComponent(g); 
        g.drawImage(backgroundTrangchu, 0, 0, Pikachu.width, Pikachu.height,null); // Tạo background
    
        Font f = new Font("Arial",Font.BOLD,50);
        g.setFont(f);         
//        String text = "Pikachu 2 người chơi";
        FontMetrics fontMetrics = g.getFontMetrics();
//        int textWidth = fontMetrics.stringWidth(text);
        int textHeight = fontMetrics.getHeight();

        // Kích thước của JPanel
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Tính toán vị trí để căn giữa văn bản
//        int x = (panelWidth - textWidth) / 2;
//        int y = (panelHeight - textHeight) / 2 + fontMetrics.getAscent(); // thêm fontMetrics.getAscent() để căn giữa theo chiều dọc

        // Vẽ background và văn bản
        g.drawImage(backgroundTrangchu, 0, 0, Pikachu.width, Pikachu.height, null);
        g.setColor(Color.WHITE);
//        g.drawString(text, x, y);
    }
    

     @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() ==  startButton)
        {
            buttonClickSound.start();
            
            // Ẩn màn hình trang chủ
            setVisible(false);
            // Thêm màn hình chơi game vào JFrame
            Pikachu game = (Pikachu) SwingUtilities.getWindowAncestor(this);
            game.add(new Settings());
            stopTrangChuMusic(); // Dừng nhạc khi chuyển sang màn hình chơi game
            while(PLAYER1.equals("")) // vòng lặp bắt buộc nhập tên
            {
                PLAYER1 = JOptionPane.showInputDialog(null,"Nhập tên người chơi 1","Nhập tên",JOptionPane.PLAIN_MESSAGE); // Popup đặt tên
                if(PLAYER1 == null)
                {
                    PLAYER1 = "";
                }
            }
            while(PLAYER2.equals("")) // vòng lặp bắt buộc nhập tên
            {
                PLAYER2 = JOptionPane.showInputDialog(null,"Nhập tên người chơi 2","Nhập tên",JOptionPane.PLAIN_MESSAGE); // Popup đặt tên
                if(PLAYER2 == null)
                {
                    PLAYER2 = "";
                }
            }
            // Âm thanh trộn bài khi bắt đầu màn
                try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("./src/music/troncard.wav"));
                Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
        }
        else if (e.getSource() == xemHuongDanButton) {
            buttonClickSound.start();
            setVisible(false);
            Pikachu game = (Pikachu) SwingUtilities.getWindowAncestor(this);
            game.add(new XemHuongDan());
            stopTrangChuMusic(); // Dừng nhạc khi chuyển sang màn hình chơi game
        } 
        else if (e.getSource() == thoatButton) {
            buttonClickSound.start();
            // Thoát khỏi trò chơi bằng phương thức System.exit(0);
            int dialogResult = JOptionPane.showConfirmDialog(this, "Bạn có muốn thoát không?", "Xác nhận thoát", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                // If "Yes" is clicked, exit the application
                System.exit(0);
            }
        }
    }
}
