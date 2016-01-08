package Main;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import GameState.GameStateManager;

public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 720;
	public static final int HEIGHT = 720;
	public static final double SCALE = 0.5;
	private Thread thread;
	private boolean running;
	private BufferedImage image;
	public static Graphics2D g;

	private static GameStateManager gsm;

	private JFileChooser jfc = new JFileChooser();
	FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");
	
	
	
	public GamePanel() {
		super();
		jfc.setFileFilter(filter);
		setPreferredSize(new Dimension((int)(WIDTH*SCALE), (int)(HEIGHT*SCALE)));
		setFocusable(true);
		requestFocus();
	}
	public void addNotify(){
		super.addNotify();
		if(thread == null){
			thread = new Thread(this);
			addMouseListener(this);
			addKeyListener(this);
			addMouseMotionListener(this);
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
		while(running){
			if(gsm.WIN != null){
				gsm.setState(GameStateManager.GAMEOVER);
			}
			update();
			draw();
			drawToScreen();
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
		g2.drawImage(image, 0, 0, (int)(WIDTH*SCALE), (int)(HEIGHT*SCALE), null);
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
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		gsm.mouseMoved(e);
	}
	public File getImage() {
		if(jfc.showOpenDialog(this)==JFileChooser.APPROVE_OPTION)return jfc.getSelectedFile();
		else return null;
	}
}
