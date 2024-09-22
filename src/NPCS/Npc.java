package NPCS;

import javax.swing.*;
import java.awt.*;

public class Npc {
        public int x;
        public int y;

        private int w;
        private int h;

        private Image IDOL;
        private String[] Dialog = {"Hey in your portal you'll find aliens ", "There is a blackhole waiting for you \n you need a sword"};

        public Npc() {
            loadNPC();
        }

        private void loadNPC() {
            ImageIcon ii1 = new ImageIcon("src/King.png");

            IDOL = ii1.getImage();

            w = IDOL.getWidth(null);
            h = IDOL.getHeight(null);


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

        public int getWidth() {

            return w;
        }

        public int getHeight() {

            return h;
        }
        public String loadDialogue(int p) {
            return Dialog[p];
        }
        public Image getImage() {
            return  IDOL;

        }
}




