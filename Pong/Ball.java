import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Ball {
	public double x,y;
	public int width, height;
	
	public double dx,dy;
	public double spd = 1.7;
	
	public int ptsWin = 5;
	
	public Ball(int x, int y) {
		//this = classe, sem this = metodo
		this.x = x;
		this.y = y;
		this.width = 4;
		this.height = 4;
		
		randomizeBall();
	}
	
	public void randomizeBall() {
		int angle = new Random().nextInt(100 - 20) + 20;
		dx = Math.cos(Math.toRadians(angle));
		dy = Math.sin(Math.toRadians(angle));
	}
	
	public void tick() {
		
		if(x+(dx*spd)+ width >= Game.WIDTH) {
			dx*=-1;
		}
		else if(x+(dx*spd) < 0) {
			dx*=-1;
		}
		
		if(y >= Game.HEIGHT) {
			//ponto do enimigo
			Game.ptsEnemy += 1;
			this.x = Game.WIDTH/2;
			this.y = Game.HEIGHT/2;
			randomizeBall();
			System.out.println("Inimigo = " + Game.ptsEnemy);
			return;
		}
		else if(y < 0) {
			//ponto do jogador
			Game.ptsPlayer += 1;
			this.x = Game.WIDTH/2;
			this.y = Game.HEIGHT/2;
			randomizeBall();
			System.out.println("Jogador = " + Game.ptsPlayer);
			return;
		}
		
		if(Game.ptsPlayer == ptsWin) {
			Game.ptsPlayer = 0;
			Game.ptsEnemy = 0;
			System.out.println("Jogador venceu com " + ptsWin + " pontos!");
			Game.fundoWin = true;
			new Game();
		} else if(Game.ptsEnemy == ptsWin) {
			Game.ptsPlayer = 0;
			Game.ptsEnemy = 0;
			System.out.println("Inimigo venceu com " + ptsWin + " pontos!");
			Game.fundoDefeat = true;
			new Game();
		}
		
		Rectangle bounds = new Rectangle((int)(x+(dx*spd)),(int)(y+(dy*spd)),width,height);
		
		Rectangle boundsPlayer = new Rectangle(Game.player.x,Game.player.y,Game.player.width,Game.player.height);
		Rectangle boundsEnemy = new Rectangle((int)Game.enemy.x,(int)Game.enemy.y,Game.enemy.width,Game.enemy.height);
		
		if(bounds.intersects(boundsPlayer)) {
			int angle = new Random().nextInt(100 - 20) + 20;
			dx = Math.cos(Math.toRadians(angle));
			dy = Math.sin(Math.toRadians(angle));
			if(dy > 0) {
				dy*=-1;
			}
		}else if(bounds.intersects(boundsEnemy)) {
			int angle = new Random().nextInt(100 - 20) + 20;
			dx = Math.cos(Math.toRadians(angle));
			dy = Math.sin(Math.toRadians(angle));
			if(dy < 0) {
				dy*=-1;
			}
		}
		x+=dx*spd;
		y+=dy*spd;
	}
	
	public void render(Graphics g) {
		if(this.y <= Game.HEIGHT/2) {
			g.setColor(Color.green);
		} else{
			g.setColor(Color.red);
		}
		g.fillRect((int)x,(int)y,width,height);
	}
}
