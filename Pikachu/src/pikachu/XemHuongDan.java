/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pikachu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author ADMIN
 */
public class XemHuongDan extends JPanel implements ActionListener{
    Image backgroundXemHuongDan;
    JButton returnTrangChuButton;
    private Clip backgroundMusic;
    private Clip buttonClickSound;
    private Clip backgroundVoice;
    
    public XemHuongDan()
    {
        // tạo background
        try {
            backgroundXemHuongDan = ImageIO.read(new File("./src/images/xemhuongdan.png"));
        } catch (IOException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }    
        
        // tạo âm thanh khi click button
        try {
            File buttonClickSoundFile = new File("./src/music/clickbutton.wav");            
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(buttonClickSoundFile);
            buttonClickSound = AudioSystem.getClip();
            buttonClickSound.open(audioStream);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
        
        setLayout(null);
        
        Color lighterRed = makeColorLighter(Color.RED, -70);
        
        returnTrangChuButton = new JButton("Trờ về trang chủ");
        returnTrangChuButton.addActionListener(this);   
        returnTrangChuButton.setBounds(100, 670, 150, 50);      
        returnTrangChuButton.setBackground(lighterRed);
        returnTrangChuButton.setForeground(Color.WHITE);
        add(returnTrangChuButton);
        
        playXemHuongDanMusic();
        playXemHuongDanVoice();
    }
     
    private Color makeColorLighter(Color color, int factor) {
        int r = Math.max(0, Math.min(255, color.getRed() - factor));
        int g = Math.max(0, Math.min(255, color.getGreen() - factor));
        int b = Math.max(0, Math.min(255, color.getBlue() - factor));
        return new Color(r, g, b);
    }
    
     @Override
    protected void paintComponent(Graphics g) { 
        super.paintComponent(g); 
        g.drawImage(backgroundXemHuongDan, 0, 0, 1500, 760,null); // Tạo background
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returnTrangChuButton)
        {
            buttonClickSound.start();
            setVisible(false);
            Pikachu game = (Pikachu) SwingUtilities.getWindowAncestor(this);
            game.add(new TrangChu());
            stopXemHuongDanMusic();
            stopXemHuongDanVoice();
        }
    }
    
    public void playXemHuongDanVoice()
    {
        try {
            File voiceFile = new File("./src/music/voicehuongdan.wav"); // Đường dẫn tới tệp nhạc
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(voiceFile);

            // Tạo Clip và mở tệp âm thanh
            backgroundVoice = AudioSystem.getClip();
            backgroundVoice.open(audioStream);

            // Bắt đầu chơi nhạc
            backgroundVoice.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    public void playXemHuongDanMusic() {
        try {
            File musicFile = new File("./src/music/xemhuongdan.wav"); // Đường dẫn tới tệp nhạc
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);

            // Tạo Clip và mở tệp âm thanh
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioStream);
            
            // Lấy FloatControl để điều chỉnh âm lượng
            FloatControl volumeControl = (FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);
            
            // Đặt mức âm lượng (phạm vi: từ -80.0 đến 6.0206)
            float volume = -5.0f; // Điều chỉnh giá trị này để thay đổi âm lượng (ví dụ, -10.0f là nhạc yên tĩnh hơn 10 decibels)
            volumeControl.setValue(volume);

            // Lặp lại nhạc vô hạn
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
            
            // Bắt đầu chơi nhạc
            backgroundMusic.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public void stopXemHuongDanMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }
    
    public void stopXemHuongDanVoice()
    {
        if (backgroundVoice != null) {
            backgroundVoice.stop();
        }
    }
   
}
