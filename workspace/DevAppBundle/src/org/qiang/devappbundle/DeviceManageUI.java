package org.qiang.devappbundle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.qiang.devicemngbundle.service.user.DeviceMng;

class DeviceItemNode {
	int     dev_id;
	String  dev_ip;
	JButton delDevJB;
	JButton loginDevJB;
	JLabel  devIpJL;
}

public class DeviceManageUI {
	
	public static final String gStrFont = "ËÎÌו";
	public static final int gStrFontSize = 12;
	
	private JFrame mainWin;
	private JPanel contentPane;
	private static JButton addNewDevJB;
	private static JTextField newDevIpaddrJT;
	private int iCurDevXPos = 5;
	private int iCurDevYPos = 40;
	
	private static DeviceMng devMngService;
	
	List<DeviceItemNode> device_item_list = new ArrayList<DeviceItemNode>();
	
	public DeviceManageUI( DeviceMng devMng ) {
		devMngService = devMng;
	}
	
	private int get_device_item_dev_id( JButton loginDevJB ) {
		int i = 0;
		
		for ( i = 0; i < device_item_list.size(); i++ ) {
			if ( loginDevJB.equals( device_item_list.get( i ).loginDevJB ) ) {
				return device_item_list.get( i ).dev_id;
			}
		}
		
		return -1;
	}
	
	private boolean delete_one_device_item( JButton delDevJB ) {
		int i = 0;
		
		for ( i = 0; i < device_item_list.size(); i++ ) {
			if ( delDevJB.equals( device_item_list.get( i ).delDevJB ) ) {
				contentPane.remove( device_item_list.get( i ).delDevJB );
				contentPane.remove( device_item_list.get( i ).loginDevJB );
				contentPane.remove( device_item_list.get( i ).devIpJL );
				
				try {
					devMngService.delete_dev( device_item_list.get( i ).dev_id );
				} catch (Exception e) {
					
					device_item_list.remove( i );
					
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					return false;
				}
				
				device_item_list.remove( i );
			}
		}
		
		contentPane.repaint();
		
		return true;
	}
	
	private boolean delete_all_device_item() {
		int i = 0;
		
		for ( i = 0; i < device_item_list.size(); i++ ) {
			contentPane.remove( device_item_list.get( i ).delDevJB );
			contentPane.remove( device_item_list.get( i ).loginDevJB );
			contentPane.remove( device_item_list.get( i ).devIpJL );
			
			try {
				devMngService.delete_dev( device_item_list.get( i ).dev_id );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			device_item_list.remove( i );
			
			iCurDevYPos = iCurDevYPos - 35;
		}
		
		contentPane.repaint();
		
		return true;
	}
	
	private void repaint_all_device_item() {
		int i = 0;
		int x = 0, y = 0, w = 0, h = 0;
		
		iCurDevXPos = 5;
		iCurDevYPos = 40;
		for ( i = 0; i < device_item_list.size(); i++ ) {
			
			x = iCurDevXPos;
			y = iCurDevYPos;
			w = device_item_list.get( i ).delDevJB.getWidth();
			h = device_item_list.get( i ).delDevJB.getHeight();
			device_item_list.get( i ).delDevJB.setBounds( x, y, w, h );
			
			x = iCurDevXPos + 85;
			y = iCurDevYPos;
			w = device_item_list.get( i ).loginDevJB.getWidth();
			h = device_item_list.get( i ).loginDevJB.getHeight();
			device_item_list.get( i ).loginDevJB.setBounds( x, y, w, h );
			
			x = iCurDevXPos + 170;
			y = iCurDevYPos;
			w = device_item_list.get( i ).devIpJL.getWidth();
			h = device_item_list.get( i ).devIpJL.getHeight();
			device_item_list.get( i ).devIpJL.setBounds( x, y, w, h );
			
			iCurDevYPos = iCurDevYPos + 35;
		}
		
		contentPane.repaint();
	}
	
	private boolean add_new_device_item( String dev_ip ) {
		int dev_id = 0;
		final DeviceItemNode devItemTmp;
		
		try {
			dev_id = devMngService.init_dev( dev_ip );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if ( -1 == dev_id ) {
			System.err.println( "DeviceMng init device failed" );
			return false;
		}
		
		devItemTmp = new DeviceItemNode();
		devItemTmp.dev_id = dev_id;
		devItemTmp.dev_ip = dev_ip;
		
		devItemTmp.delDevJB = new JButton("Delete");
		devItemTmp.delDevJB.setBounds(iCurDevXPos, iCurDevYPos, 80, 30);
		devItemTmp.delDevJB.setFont(new java.awt.Font(gStrFont,0,gStrFontSize));
		contentPane.add(devItemTmp.delDevJB);
		
		devItemTmp.delDevJB.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				delete_one_device_item( devItemTmp.delDevJB );				
				repaint_all_device_item();
			}
		});
		
		devItemTmp.loginDevJB = new JButton("Login");
		devItemTmp.loginDevJB.setBounds(iCurDevXPos + 85, iCurDevYPos, 80, 30);
		devItemTmp.loginDevJB.setFont(new java.awt.Font(gStrFont,0,gStrFontSize));
		contentPane.add(devItemTmp.loginDevJB);
		
		devItemTmp.loginDevJB.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				int dev_id = get_device_item_dev_id( devItemTmp.loginDevJB );
				Login login = new Login( devMngService, dev_id );
				login.init();
			}
		});
		
		devItemTmp.devIpJL = new JLabel(devItemTmp.dev_ip);
		devItemTmp.devIpJL.setBounds(iCurDevXPos + 170, iCurDevYPos, 100, 30);
		devItemTmp.devIpJL.setFont(new java.awt.Font(gStrFont,0,gStrFontSize));
		contentPane.add(devItemTmp.devIpJL);
		
		contentPane.repaint();
		
		device_item_list.add( devItemTmp );
		
		iCurDevYPos = iCurDevYPos + 35;
		
		return true;
	}
	
	public boolean init() {
		
		System.out.println( "DeviceManageUI init ..." );
		
		mainWin = new JFrame();
		mainWin.setTitle("Device Manage Demo");
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
		
		addNewDevJB = new JButton("Add");
		addNewDevJB.setBounds(5, 5, 60, 30);
		addNewDevJB.setFont(new java.awt.Font(gStrFont,0,gStrFontSize));
		contentPane.add(addNewDevJB);
		
		addNewDevJB.addActionListener(new ActionListener() {   
			public void actionPerformed(ActionEvent e) {
				add_new_device_item( newDevIpaddrJT.getText() );
			}
		});
		
		newDevIpaddrJT = new JTextField();
		newDevIpaddrJT.setBounds(70, 5, 200, 30);
		newDevIpaddrJT.setFont(new java.awt.Font(gStrFont,0,gStrFontSize));
		contentPane.add(newDevIpaddrJT);
		
		contentPane.repaint();
		
		return true;
	}
	
	public boolean close() {
		
		delete_all_device_item();
		
		mainWin.dispose();
		
		return true;
	}
}