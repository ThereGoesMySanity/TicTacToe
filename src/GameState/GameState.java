package GameState;

import java.awt.Point;
import java.awt.event.MouseEvent;

public abstract class GameState {
	protected GameStateManager gsm;
	public abstract void init();
	public abstract void update();
	public abstract void draw(java.awt.Graphics2D g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
	public abstract void mouseReleased(Point click);
	public abstract void mouseClicked(Point click);
	public abstract void mouseMoved(MouseEvent e);
}
