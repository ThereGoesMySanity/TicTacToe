package Main;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import GameState.GameStateManager;

public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 360;
	public static final int HEIGHT = 360;
	public static final int SCALE = 1;
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000/FPS;
	private BufferedImage image;
	public static Graphics2D g;
	
	private GameStateManager gsm;
	
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
		
		long start;
		long elapsed;
		long wait;
		
		while(running){
			
			start = System.nanoTime();

			update();
			draw();
			drawToScreen();
			
			elapsed = System.nanoTime() - start;
			wait = targetTime - elapsed/1000000;
			if(wait < 0){wait=5;}
			try{Thread.sleep(wait);}catch(Exception e){e.printStackTrace();}
		}
	}
	private void update(){
		gsm.update();
	}
	public static void drawImage(BufferedImage i){
		g.drawImage(i, 0, 0, null);
	}
	private void draw(){gsm.draw(g);}
	private void drawToScreen(){
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		g2.dispose();
	}
	
	public void keyTyped(KeyEvent key){}
	public void keyPressed(KeyEvent key){gsm.keyPressed(key.getKeyCode());}
	public void keyReleased(KeyEvent key){gsm.keyReleased(key.getKeyCode());}
	public void mouseClicked(MouseEvent mouse) {
		gsm.mouseClicked(mouse.getPoint());
	}
	public void mouseEntered(MouseEvent mouse) {
		
	}
	public void mouseExited(MouseEvent mouse) {
		
	}
	public void mousePressed(MouseEvent mouse) {
		
	}
	public void mouseReleased(MouseEvent mouse) {
		
	}
	
}
