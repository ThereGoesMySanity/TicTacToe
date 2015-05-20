package GameState;
import java.awt.Graphics2D;
import java.awt.Point;
public class GameStateManager {
	private GameState[] gameStates;
	private int currentState;
	public boolean bulletTime;
	public long btTime;
	public static long btTimer;
	public static boolean btCooldown;
	public static final int NUMGAMESTATES = 2;
	public static final int LEVEL1STATE = 0;
	public static final int GAMEOVER = 1;

	public GameStateManager(){
		gameStates = new GameState[NUMGAMESTATES];
		currentState = LEVEL1STATE;
		loadState(currentState);

	}
	private void loadState(int state){
		if(state == LEVEL1STATE){
			gameStates[state] = new BoardState(this);
		}
		if(state == GAMEOVER){
			gameStates[state] = new GameOver(this);
		}
	}

	private void unloadState(int state){
		gameStates[state] = null;
	}

	public void setState(int state){
		unloadState(currentState);
		currentState = state;
		loadState(state);
	}
	public void update(){
		gameStates[currentState].update();
	}

	public void draw(Graphics2D g){
		if(gameStates[currentState] != null){
			gameStates[currentState].draw(g);
		}
	}
	public void keyPressed(int k){
		gameStates[currentState].keyPressed(k);
	}
	public void keyReleased(int k){
		gameStates[currentState].keyReleased(k);
	}
	public void mouseReleased(Point click) {
		gameStates[currentState].mouseReleased(click);

	}
}
