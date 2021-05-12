/**
Shalev Yohanan
May 5, 2021
*/
package il.ac.telhai.cn.chapter8_sockets;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.sun.mail.iap.ProtocolException;

public class POP3 {
	private static final int POP3_PORT = 110;
	private Scanner scanner;
	private PrintStream writer;
	private Socket socket;


	public POP3 (String host , String user, String pass) throws UnknownHostException, IOException, ProtocolException {
		socket = new Socket(host, POP3_PORT);
		scanner = new Scanner(socket.getInputStream());
		writer = new PrintStream(socket.getOutputStream());

		getOK();
		writer.println("USER " + user);
		getOK();
		writer.println("PASS " + pass);
		getOK();
	}

	private void getOK() throws ProtocolException {
		String line = scanner.nextLine();
		if (! line.startsWith("+OK")) throw new ProtocolException(line);		
	}
	
	public int getNumberOfMessages() {
		try {
			writer.println("LIST");
			System.out.println(scanner.nextLine());
			int count = 0;
			String line = "";
			while(true) {
				line = scanner.nextLine();
				if (line.equals(".")) {
					break;
				}
				count++;
			}
			
			
			return count;
			
		}catch(Exception e) {
			//System.out.println(e.toString());
			return 0;
		}
	}
	public void close() throws IOException, ProtocolException {
		writer.println("QUIT");
		getOK();
		writer.close();
		scanner.close();
		socket.close();

}
}
