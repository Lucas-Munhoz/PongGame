import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Enemy {

	//IA pode possuir numeros não inteiros, por isso double
	public double x,y;
	public int width, height;
	public int n;
	
	Random gerador = new Random();
	
	public Enemy(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 20;
		this.height = 3;
	}
	
	public void tick() {
		
		x += (Game.bola.x - x - 1) * 0.25;
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect((int)x,(int)y,width,height);
	}
}
