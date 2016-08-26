package org.qiang.ipcmsgprotolibbundle.service.user;

public interface IpcMsgProtoLib {
	public boolean init( int dev_id, String dev ) throws Exception;
	public byte[] cmd_ipc_lib( int dev_id, String cmd, byte[] data_byte, int data_len ) throws Exception;
	public boolean close( int dev_id ) throws Exception;
}