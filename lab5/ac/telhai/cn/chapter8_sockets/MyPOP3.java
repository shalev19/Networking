package il.ac.telhai.cn.chapter8_sockets;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

public class MyPOP3 {
	private Session session;
	private Store store;


	public MyPOP3 (String host , String user, String pass) throws MessagingException {
		Properties properties = new Properties();
		properties.put("mail.pop3.host", host);
		properties.put("mail.pop3.port", "110");
		properties.put("mail.pop3.starttls.enable", "false");		
		session = Session.getDefaultInstance(properties);
		store = session.getStore("pop3");
		store.connect(host, user, pass);
	}

	public int getNumberOfMessages () throws MessagingException {
		Folder emailFolder = store.getFolder("INBOX");
		emailFolder.open(Folder.READ_ONLY);

		Message[] messages = emailFolder.getMessages();
		return messages.length;
	}

	public void close() throws MessagingException {
		store.close();
	}

}
