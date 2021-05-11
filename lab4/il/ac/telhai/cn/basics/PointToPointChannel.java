package il.ac.telhai.cn.basics;

public class PointToPointChannel extends Channel {
	public static final int BITS_PER_BYTE = 8;
	int delay;   // in msec
	int elapsed;
	int inputBuffer;  
	int inTransit;
	int delivered;
	
	public PointToPointChannel(int bandwidth, int delay) {
		super(bandwidth);
		this.delay = delay;
	}
	
	public void send (int bytes) {
		if (inputBuffer != 0) throw new IllegalStateException("Buffer Not Empty");
		inputBuffer = bytes;
		elapsed = 0;
		delivered = 0;
	}
	
	public void tick(int msecs) {
		int chunktoGet = Math.min(msecs * bandwidth, inputBuffer * BITS_PER_BYTE);
		int chunkToDeliver=0;
		if (msecs+elapsed > delay) {
			int timeToDeliver = Math.min(msecs, msecs + elapsed - delay);
			chunkToDeliver = Math.min(timeToDeliver*bandwidth, inTransit);
		}
		inputBuffer -= chunktoGet / BITS_PER_BYTE;
		delivered += chunkToDeliver / BITS_PER_BYTE;
		inTransit += (chunktoGet - chunkToDeliver);
		elapsed += msecs;
	}
	
	public String toString() {
		return "Time Elapsed:" + elapsed + " msecs, Input Buffer:" + inputBuffer + " bytes, In Transit:" + inTransit + " bits, Delivered:" + delivered + " bytes";
	}

	public int getDelay() {
		return delay;
	}

	public boolean isEmpty() {
		return (inputBuffer + inTransit) == 0;
	}

}
