import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Sound;

public class Kissarobo {
	public static double liikuttu;
	public static boolean empty;
	public static float MAX_DISTANCE;

	public static void main(String[] args) throws Exception {
		liikuttu = 0;
		empty = true;
		Movement movement = new Movement(8);
		Sensors sensors = new Sensors();
		boolean move = false;
		movement.right(100);
		movement.left(100);
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
		while (true) {
			while (!Button.ESCAPE.isPressed() && !move && empty) {

				// LCD.clear();
				screen(sensors);

				// while (Button.LEFT.isPressed()) {
				// sensors.calibrateLightHigh();
				// }

				// while (Button.RIGHT.isPressed()) {
				// sensors.calibrateLightLow();
				// }
				while (Button.ENTER.isPressed()) {
					move = true;
					empty = false;
				}
				while (Button.ESCAPE.isPressed()) {
					return;
				}
			}
			if (search(sensors, movement)) {
				move = true;
			} else {
				move = false;
			}
			while (move) {
				// movement.reset();
				while (sensors.getLight() > 50) {
					movement.forward(2);
					liikuttu += 2;
					while (Button.ESCAPE.isPressed()) {
						move = false;
					}
				}
				// liikuttu = movement.lastMove();
				// movement.stop();
				screen(sensors);
				move = false;
				break;
			}
			// liikuttu *= 1.4;
			movement.backward(liikuttu);

			liikuttu = 0;
		}

		// while (!sensors.getTouch()) {
		// movement.forward();
		// }
	}

	private static void screen(Sensors sensors) {
		LCD.drawString("Light: " + sensors.getLight(), 1, 1);
		LCD.drawInt(sensors.getHigh(), 1, 2);
		LCD.drawInt(sensors.getLow(), 1, 3);
		LCD.drawInt(sensors.getLightReading(), 1, 4);
		LCD.drawString("M : " + liikuttu, 1, 5);
		LCD.drawString("Dist: " + sensors.getDistance() + " / " + MAX_DISTANCE, 1, 6);
	}

	private static boolean search(Sensors sensors, Movement movement) {
		boolean found = false;
		int size = 0;
		int turned = 0;
		float shortest = sensors.getDistance();
		while (turned < 400) {
			movement.left(5);
			turned += 5;
			// movement.left(400);
			// turned = 400;
			// while (movement.isMoving()) {
			// if (sensors.getDistance() < 40) {
			// turned = 0;
			// break;
			// }
			// }
			// if (turned < 400) {
			// movement.stop();
			while (sensors.getDistance() < MAX_DISTANCE) {
				float current = sensors.getDistance();
				if (shortest + 3 > current) {
					if (shortest - 3 > current) {
						size = 0;
					}
					shortest = sensors.getDistance();

					found = true;
					movement.left(5);
					size += 5;
				} else {
					break;
				}
				// Sound.setVolume(10);
				// Sound.buzz();
			}
			if (size < 2) {
				found = false;
				size = 0;
			}
			if (found == true) {
				movement.right(size / 2);
				return true;
			}
		}
		// if (found == true) {
		// for (int i = 0; i < size / 2; i++) {
		// movement.right(2);
		// return true;
		// }
		// }
		// }
		empty = true;
		return false;
	}
}
