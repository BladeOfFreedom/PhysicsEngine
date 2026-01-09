package Vector2d;

public class Vector2D {

	public double x, y;
	
	public Vector2D() {
		this.x = 0;
		this.y = 0;
	}
	
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	//adds the parameter vector v to the vector 
	public void add(Vector2D v) {
		this.x = this.x + v.x;
		this.y = this.y + v.y;
	}
	
	//subtracts the parameter vector v to the vector 
	public void subtract(Vector2D v) {
		this.x = this.x - v.x;
		this.y = this.y - v.y;
	}
	
	//scales the vector by scaler
	public void scale(double scaler) {
		this.x *= scaler;
		this.y *= scaler;
	}
	
	//returns the square magnitude used for normalize function
	public double magSquared() {
		return this.x * this.x + this.y * this.y;
	}
	
	//returns the magnitude of the vector
	public double magnitude() {
		return Math.sqrt(this.magSquared());
	}
	
	
	//
	//Static functions
	//-->
	
	public static Vector2D add(Vector2D v1, Vector2D v2) {
		return new Vector2D(v1.x + v2.x, v1.y + v2.y);
	}
	
	public static Vector2D subtract(Vector2D v1, Vector2D v2) {
		return new Vector2D(v1.x - v2.x, v1.y - v2.y);
	}
	
	//scales vector v with scaler and returns it
	public static Vector2D scale(Vector2D v, double scaler) {
		return new Vector2D(v.x*scaler, v.y*scaler);
	}
	
	//returns the dot product of 2 vectors
	public static double dot(Vector2D v1, Vector2D v2) {
		return v1.x * v2.x + v1.y * v2.y;
	}
	
	//returns the normalized vector of vector v
	public static Vector2D getNormalized(Vector2D v) {
		double m = v.magnitude();
		
		if(m!=0) {
			return new Vector2D(v.x / m, v.y / m);
		}
		return new Vector2D(0, 0);
	}
	
}
