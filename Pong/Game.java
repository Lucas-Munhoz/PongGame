
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable,KeyListener{

	private static final long serialVersionUID = 1L;
	public static int WIDTH = 160;
	public static int HEIGHT = 120;
	public static int SCALE = 3;
	
	public BufferedImage layer = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	
	public static Player player;
	public static Enemy enemy;
	public static Ball bola;
	
	public static int ptsPlayer = 0;
	public static int ptsEnemy = 0;
	public static boolean fundoWin = false;
	public static boolean fundoDefeat = false;
	
	public Game() {
		this.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		this.addKeyListener(this);
		player = new Player(WIDTH/2,HEIGHT-3);
		enemy = new Enemy(WIDTH/2,0);
		bola = new Ball(WIDTH/2,HEIGHT/2);
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame("Pong");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(game);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		new Thread(game).start();
	}
	
	public void tick() {
		player.tick();
		enemy.tick();
		bola.tick();
	}
	
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = layer.getGraphics();
		g.setColor(Color.darkGray);
		g.fillRect(0,0,WIDTH,HEIGHT);
		player.render(g);
		enemy.render(g);
		bola.render(g);
		
		g = bs.getDrawGraphics();
		g.drawImage(layer,0,0,WIDTH*SCALE,HEIGHT*SCALE,null);
		
		g.setFont(new Font("Arial",Font.BOLD,12));
		g.setColor(Color.WHITE);
		g.drawString("Jogador = " + Game.ptsPlayer, 5, HEIGHT*SCALE/2 + 15);
		g.fillRect(0, HEIGHT*SCALE/2, WIDTH*SCALE, 2);
		g.drawString("Inimigo = " + Game.ptsEnemy, 5, HEIGHT*SCALE/2 - 5);
		
		if(fundoWin == true) {
			g.setColor(Color.green);
			g.fillRect(0,0,WIDTH*SCALE,HEIGHT*SCALE);
			Game.fundoWin = false;
		}
		if(fundoDefeat == true) {
			g.setColor(Color.red);
			g.fillRect(0,0,WIDTH*SCALE,HEIGHT*SCALE);
			Game.fundoDefeat = false;
		}
		bs.show();
	}
	
	@Override
	public void run() {
		while(true) {
			tick();
			render();
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = false;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = false;
		}
	}

}
