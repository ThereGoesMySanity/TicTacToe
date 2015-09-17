package GameState;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Main.GamePanel;

public class BoardState extends GameState {
	//0 = blank but part of board, 1 and 2 = X and O, 3 and 4 = completed X and O row, 5 = Not board
	private ArrayList<ArrayList<Integer>> fullBoard = new ArrayList<ArrayList<Integer>>();
	private int turn = 1;
	private Point lastMove;
	private int winner = 0;
	public static String WIN;
	private int offsetx;
	private int offsety;
	private int squareSize;
	public BoardState(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}

	private void addNewBoard(int x, int y, ArrayList<ArrayList<Integer>> board){
		//Okay, I consider x to be the outside arraylist and I don't care if that's weird
		//Also, I'll probably mess up a lot either way.
		//Update 5/20/15: Yes I messed up a lot, oh well
		if(x<0){ //expanding up
			for(int i = 0; i < 3; i++){
				ArrayList<Integer> z = new ArrayList<Integer>();
				for(int j = 0; j < board.get(0).size(); j++){
					if(j < y+3 && j >= y && y >= 0){ //adjusts for y if it isn't 0 or negative, almost forgot about this 
						z.add(0);
					}
					else{
						z.add(5);
					}
				}
				board.add(0, z);
				x=0;
			}
		}
		else if(x > board.size()-1){ //expanding down, basically identical to the first one
			for(int i = 0; i < 3; i++){
				ArrayList<Integer> z = new ArrayList<Integer>();
				for(int j = 0; j < board.get(0).size(); j++){
					if((j < y+3 && j >= y && y >= 0)){ //adjusts for y if it isn't 0 or negative, almost forgot about this 
						z.add(0);
					}
					else{
						z.add(5);
					}
				}
				board.add(z);
			}
		}
		if(y<0){ //expanding to the left
			for(int i = 0; i < 3; i++){
				for(int j = 0; j < board.size(); j++){
					if(j < x + 3 && j >= x){
						board.get(j).add(0, 0); //add at position 0
					}
					else{
						board.get(j).add(0, 5); //add lack of board everywhere else
					}
				}
			}
		}
		else if(y > board.get(0).size()-1){ //expanding to the right, same as above again
			for(int i = 0; i < 3; i++){
				for(int j = 0; j < board.size(); j++){
					if(j < x + 3 && j >= x){
						board.get(j).add(0); //append as opposed to add
					}
					else{
						board.get(j).add(5); //append lack of board everywhere else
					}
				}
			}
		}
		if((x >= 0 && x < board.size())&&(y >= 0 && y < board.get(0).size())){ //If within board
			for(int i = 0; i < 3; i++){
				for(int j = 0; j < 3; j++){
					board.get(x + i).set(y + j, 0);
				}
			}
		}
	}

	private void printBoard(ArrayList<ArrayList<Integer>> board){
		for(int i = 0; i < board.size(); i++){
			System.out.println(board.get(i));
		}
	}

