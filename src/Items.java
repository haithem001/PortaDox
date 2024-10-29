import java.awt.Image;

import javax.swing.JComponent;

public class Items extends JComponent{
		private int x=125;
		private int y=135;
		private int gapX=80;
		private int gapY=80;
		private int w;
		private int h;
		public int rows=5;
		public int columns=7;
		private Item[][] list_items ={


				{
					new Item(1,10,0,0,false,true ,125		 ,y),
					new Item(0,0,0,0,false,true,205       ,y),
					new Item(40,0,4,0,false,true,205+gapX  ,y),
					new Item(41,0,3,0,false,true ,205+2*gapX,y),
					new Item(42,0,2,0,false,true ,205+3*gapX,y),
					new Item(43,0,1,0,false,true ,205+4*gapX,y),
					new Item(2,0,0,0,false,true ,205+5*gapX,y),
				}

				,{

				new Item(0,0,0,0,false,false,x     ,y+gapX),
				new Item(0,0,0,0,false,false,205       ,y+gapX ),
				new Item(0,0,0,0,false,false,205+gapX  ,y+gapX),
				new Item(0,0,0,0,false,false,205+2*gapX,y+gapX),
				new Item(0,0,0,0,false,false,205+3*gapX,y+gapX),
				new Item(0,0,0,0,false,false,205+4*gapX,y+gapX),
				new Item(0,0,0,0,false,false ,205+5*gapX,y+gapX),

				}
				,{
					new Item(0,0,0,0,false,false,x            ,y+2*gapX),
					new Item(0,0,0,0,false,false,205       ,y+2*gapX),
					new Item(0,0,0,0,false,false,205+gapX  ,y+2*gapX),
					new Item(0,0,0,0,false,false,205+2*gapX,y+2*gapX),
					new Item(0,0,0,0,false,false,205+3*gapX,y+2*gapX),
					new Item(0,0,0,0,false,false,205+4*gapX,y+2*gapX),
					new Item(0,0,0,0,false,false,205+5*gapX,y+2*gapX)
				}
				,{
					new Item(0,0,0,0,false,false,x		    	,y+3*gapX),
					new Item(0,0,0,0,false,false,205       ,y+3*gapX),
					new Item(0,0,0,0,false,false,205+gapX  ,y+3*gapX),
					new Item(0,0,0,0,false,false,205+2*gapX,y+3*gapX),
					new Item(0,0,0,0,false,false,205+3*gapX,y+3*gapX),
					new Item(0,0,0,0,false,false,205+4*gapX,y+3*gapX),
					new Item(0,0,0,0,false,false,205+5*gapX,y+3*gapX)
				},
				{
						new Item(0,0,0,0,false,false,x		     ,y+4*gapX),
						new Item(0,0,0,0,false,false,205       ,y+4*gapX),
						new Item(0,0,0,0,false,false,205+gapX  ,y+4*gapX),
						new Item(0,0,0,0,false,false ,205+2*gapX,y+4*gapX),
						new Item(0,0,0,0,false,false ,205+3*gapX,y+4*gapX),
						new Item(0,0,0,0,false,false ,205+4*gapX,y+4*gapX),
						new Item(0,0,0,0,false,false ,205+5*gapX,y+4*gapX),
				}



		};

	Items(){




	}
	public int getId(int i,int j){

		return list_items[i][j].getId();
	}
	public int[] findClosestItem( int targetX, int targetY) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				Item currentItem = list_items[i][j];
				int itemX = currentItem.getX();
				int itemY = currentItem.getY();
				int itemWidth = 76;
				int itemHeight = 76;

				if (targetX > itemX && targetX < itemX + itemWidth
						&&targetY > itemY && targetY < itemY + itemHeight) {

					return new int[]{itemX, itemY,i,j};
					}

			}
		}
		return null;
	}
	public void showItems() {
		for(int i=0;i<rows;i++){
			for(int j=0;j<columns;j++){
				if(list_items[i][j].getId()!=0) {
					System.out.print("X");

				}
				else {
					System.out.print("O");
				}
			}
			System.out.println(" ");
		}
	}
	public void remove_item(int i,int j){
		list_items[i][j].setExist(false);
		list_items[i][j].setId(0);
		list_items[i][j].setHeal(0);
		list_items[i][j].setShield(0);
		list_items[i][j].setFunctional(false);
		list_items[i][j].setDamage(0);

	}
		public int GetCenterItemX(int i,int j) {
			Item item =list_items[i][j];
            return item.getX() + item.getW() / 2;
		}
		public int GetCenterItemY(int i,int j){
			Item item =list_items[i][j]
					;
            return item.getY() + item.getH() / 2;
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
			return list_items[i][j].getImage(1);
		}
		public Item[][] getItemsList() {
			return list_items;
		}
		public Item getItem(int i,int j) {
			return list_items[i][j];
		}
		public void setItem(Item x,int i,int j) {
			list_items[i][j]=x;
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
		public int getItemI(Item item) {
			for (int i =0;i<rows;i++) {
				for (int j=0;j<columns;j++) {
					if (list_items[i][j].equals(item)) {
						return i;
					}
				}
			}
			return -1;

		}
		public void change_item(int i,int j,int k,int l) {

			Item item=list_items[i][j];
			list_items[i][j]=list_items[k][l];
			list_items[k][l]=item;




		}
		public int getItemJ(Item item) {
			for (int i =0;i<rows;i++) {
				for (int j=0;j<columns;j++) {
					if (list_items[i][j].equals(item)) {
						return j;
					}
				}
			}
			return -1;

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

	public void  addItem(Item item) {
		int k =0;
		int l=0;
		boolean stop=false;


		for(int i = 0 ;i<rows-1;i++) {
			for (int j = 0; j < columns; j++) {
				if (list_items[i][j].getId() == 0) {
					k=i;
					l=j;

					break;

				}

			}
		}
		list_items[k][l].setId(item.getId());
		list_items[k][l].setHeal(item.getHeal());
		list_items[k][l].setShield(item.getShield());
		list_items[k][l].setFunctional(item.isFunctional());
		list_items[k][l].setDamage(item.getDamage());
		list_items[k][l].setExist(true);

	}
}

