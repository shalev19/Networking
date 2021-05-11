/**
Shalev Yohanan
May 1, 2021
*/
package il.ac.telhai.cn.chapter4_inetaddres;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressLab {

	public static String getAddress(String string) {
		try {
			InetAddress ip = InetAddress.getByName(string);
			return ip.getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
