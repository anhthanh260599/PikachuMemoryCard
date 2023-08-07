/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pikachu;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author ADMIN
 */
public class Card {
    
    private int x;
    private int y;
    private int width;
    private int height;
    private String name;
    private int index;
    private Image pic;
    private boolean matched; // Trạng thái thẻ (đã cộng điểm hoặc chưa)

    
    public Card(String name, int i)
    {
        this.width = 150;
        this.height = 200;
        this.name = name;
        this.index = i;
        setPicture("unknown"); // thẻ bị úp khi khởi tạo
        // set vị trí của các thẻ, (tăng giá trị thì khoảng cách giữa các thẻ tăng)
        setX(50 + i*175); 
        // 1 hàng chỉ lấy 8 thẻ, nếu mà hơn 8 thẻ thì sẽ xuống dòng
        if(i >= 8 && i < 16) 
        {
            setX(50 + (i - 8) * 175);
            setY(250);
        }

        if(i >= 16 && i < 24) 
        {
            setX(50 + (i - 16) * 175);
            setY(500);
        }
        
        matched = false; // Khởi tạo thẻ chưa cộng điểm

    }
    
    // Thuật toán xoay thẻ
    int direction=1; // hướng xoay thẻ
    public boolean flip() 
    {
        if(matched)
        {
            return false; // Nếu thẻ đã cộng điểm, không cho phép xoay
        }
        
        width-=30 * direction; // chiều rộng giảm khi xoay
        x+=15 * direction; 
        if(width <= 0) // nếu chiều rộng <= 0, nghĩa là đã xoay xong hoàn toàn
            direction*=-1; // thì đổi chiều hướng xoay
        if(direction == -1 && width == 150) //nếu hướng xoay (direction) là -1 
        {                                    //và chiều rộng (width) của thẻ là 150,
                                            // => trạng thái ban đầu,   
            direction = 1; //thì hướng xoay (direction) sẽ được đặt lại là 1
            return false;
        }          
        return true;
    }
    // End thuật toán xoay thẻ
    
    
    public boolean collision(int x_cursor, int y_cursor) //Xác định vị trí của con trỏ
    {
        Rectangle carRect = new Rectangle(x,y,width,height);
        
        return carRect.contains(x_cursor,y_cursor);
    }
    
    public void setPicture(String name)
    {
        try {
            pic = ImageIO.read(new File("./src/images/pokemon/" + name + ".jpg"));
        } catch (IOException ex) {
            Logger.getLogger(Card.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Phương thức để kiểm tra xem thẻ đã cộng điểm hay chưa
    public boolean isMatched() {
        return matched;
    }

    // Phương thức để đánh dấu thẻ đã cộng điểm
    public void setMatched(boolean matched) {
        this.matched = matched;
    }
    // ...
    
    // lấy hình ảnh
    public Image getPicture() {
        return pic;
    }
    
      /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }
}
