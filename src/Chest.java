import javax.swing.*;
import java.awt.*;

public class Chest {
    private int x, y,w,h;
    private boolean open;
    private int id;
    private Image[] image=new Image[2];
    private boolean Empty;
    public Item item;

    Chest(int x,int y,int map){
        this.x=x;
        this.y=y;

        ImageIcon i1=new ImageIcon("src/Chest1.png");
        ImageIcon i2=new ImageIcon("src/Chest2.png");
        image[0]=i1.getImage();
        image[1]=i2.getImage();
        w=image[0].getWidth(null);
        h=image[0].getHeight(null);
        open=false;
        Empty=false;
        if(map==1){

            item=new Item(40,0,3,0,false,true,this.getX(),this.getY());

        }else if(map==10){

            item=new Item(1,10,0,0,false,true,this.getX(),this.getY());

        }


    }
    public void loadChest(){

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
    public Item getItem(){

        return item;
    }
    public Image getImage(){
        return image[open?1:0];
    }
    public void BounceItem(){
                item.bounceItem();
    }
    public void equipItems(Item item){
                this.item=item;
    }
    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void drawChest(Graphics2D g2) {

    }

    public void removeItem() {
        item=null;
    }
}
