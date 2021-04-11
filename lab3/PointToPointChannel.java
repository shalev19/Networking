package il.ac.telhai.cn.basics;

public class PointToPointChannel extends Channel {
	public static final int BITS_PER_BYTE = 8;
	private int delay;
	private int bytesToSend;
	private int bytesDelivered;
	private int bytesInTransit;
	private int time;
	private int tempBandwidth;
	private int bytesPerSend;
	

	public PointToPointChannel(int bandwidth, int delay){
		super(bandwidth);
		this.delay = delay;
		tempBandwidth = getBandwidth();
		bytesPerSend = tempBandwidth/BITS_PER_BYTE;
	}

	public void send (int bytes) throws RuntimeException{
		if(bytesToSend != 0)
			throw new RuntimeException();

		bytesToSend = bytes;
		this.bytesDelivered = 0;
		this.bytesInTransit = 0;
		this.time = 0;
	}

	public void tick(int msecs){

		for(int i=0; i<msecs; i++) {
			if(bytesToSend > 0) {
				if(bytesToSend >= bytesPerSend) {
					bytesToSend -= bytesPerSend;
					bytesInTransit += tempBandwidth;
				}
				
				else if(bytesToSend > 0) {
					bytesInTransit += bytesToSend*BITS_PER_BYTE;
					bytesToSend  = 0;
				}
			}
			
			
			if(time >= delay) {
				if(bytesInTransit >= tempBandwidth) {
					bytesInTransit -= tempBandwidth;
					bytesDelivered += bytesPerSend;
				}
				else if(bytesInTransit > 0) {
					bytesDelivered += bytesInTransit/BITS_PER_BYTE;
					bytesInTransit = 0;
				}
			}
			time += 1;
		}
		
	}


	public int getDelay() {
		return delay;
	}

	public boolean isEmpty() {
		if(bytesToSend == 0 && bytesInTransit ==0)
			return true;

		return false;
	}

	public String toString() {
		return "Time Elapsed:" + time + " msecs, Input Buffer:" + bytesToSend + " bytes, In Transit:" + bytesInTransit + " bits, Delivered:" + bytesDelivered + " bytes";
	}

}