import lejos.nxt.*;

/**
 * Class for reading the sensors of the robot
 * @author Ville-Veikko Saari
 *
 */
public class Sensors {
	private UltrasonicSensor distance; // Distance sensor
	private LightSensor light; // Light sensor

	/**
	 * Creates sensors
	 */
	public Sensors() {
		this.distance = new UltrasonicSensor(SensorPort.S4);
		this.light = new LightSensor(SensorPort.S1, true);
	}

	/**
	 * return current reading from distance sensor
	 * 
	 * @return
	 */
	public float getDistance() {
		float shortest = distance.getRange();
		// for(int i = 0; i < 2; i++) {
		// if(distance.getRange() < shortest) {
		// shortest = distance.getRange();
		// }
		// }
		return shortest;
	}

	/**
	 * returns current light value 0-100%
	 * 
	 * @return
	 */
	public int getLight() {
		return light.getLightValue();
	}

	/**
	 * returns current high light value
	 * 
	 * @return
	 */
	public int getHigh() {
		return light.getHigh();
	}

	/**
	 * return current low light value
	 * 
	 * @return
	 */
	public int getLow() {
		return light.getLow();
	}

	/**
	 * return normalized light value 0-1023
	 * 
	 * @return
	 */
	public int getLightReading() {
		return light.getNormalizedLightValue();
	}

	/**
	 * Used to set high light value from current light reading
	 */
	public void calibrateLightHigh() {
		light.setHigh(light.getNormalizedLightValue());
	}

	/**
	 * Used to set low light value from current light reading
	 */
	public void calibrateLightLow() {
		light.setLow(light.getNormalizedLightValue());
	}
}
