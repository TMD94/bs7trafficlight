package bs7trafficlight;

/**
 * Generic timer class. The object runs in a separate thread. It can 
 * be reset while it is running.
 * @author Maik Aicher
 *
 */
public class TlTimer implements Runnable {
	/** time for possible update call checks */
	private long STEP_TIME = 10;
	
	/** true as long as the timer is running */
	private boolean timerRunning = false;
	
	/** Set waiting time in milliseconds */
	private long waitTimeInMs = 0;
	
	/** reference to the caller of the timer for informing the expiration */
	private TimerCallback caller;
	
	/**
	 * Constructor only takes the reference to the caller. The waitTimeInMs must
	 * be set seperateley.
	 * @param caller Reference to the caller for callbacks.
	 */
	public TlTimer(TimerCallback caller) {
		this.caller = caller;
	}
	
	/**
	 * Setter of waiting time. If set as long as the timer is running,
	 * the timer will continue to run for the wainting time from the 
	 * setter call.
	 * @param waitTimeInMs Waiting time for the timer
	 */
	public void setWaitTime(long waitTimeInMs) {
		this.waitTimeInMs = waitTimeInMs;
	}
	
	@Override
	public void run() {
		timerRunning = true;
		do { 
			try {
				// wait for a short time
				Thread.sleep(STEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// reduce the waiting time by the short step time
			// and check if the waiting time has expired
			if ((waitTimeInMs -= STEP_TIME) <= 0) {
				// if expired, set the timer running flag to false, so the
				// loop will end
				timerRunning = false;
			}
		} while (timerRunning);
		
		// inform caller that the timer has expired
		caller.timerExpired();
	}

	/**
	 * Getter of the running flag
	 * @return true if the timer is currently running
	 */
	public boolean timerIsRunning() {
		return timerRunning;
	}
}
