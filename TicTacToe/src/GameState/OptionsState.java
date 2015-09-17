package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;

import Main.GamePanel;

public class OptionsState extends GameState {
	private String[] options = {
		"X color",
		"X image",
		"O color",
		"O image",
		"Board color",
		"Back"
	};
	private Color titleColor;
	private Font titleFont;
	private Font font;
	
	public OptionsState(GameStateManager gsm){
		this.gsm = gsm;
		try{
			titleColor = new Color (128, 0, 0);
			titleFont = new Font("Fixedsys", Font.TRUETYPE_FONT, 28*2/GamePanel.SCALE);
			font = new Font("Fixedsys", Font.TRUETYPE_FONT, 16*2/GamePanel.SCALE);
		}catch(Exception e){e.printStackTrace();}
		
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {

	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.drawRect(0, 0, 720, 720);
		g.setColor(titleColor);
		g.setFont(titleFont);
		FontMetrics fm = g.getFontMetrics();
        int x = ((320*2/GamePanel.SCALE - fm.stringWidth("Options")) / 2);
        int y = ((240*2/GamePanel.SCALE - fm.getHeight()) / 2) + fm.getAscent();
		g.drawString("Options", x, y-64*2/GamePanel.SCALE);
		g.setFont(font);
		for(int i = 0; i < options.length; i++){
			g.setColor(Color.LIGHT_GRAY);
			g.drawString(options[i], 200/GamePanel.SCALE, 200 + i*60);
		}

	}
	private void select(int choice){
		switch(choice){
		case 0:
			
			break;
		case 1:
			
			break;
		case 2:
			
			break;
		case 3:
			
			break;
		case 4:
			
			break;
		case 5:
			gsm.setState(GameStateManager.MENUSTATE);
			break;
		}
	}

	@Override
	public void keyPressed(int k) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(Point click) {
		// TODO Auto-generated method stub
		if(click.y >= 140 && click.y <= 140 + options.length*60&&click.x>=200){
			select((click.y-140)/60);
		}
	}
	@Override
	public void mouseClicked(Point click) {
		// TODO Auto-generated method stub
		
	}
}
