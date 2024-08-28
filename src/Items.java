import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class Items extends JComponent{
		private int x=125;
		private int y=135;
		private int gapX=80;
		private int gapY=80;
		private int w;
		private int h;
		private int rows=4;
		private int columns=7;
		private Item[][] list_items ={
				
				
				{
					new Item(60,20,0,0,false,true,205,y),
					new Item(40,0,20,0,false,true,205+gapX,y),
					new Item(0,0,0,0,false,false,205+2*gapX,y),
					new Item(0,0,0,0,false,false,205+3*gapX,y),
					new Item(0,0,0,0,false,false,205+4*gapX,y),
					new Item(0,0,0,0,false,false,205+5*gapX,y),
					new Item(1,10,0,0,false,true,205+6*gapX,y),
				}

				,{

				new Item(60,20,0,0,false,true,205,y),
				new Item(40,0,20,0,false,true,205+gapX  ,y+gapX  ),
				new Item(0,0,0,0,false,false,205 +2*gapX,y+2*gapX),
				new Item(0,0,0,0,false,false,205 +3*gapX,y+3*gapX),
				new Item(0,0,0,0,false,false,205 +4*gapX,y+4*gapX),
				new Item(0,0,0,0,false,false,205 +5*gapX,y+5*gapX)

				}
				,{
					new Item(0,0,0,0,false,false,x,y),
					new Item(0,0,0,0,false,false,40,y),
					new Item(0,0,0,0,false,false,x+80,y),
					new Item(0,0,0,0,false,false,x+120,y),
					new Item(0,0,0,0,false,false,x+160,y),
					new Item(0,0,0,0,false,false,x+200,y),
					new Item(0,0,0,0,false,false,x+240,y)
				}
				,{
					new Item(0,0,0,0,false,false,x,y),
					new Item(0,0,0,0,false,false,40,y),
					new Item(0,0,0,0,false,false,x+80,y),
					new Item(0,0,0,0,false,false,x+120,y),
					new Item(0,0,0,0,false,false,x+160,y),
					new Item(0,0,0,0,false,false,x+200,y),
					new Item(0,0,0,0,false,false,x+240,y)
				}
				  
				 
				
		};

	public  Item findClosestItem( int targetX, int targetY) {
		Item closestItem = null;
		double minDistance = Double.MAX_VALUE;


		for (Item[] row : list_items) {
			for (Item item : row) {
				int itemWidth = item.getW();
				int itemLength = item.getH();
				int centerX = item.getX() + itemWidth / 2;
				int centerY = item.getY() + itemLength / 2;
				double distance = Math.sqrt(Math.pow(targetX - centerX, 2) + Math.pow(targetY - centerY, 2));
				if (distance < minDistance) {
					minDistance = distance;
					closestItem = item;
				}
			}
		}
		if (minDistance < 50) {
			return closestItem;

		}
		return null;
	}

		Items(){
			
		
			
			
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
		public Image getItemImage(int i,int j) {
			return list_items[i][j].getImage();
		}
		public Item[][] getItemsList() {
			return list_items;
		}
		public JComponent getItem(int i,int j) {
			return list_items[i][j];
		}
		public int getItemX(int i,int j) {
			return list_items[i][j].getX();
		}
		public int getItemY(int i,int j) {
			return list_items[i][j].getY();
		}
		public void setItemXY(int i,int j ,int a,int b) {
			list_items[i][j].setX(a);
			list_items[i][j].setY(b);
		}
		
		public int getY() {
			return this.y;
		}
		public int getW() {
			return this.w;
		}
		public int getH() {
			return this.h;
		}
		public int getrows() {
			return this.rows;
		}
		public int getcolumns() {
			return this.columns;
		}
		public int  showItemsExisted(){
			int sum=0;
			for (int i =0;i<rows;i++) {
				for (int k=0;k<columns;k++) {
					if (list_items[i][k].isExisted()==true) {
						sum+=1;
					}

				}
			
	
			
		}
		
			 return sum;
		
		
		}
		public void ItemExistence( int i, int j , boolean b){
			list_items[i][j].setExist(b);
		}
		
}

