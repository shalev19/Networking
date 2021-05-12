package il.ac.telhai.cn.chapter8_sockets;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Date;
import java.text.DateFormat;
import java.util.Locale;

public class SMTP {
	
	 final int SMTP_PORT = 25;
	 	Socket client;
	    PrintStream os;
	    DataInputStream is;
	    
	public SMTP(String host) throws UnknownHostException, IOException {
		client = new Socket(host, SMTP_PORT);
		os = new PrintStream(client.getOutputStream());
	
	}

	public void sendMessage(String from, String to, String subject, String content) throws IOException {
	      System.out.println("hi0");

	    //if (client != null && os != null && is != null) {
	      os.println("MAIL FROM: " + from + " \n");
	      os.println("RCPT TO: " + to + "\n");
	      os.println("DATA\n");
	     // os.writeBytes("DATE: " + DateFormat.getDateInstance(DateFormat.FULL, Locale.US).format(new Date()) + "\r\n");
//	      os.writeBytes("From:" + from + "\r\n");
//	      os.writeBytes("To:" + to + "\r\n");
	    //}
	      System.out.println("hi1");
	    os.println("Subject: " + subject + "\n");
	    os.println(content + "\r\n");
	    os.println("\n.\n");
	    os.println("QUIT\n");
	    String responseline;
//	    while ((responseline = is.readUTF()) != null) { 
//	      if (responseline.indexOf("Ok") != -1)
//	        break;
//	    }		
	}

}