import java.util.concurrent.CopyOnWriteArrayList;

import Vector2d.*;
import objects.*;

public class PhysicsManager {

	private static final double PIXELS_PER_METER = 100;
	
	public static double getPixelsPerMeter() {
		return PIXELS_PER_METER;
	}

	private static final Vector2D g = new Vector2D (0, 9.8 * PIXELS_PER_METER);
	
	public static void applyGravity(RigidBody body, double dt) {
		if(body.getInverseMass() != 0)
			body.velocity.add(Vector2D.scale(g, dt));
	}
	
	public static void accelerate(RigidBody body, double dt) {
		if(body.getInverseMass() != 0)
		{
			body.acceleration.add(body.constantAcceleration);
			body.velocity.add(Vector2D.scale(body.acceleration, dt));
		}
	}
	
	public static void moveObjects(RigidBody body, double dt) {
		body.position.add(Vector2D.scale(body.velocity, dt));
	}
	
	public static void checkAllCollisions(CopyOnWriteArrayList<RigidBody> bodies) {
		RigidBody body1;
		RigidBody body2;
		//iterate over the list
		for (int i = 0; i < bodies.size() - 1; i++) {
			body1 = bodies.get(i);
			for (int k = i + 1; k < bodies.size(); k++) {
				body2 = bodies.get(k);
				
				//if collision occurred resolve it
				if(checkCollision(body1, body2))
					resolveCollision(body1, body2);
			}
		}
	}
	
	public static boolean checkCollision(RigidBody body1, RigidBody body2) {
		if(body1 instanceof Circle) {
			Circle c1 = (Circle)body1;
			if(body2 instanceof Circle) {
				//check collision between circle and circle
				Circle c2 = (Circle)body2;
				
				double Xdifference = c1.position.x - c2.position.x;
				double Ydifference = c1.position.y - c2.position.y;
				
				double radiusSquared = (c1.getRadius() + c2.getRadius()) * (c1.getRadius() + c2.getRadius());
				double differenceSquared = Xdifference * Xdifference + Ydifference * Ydifference;
				
				if(differenceSquared < radiusSquared)
					return true;
			
			}
			if(body2 instanceof Plane2D) {
				//check collision between circle and plane
				Plane2D p1 = (Plane2D)body2;
				
				double distance = Vector2D.dot(Vector2D.subtract(c1.position, p1.position), p1.normal);
				
				if(distance < c1.getRadius()) {
					return true;
				}
				
				
			}
			
		}
		else if(body1 instanceof Plane2D) {
			Plane2D p1 = (Plane2D)body1;
			if(body2 instanceof Circle) {
				Circle c1 = (Circle)body2;
				double distance = Vector2D.dot(Vector2D.subtract(c1.position, p1.position), p1.normal);
				
				if(distance < c1.getRadius()) {
					return true;
				}
			}
			
			
		}
		return false;
	}
	
