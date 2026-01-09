package objects;

import Vector2d.*;

public class RigidBody {

	private final int PIXEL_PER_METER = 100;
	public Vector2D velocity, position, acceleration, constantAcceleration;
	private double inverseMass, m;
	//restitution
	private double e;
	
	public RigidBody() {
		this.position = new Vector2D(0, 0);
		this.velocity = new Vector2D(0, 0);
		this.acceleration = new Vector2D(0, 0);
		this.constantAcceleration = new Vector2D(0, 0);
		this.inverseMass = 0.5;
		this.e = 1;
	}
	
	public RigidBody(double x, double y, Vector2D velocity, Vector2D acceleration, Vector2D constantAcceleration,double mass, double restitution) {
		this.position = new Vector2D(x, y);
		this.velocity = velocity;
		this.velocity.scale(PIXEL_PER_METER);
		this.acceleration = acceleration;
		this.acceleration.scale(PIXEL_PER_METER);
		this.constantAcceleration = constantAcceleration;
		this.constantAcceleration.scale(PIXEL_PER_METER);
		this.e = restitution;
		this.m = mass;
		if(mass == 0)
			this.inverseMass = 0;
		else
			this.inverseMass = 1 / m;
	}
	
	public void resetAcceleration() {
		this.acceleration.x = 0;
		this.acceleration.y = 0;
	}

	public double getInverseMass() {
		return inverseMass;
	}

	public double getE() {
		return e;
	}
	
}
