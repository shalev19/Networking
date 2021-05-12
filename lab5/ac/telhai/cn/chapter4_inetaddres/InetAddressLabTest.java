package il.ac.telhai.cn.chapter4_inetaddres;

import static org.junit.Assert.*;


import org.junit.Test;

public class InetAddressLabTest {

	@Test
	public void testGetAllAddresses() {
		try {
			assertEquals("133.7.133.7", InetAddressLab.getAddress("cn.abziz.dev"));
		} catch (Exception e) {
			fail("Exception" + e);
		}
	}
}
