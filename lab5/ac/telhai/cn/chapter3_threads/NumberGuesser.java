package il.ac.telhai.cn.chapter3_threads;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.Reader;

public class NumberGuesser implements Runnable {
	private PipedInputStream myPis;
	private PipedOutputStream myPos;
	
	private Reader choosersFeedback;
	PrintStream myAnswers;
	
	public NumberGuesser (PipedInputStream choosersPis, PipedOutputStream choosersPos) throws IOException {
		myPis = new PipedInputStream(choosersPos);
		myPos = new PipedOutputStream(choosersPis);
		choosersFeedback = new InputStreamReader(myPis);
		myAnswers = new PrintStream(myPos);
	}

	@Override
	public void run() {
		int low = Integer.MIN_VALUE;
		int high = Integer.MAX_VALUE;
		int guess;
		char feedback='\0';
		do {
			guess = (int) (((double) low + (double) high)/2);
			myAnswers.println(guess);
			myAnswers.flush();
			try {
				feedback = (char) choosersFeedback.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
			switch (feedback) {
			case '<':
				low = guess+1;
				break;
			case '>':
                high = guess - 1;				
				break;
			default:
				break;
			}
		} while (feedback != '=');
	}

}
