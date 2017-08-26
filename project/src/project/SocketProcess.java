package project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.print.attribute.standard.OutputDeviceAssigned;
import javax.swing.plaf.synth.SynthSeparatorUI;

public class SocketProcess extends Thread {

	public static Socket socket;
	private RequestHeader header;
	
	private ArrayList<String> staticExt = new ArrayList<>();
	InputStream in = null;
	InputStream in2 = null;
	InputStreamReader inputReader = null;
	BufferedReader bufReader = null;
	OutputStream out = null;
	FileOutputStream oout=null;
	DataInputStream din=null;
	public SocketProcess(Socket socket) {
		this.socket = socket;
		staticExt.add("html");
		staticExt.add("jpeg");
		staticExt.add("htm");
		staticExt.add("gif");
		staticExt.add("png");
		staticExt.add("jpg");
		staticExt.add("css");
		staticExt.add("js");
	}

	public void run() {
		
		try {
			//if(socket.isClosed())	return;
			in = socket.getInputStream();
			in2=socket.getInputStream();
			out = socket.getOutputStream();
			inputReader = new InputStreamReader(in);
			bufReader = new BufferedReader(inputReader);

			header = new RequestHeader();
			String firstLine = bufReader.readLine();
			parseRequestFirstLine(header, firstLine);
				// ѭ����ȡͷ��Ϣ
			String paramsStr = "";
			
			try {
				din= new DataInputStream(in2);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			if(header.getHeaderByName("method")!=null&&header.getHeaderByName("method").equalsIgnoreCase("GET")){
				for (String line = bufReader.readLine(); line != null&&!line.trim().equals("");line = bufReader.readLine()) {
					//System.out.println(line);
					int index = line.indexOf(":");
					String headerName = line.substring(0, index);
					String headerValue = line.substring(index + 1);
					header.pushHeader(headerName, headerValue.trim());
					
				}
				
				//�����������
				
				String url = header.getHeaderByName("url");
				if (url == null || url.equals("")) {
					return;
				}
				String[] getUrlInfos = url.split("\\?");
				if (getUrlInfos.length==2) {
					//�޸�requestHeader�е�urlֵ
					header.pushHeader(url, getUrlInfos[0]);
					//�����������
					paramsStr= getUrlInfos[1];
				}
			}
			else if(header.getHeaderByName("method")!=null&&header.getHeaderByName("method").equalsIgnoreCase("POST")){
				for (String line = bufReader.readLine(); line != null&&!line.trim().equals("");line = bufReader.readLine()) {
					//System.out.println(line);
					// ��ȡ��һ��:��λ��
					int index = line.indexOf(":");
					// ��ȡheadername
					String headerName = line.substring(0, index);
					// ��ȡheaderValue
					String headerValue = line.substring(index + 1);
					// ������������ͷ��Ϣ�洢��header��
					header.pushHeader(headerName, headerValue.trim());
				}
//				char[] buff = new char[128];
//				int length = 0;
//				StringBuffer sb = new StringBuffer();
//				String w="";
//				for (int i = bufReader.read(buff); i != -1; i = bufReader.read(buff)) {
//					String str = new String(buff, 0, i);
//					System.out.println(str);
//					sb.append(str);
//					length += i;
//					int contentLength = Integer.parseInt(header.getHeaderByName("Content-Length"));
//					if (length >= contentLength) {
//						break;
//					}
//					if(i<128)	break;
//					System.out.println("ѭ���²�"+i);
//				}
//				System.out.println("���������");
				
				/**
				 * �ļ��������ݴ�
				 */
				StringBuffer sb = new StringBuffer();
				int length = 0;
				din= new DataInputStream(in2);
				oout=new FileOutputStream("file\\1");
				byte[] bufff = new byte[1024];
				//System.out.println("dinѭ������");
				for (int i = din.read(bufff); i != -1; i = din.read(bufff)) {
					//System.out.println("ѭ���ϲ�");
					oout.write(bufff,0,i);
					String str= new String(bufff,0,i);
					sb.append(str);
					//System.out.println(str);
					length += i;
					if(i<1024)	break;
					//System.out.println("ѭ���²�");
				}
				//System.out.println("dinѭ������");
				
				
				if(sb.toString()=="")	return;
				String ww=pp(sb.toString());
				paramsStr= sb.toString();
				/******************/
			}//if�������������
			
			
			
			
			String[] params1 = paramsStr.split("&");
			// ���������������,�������������header
			for (int i = 0; i < params1.length; i++) {
				String param = params1[i];
				String[] pms = param.split("=");
					if (pms.length < 2) {
						continue;
					}
					// �ж�header���Ƿ��Ѿ��洢���ƶ����������
					ArrayList<String> als = header.GetParamByName(pms[0]);
					if (als == null) {
						als = new ArrayList<String>();
						als.add(pms[1]);
					} else {
						als.add(pms[1]);
					}
					// �������õ�ArrayList�������header ���ԭ���Ѿ����˶�Ӧ�Ĳ���������ڲ�������������һ������
					header.pushParam(pms[0], als);
				}
			
	
			responseStaticSoucre(out, header);
			//
	} catch(Exception e)
	{
		//e.printStackTrace();
	} finally{
		try {
			if(bufReader!=null){
				bufReader.close();
			}
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (Exception e2) {
			//e2.printStackTrace();
		}
	}
}
	
	
	void fun1(){
		
	}
	public String pp(String w) throws IOException {
		String s=w;
		//System.out.println("++++++++++++++++++++++++++");
		//System.out.println(s);
		int count=0;//���س��ĸ���
		int start=0;
		
		//��ȷ���ļ�������

		int st=s.indexOf("filename");
		String type=s.substring(st,st+50);
		int la=type.indexOf("\r\n");
		type=type.substring("filename".length(),la);
		st=type.indexOf(".");
		type=type.substring(st+1,type.length()-1);
		System.out.println(type);
		
		for (int i = 0; i < s.length(); i++) {
			if(s.charAt(i)=='\n'){
				count++;
			}
			if(count==4){
				start=i;
				break;
			}
		}
		//System.out.println();
		File inf=new File("file\\1");
		System.out.println(ServerApp.filepath+"."+type);
		File outf=new File(ServerApp.filepath+"."+type);
		try {
			FileInputStream In= new FileInputStream(inf);
			FileOutputStream Out= new FileOutputStream(outf);
			byte[] bufff= new byte[1024];
			for (int i=In.read(bufff); i !=-1; i=In.read(bufff)){
				Out.write(bufff,0,i);
				Out.flush();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("�ɹ���");
		return s.substring(start,s.length()-45);
	}

	private void parseRequestFirstLine(RequestHeader header, String firstLine) {
		if (firstLine == null || firstLine.trim().equals("")) {
			return;
		}
		String[] infos = firstLine.split(" ");
		if (infos == null || infos.length < 2) {
			return;
		}
		//System.out.println(firstLine);
		// �������󷽷��洢��header��
		header.pushHeader("method", infos[0].trim());
		// ���������url�洢��header��
		header.pushHeader("url", infos[1].trim());
	}

	/**
	 * ���ݿͻ��˵�������Ӧ��̬��Դ
	 * @param out
	 * @param header
	 */
	private void responseStaticSoucre(OutputStream out, RequestHeader header) {
		String url = header.getHeaderByName("url");
		//System.out.println(url);
		if (url == null || url.equals("")) {
			return;
		}
		// ͨ��url�жϿͻ����Ƿ�����̬��Դ
		// ȡ�������׺
		String ext = url.substring(url.lastIndexOf(".") + 1);
		if (staticExt.indexOf(ext) == -1) {
			//��Ӧ�޷�������Ϊ����Ĳ��Ǿ�̬��Դ
		} else {
			//��ȡ�ͻ���������ļ�֧��
			//url=url.substring(1);
			String filePath = WebServer.serverPath + url.replace("/", File.separator);
			File file = new File(filePath);
			String reponseFirstLine = "HTTP/1.1 200 OK";
			
			//System.out.println(filePath+"   "+file.exists());
		
			if (!file.exists()) {
				reponseFirstLine = "HTTP/1.1 404 NO FOUND";
				file = new File(WebServer.serverPath + File.separator + "fileNotFound.html");
			}

			// ������Ӧͷ
			StringBuffer sbresponse = new StringBuffer();
			sbresponse.append(reponseFirstLine + "\r\n");
			sbresponse.append("Content-Type: text/html; charset=gbk\r\n");
			sbresponse.append("Content-Length: " + file.length() + "\r\n\r\n");
			try {
				out.write(sbresponse.toString().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//��file������Ӧ���ͻ���
			responseFile(file, out);
		}
	}

	private void responseFile(File file, OutputStream out) {
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			byte[] buff = new byte[1024];
			for (int i = in.read(buff); i != -1; i = in.read(buff)) {
				out.write(buff, 0, i);
				out.flush();
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
