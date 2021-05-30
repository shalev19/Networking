package il.ac.telhai.cn.chapter8_sockets;

import static org.junit.Assert.*;

import org.junit.Test;

public class SMTPTest {

	private final String FROM = "cmshalom@cs.telhai.ac.il";
	private final String TO = "cn@cs.telhai.ac.il";
	private final String SUBJECT = "Hello from Mordo";
	private final String CONTENT = "This is a test";

	@Test
	public void test() throws Exception {
		MyPOP3 myPop3 = new MyPOP3("localhost", "cn", "CNPass123");
		int currentMessaages = myPop3.getNumberOfMessages(); 
		SMTP smtp = new SMTP("localhost");
		smtp.sendMessage(FROM, TO, SUBJECT, CONTENT);
		Thread.sleep(1000);
		assertTrue(myPop3.getNumberOfMessages() > currentMessaages);
		myPop3.close();
	}

}
