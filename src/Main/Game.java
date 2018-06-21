package Main;
import javax.swing.JFrame;
public class Game {
	public static GamePanel panel = new GamePanel();
	public static void main(String[] args){
		JFrame window = new JFrame("Giant TTT");
		window.setContentPane(panel);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
	}
}
