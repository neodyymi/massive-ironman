import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Sound;

public class Logic {
	public static double distance_moved; // Distance moved since this was last
	// reset.
	public static boolean empty; // Is designated area empty? / Did search find
	// something?
	public static float MAX_DISTANCE; // Maximum distance for UltrasonicSensor

	// to look for items at.

	public static void run() throws Exception {
		distance_moved = 0; // Distance moved is 0 before movement has happened.
		empty = true; // Area designated to be empty until something is found.
		Movement movement = new Movement(8);
		Sensors sensors = new Sensors();
		boolean move = false;
		movement.right(100);
		movement.left(100);
		calibrate(sensors);

		while (true) {
			while (!Button.ESCAPE.isPressed() && !move && empty) {
				// Waiting for ENTER to start searching,
				// or ESCAPE to stop program.

				screen(sensors);

				while (Button.ENTER.isPressed()) {
					move = true;
					empty = false;
				}
				while (Button.ESCAPE.isPressed()) {
					return;
				}
			}
			if (search(sensors, movement)) { // Searches for targets.
				move = true; // if found move = true;
			} else {
				move = false; // else move = false;
			}
			if (move) {
				while (sensors.getLight() > 50 && move) {
					// moves forward to push target off table.
					// Stops at edge of table or ESCAPE button press.
					movement.forward(2);
					distance_moved += 2; // Remembers how far it has moved.
					while (Button.ESCAPE.isPressed()) {
						move = false;
						break;
					}
				}
				screen(sensors);
				move = false;
			}
			movement.backward(distance_moved); // Move back to starting position

			distance_moved = 0;
		}
	}

	/**
	 * Asks for input from user to calibrate the sensors appropriately. First
	 * maximum scan distance. Secondly for a high light level position ie. on
	 * the table. Third for a low light level position ie. so that the light
	 * sensor is off the table edge Last it requests you to accept the settings
	 * with Enter, or re-do them with any other button.
	 * 
	 * @param sensors
	 *            Requires sensors as parameter.
	 */
	private static void calibrate(Sensors sensors) {
		int pressed = -1;
		while (pressed != Button.ENTER.getId()) {
			LCD.drawString("Set distance", 1, 1);
			Button.ENTER.waitForPressAndRelease();
			MAX_DISTANCE = sensors.getDistance();
			LCD.clear();
			LCD.drawString("Set high lightlevel", 1, 1);
			Button.ENTER.waitForPressAndRelease();
			sensors.calibrateLightHigh();
			LCD.clear();
			LCD.drawString("Set low lightlevel", 1, 1);
			Button.ENTER.waitForPressAndRelease();
			sensors.calibrateLightLow();
			LCD.clear();
			screen(sensors);
			pressed = Button.waitForPress();
		}
	}

	/**
	 * Generic screen information output. Displays information from sensors and
	 * movement on the LCD display.
	 * 
	 * @param sensors
	 *            Requires sensors as parameter
	 */
	private static void screen(Sensors sensors) {
		LCD.drawString("Light: " + sensors.getLight(), 1, 1);
		LCD.drawInt(sensors.getHigh(), 1, 2);
		LCD.drawInt(sensors.getLow(), 1, 3);
		LCD.drawInt(sensors.getLightReading(), 1, 4);
		LCD.drawString("M : " + distance_moved, 1, 5);
		LCD.drawString("Dist: " + sensors.getDistance() + " / " + MAX_DISTANCE,
				1, 6);
	}

	/**
	 * Scans area for next target. If two targets are too close, it will try to
	 * pick the one closest. If two targets are right next to each other, they
	 * will probably be dealt as one.
	 * 
	 * @param sensors
	 *            Requires sensors as parameter.
	 * @param movement
	 *            Requires movement as parameter
	 * @return True, if target was found, false, if not.
	 */
	private static boolean search(Sensors sensors, Movement movement) {
		boolean found = false;
		int size = 0;
		int turned = 0;
		float shortest = sensors.getDistance();
		while (turned < 400) {
			movement.left(5);
			turned += 5;
			while (sensors.getDistance() < MAX_DISTANCE) {
				float current = sensors.getDistance();
				if (shortest + 3 > current) {
					if (shortest - 3 > current) {
						size = 0;
					}
					shortest = sensors.getDistance();

					found = true;
					movement.left(4);
					size += 4;
				} else {
					break;
				}
				while (Button.ESCAPE.isPressed()) {
					return false;
				}
			}
			while (Button.ESCAPE.isPressed()) {
				return false;
			}
			if (size < 1) {
				found = false;
				size = 0;
			}
			if (found == true) {
				movement.right(size / 2);
				return true;
			}
		}
		empty = true;
		return false;
	}
}
