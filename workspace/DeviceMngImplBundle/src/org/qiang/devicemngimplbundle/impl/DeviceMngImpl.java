package org.qiang.devicemngimplbundle.impl;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.qiang.devicemngbundle.service.user.DeviceMng;
import org.qiang.devicemngimplbundle.Activator;
import org.qiang.ipcmsgprotolibbundle.service.user.IpcMsgProtoLib;

class DeviceNode {
	int dev_id;
	String dev_ip;
}

public class DeviceMngImpl implements DeviceMng {
	
	BundleContext context;
	ServiceReference service_ref;
	private static IpcMsgProtoLib ipc_lib;
	List<DeviceNode> device_list = new ArrayList<DeviceNode>();
	private final int DefaultMaxDeviceNum = 50;
	
	private int alloc_device_id() {
		int i = 0;
		int dev_id = -1;
		int find_flag = 0;
		
		for ( dev_id = 1; dev_id <= DefaultMaxDeviceNum; dev_id++ ) {
			find_flag = 0;
			
			for ( i = 0; i < device_list.size(); i++ ) {
				if ( dev_id == device_list.get( i ).dev_id ) {
					find_flag = 1;
					break;
				}
			}
			
			if ( 0 == find_flag ) {
				return dev_id;
			}
		}
		
		return -1;
	}
	
	private boolean add_one_dev( int dev_id, String dev ) {
		DeviceNode devTmp;
		
		devTmp = new DeviceNode();
		devTmp.dev_id = dev_id;
		devTmp.dev_ip = dev;
		device_list.add( devTmp );
		
		return true;
	}
	
	private boolean delete_one_dev( int dev_id ) {
		int i = 0;
		
		for ( i = 0; i < device_list.size(); i++ ) {
			if ( dev_id == device_list.get( i ).dev_id ) {
				device_list.remove( i );
				return true;
			}
		}
		
		System.err.println( "Delete one device failed, device id : " + dev_id );
		
		return false;
	}
	
	private boolean delete_all_dev() {
		int i = 0;
		
		for ( i = 0; i < device_list.size(); i++ ) {
			device_list.remove( i );
		}
		
		return true;
	}
	
	public int init_dev( String dev ) throws Exception {
		int dev_id = 0;
		
		context = Activator.getContext();
		service_ref = context.getServiceReference( IpcMsgProtoLib.class.getName() );
		ipc_lib = (IpcMsgProtoLib)context.getService( service_ref );
		if( null == ipc_lib ) {
			System.err.println( "No usable IpcMsgProtoLib service" );
            return -1;
        }
		
		dev_id = alloc_device_id();
		if ( -1 == dev_id ) {
			System.err.println( "Alloc device id failed" );
            return -1;
		}
		
		add_one_dev( dev_id, dev );
		
		if ( false == ipc_lib.init( dev_id, dev ) ) {
			System.err.println( "IpcMsgProtoLib init failed, device id : " + dev_id );
			
			delete_one_dev( dev_id );
			
            return -1;
		}
		
		return dev_id;
	}
	
	public byte[] dev_cmd_ipc_lib( int dev_id, String cmd, byte[] data_byte, int data_len ) throws Exception
	{
		return ipc_lib.cmd_ipc_lib( dev_id, cmd, data_byte, data_len );
	}
	
	public boolean delete_dev( int dev_id ) throws Exception {
		
		if ( false == ipc_lib.close( dev_id ) ) {
			System.err.println( "IpcMsgProtoLib close failed, device id : " + dev_id );
		}
		
		delete_one_dev( dev_id );
		
		return true;
	}
	
	public boolean close() throws Exception {
		return delete_all_dev();
	}
}