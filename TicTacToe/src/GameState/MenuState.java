package GameState;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import com.sun.glass.events.KeyEvent;

import Main.GamePanel;

public class MenuState extends GameState{

	private int currentChoice = 0;
	private String[] options = {
		"Start",
		"Options",
		"Help",
		"Quit"
	};
	private Color titleColor;
	private Font titleFont;
	public MenuState(GameStateManager gsm){
		this.gsm = gsm;
		try{
			titleColor = new Color (128, 0, 0);
			titleFont = new Font("Fixedsys", Font.TRUETYPE_FONT, (56));
		}catch(Exception e){e.printStackTrace();}
		
	}
	public void init(){}
	public void draw(Graphics2D g){
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 720, 720);
		g.setColor(titleColor);
		g.setFont(titleFont);
		FontMetrics fm = g.getFontMetrics();
		int x = ((GamePanel.WIDTH - fm.stringWidth("Tic-Tac-Toe")) / 2);
		int y = fm.getHeight();
		g.drawString("Tic-Tac-Toe", x, y+32);
		g.setFont(titleFont);
		for(int i = 0; i < options.length; i++){
			if(i==currentChoice){
				g.setColor(Color.DARK_GRAY);
			}else{
				g.setColor(Color.LIGHT_GRAY);
			}
			g.drawString(options[i], ((360)), (280) + (i*60));
		}
	}
	private void select(int choice){
		switch(choice){
		case 0:
			gsm.setState(GameStateManager.BOARDSTATE);
			break;
		case 1:
			gsm.setState(GameStateManager.OPTIONSSTATE);
			break;
		case 2:
			//help
			break;
		case 3:
			System.exit(0);
		}
	}
	
	public void keyPressed(int k){
		if(k == KeyEvent.VK_ENTER){
			select(0);
		}
	}
	public void keyReleased(int k){}
	@Override
	public void mouseReleased(Point click) {
		// TODO Auto-generated method stub
		if(click.y >= (220) && click.y <= (220) + options.length*60&&click.x>=(360)){
			select((click.y-(220))/60);
		}
	}
	@Override
	public void mouseClicked(Point click) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		if(e.getY() >= (220) && e.getY() <= (220) + options.length*60&&e.getX()>=(360)){
			currentChoice = (e.getY()-(220))/60;
		}
	}
}
