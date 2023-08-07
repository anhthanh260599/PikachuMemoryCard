/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pikachu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;


/**
 *
 * @author ADMIN
 */
public class Settings extends JPanel implements ActionListener, MouseListener, KeyListener {
    
    Image background;
    Timer timer;
    boolean flip = false; // Các thẻ khi tạo sẽ úp xuống
    boolean check = false;
    int[] selected = new int[2];
    int count = 0, index;
    int turn = 0;
    private int initialCardSize = 8;
    private Clip backgroundMusic;
    int score1 = 0;
    int score2 = 0;
    private boolean isFlipping = false; // Biến để theo dõi trạng thái lật thẻ

    
    Card[] card;
    int cardSize = initialCardSize; // số lượng thẻ
    String[] pokemons = {"blastoise","blastoise","chazizard","chazizard","clefable","clefable","natu","natu","nidoqueen","nidoqueen","rapidash","rapidash",
                        "bulbasaur","bulbasaur","chikorita","chikorita","golduck","golduck","nidoking","nidoking","pikachu","pikachu","vaporeon","vaporeon",
                        
                            };
    
    String[] firstEight;
    String[] secondEight;
    String[] thirdEight;
    String[] round2;
    
    public Settings(){
        card = new Card[cardSize];
        
        
        firstEight = Arrays.copyOfRange(pokemons, 0, 8);
//        secondEight = Arrays.copyOfRange(pokemons, 8, 16);
//        thirdEight = Arrays.copyOfRange(pokemons, 16, 24);
        
        round2 = Arrays.copyOfRange(pokemons, 8, 24);
        
        List<String> firstEightList = Arrays.asList(firstEight);
//        List<String> secondEightList = Arrays.asList(secondEight);
//        List<String> thirdEightList = Arrays.asList(thirdEight);       
        List<String> Round2 = Arrays.asList(round2);
        List<String> Round3 = Arrays.asList(pokemons);

        Collections.shuffle(firstEightList);  
//        Collections.shuffle(secondEightList);
//        Collections.shuffle(thirdEightList);       
        Collections.shuffle(Round2);
        Collections.shuffle(Round3);

        System.out.println(firstEightList); // In ra list thẻ round 1
        System.out.println(Round2); // In ra list thẻ round 2
        System.out.println(Round3); // In ra list thẻ round 3


        
//        List<String> shuffledList = Arrays.asList(pokemons); 
//        Collections.shuffle(shuffledList); // trộn các thẻ lại với nhau
        
        turn = (int)(Math.random() * 10) % 2;

        for(int i = 0; i < cardSize;i++)
        {
            if(i<8)
            {
                card[i] = new Card(firstEight[i],i);
            }
            else if (i < 16)
            {
                card[i] = new Card(secondEight[i - 8],i);
            }
            else if (i < 24)
            {
                card[i] = new Card(thirdEight[i - 16],i);
            }
            else
            {
                card[i] = new Card("unknown", i);
            }
            
        } 
        

        addMouseListener(this);
        

        timer = new Timer(50, this); // tốc độ xoay của thẻ
        timer.start();
        playGameMusic();

        
        try {
            background = ImageIO.read(new File("./src/images/background.png"));
        } catch (IOException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
    
    // Tạo background, thẻ
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
   
        g.drawImage(background, 0, 0, Pikachu.width, Pikachu.height,null); // Tạo background

        // Vòng lặp, tạo danh sách thẻ
        for (int i = 0; i < cardSize; i++) {
            g.drawImage(card[i].getPicture(),card[i].getX(), card[i].getY(), card[i].getWidth(), card[i].getHeight(),null); // tạo 1 thẻ
        }
        
        // Hiển thị tên người chơi
        Font f = new Font("Arial",Font.BOLD,35);
        g.setFont(f);
             
        g.drawString(Pikachu.PLAYER1, 200, 750); // set vị trí đặt tên
        g.drawString(score1 + "", 150, 750); // set vị trí đặt điểm
        
        g.drawString(Pikachu.PLAYER2, 900, 750);
        g.drawString(""+score2, 850, 750); // set vị trí đặt điểm
        
        
        if (turn == 0) {
            g.setColor(Color.RED); // Đặt màu chữ thành đỏ
            g.drawString(Pikachu.PLAYER1, 200, 750); // set vị trí đặt tên
            g.drawString(score1 + "", 150, 750); // set vị trí đặt điểm
            g.setColor(Color.WHITE); // Đặt màu chữ của người chơi 2 thành trắng
            g.drawString(Pikachu.PLAYER2, 900, 750);
            g.drawString("" + score2, 850, 750); // set vị trí đặt điểm
        } 
        else 
        { // Đặt màu chữ cho người chơi 2
             g.setColor(Color.WHITE); // Đặt màu chữ của người chơi 1 thành trắng
             g.drawString(Pikachu.PLAYER1, 200, 750); // set vị trí đặt tên
             g.drawString(score1 + "", 150, 750); // set vị trí đặt điểm
             g.setColor(Color.RED); // Đặt màu chữ thành trắng
             g.drawString(Pikachu.PLAYER2, 900, 750);
             g.drawString("" + score2, 850, 750); // set vị trí đặt điểm
         }
        
        
         //Lượt chơi
        if(turn ==0)
        {
            g.setColor(Color.RED); // Đặt màu chữ của lượt chơi thành đỏ cho người chơi 1
            g.drawString("•>", 100, 750);          
        }
        else
        {
            g.setColor(Color.RED); // Đặt màu chữ của lượt chơi thành đỏ cho người chơi 1
            g.drawString("•>", 800, 750);
        }  
        // End hiển thị tên người chơi
    }

    int direction =1;
    @Override
    public void actionPerformed(ActionEvent e) {
        AudioInputStream audioInputStream;
        Clip clip;   
        if(flip) {
           flip = card[index].flip();
           if (card[index].getWidth() <= 0) {
                direction *= -1;
            }

            if (direction == -1 && card[index].getWidth() == 150) {
                direction = 1;
                isFlipping = false; // Đánh dấu hoàn thành lật thẻ đầu tiên
                return; // Không cho phép lật thẻ thứ hai ngay lúc này
            }
            
           if(card[index].getWidth() <= 0) // Khi lật thẻ thì lấy ảnh và tên của thẻ đó, hiển thị lên màn hình
           {
               try {
                    audioInputStream = AudioSystem.getAudioInputStream(new File("./src/music/clickcard.wav"));
                    clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
               card[index].setPicture(card[index].getName());                
           }
        }
        if (check) // Kiểm tra khi chọn 2 thẻ
        {
            if(card[selected[0]].getName() != card[selected[1]].getName()) // Nếu 2 thẻ khác nhau
            {
                
                try {
                    Thread.sleep(500); // Thời gian đợi để úp 2 thẻ
                } catch (InterruptedException ex) {
                    Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
                }
                card[selected[0]].setPicture("unknown"); // úp bài lại
                card[selected[1]].setPicture("unknown");
                if(turn == 0) // đổi lượt chơi
                {
                    turn = 1;
                }
                else 
                {
                    turn = 0;
                }
            }
            else 
            {
                try {
                    audioInputStream = AudioSystem.getAudioInputStream(new File("./src/music/matching.wav"));
                    clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
       

                if(turn == 0) // cộng điểm
                {
                    score1+= 2;
                   card[selected[0]].setMatched(true); // Đánh dấu thẻ đã cộng điểm
                   card[selected[1]].setMatched(true);
                }
                else 
                {
                    score2+= 2;    
                    card[selected[0]].setMatched(true); // Đánh dấu thẻ đã cộng điểm
                    card[selected[1]].setMatched(true);
                }
            }
            check = false;
            
            if (score1 + score2 == 8 && cardSize == 8) 
            {   
                repaint();
                JOptionPane.showMessageDialog(
                    null,
                    (score1 > score2)
                        ? "Người chơi " + Pikachu.PLAYER1 + " thắng màn này!"
                        : (score1 < score2)
                            ? "Người chơi " + Pikachu.PLAYER2 + " thắng màn này!"
                            : "Hoà! Cả 2 bằng điểm"
                );
                JOptionPane.showMessageDialog(null, "Chúc mừng! Bạn đã hoàn thành màn 1."); 
                // Âm thanh trộn bài khi bắt đầu màn
                try {
                    audioInputStream = AudioSystem.getAudioInputStream(new File("./src/music/troncard.wav"));
                    clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                 // Chuyển sang màn 2
                cardSize = 16;
                card = new Card[cardSize];
                for (int i = 0  ; i < cardSize; i++) {
                    card[i] = new Card(round2[i], i);
//                    if (i < 8) {
//                        card[i] = new Card(secondEight[i], i);
//                    } 
//                    else 
//                    {
//                        card[i] = new Card(thirdEight[i - 8], i);
//                    }
                }
                turn = (int) (Math.random() * 10) % 2;   
                score1 = 0;
                score2 = 0;
            }
            else if (score1 + score2 == 16 && cardSize == 16)
            {
                
                repaint();
                JOptionPane.showMessageDialog(
                    null,
                    (score1 > score2)
                        ? "Người chơi " + Pikachu.PLAYER1 + " thắng màn này!"
                        : (score1 < score2)
                            ? "Người chơi " + Pikachu.PLAYER2 + " thắng màn này!"
                            : "Hoà! Cả 2 bằng điểm"
                );
                JOptionPane.showMessageDialog(null, "Chúc mừng! Bạn đã hoàn thành màn 2.");
                
                 // Âm thanh trộn bài khi bắt đầu màn
                try {
                    audioInputStream = AudioSystem.getAudioInputStream(new File("./src/music/troncard.wav"));
                    clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                // Chuyển sang màn 3
                cardSize = 24;
                card = new Card[cardSize];                              
                for (int i = 0; i < cardSize; i++) {
                    card[i] = new Card(pokemons[i], i);         
                }
                turn = (int) (Math.random() * 10) % 2;
                score1 = 0;
                score2 = 0;
            }
            else if (score1 + score2 == 24 && cardSize == 24) 
            { 
                repaint();
                JOptionPane.showMessageDialog(
                    null,
                    (score1 > score2)
                        ? "Người chơi " + Pikachu.PLAYER1 + " thắng màn này!"
                        : (score1 < score2)
                            ? "Người chơi " + Pikachu.PLAYER2 + " thắng màn này!"
                            : "Hoà! Cả 2 bằng điểm"
                );
                JOptionPane.showMessageDialog(null, "Chúc mừng! Bạn đã hoàn thành màn 3.");
                timer.stop();
                stopGameMusic();
                returnToTrangChu();
            }
        }
        repaint();
        if(count == 2 && flip == false) // Nếu 2 thẻ giống nhau
        {
            check = true;
            count = 0;
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    //////// Khi chọn 1 thẻ, thì get vị trí của thẻ đó, đồng thời xoay mặt của thẻ
    @Override
    public void mousePressed(MouseEvent e) {
        
        if (isFlipping) {
            return; // Không cho phép lật thẻ thứ hai khi đang lật thẻ đầu tiên
        }        
        flip = true;
        direction = 1;        
        for (Card card1:card)
        {
            if( card1 != null && card1.collision(e.getX(),e.getY()) && !card1.isMatched() )
            {
                selected[count] = card1.getIndex(); // khi mở thẻ chỉ mở tối đa 2
                
                if (count == 0 || (count == 1 && selected[0] != selected[1]))
                {
                    index = card1.getIndex();
                    count++;
                }              
            }
        }       
        if (count == 1) {
            isFlipping = true; // Đánh dấu bắt đầu lật thẻ đầu tiên
        }
    }   

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }   
    
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    
     public void returnToTrangChu() {
        // Xóa các panel cũ trên JFrame
        Pikachu game = (Pikachu) SwingUtilities.getWindowAncestor(this);
        game.getContentPane().removeAll();

        // Hiển thị lại màn hình trang chủ
        TrangChu trangChu = new TrangChu();
        game.add(trangChu);
        trangChu.setVisible(true);

        // Cập nhật lại JFrame
        game.revalidate();
        game.repaint();
    }
     
    public void playGameMusic() {
        try {
            File musicFile = new File("./src/music/nhaclucchoigame.wav"); // Đường dẫn tới tệp nhạc
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
    public void stopGameMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }

}
