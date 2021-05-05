package lu.uni.hyperloop;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Pod {
	Station destination;
	BlockingQueue<Passenger> passengers = new LinkedBlockingDeque<>();

	public Pod(Station destination) {
		this.destination = destination;
	}

	@Override
	public String toString() {
		return "Pod " + this.hashCode() + " [passengers=" + passengers.size() + "]";
	}
}