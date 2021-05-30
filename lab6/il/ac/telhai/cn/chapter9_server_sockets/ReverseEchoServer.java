/**
Shalev Yohanan
May 12, 2021
 */
package il.ac.telhai.cn.chapter9_server_sockets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class ReverseEchoServer implements Runnable {
	static final int PORT = 7030;

	public ReverseEchoServer() throws IOException {

	}

	public static int getPortNumber() {
		return PORT;
	}

	@Override
	public void run() {
		try(ServerSocket serverSocket = new ServerSocket( PORT )){
			while(true) {
				try(Socket connection = serverSocket.accept()){
					PrintWriter writer = new PrintWriter(connection.getOutputStream(), true);
					Scanner scanner = new Scanner(connection.getInputStream());
					Random rand = new Random();
					char c = (char) ( rand.nextInt(25) + 97 );
					writer.println( c );
					while(true) {
						String clientMsg = scanner.nextLine();
						if( clientMsg.charAt(0) != c ) writer.println("?");
						else if( clientMsg.charAt(0) == c && 
								( clientMsg.contains("QUIT") || clientMsg.contains("BYE") ||
										clientMsg.contains("EXIT") ) ) {
							writer.println("BYE BYE");
							break;
						}
						else {
							StringBuilder rev = new StringBuilder();
							rev.append(clientMsg);
							rev = rev.reverse();
							writer.println(rev);
							c = (char) ( rand.nextInt(25) + 97 );
							writer.println( c );
						}
					}
					scanner.close();
					writer.close();
				}catch(IOException ex) {
					ex.printStackTrace();
				}catch(NoSuchElementException ex) {
					
				}
			}
		}catch(IOException ex) {
			
		}
	}
	public static void main(String[] args) throws IOException {
		new ReverseEchoServer().run();
	}
}