	public static void resolveCollision(RigidBody body1, RigidBody body2) {
		//apply correct things if there is collision between 2 bodies
		if(body1 instanceof Circle) {
			Circle c1 = (Circle)body1;
			if(body2 instanceof Circle) {
				//resolve collision between circle and circle
				Circle c2 = (Circle)body2;
				
				// get the collision normal by subtracting the position of the two circles
				Vector2D normal = Vector2D.getNormalized(Vector2D.subtract(c1.position, c2.position));
				
				// collision cares about relative speed so to get it subtract the velocity vectors
				Vector2D rel_speed = Vector2D.subtract(c1.velocity, c2.velocity);
				
				// get the velocity along the normal collision using dot product
				//(how much the rel_velocity vector points in the direction of the normal)
				double velocity_along_normal = Vector2D.dot(normal, rel_speed);	
				
				//check if objects are moving towards each other if not just return
				if(velocity_along_normal > 0) {
					return;
				}
				
				//calculate bounce
				double e =  Math.min(c1.getE(), c2.getE());
				
				double totalInverseMass = c1.getInverseMass() + c2.getInverseMass();
				if (totalInverseMass == 0) {
				    return; // Two static objects colliding? Do nothing.
				}
				
				double j = ( -(1 + e) * velocity_along_normal ) / totalInverseMass;
				
				
				
				c1.velocity.add(Vector2D.scale(Vector2D.scale(normal, j), c1.getInverseMass()));

				c2.velocity.subtract(Vector2D.scale(Vector2D.scale(normal, j), c2.getInverseMass()));
				
				Vector2D collisionVector = Vector2D.subtract(c1.position, c2.position);
				double distance = collisionVector.magnitude();

				// Safety: Avoid divide by zero if they are on exact same spot
				if (distance == 0.0) {
				    distance = 0.001; // Fake small distance
				    collisionVector = new Vector2D(0, 1);
				}

				// 2. Calculate Overlap
				double radiiSum = c1.getRadius() + c2.getRadius();
				double overlap = radiiSum - distance;

				// 3. Only correct if they are actually touching
				if (overlap > 0) {
				    
				    // NORMALIZE the collision vector to get direction
				    // (We reuse the vector we calculated in step 1)
				    Vector2D correctionDir = Vector2D.getNormalized(collisionVector);

				    // --- STABILITY TUNING ---
				    // Percent: Only correct 90% of the error to prevent jittering
				    // Slop: Allow objects to overlap by 0.01 pixels without snapping
				    double percent = 0.9; 
				    double slop = 0.01; 
				    
				    double correctionMagnitude = Math.max(overlap - slop, 0.0) / (c1.getInverseMass() + c2.getInverseMass()) * percent;
				    
				    Vector2D correction = Vector2D.scale(correctionDir, correctionMagnitude);
				    
				    // 4. Apply Correction (Weighted by Inverse Mass)
				    // C1 moves + (It is "collisionVector" away from C2)
				    Vector2D moveC1 = Vector2D.scale(correction, c1.getInverseMass());
				    c1.position.add(moveC1);
				    
				    // C2 moves - (Opposite direction)
				    Vector2D moveC2 = Vector2D.scale(correction, c2.getInverseMass());
				    c2.position.subtract(moveC2);
				}
				
				
			}
			if(body2 instanceof Plane2D) {
				//resolve collision between circle and plane
				Plane2D p1 = (Plane2D)body2;
				
				//need the calculations
				double velocity_along_normal = Vector2D.dot(p1.normal, c1.velocity);	
				
				//check if objects are moving towards each other if not just return
				if(velocity_along_normal > 0) {
					return;
				}
				
				//calculate bounce
				double e =  Math.min(c1.getE(), p1.getE());
				
				double totalInverseMass = c1.getInverseMass();
				if (totalInverseMass == 0) {
				    return; // Two static objects colliding? Do nothing.
				}
				
				double j = ( -(1 + e) * velocity_along_normal ) / totalInverseMass;
				
				
				
				c1.velocity.add(Vector2D.scale(Vector2D.scale(p1.normal, j), c1.getInverseMass()));
				
				Vector2D planeToCircle = Vector2D.subtract(c1.position, p1.position);

				// 2. Project that vector onto the Normal to find "Altitude"
				double distanceToPlane = Vector2D.dot(planeToCircle, p1.normal);

				// 3. Calculate Overlap (Radius - Altitude)
				double overlap = c1.getRadius() - distanceToPlane;

				// 4. Push the circle out if it's inside
				if (overlap > 0) {
				    // Push the circle along the normal by the overlap amount
				    Vector2D correction = Vector2D.scale(p1.normal, overlap);
				    c1.position.add(correction);
				}
				
			}
			
		}
		else if(body1 instanceof Plane2D) {
			Plane2D p1 = (Plane2D)body1;
			if(body2 instanceof Circle) {
				Circle c1 = (Circle)body2;
				
				//need the calculations
				double velocity_along_normal = Vector2D.dot(p1.normal, c1.velocity);	
				
				//check if objects are moving towards each other if not just return
				if(velocity_along_normal > 0) {
					return;
				}
				
				//calculate bounce
				double e =  Math.min(c1.getE(), p1.getE());
				
				double totalInverseMass = c1.getInverseMass();
				if (totalInverseMass == 0) {
				    return; // Two static objects colliding? Do nothing.
				}
				
				double j = ( -(1 + e) * velocity_along_normal ) / totalInverseMass;
				
				
				
				c1.velocity.add(Vector2D.scale(Vector2D.scale(p1.normal, j), c1.getInverseMass()));
				
				Vector2D planeToCircle = Vector2D.subtract(c1.position, p1.position);

				// 2. Project that vector onto the Normal to find "Altitude"
				double distanceToPlane = Vector2D.dot(planeToCircle, p1.normal);

				// 3. Calculate Overlap (Radius - Altitude)
				double overlap = c1.getRadius() - distanceToPlane;

				// 4. Push the circle out if it's inside
				if (overlap > 0) {
				    // Push the circle along the normal by the overlap amount
				    Vector2D correction = Vector2D.scale(p1.normal, overlap);
				    c1.position.add(correction);
				}
				
			}
		}
	}
	
}
