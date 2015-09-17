package GameState;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;

import Main.GamePanel;

public class GameOver extends GameState {
	
	public GameOver(GameStateManager gsm){
		this.gsm = gsm;
	}
	public void init() {
		
	}

	public void update() {}

	public void draw(Graphics2D g) {
		FontMetrics fm = g.getFontMetrics();
        int x = ((GamePanel.WIDTH - fm.stringWidth("Game Over")) / 2);
        int x2 = ((GamePanel.WIDTH - fm.stringWidth(BoardState.WIN + " Wins")) / 2);
        g.setColor(Color.GREEN);
		g.drawString("Game Over", x, 32);
		g.drawString(BoardState.WIN + " Wins", x2, 128);
	}

	public void keyPressed(int k) {
		if (k == KeyEvent.VK_ENTER){
			select();
		}
	}

	public void keyReleased(int k) {}
	public void select(){
		gsm.setState(GameStateManager.BOARDSTATE);
	}
	public void mouseClicked(Point click) {
		
	}
	@Override
	public void mouseReleased(Point click) {}
}
