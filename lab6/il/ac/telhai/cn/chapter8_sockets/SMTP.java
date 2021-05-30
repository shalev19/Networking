package il.ac.telhai.cn.chapter8_sockets;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SMTP {
	private static final int SMTP_PORT = 25;
	private Scanner scanner;
	private PrintStream printStream;
	private Socket socket;

	public SMTP (String host) throws UnknownHostException, IOException {
		socket = new Socket(host, SMTP_PORT);
		scanner = new Scanner(socket.getInputStream());
		printStream = new PrintStream(socket.getOutputStream(),true);
		get(220);
		printStream.println("HELO " + InetAddress.getLocalHost().getCanonicalHostName());
		get(250);
	}

	private void get(int num) throws ProtocolException {
		int readNumber = scanner.nextInt();
		if (readNumber != num) throw new ProtocolException(scanner.nextLine());		
		scanner.nextLine();
	}
	
	public void disconnect() throws IOException {
		printStream.println("QUIT");
		get(221);
		printStream.close();
		scanner.close();
		socket.close();
	}

	public void sendMessage(String from, String to, String subject, String content) throws ProtocolException {
		printStream.println("MAIL FROM: " + from);
		get(250);
		printStream.println("RCPT TO: " + to);
		get(250);
		printStream.println("DATA");
		scanner.nextLine();
		printStream.println("Subject: " + subject);
		printStream.println(content);
		printStream.println(".");
		get(250);
	}
}
