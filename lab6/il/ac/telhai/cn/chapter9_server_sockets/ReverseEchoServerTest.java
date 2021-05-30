package il.ac.telhai.cn.chapter9_server_sockets;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReverseEchoServerTest {

	private int portNumber;
	private Socket socket;
	private Thread serverThread;
	private PrintWriter writer;
	private Scanner scanner;

	@Before
	public void init () throws IOException, InterruptedException {
		startServer();
		startClient();
	}
	
	private void startServer() throws IOException, InterruptedException {
		portNumber = ReverseEchoServer.getPortNumber();
		serverThread = new Thread(new ReverseEchoServer());
		serverThread.start();
		Thread.sleep(300);		
	}
	
	private void startClient() throws IOException {
		socket = new Socket("localhost", portNumber);
		writer = new PrintWriter(socket.getOutputStream(), true);
		scanner = new Scanner(socket.getInputStream());
	}

	private void terminateServer() throws InterruptedException {
		serverThread.interrupt();
		serverThread.join(300);
	}
	
	private void terminateClient() throws IOException {
		if (socket != null) {
			writer.close();
			scanner.close();
			socket.close();
		}		
	}
	
	@After
	public void terminate() throws IOException, InterruptedException {
		terminateClient();
		terminateServer();
	}
	
	@Test
	public void testQuestionMark() {
		String s = scanner.nextLine();
		assertEquals(1, s.length());
	 	int prev = s.charAt(0) - 1;
		writer.println(Character.toString((char) prev)+"kuku");
		assertTrue(scanner.hasNextLine());
		assertEquals("?", scanner.nextLine());
	}

	@Test
	public void testNormal() {
		String s = scanner.nextLine();
		assertEquals(1, s.length());
		String sent = Character.toString(s.charAt(0))+"kuku";
		writer.println(sent);
		assertTrue(scanner.hasNextLine());
		assertEquals(new StringBuilder(sent).reverse().toString(), scanner.nextLine());
		assertTrue(scanner.hasNextLine());
	}
	

	private void testTermination(String terminator) throws IOException {
		String s = scanner.nextLine();
		assertEquals(1, s.length());
		String sent = Character.toString(s.charAt(0))+terminator;
		writer.println(sent);
		assertTrue(scanner.hasNextLine());
		assertEquals("BYE BYE", scanner.nextLine());
		assertFalse(scanner.hasNextLine());		
		terminateClient();
	}
	
	@Test
	public void testTermination() throws IOException {
		testTermination("BYE");
		startClient();
		testTermination("QUIT");
		startClient();
		testTermination("EXIT");
	}
	
	@Test
	public void testDisconnect() throws IOException {
		String s = scanner.nextLine();
		assertEquals(1, s.length());
		terminateClient();
		startClient();
		s = scanner.nextLine();
		assertEquals(1, s.length());
		terminateClient();
		startClient();
		s = scanner.nextLine();
		assertEquals(1, s.length());
	}

}
