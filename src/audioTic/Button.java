package audioTic;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JButton;
  
public class Button extends JButton {
  private String name;
  public Button(String str){
    super(str);
    this.name = str;
    this.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    this.setMargin(new Insets(10,10,10,10));
    this.setFocusable(false);

  }
        
  public void paintComponent(Graphics g){
    g.fillRect(0, 0, this.getWidth(), this.getHeight());
    g.setColor(Color.white);
    //g.setBackground(Color.black);
    g.drawString(this.name, this.getWidth() / 2 - (this.getWidth()/ 2 /4), (this.getHeight() / 2) + 5);
  }        
}