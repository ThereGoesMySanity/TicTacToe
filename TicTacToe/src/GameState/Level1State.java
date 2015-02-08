package GameState;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import Main.GamePanel;

public class Level1State extends GameState {
	//0 = blank but part of board, 1 and 2 = X and O, 3 and 4 = completed X and O row, 5 = Not board
	private ArrayList<ArrayList<Integer>> fullBoard = new ArrayList<ArrayList<Integer>>();
	private int turn = 1;
	private Point lastMove;
	private int winner = 0;
	public static String WIN;

	public Level1State(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}

	private void addNewBoard(int x, int y, ArrayList<ArrayList<Integer>> board){
		//Okay, I consider x to be the outside arraylist and I don't care if that's weird
		//Also, I'll probably mess up a lot either way.
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
		int max;
		if(fullBoard.size() > fullBoard.get(0).size()){
			max = fullBoard.size();
		}
		else{
			max = fullBoard.get(0).size();
		}
		return new Point(x*(GamePanel.HEIGHT/max), y*(GamePanel.HEIGHT/max));
	}
	public void draw(Graphics2D g) {
		Point point1 = new Point();
		Point point2 = new Point();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.HEIGHT, GamePanel.WIDTH);
		g.setColor(Color.BLACK);
		for(int i = 0; i < fullBoard.size(); i++){
			for(int j = 0; j < fullBoard.get(0).size(); j++){
				if(fullBoard.get(i).get(j) != 5){
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
					if(fullBoard.get(i).get(j) == 1){
						point1 = getCoords(j, i);
						point2 = getCoords(j+1, i+1);
						g.drawLine(point1.x, point1.y, point2.x, point2.y);
						point1 = getCoords(j+1, i);
						point2 = getCoords(j, i+1);
						g.drawLine(point1.x, point1.y, point2.x, point2.y);
					}
					if(fullBoard.get(i).get(j) == 2){
						point1 = getCoords(j, i);
						point2 = getCoords(j+1, i+1);
						g.drawOval(point1.x+1, point1.y+1, point2.x/(j+1)-2, point2.y/(i+1)-2);
					}
					if(fullBoard.get(i).get(j) == 3){
						point1 = getCoords(j, i);
						point2 = getCoords(j+1, i+1);
						g.setColor(Color.RED);
						g.fillRect(point1.x+1, point1.y+1, point2.x/(j+1)-1, point2.y/(i+1)-1);
					}
					if(fullBoard.get(i).get(j) == 4){
						point1 = getCoords(j, i);
						point2 = getCoords(j+1, i+1);
						g.setColor(Color.BLUE);
						g.fillRect(point1.x+1, point1.y+1, point2.x/(j+1)-1, point2.y/(i+1)-1);
					}
					g.setColor(Color.BLACK);
				}
			}
		}
	}

	public void keyPressed(int k) {}

	public void keyReleased(int k) {}

	public void update() {
		boolean full = true;
		for(int i = 0; i < fullBoard.size(); i++){
			for(int j = 0; j < fullBoard.get(0).size(); j++){
				if(fullBoard.get(i).get(j) == 0)full = false;
				if(i < fullBoard.size()-2 
						&& j < fullBoard.get(0).size()-2){
					if(fullBoard.get(i).get(j)==fullBoard.get(i+1).get(j+1)
							&&fullBoard.get(i+1).get(j+1)==fullBoard.get(i+2).get(j+2)
							&&(fullBoard.get(i).get(j)==1||fullBoard.get(i).get(j)==2)){
						fullBoard.get(i).set(j, fullBoard.get(i).get(j)+2);
						fullBoard.get(i+1).set(j+1, fullBoard.get(i).get(j));
						fullBoard.get(i+2).set(j+2, fullBoard.get(i).get(j));
						buildFromLastMove(false);
					}
				}
				if(i < fullBoard.size()-2){
					if(fullBoard.get(i).get(j)==fullBoard.get(i+1).get(j)
							&&fullBoard.get(i+1).get(j)==fullBoard.get(i+2).get(j)
							&&(fullBoard.get(i).get(j)==1||fullBoard.get(i).get(j)==2)){
						fullBoard.get(i).set(j, fullBoard.get(i).get(j)+2);
						fullBoard.get(i+1).set(j, fullBoard.get(i).get(j));
						fullBoard.get(i+2).set(j, fullBoard.get(i).get(j));
						buildFromLastMove(false);
					}
				}
				if(j < fullBoard.get(0).size()-2){
					if(fullBoard.get(i).get(j)==fullBoard.get(i).get(j+1)
							&&fullBoard.get(i).get(j+1)==fullBoard.get(i).get(j+2)
							&&(fullBoard.get(i).get(j)==1||fullBoard.get(i).get(j)==2)){
						fullBoard.get(i).set(j, fullBoard.get(i).get(j)+2);
						fullBoard.get(i).set(j+1, fullBoard.get(i).get(j));
						fullBoard.get(i).set(j+2, fullBoard.get(i).get(j));
						buildFromLastMove(false);
					}
				}
				if(j > 1 && i < fullBoard.size()-2){
					if(fullBoard.get(i).get(j)==fullBoard.get(i+1).get(j-1)
							&&fullBoard.get(i+1).get(j-1)==fullBoard.get(i+2).get(j-2)
							&&(fullBoard.get(i).get(j)==1||fullBoard.get(i).get(j)==2)){
						fullBoard.get(i).set(j, fullBoard.get(i).get(j)+2);
						fullBoard.get(i+1).set(j-1, fullBoard.get(i).get(j));
						fullBoard.get(i+2).set(j-2, fullBoard.get(i).get(j));
						buildFromLastMove(false);
					}
				}
			}
		}
		if(full){
			System.out.println("full");
			printBoard(fullBoard);
			System.out.println("---------");
			buildFromLastMove(true);
			printBoard(fullBoard);
		}
		for(int i = 0; i < fullBoard.size(); i++){
			for(int j = 0; j < fullBoard.get(0).size(); j++){
				ArrayList<Point> match = new ArrayList<Point>();
				int previousSize = 0;
				match.add(new Point(i, j));
				if(fullBoard.get(i).get(j)==3){
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
				if(fullBoard.get(i).get(j)==4){
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


	private void buildFromLastMove(boolean noMovesLeft){
		int posx = lastMove.x%3;
		int posy = lastMove.y%3;
		System.out.println(posx + ":" + posy);
		switch(posx){
		case 0:
			switch(posy){
			case 0:
				System.out.println(":" + fullBoard.get(lastMove.y-3-posy).get(lastMove.x-3-posx));
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

	public void mouseClicked(Point point) {
		Point coords = getCoords(1, 1);
		int currentX = (point.x/coords.x)/GamePanel.SCALE;
		int currentY = (point.y/coords.y)/GamePanel.SCALE;
		System.out.println(currentX + ", " + currentY);
		if(currentY < fullBoard.size() && currentX < fullBoard.get(0).size()){ //One has to remember that X is Y and Y is X
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
		switch(turn){
		case 1:
			turn = 2;
			break;
		case 2:
			turn = 1;
			break;
		}
	}
}