	public void init(){
		for(int i = 0; i < 3; i++){
			ArrayList<Integer> z = new ArrayList<Integer>();
			for(int j = 0; j < 3; j++){
				z.add(0);

			}
			fullBoard.add(z);
		}
	}
	private Point getCoords(int x, int y){
		return new Point(x*squareSize + offsetx, y*squareSize + offsety);
	}
	public void draw(Graphics2D g) {
		Point point1 = new Point();
		Point point2 = new Point();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.HEIGHT, GamePanel.WIDTH);
		g.setColor(Color.BLACK);
		for(int i = 0; i < fullBoard.size(); i++){
			for(int j = 0; j < fullBoard.get(0).size(); j++){
				if(getSquare(i,  j) != 5){
					if(i != fullBoard.size()-1){
						if(fullBoard.get(i+1).get(j) != 5){ // if the one below it is also a valid space
							point1 = getCoords(j, i+1);
							point2 = getCoords(j+1, i+1);
							g.drawLine(point1.x, point1.y, point2.x, point2.y);
						}
					}
					if(j != fullBoard.get(0).size()-1){
						if(fullBoard.get(i).get(j+1) != 5){ // if the one to the right is valid
							point1 = getCoords(j+1, i);
							point2 = getCoords(j+1, i+1);
							g.drawLine(point1.x, point1.y, point2.x, point2.y);
						}
					}
					if(getSquare(i,  j) == 1){
						point1 = getCoords(j, i);
						point2 = getCoords(j+1, i+1);
						g.drawLine(point1.x, point1.y, point2.x, point2.y);
						point1 = getCoords(j+1, i);
						point2 = getCoords(j, i+1);
						g.drawLine(point1.x, point1.y, point2.x, point2.y);
					}
					if(getSquare(i,  j) == 2){
						point1 = getCoords(j, i);
						point2 = getCoords(j+1, i+1);
						g.drawOval(point1.x+1, point1.y+1, squareSize-2, squareSize-2);
					}
					if(getSquare(i,  j) == 3){
						point1 = getCoords(j, i);
						point2 = getCoords(j+1, i+1);
						g.setColor(Color.RED);
						g.drawLine(point1.x, point1.y, point2.x, point2.y);
						point1 = getCoords(j+1, i);
						point2 = getCoords(j, i+1);
						g.drawLine(point1.x, point1.y, point2.x, point2.y);
						for(int k = -1; k < 2; k++){
							for(int l = -1; l < 2; l++){
								if(i+k>0 && j+l>0 && i+k<fullBoard.size() && j+l<fullBoard.get(0).size()){
									if(fullBoard.get(i+k).get(j+l) == 3){
										point1 = getCoords(j, i);
										point2 = getCoords(j+l, i+k);
										g.drawLine(point1.x + squareSize/2, point1.y + squareSize/2, point2.x + squareSize/2, point2.y + squareSize/2);
										g.drawLine(point1.x + squareSize/2 +1, point1.y + squareSize/2, point2.x + squareSize/2 +1, point2.y + squareSize/2);
										g.drawLine(point1.x + squareSize/2, point1.y + squareSize/2 +1, point2.x + squareSize/2, point2.y + squareSize/2 +1);
										g.drawLine(point1.x + squareSize/2 -1, point1.y + squareSize/2, point2.x + squareSize/2 -1, point2.y + squareSize/2);
										g.drawLine(point1.x + squareSize/2, point1.y + squareSize/2 -1, point2.x + squareSize/2, point2.y + squareSize/2 -1);
									}
								}
							}
						}
					}
					if(getSquare(i,  j) == 4){
						point1 = getCoords(j, i);
						point2 = getCoords(j+1, i+1);
						g.setColor(Color.BLUE);
						g.drawOval(point1.x+1, point1.y+1, squareSize, squareSize);
						for(int k = -1; k < 2; k++){
							for(int l = -1; l < 2; l++){
								if(i+k>0 && j+l>0 && i+k<fullBoard.size() && j+l<fullBoard.get(0).size()){
									if(fullBoard.get(i+k).get(j+l) == 4){
										point1 = getCoords(j, i);
										point2 = getCoords(j+l, i+k);
										g.drawLine(point1.x + squareSize/2, point1.y + squareSize/2, point2.x + squareSize/2, point2.y + squareSize/2);
										g.drawLine(point1.x + squareSize/2 +1, point1.y + squareSize/2, point2.x + squareSize/2 +1, point2.y + squareSize/2);
										g.drawLine(point1.x + squareSize/2, point1.y + squareSize/2 +1, point2.x + squareSize/2, point2.y + squareSize/2 +1);
										g.drawLine(point1.x + squareSize/2 -1, point1.y + squareSize/2, point2.x + squareSize/2 -1, point2.y + squareSize/2);
										g.drawLine(point1.x + squareSize/2, point1.y + squareSize/2 -1, point2.x + squareSize/2, point2.y + squareSize/2 -1);
									}
								}
							}
						}
					}
					g.setColor(Color.BLACK);
				}
			}
		}
	}

	public void keyPressed(int k) {
		if(k == KeyEvent.VK_R)gsm.setState(GameStateManager.BOARDSTATE);
	}
	
	public void keyReleased(int k) {}

	public void update() {
		if (fullBoard.size() > fullBoard.get(0).size()){
			squareSize = GamePanel.HEIGHT/fullBoard.size();
			offsetx = (GamePanel.WIDTH - squareSize*fullBoard.get(0).size())/2;
			offsety = 0;
		}
		else if (fullBoard.get(0).size() > fullBoard.size()){
			squareSize = GamePanel.WIDTH/fullBoard.get(0).size();
			offsety = (GamePanel.HEIGHT - squareSize*fullBoard.size())/2;
			offsetx = 0;
		}
		else{
			squareSize = GamePanel.WIDTH/fullBoard.size();
			offsetx = 0;
			offsety = 0;
		}
		boolean full = true;
		for(int i = 0; i < fullBoard.size(); i++){
			for(int j = 0; j < fullBoard.get(0).size(); j++){      //god this looks like such a mess, I know
				if(getSquare(i,  j) == 0)full = false;
				if(i < fullBoard.size()-2 
						&& j < fullBoard.get(0).size()-2){
					if(getSquare(i,  j)==fullBoard.get(i+1).get(j+1)
					 &&fullBoard.get(i+1).get(j+1)==fullBoard.get(i+2).get(j+2)
					&&(getSquare(i,  j)==1
					 ||getSquare(i,  j)==2)){
						fullBoard.get(i).set(j, getSquare(i,  j)+2);
						fullBoard.get(i+1).set(j+1, getSquare(i,  j));
						fullBoard.get(i+2).set(j+2, getSquare(i,  j));
						buildFromLastMove(false);
					}
				}
				if(i < fullBoard.size()-2){
					if(getSquare(i,  j)==fullBoard.get(i+1).get(j)
							&&fullBoard.get(i+1).get(j)==fullBoard.get(i+2).get(j)
							&&(getSquare(i,  j)==1||getSquare(i,  j)==2)){
						fullBoard.get(i).set(j, getSquare(i,  j)+2);
						fullBoard.get(i+1).set(j, getSquare(i,  j));
						fullBoard.get(i+2).set(j, getSquare(i,  j));
						buildFromLastMove(false);
					}
				}
				if(j < fullBoard.get(0).size()-2){
					if(getSquare(i,  j)==fullBoard.get(i).get(j+1)
							&&fullBoard.get(i).get(j+1)==fullBoard.get(i).get(j+2)
							&&(getSquare(i,  j)==1||getSquare(i,  j)==2)){
						fullBoard.get(i).set(j, getSquare(i,  j)+2);
						fullBoard.get(i).set(j+1, getSquare(i,  j));
						fullBoard.get(i).set(j+2, getSquare(i,  j));
						buildFromLastMove(false);
					}
				}
				if(j > 1 && i < fullBoard.size()-2){
					if(getSquare(i,  j)==fullBoard.get(i+1).get(j-1)
							&&fullBoard.get(i+1).get(j-1)==fullBoard.get(i+2).get(j-2)
							&&(getSquare(i,  j)==1||getSquare(i,  j)==2)){
						fullBoard.get(i).set(j, getSquare(i,  j)+2);
						fullBoard.get(i+1).set(j-1, getSquare(i,  j));
						fullBoard.get(i+2).set(j-2, getSquare(i,  j));
						buildFromLastMove(false);
					}
				}
			}
		}
		if(full){
			printBoard(fullBoard);
			buildFromLastMove(true);
			printBoard(fullBoard);
		}
		for(int i = 0; i < fullBoard.size(); i++){
			for(int j = 0; j < fullBoard.get(0).size(); j++){
				ArrayList<Point> match = new ArrayList<Point>();
				int previousSize = 0;
				match.add(new Point(i, j));
				if(getSquare(i,  j)==3){
					while(previousSize < match.size()){
						previousSize = match.size();
						for(int x = 0; x < match.size(); x++){
							for(int k = -1; k < 2; k++){
								for(int l = -1; l < 2; l++){
									if(match.get(x).x+k > 0 
											&& match.get(x).x+k < fullBoard.size()
											&& match.get(x).y+l > 0 
											&& match.get(x).y+l < fullBoard.get(0).size()){
										if(fullBoard.get(match.get(x).x+k).get(match.get(x).y+l) == 3
												&& !(match.contains(new Point(match.get(x).x+k, match.get(x).y+l)))){
											match.add(new Point(match.get(x).x+k, match.get(x).y+l));
										}
									}
								}
							}
						}
					}
					if(match.size()>=9)winner=1;
				}
				if(getSquare(i,  j)==4){
					while(previousSize < match.size()){
						previousSize = match.size();
						for(int x = 0; x < match.size(); x++){
							for(int k = -1; k < 2; k++){
								for(int l = -1; l < 2; l++){
									if(match.get(x).x+k > 0 
											&& match.get(x).x+k < fullBoard.size()
											&& match.get(x).y+l > 0 
											&& match.get(x).y+l < fullBoard.get(0).size()){
										if(fullBoard.get(match.get(x).x+k).get(match.get(x).y+l) == 4
												&& !(match.contains(new Point(match.get(x).x+k, match.get(x).y+l)))){
											match.add(new Point(match.get(x).x+k, match.get(x).y+l));
										}
									}
								}
							}
						}
					}
					if(match.size()>=9){
						winner=2;
					}
				}
			}
		}
		if(winner != 0){
			if(winner == 1){
				WIN = "X";
			}
			else{
				WIN = "O";
			}
		}
	}
	
	private int getSquare(int x, int y){
		return fullBoard.get(x).get(y);
	}

	private void buildFromLastMove(boolean noMovesLeft){
		int posx = lastMove.x%3;
		int posy = lastMove.y%3;
		System.out.println(posx + ":" + posy);
		switch(posx){
		case 0:
			switch(posy){
			case 0:
				if(lastMove.y-3-posy < 0
						||lastMove.y-3-posy>=fullBoard.size()
						||lastMove.x-3-posx<0
						||lastMove.x-3-posx>=fullBoard.get(0).size()){
					addNewBoard(lastMove.y-posy-3, lastMove.x-3-posx, fullBoard);
				}
				else if(fullBoard.get(lastMove.y-3-posy).get(lastMove.x-3-posx) == 5){
					addNewBoard(lastMove.y-posy-3, lastMove.x-posx-3, fullBoard);
				}else if(noMovesLeft){
					addNewBoard(-3, -3, fullBoard);
				}
				break;
			case 1:
				if(lastMove.y-posy < 0
						||lastMove.y-posy>=fullBoard.size()
						||lastMove.x-3-posx<0
						||lastMove.x-3-posx>=fullBoard.get(0).size()){
					addNewBoard(lastMove.y-posy, lastMove.x-3-posx, fullBoard);
				}
				else if(fullBoard.get(lastMove.y-posy).get(lastMove.x-3-posx) == 5){
					addNewBoard(lastMove.y-posy, lastMove.x-posx-3, fullBoard);
				}else if(noMovesLeft){
					addNewBoard(-3, -3, fullBoard);
				}
				break;
			case 2:
				if(lastMove.y+3-posy < 0
						||lastMove.y+3-posy>=fullBoard.size()
						||lastMove.x-3-posx<0
						||lastMove.x-3-posx>=fullBoard.get(0).size()){
					addNewBoard(lastMove.y-posy+3, lastMove.x-3-posx, fullBoard);
				}
				else if(fullBoard.get(lastMove.y+3-posy).get(lastMove.x-3-posx) == 5){
					addNewBoard(lastMove.y-posy+3, lastMove.x-posx-3, fullBoard);
				}else if(noMovesLeft){
					addNewBoard(-3, -3, fullBoard);
				}
				break;
			}
			break;
		case 1:
			switch(posy){
			case 0:
				if(lastMove.y-3-posy < 0
						||lastMove.y-3-posy>=fullBoard.size()
						||lastMove.x-posx<0
						||lastMove.x-posx>=fullBoard.get(0).size()){
					addNewBoard(lastMove.y-posy-3, lastMove.x-posx, fullBoard);
				}
				else if(fullBoard.get(lastMove.y-3-posy).get(lastMove.x-posx) == 5){
					addNewBoard(lastMove.y-posy-3, lastMove.x-posx, fullBoard);
				}else if(noMovesLeft){
					addNewBoard(-3, -3, fullBoard);
				}
				break;
			case 1:
				if(noMovesLeft){
					addNewBoard(-3, -3, fullBoard);
				}
				break;
			case 2:
				if(lastMove.y+3-posy < 0
						||lastMove.y+3-posy>=fullBoard.size()
						||lastMove.x-posx<0
						||lastMove.x-posx>=fullBoard.get(0).size()){
					addNewBoard(lastMove.y-posy+3, lastMove.x-posx, fullBoard);
				}
				else if(fullBoard.get(lastMove.y+3-posy).get(lastMove.x-posx) == 5){
					addNewBoard(lastMove.y-posy+3, lastMove.x-posx, fullBoard);
				}else if(noMovesLeft){
					addNewBoard(-3, -3, fullBoard);
				}
				break;
			}
			break;
		case 2:
			switch(posy){
			case 0:
				if(lastMove.y-3-posy < 0
						||lastMove.y-3-posy>=fullBoard.size()
						||lastMove.x+3-posx<0
						||lastMove.x+3-posx>=fullBoard.get(0).size()){
					addNewBoard(lastMove.y-posy-3, lastMove.x-posx+3, fullBoard);
				}
				else if(fullBoard.get(lastMove.y-3-posy).get(lastMove.x+3-posx) == 5){
					addNewBoard(lastMove.y-posy-3, lastMove.x-posx+3, fullBoard);
				}else if(noMovesLeft){
					addNewBoard(-3, -3, fullBoard);
				}
				break;
			case 1:
				if(lastMove.y-posy < 0
						||lastMove.y-posy>=fullBoard.size()
						||lastMove.x+3-posx<0
						||lastMove.x+3-posx>=fullBoard.get(0).size()){
					addNewBoard(lastMove.y-posy, lastMove.x-posx+3, fullBoard);
				}
				else if(fullBoard.get(lastMove.y-posy).get(lastMove.x+3-posx) == 5){
					addNewBoard(lastMove.y-posy, lastMove.x-posx+3, fullBoard);
				}else if(noMovesLeft){
					addNewBoard(-3, -3, fullBoard);
				}
				break;
			case 2:
				if(lastMove.y+3-posy < 0
						||lastMove.y+3-posy>=fullBoard.size()
						||lastMove.x+3-posx<0
						||lastMove.x+3-posx>=fullBoard.get(0).size()){
					addNewBoard(lastMove.y-posy+3, lastMove.x-posx+3, fullBoard);
				}
				else if(fullBoard.get(lastMove.y+3-posy).get(lastMove.x+3-posx) == 5){
					addNewBoard(lastMove.y-posy+3, lastMove.x-posx+3, fullBoard);
				}
				else if(noMovesLeft){
					addNewBoard(-3, -3, fullBoard);
				}
				break;
			}
			break;
		}
		printBoard(fullBoard);
	}

	public void mouseReleased(Point point) {
		int currentX = (point.x - offsetx)/squareSize;
		int currentY = (point.y - offsety)/squareSize;
		if(currentY < fullBoard.size() && currentX < fullBoard.get(0).size()){ //I have to remember that X is Y and Y is X
			if(fullBoard.get(currentY).get(currentX) == 0){                    //Literally 80% of my problems are that
				fullBoard.get(currentY).set(currentX, getTurn());
				lastMove = new Point(currentX, currentY);
				System.out.println(lastMove);
				nextTurn();
			}
		}
	}

	public int getTurn() {
		return turn;
	}

	private void nextTurn() {
		if(turn == 1){
			turn = 2;
		}else{
			turn = 1;
		}
	}

	@Override
	public void mouseClicked(Point click) {}
}
