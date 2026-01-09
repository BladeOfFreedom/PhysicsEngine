import objects.*;
import Vector2d.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;

public class Panel extends JPanel implements Runnable, KeyListener{
	// SCREEN SETTINGS
	final int WIDTH = 1200;
	final int HEIGHT = 800;
	final int FPS = 60;
	
	public static CopyOnWriteArrayList<RigidBody> bodies = new CopyOnWriteArrayList<>();
	
	Circle c1, c2, c3;
	Plane2D p1, p2, p3, p4;
	
	Thread engineThread;
	
	AddPanel add = new AddPanel();
	
	public Panel() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(Color.white);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.addKeyListener(this);
		
		
		resetSimulation();
	}
	
	public void resetSimulation() {
		
		bodies.clear();
		
		c1 = new Circle(1200/2, 800, new Vector2D(10, -10), new Vector2D(0, 0), new Vector2D(0, 0), 100, 0.8, 30);
		c2 = new Circle(600/2, 500, new Vector2D(12, -10), new Vector2D(0, 0), new Vector2D(0, 0), 10, 0.8, 50);
		c3 = new Circle(1000, 500, new Vector2D(14, -10), new Vector2D(0, 0), new Vector2D(0, 0), 10, 0.8, 50);
		
		p1 = new Plane2D(0, 790, 1200, 10, new Vector2D(0, -1));
		p2 = new Plane2D(1190, 0, 10, 800, new Vector2D(-1, 0));
		p3 = new Plane2D(0, 0, 1200, 10, new Vector2D(0, 1));
		p4 = new Plane2D(0, 0, 10, 800, new Vector2D(1, 0));
		
		
		bodies.add(c1);
		bodies.add(c2);
		bodies.add(c3);
		
		bodies.add(p1);
		bodies.add(p2);
		bodies.add(p3);
		bodies.add(p4);
		
	}
	
	public static CopyOnWriteArrayList<RigidBody> getBodies(){
		return bodies;
	}

	public void startEngineThread() {
		engineThread = new Thread(this);
		engineThread.start();
	}
	
	@Override
	public void run() {

		double updateInterval = 1000000000/FPS;
		long lastTime = System.nanoTime();
		long accummulatedTime = 0;
		long currentTime;
		
		while(engineThread != null)
		{
			currentTime = System.nanoTime();
			accummulatedTime += (currentTime - lastTime);
			lastTime = currentTime;
			
			while(accummulatedTime >= updateInterval) {
				
			//.1 update information such as object position
			update(updateInterval / 1000000000);
			
			
			accummulatedTime -= updateInterval;
			}
			
			
			//2. draw the screen with the updated information
			repaint();
		}
	}
	
	
	public void update(double dt) {
		//adjust positions depending on the velocity and acceleration
		//We calculate the new acceleration every time we update and reset it so it doesn't stack
		for (RigidBody body : bodies) {
			PhysicsManager.applyGravity(body, dt);
			PhysicsManager.accelerate(body, dt);
			PhysicsManager.moveObjects(body, dt);
			body.resetAcceleration();
		}
		
		
		PhysicsManager.checkAllCollisions(bodies);
		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		for (RigidBody body : bodies) {
			if(body instanceof Circle)
				((Circle) body).draw(g2, Color.pink);

			if(body instanceof Plane2D) {
				((Plane2D) body).draw(g2, Color.cyan);
			}
		}
		

		
		g2.dispose();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_F1) {
			add.setVisible(true);
		}
		if(e.getKeyCode() == KeyEvent.VK_F2) {
			resetSimulation();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
