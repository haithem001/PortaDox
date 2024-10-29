import javax.swing.*;
import java.awt.*;

public class Npc {
        private String name;
        public int x;
        public int y;
        private boolean giveItem=false;
        private boolean stopGiving=false;
        private int w;
        private int h;
        private Image Market;
        private Image IDOL;
        private Item item;

        private String[][] Dialog = {
                {"Hey in your portal you'll find aliens ", "There is a blackhole waiting for you \n you need a sword"}
        };
    private boolean isAllowing=false;

    public Npc(int x , int y ,String name,int map) {
            this.name=name;
            ImageIcon ii1 = new ImageIcon("src/Idols/"+map+".png");

            ImageIcon selloMarket=new ImageIcon("src/Maps/SelloMarket.png");

            Market=selloMarket.getImage();

            IDOL = ii1.getImage();

            w = IDOL.getWidth(null);
            h = IDOL.getHeight(null);
            this.x = x;
            this.y = y;

        }
        public String getName() {return name;}
        private void loadNPC() {

        }
        public void setX(int x) {

            this.x=x;

        }

        public void setY(int y) {

            this.y=y;
        }
        public int getX() {

            return this.x;
        }

        public int getY() {

            return this.y;
        }
        public void setAllowing(boolean b) {
        this.isAllowing=b;
    }
        public boolean getAllowing() {return this.isAllowing;}
        public int getWidth() {

            return w;
        }

        public int getHeight() {

            return h;
        }
        public String loadDialogue(int p,int selector) {
            return Dialog[selector][p];
        }
        public Image getImage() {
            return  IDOL;

        }
        public Image getMarketImage() {
            return  Market;

        }
    public boolean isGiveItem(){
        if (giveItem && stopGiving==false){
            giveItem=false;
            stopGiving=true;
            item=	new Item(60,0,0,0,false,true,x   ,y);
        }
        return this.giveItem;
    }
    public void setGiveItem(boolean b){
        this.giveItem=b;
    }
    public Item getItem(){
        return item;
    }
    public void BounceItem(){
        if (item!=null && giveItem){
            item.bounceItem(-15);

        }
    }
    public Image getItemImage(){
        return item.getImage(1);
    }
    public void removeItem() {
        this.item=null;
    }
}




