package objects;

import Vector2d.*;
import java.awt.Color;
import java.awt.Graphics2D;

public class Circle extends RigidBody{
	private double radius = 0;
		
	public Circle() {
		super();
		this.radius = 100;
	}
	
	public Circle(double x, double y, Vector2D velocity, Vector2D acceleration, Vector2D constantAcceleration, double mass, double restitution, double r) {
		super(x, y, velocity, acceleration, constantAcceleration, mass, restitution);
		this.radius = r;
	}



	public void draw(Graphics2D g2, Color c) {
		g2.setColor(c);
		g2.fillOval(
				(int)(this.position.x - this.radius), 
				(int)(this.position.y - this.radius), 
				(int)(this.radius * 2), 
				(int)(this.radius * 2));
	}
	
	public double getRadius() {
		return radius;
	}
	
}
