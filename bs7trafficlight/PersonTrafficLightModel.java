package bs7trafficlight;

/**
 * Model of the pedestrian traffic light. It is only used for 
 * switching the different lights on or off.
 * @author Maik Aicher
 *
 */
public class PersonTrafficLightModel {
	/** set to true if the red light should be on */
	private boolean redOn = false;

	/** set to true if the green light should be on */
	private boolean greenOn = false;
	
	/**
	 * Setter of all three lights
	 * @param red True if the red light should be on
	 * @param green True if the green light should be on
	 */
	public void setLights(boolean red, boolean green) {
		redOn = red;
		greenOn = green;
	}

	/**
	 * Getter of the red light.
	 * @return True if the light should be on.
	 */
	public boolean isRedOn() {
		return redOn;
	}

	/**
	 * Getter of the green light.
	 * @return True if the light should be on.
	 */
	public boolean isGreenOn() {
		return greenOn;
	}
}
