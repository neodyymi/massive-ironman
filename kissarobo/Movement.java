import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Move;
/**
 * Class for movement of the robot
 * @author Ville-Veikko Saari
 *
 */
public class Movement {
	private double speed; // Speed for differentialpilot
	private DifferentialPilot pilot;
	private double WHEEL_DIAMETER = 5.6; // Wheel diameter for differentialpilot
	private double TRACK_WIDTH = 11.8; // Track width for differentialpilot

	/**
	 * Constructor for class movement. This controls all the movement the robot
	 * does.
	 * 
	 * @param speed
	 */
	public Movement(double speed) {
		this.speed = speed;
		this.pilot = new DifferentialPilot(WHEEL_DIAMETER, TRACK_WIDTH,
				Motor.A, Motor.C);
		pilot.setTravelSpeed(this.speed);
		pilot.setRotateSpeed(this.speed * 10);
		// pilot.setAcceleration(10);
	}

	/**
	 * Moves forward for d measures
	 * 
	 * @param d
	 */
	public void forward(double d) {
		pilot.travel(d);
		LCD.drawString("Forward " + d, 1, 1);
	}

	/**
	 * moves forward until stopped
	 */
	public void forward() {
		pilot.forward();
	}

	/**
	 * moves backward for d measures
	 * 
	 * @param d
	 */
	public void backward(double d) {
		pilot.travel((-1) * d);
		LCD.drawString("Backward " + d, 1, 1);
	}

	/**
	 * moves backward until stopped
	 */
	public void backward() {
		pilot.backward();
	}

	/**
	 * rotates right for d degrees
	 * 
	 * @param d
	 */
	public void right(int d) {
		pilot.rotate((-1) * d);
	}

	/**
	 * rotates left for d degrees
	 * 
	 * @param d
	 */
	public void left(int d) {
		pilot.rotate(d);
	}

	/**
	 * rotates left until stopped
	 */
	public void left() {
		pilot.rotateLeft();
	}

	/**
	 * rotates right until stopped
	 * 
	 * @return
	 */
	public boolean isMoving() {
		return pilot.isMoving();
	}

	/**
	 * Stops all movement
	 */
	public void stop() {
		pilot.stop();
	}

	/**
	 * Resets motor tachometer readings
	 */
	public void reset() {
		pilot.reset();
	}

	/**
	 * Returns last movement distance
	 * 
	 * @return
	 */
	public float lastMove() {
		return pilot.getMovement().getDistanceTraveled();
	}
}
