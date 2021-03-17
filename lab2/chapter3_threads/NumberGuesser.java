/**
Shalev Yohanan
Mar 17, 2021
*/
package il.ac.telhai.cn.chapter3_threads;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Reader;

public class NumberGuesser implements Runnable {
	private PipedOutputStream po;
	private PipedInputStream pi;
	private int max = Integer.MAX_VALUE;
	private int min = 0;
	private int chosen;
	private int feedback;
	private Reader feedbackReader;
	private String str;
	
	public NumberGuesser(PipedInputStream pis, PipedOutputStream pos) throws IOException {
		pi = new PipedInputStream(pos);
		po = new PipedOutputStream(pis);
		feedbackReader = new InputStreamReader(pi);
	}

	
	@Override
	public void run() {
		int i=0;
		chosen = (Integer.MAX_VALUE/2);
		while(feedback != '=' && i<100) {
			try {
				writeGuess();
				feedback = feedbackReader.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
			checkFeedback(feedback);	
			i++;
		}
	}
	
	void writeGuess() throws IOException {  //write the number to the pipe in bytes array
		str = String.valueOf(chosen);
		str += '\n';
		po.write(str.getBytes());  
		po.flush();
	}
	
	void checkFeedback(int fb) {  //check the sign and do binary search
		if(fb == '>') {  //if my guess too big
			max = chosen;
			chosen = (max - min) / 2  + min;
			return;
		}
		if(fb == '<') {   //if my guess is too small
			min = chosen;
			chosen = (max - min) / 2 + min;
			return;
		}
		if(fb == '=') {
			return;
		}
		
		System.exit(0);  //else exit
	}

}
