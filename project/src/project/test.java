package project;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class test {
	
	public static void main(String[] args) throws IOException {
		File filein = new File("D:\\file\\1.rar");
		File fileout = new File("D:\\file\\2.rar");
		FileInputStream in=null;
		FileOutputStream out=null;
		
		FileReader ff= new FileReader(filein);
		BufferedReader bufReader = new BufferedReader(ff);
		char[] buff= new char[1024];
		String sum="";
		for (int i = bufReader.read(buff); i != -1; i = bufReader.read(buff)) {
		}
		
		try {
			in=new FileInputStream(filein);
			out= new FileOutputStream(fileout);

			DataInputStream din= new DataInputStream(in);
			byte[] bufff= new byte[1024];
				for (int i=din.read(bufff); i !=-1; i=din.read(bufff)){
					String str=new String(buff,0,i);
					System.out.println(str);
					out.write(bufff,0,i);
					out.flush();
					
				}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
