package il.ac.telhai.cn.chapter8_sockets;

import static org.junit.Assert.*;

import org.junit.Test;

public class POP3Test {
	
	@Test
	public void test() throws Exception {
		MyPOP3 myPop3 = new MyPOP3("localhost", "cn", "CNPass123");
		POP3 pop3 = new POP3("localhost", "cn", "CNPass123");
		assertEquals(myPop3.getNumberOfMessages(), pop3.getNumberOfMessages());
		pop3.close();
		myPop3.close();
	}

}
