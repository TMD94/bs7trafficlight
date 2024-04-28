package bs7trafficlight;

/**
 * Event object carries only the type of event.
 * @author admin
 *
 */
public class Event {
	/** all possible event types */
	public enum Types {
		TIMER, CALL, ON, OFF;
	}
	
	/** holds the type of the current object */
	private Types type;
	
	/**
	 * Only the type will be set
	 * @param type Event type of the object
	 */
	public Event(Types type) {
		this.type = type;
	}
	
	/**
	 * Getter of the type
	 * @return
	 */
	public Types getType() {
		return type;
	}
}
