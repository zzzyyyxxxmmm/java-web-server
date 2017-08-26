package project;

import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class main {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	static JTextArea textArea = new JTextArea();
	private static main instance;
	private JTextField textField_2;
	
	/**
	 * Launch the application.
	 */
	
	public synchronized static main GetInstance(){
		if(instance==null)
			instance= new main();
		return instance;
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main window = new main();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}
		});
	}

	/**
	 * Create the application.
	 */
	public main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		WebServer ws =new WebServer();
		frame = new JFrame();
		frame.setBounds(100, 100, 522, 426);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 506, 133);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		final JButton btnNewButton = new JButton("开启服务器");
		btnNewButton.setBounds(10, 10, 105, 23);
		panel.add(btnNewButton);
		
		final JButton button = new JButton("停止服务器");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				startServer();
				btnNewButton.setEnabled(false);
				button.setEnabled(true);
			}
		});
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				stopServer();
				btnNewButton.setEnabled(true);
				button.setEnabled(false);
				
				
			}
		});
		button.setBounds(153, 10, 105, 23);
		panel.add(button);
		
		JButton button_1 = new JButton("关闭");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
				
			}
		});
		button_1.setBounds(291, 10, 105, 23);
		panel.add(button_1);
		
		JLabel label = new JLabel("端口设置");
		label.setBounds(20, 43, 54, 15);
		panel.add(label);
		
		textField = new JTextField();
		textField.setBounds(84, 40, 66, 21);
		panel.add(textField);
		textField.setColumns(10);
		textField.setText("8090");
		
		JButton btnNewButton_1 = new JButton("设置");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ServerApp.PORT=Integer.parseInt(textField.getText()) ;
				stopServer();
				startServer();
			}
		});
		btnNewButton_1.setBounds(153, 39, 105, 23);
		panel.add(btnNewButton_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(84, 71, 162, 21);
		panel.add(textField_1);
		textField_1.setColumns(10);
		textField_1.setText("webapp");
		
		JLabel label_1 = new JLabel("根目录");
		label_1.setBounds(20, 74, 54, 15);
		panel.add(label_1);
		
		JButton btnNewButton_2 = new JButton("浏览文件");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fc= new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				String path=null;
				File f= null;
				int flag=-1;
				try{
					flag=fc.showOpenDialog(null);
				}catch(HeadlessException head){
					System.out.println("OPEN FILE DIALOG ERROR");
				}
				if(flag==JFileChooser.APPROVE_OPTION){
					f=fc.getSelectedFile();
					path= f.getPath();
					System.out.println(path);
					int n=JOptionPane.showConfirmDialog(null, "更换目录？");
					if(n==0){
						textField_1.setText(path);
						ServerApp.serverPath=path;
						stopServer();
						startServer();
					}
				}
				
				
			}
		});
		btnNewButton_2.setBounds(291, 70, 105, 23);
		panel.add(btnNewButton_2);
		
		
		
		JLabel label_2 = new JLabel("文件存放目录");
		label_2.setBounds(20, 105, 95, 15);
		panel.add(label_2);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(119, 102, 162, 21);
		panel.add(textField_2);
		
		JButton button_2 = new JButton("浏览文件");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fc= new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				String path=null;
				File f= null;
				int flag=-1;
				try{
					flag=fc.showOpenDialog(null);
				}catch(HeadlessException head){
					System.out.println("OPEN FILE DIALOG ERROR");
				}
				if(flag==JFileChooser.APPROVE_OPTION){
					f=fc.getSelectedFile();
					path= f.getPath();
					System.out.println(path);
					int n=JOptionPane.showConfirmDialog(null, "更换目录？");
					if(n==0){
						textField_2.setText(path);
						ServerApp.filepath=path+"\\"+"zancun";
						stopServer();
						startServer();
					}
				}
			}
		});
		button_2.setBounds(313, 101, 105, 23);
		panel.add(button_2);
		
		textArea.setBounds(0, 134, 506, 284);
		frame.getContentPane().add(textArea);
		
		
	}
	
	static void add(String s){
		Date day= new Date();
		textArea.setText("[ "+day.toString()+" ] :"+s+"\r\n"+textArea.getText());
	}
	
	
	protected void startServer() {
		// TODO Auto-generated method stub
		WebServer.shutdown=false;
		try {
			new Thread(WebServer.GetInstance()).start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void stopServer(){
		WebServer.shutdown=true;
			//Socket socket= new Socket(InetAddress.getByAddress(ServerApp.address),ServerApp.PORT+1);
			try {
				WebServer.severSocket.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
}
