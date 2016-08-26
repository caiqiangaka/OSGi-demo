package org.qiang.devicemngbundle.service.user;

public interface DeviceMng {
	public int init_dev( String dev ) throws Exception;
	public byte[] dev_cmd_ipc_lib( int dev_id, String cmd, byte[] data_byte, int data_len ) throws Exception;
	public boolean delete_dev( int dev_id ) throws Exception;
	public boolean close() throws Exception;
}