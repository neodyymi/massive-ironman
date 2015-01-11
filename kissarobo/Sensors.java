import lejos.nxt.*;

public class Sensors {
	private UltrasonicSensor distance;
	private TouchSensor touch;
	private LightSensor light;

	public Sensors() {
		this.distance = new UltrasonicSensor(SensorPort.S4);
		this.touch = new TouchSensor(SensorPort.S1);
		this.light = new LightSensor(SensorPort.S1, true);
		this.light.calibrateHigh();
	}

	public float getDistance() {
		float shortest = distance.getRange();
//		for(int i = 0; i < 2; i++) {
//			if(distance.getRange() < shortest) {
//				shortest = distance.getRange();
//			}
//		}
		return shortest;
	}

	public boolean getTouch() {
		return touch.isPressed();
	}

	public int getLight() {
		return light.getLightValue();
	}

	public int getHigh() {
		return light.getHigh();
	}

	public int getLow() {
		return light.getLow();
	}
	
	public int getLightReading() {
		return light.getNormalizedLightValue();
	}
	
	public void calibrateLightHigh() {
		light.setHigh(light.getNormalizedLightValue());
	}
	public void calibrateLightLow() {
		light.setLow(light.getNormalizedLightValue());
	}
}
