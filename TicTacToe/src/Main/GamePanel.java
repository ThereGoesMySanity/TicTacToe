package Main;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import GameState.GameStateManager;
import GameState.BoardState;

public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 720;
	public static final int HEIGHT = 720;
	public static final int SCALE = 1;
	private Thread thread;
	private boolean running;
	private BufferedImage image;
	public static Graphics2D g;

	private static GameStateManager gsm;

	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		setFocusable(true);
		requestFocus();
	}
	public void addNotify(){
		super.addNotify();
		if(thread == null){
			thread = new Thread(this);
			addMouseListener(this);
			addKeyListener(this);
			thread.start();
		}
	}
	private void init() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		running = true;
		gsm = new GameStateManager();
	}

	public void run(){
		init();


		draw();
		int z = 0;
		while(running){
			if(BoardState.WIN != null){
				gsm.setState(GameStateManager.GAMEOVER);
			}
			update();
			draw();
			drawToScreen();
			if(z == 1000){
				z = 0;
				drawToScreen();
			}
			z++;
		}
	}

	private void update(){
		gsm.update();
	}

	public static void drawImage(BufferedImage i){
		g.drawImage(i, 0, 0, null);
	}

	public void draw(){gsm.draw(g);}

	private void drawToScreen(){
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		g2.dispose();
	}

	public void keyTyped(KeyEvent key){}

	public void keyPressed(KeyEvent key){gsm.keyPressed(key.getKeyCode());}

	public void keyReleased(KeyEvent key){gsm.keyReleased(key.getKeyCode());}

	public void mouseReleased(MouseEvent mouse) {
		gsm.mouseReleased(mouse.getPoint());
	}

	public void mouseEntered(MouseEvent mouse) {}

	public void mouseExited(MouseEvent mouse) {}

	public void mousePressed(MouseEvent mouse) {}

	public void mouseClicked(MouseEvent mouse) {}

	public static void reDraw() {
		gsm.draw(g);

	}
}
