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
import org.qiang.devicemngbundle.service.user.DeviceMng;
import org.qiang.ipcmsgprotolibbundle.service.user.IpcMsgProtoLib;

public class Login {
	
	private static int devID = 0;
	private static DeviceMng devMngService;
	
	private JFrame mainWin;
	private JPanel contentPane;
	
	public static final String gStrFont = "ËÎÌו";
	public static final int gStrFontSize = 12;
	
	private static JButton getSoftVerJB;
	private static JLabel resultJL;
	
	public Login( DeviceMng devMng, int dev_id ) {
		devMngService = devMng;
		devID = dev_id;
	}
	
	public boolean init() {
		
		System.out.println("Login init ...");
		
		mainWin = new JFrame();
		mainWin.setTitle("OSL3030 Test");
		//mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//mainWin.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainWin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainWin.setLayout(null);
		mainWin.setBounds(100, 100, 550, 500);
		mainWin.setVisible(true);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainWin.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton getSoftVerJB = new JButton("GetSoftVer");
		getSoftVerJB.setBounds(5, 95, 88, 23);
		getSoftVerJB.setFont(new java.awt.Font(gStrFont,0,gStrFontSize));
		contentPane.add(getSoftVerJB);
		
		resultJL = new JLabel("Result");
		resultJL.setBounds(100, 95, 300, 23);
		resultJL.setFont(new java.awt.Font(gStrFont,0,gStrFontSize));
		contentPane.add(resultJL);
		
		getSoftVerJB.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				StSoftwareVersion stSoftVer = new StSoftwareVersion( null );
				CommonSystem comSys = new CommonSystem( devMngService );
				stSoftVer = CommonSystem.get_software_ver_ipc_lib( devID, stSoftVer );
				String soft_ver = stSoftVer.getSoftVersion();
				if ( null == soft_ver ) {
					resultJL.setText( "" );
				} else {
					resultJL.setText( soft_ver );
				}
			}   
		});
		
		contentPane.repaint();
		
		return true;
	}
	
	public boolean close() {
		
		System.out.println("Login close ...");
		
		mainWin.dispose();
		return true;
	}
}