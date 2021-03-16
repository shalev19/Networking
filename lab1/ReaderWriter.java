package il.ac.telhai.cn.chapter2_io;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;



public class ReaderWriter {
	static byte[] b;
	static PipedInputStream inPipe;
	static PipedOutputStream outPipe;
	static  
	{  
		inPipe =  new PipedInputStream();
		outPipe = new PipedOutputStream();
		try {
			outPipe.connect(inPipe);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void writeToFile(String fileName, String arg) throws IOException {
		try { 

			// Open given file in append mode. 
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true), StandardCharsets.UTF_8)); 
			out.write(arg); 
			out.close(); 
		} 
		catch (IOException e) { 
			System.out.println("exception occoured" + e); 
		} 

	}

	public static String getBytesDecimalFromFile(String fileName) throws IOException {
		StringBuilder newStr = new StringBuilder();
		try (FileInputStream fis = new FileInputStream(fileName)) {

			int i; 
			while ((i = fis.read()) != -1) {
				if(newStr.length() > 0)
					newStr.append(',');
				newStr.append(i);
			}
		}  
		return newStr.toString();
	}

	public static void writeToByteArray(String arg) throws IOException {
		b = arg.getBytes(StandardCharsets.UTF_8);


	}

	public static String getBytesDecimalFromByteArray() throws IOException {
		StringBuilder newStr = new StringBuilder();


		for(int i=0; i<b.length; i++) {
			if(newStr.length() > 0)
				newStr.append(',');
			newStr.append(b[i]& 0xFF);
		}

		return newStr.toString();
	}

	public static void writeToPipe(String arg) throws IOException {
		b = null;
		writeToByteArray(arg);
		outPipe.write(b);
		outPipe.flush();

		return;
	}

	public static String getBytesDecimalFromPipe() throws IOException {
		StringBuilder newStr = new StringBuilder();

		try {
			int num =0;
			while (inPipe.available() != 0) {
				num = inPipe.read();
				if(newStr.length() > 0) {
					newStr.append(',');
				}
				newStr.append(num);
			}
			inPipe.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return newStr.toString();
	}
	
	public static void readIntoFile(String fileName, Scanner s) throws IOException {
		try { 

			// Open given file in append mode. 
			PrintWriter out = new PrintWriter(fileName, "UTF-8");
			int  n = 0;
			while((n = s.nextInt()) > 0) {
				out.print(n);
				out.print(" ");
			}
			out.close(); 
			return;
		} 
		catch (IOException e) { 
			System.out.println("exception occoured" + e); 
		} 
		
	}
	
	public static void readFromFiles(String[] args) {
		List<Integer> result = new ArrayList<Integer>();
		int filesNum = args.length;
		
		for(int i =0; i<filesNum; i++) {  //write to all the given files.
			try (Scanner s = new Scanner(new FileReader(args[i]))) {
			    while (s.hasNext()) {
			        result.add(s.nextInt());
			    }
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		Collections.sort(result);
		for(int i=0; i<result.size(); i++) {
			System.out.println(result.get(i));
		}
	}
	
	public static void main(String[] args) throws IOException {
		
		int filesCnt = args.length;
		Scanner s = new Scanner(System.in);
		
		if(filesCnt < 1) {  
			System.out.println("No files name were recived");
			System.exit(0);
		}
		for(int i =0; i<filesCnt; i++) {  //write to all the given files.
			readIntoFile(args[i],s);
		}
		readFromFiles(args);
		
	
		
		s.close();
		
	}
	

}