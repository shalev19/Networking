package il.ac.telhai.cn.chapter4_inetaddres;
import java.net.*;

public class InetAddressLab {

	static String getAddress(String hostName) throws UnknownHostException {
		StringBuilder sb = new StringBuilder();
		for (InetAddress address :	InetAddress.getAllByName(hostName)) {
			if (sb.length() > 0) sb.append(",");
			sb.append(address.getHostAddress());
		}
		return sb.toString();
	}
}
