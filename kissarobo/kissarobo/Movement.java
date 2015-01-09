import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Move;

public class Movement {
	private double speed;
	private DifferentialPilot pilot;
	private double WHEEL_DIAMETER = 5.6;
	private double TRACK_WIDTH = 11.8;

	public Movement(double speed) {
		this.speed = speed;
		this.pilot = new DifferentialPilot(WHEEL_DIAMETER, TRACK_WIDTH,
				Motor.A, Motor.C);
		pilot.setTravelSpeed(this.speed);
		pilot.setRotateSpeed(this.speed*10);
//		pilot.setAcceleration(10);
	}

	public void forward(double d) {
		pilot.travel(d);
		LCD.drawString("Forward " + d, 1, 1);
	}

	public void forward() {
		pilot.forward();
	}

	public void backward(double d) {
		pilot.travel((-1) * d);
		LCD.drawString("Backward " + d, 1, 1);
	}

	public void backward() {
		pilot.backward();
	}

	public void right(int d) {
		pilot.rotate((-1) * d);
	}

	public void left(int d) {
		pilot.rotate(d);
	}
	
	public void left() {
		pilot.rotateLeft();
	}

	public boolean isMoving() {
		return pilot.isMoving();
	}
	
	public void stop() {
		pilot.stop();
	}
	
	public void reset() {
		pilot.reset();
	}
	
	public float lastMove() {
		return pilot.getMovement().getDistanceTraveled();
	}
}
