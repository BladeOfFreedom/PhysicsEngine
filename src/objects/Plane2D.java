package objects;

import java.awt.Color;
import java.awt.Graphics2D;

import Vector2d.*;

public class Plane2D extends RigidBody{

	public double width, height;
	public Vector2D position, normal;
	
	public Plane2D() {
		super();
		this.width = 100;
		this.normal = new Vector2D(0, 0);
		this.height = 10;
	}
	
	public Plane2D(double x, double y, double width, double height,Vector2D normal) {
		super();
		this.position = new Vector2D(x, y);
		this.width = width;
		this.normal = normal;
		this.height = height;
	}
	
	public void draw(Graphics2D g2, Color c) {
		g2.setColor(c);
		g2.fillRect((int)position.x, (int)position.y, (int)width, (int)height);
		
	}
	
}
