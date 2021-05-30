package il.ac.telhai.cn.chapter8_sockets;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class POP3 {
	private static final int POP3_PORT = 110;
	private Scanner scanner;
	private PrintStream printStream;
	private Socket socket;

	public POP3 (String host , String user, String pass) throws UnknownHostException, IOException {
		socket = new Socket(host, POP3_PORT);
		scanner = new Scanner(socket.getInputStream());
		printStream = new PrintStream(socket.getOutputStream(),true);
		getOK();
		printStream.println("USER " + user);
		getOK();
		printStream.println("PASS " + pass);
		getOK();
	}

	private void getOK() throws ProtocolException {
		String line = scanner.nextLine();
		if (! line.startsWith("+OK")) throw new ProtocolException(line);		
	}
	
	public int getNumberOfMessages () throws ProtocolException {
		int i = 0;

		printStream.println("LIST");
		getOK();
		while (! scanner.nextLine().equals(".")) {
			i++;
		}
		return i;
	}
	
	public void close() throws IOException {
		printStream.println("QUIT");
		getOK();
		printStream.close();
		scanner.close();
		socket.close();
	}
}
