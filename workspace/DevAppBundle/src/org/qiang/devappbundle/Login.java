package org.qiang.devappbundle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.qiang.ipcmsgprotolibbundle.service.user.IpcMsgProtoLib;

public class Login {
	
	private static BundleContext context;
	private static ServiceReference service_ref;
	private static IpcMsgProtoLib ipc_lib;
	private static BaseTest base_test;
	
	public static final String gStrFont = "����";
	public static final int gStrFontSize = 12;
	
	private JFrame mainWin;
	private static JTextField userNameJT;
	private static JLabel userNameJL;
	private static JTextField passwdJT;
	private static JLabel passwdJL;
	private static JTextField ipaddrJT;
	private static JLabel ipaddrJL;
	private static JButton loginJB;
	private static JLabel resultJL;
	
	public Login( BundleContext bundle_context ) {
		context = bundle_context;
	}
	
	public static IpcMsgProtoLib getIpcMsgProtoLib() {
		return ipc_lib;
	}
	
	public boolean init() {
		
		System.out.println("Login init ...");
		
		mainWin = new JFrame();
		mainWin.setTitle("Test");
		//mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//mainWin.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainWin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainWin.setLayout(null);
		mainWin.setBounds(100, 100, 550, 500);
		mainWin.setVisible(true);
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainWin.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		userNameJL = new JLabel("UserName");
		userNameJL.setBounds(5, 5, 100, 23);
		userNameJL.setFont(new java.awt.Font(gStrFont,0,gStrFontSize));
		contentPane.add(userNameJL);
		
		userNameJT = new JTextField();
		userNameJT.setBounds(115, 5, 200, 23);
		userNameJT.setFont(new java.awt.Font(gStrFont,0,gStrFontSize));
		contentPane.add(userNameJT);
		
		passwdJL = new JLabel("Password");
		passwdJL.setBounds(5, 35, 100, 23);
		passwdJL.setFont(new java.awt.Font(gStrFont,0,gStrFontSize));
		contentPane.add(passwdJL);
		
		passwdJT = new JTextField();
		passwdJT.setBounds(115, 35, 200, 23);
		passwdJT.setFont(new java.awt.Font(gStrFont,0,gStrFontSize));
		contentPane.add(passwdJT);
		
		ipaddrJL = new JLabel("Device IP");
		ipaddrJL.setBounds(5, 65, 100, 23);
		ipaddrJL.setFont(new java.awt.Font(gStrFont,0,gStrFontSize));
		contentPane.add(ipaddrJL);
		
		ipaddrJT = new JTextField();
		ipaddrJT.setBounds(115, 65, 200, 23);
		ipaddrJT.setFont(new java.awt.Font(gStrFont,0,gStrFontSize));
		contentPane.add(ipaddrJT);
		
		resultJL = new JLabel("Result");
		resultJL.setBounds(100, 95, 300, 23);
		resultJL.setFont(new java.awt.Font(gStrFont,0,gStrFontSize));
		contentPane.add(resultJL);

		JButton loginJB = new JButton("Login");
		loginJB.setBounds(5, 95, 88, 23);
		loginJB.setFont(new java.awt.Font(gStrFont,0,gStrFontSize));
		contentPane.add(loginJB);
		
		loginJB.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				String username = userNameJT.getText();
				String password = passwdJT.getText();
				String deviceip = ipaddrJT.getText();
				
				System.out.println("Username  : " + username);
				System.out.println("Password  : " + password);
				System.out.println("Device IP : " + deviceip);
				
				service_ref = context.getServiceReference(IpcMsgProtoLib.class.getName());
				ipc_lib = (IpcMsgProtoLib)context.getService( service_ref );
				if( null == ipc_lib ) {
		            resultJL.setText("No usable IpcMsgProtoLib service");
		            return;
		        }
				
		        boolean result = false;
		        try {
		            result = ipc_lib.init( deviceip );
		            if(result)
		            {
		            	resultJL.setText("Login success");
		            	base_test = new BaseTest();
		            	base_test.init();
		            }
		            else
		            {
		            	resultJL.setText("Login fail");
		            }
		            return;
		        } catch ( Exception err ) {
		        	resultJL.setText("Login error");
		            return;
		        }
			}   
		});
		
		contentPane.repaint();
		
		return true;
	}
	
	public boolean close() {
		
		System.out.println("Login close ...");
		
		base_test.close();
		
		if ( null != ipc_lib ) {
			try {
				ipc_lib.close();
			} catch ( Exception err ) {
				err.printStackTrace();
	        }
		}
		
		mainWin.dispose();
		
		return true;
	}
}