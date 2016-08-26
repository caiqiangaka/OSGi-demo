package org.qiang.devappbundle;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.qiang.devicemngbundle.service.user.DeviceMng;
import org.qiang.ipcmsgprotolibbundle.service.user.IpcMsgProtoLib;

class StSoftwareVersion {
	private int status;
	private byte[] soft_ver_byte;
	
	public StSoftwareVersion( String str_ver ) {
		byte[] tmp_ver;
		
		soft_ver_byte = new byte[32];
		Arrays.fill( soft_ver_byte, (byte)0 );
		
		if ( null != str_ver ) {
			tmp_ver = str_ver.getBytes();
			CommonSystem.arraycopy( tmp_ver, soft_ver_byte, str_ver.length() );
		}
	}
	
	public String getSoftVersion() {
		String soft_ver = "";
		try {
			soft_ver = new String( soft_ver_byte, "ascii" );
		} catch ( UnsupportedEncodingException err ) {
			err.printStackTrace();
		}
		
		System.out.println( "getSoftVersion soft_ver : " + soft_ver );
		
		return soft_ver;
	}
	
	public byte[] toSendByteData() {
		return soft_ver_byte;
	}
	
	public int getSendLen() {
		int len = 0;
		
		len = soft_ver_byte.length;
		
		return len;
	}
	
	public boolean dataPaser( byte[] data_byte ) {
		int i = 0;
		int result = data_byte[0] + data_byte[1] << 8 + data_byte[2] << 16 + data_byte[3] << 24;
		status = result;
		
		for ( i = 0; i < 32; i++ ) {
			soft_ver_byte[i] = data_byte[i + 4];
		}
		
		return true;
	}
}

public class CommonSystem {
	private static DeviceMng devMngService;
	
	public static final int GET_SOFTWARE_VER_HOST_CMD = 20;
	
	public CommonSystem( DeviceMng devMng ) {
		devMngService = devMng;
	}
	
	public static String commandToString( int cmd ) {
		byte[] cmd_byte = new byte[2];
		cmd_byte[0] = (byte)( ( cmd >> 8 ) & 0xff );
		cmd_byte[1] = (byte)( cmd & 0xff );
		
		return new String( cmd_byte );
	}
	
	public static boolean arraycopy( byte[] src, byte[] dest, int len ) {
		int i = 0;
		
		for ( i = 0; i < len; i++ ) {
			dest[i] = src[i];
		}
		
		return true;
	}
	
	public static StSoftwareVersion get_software_ver_ipc_lib( int dev_id, StSoftwareVersion stSoftVer ) {
		byte[] receiv_data_byte = new byte[0];
		
		try {
			receiv_data_byte = devMngService.dev_cmd_ipc_lib(
							dev_id,
							commandToString( GET_SOFTWARE_VER_HOST_CMD ), 
							stSoftVer.toSendByteData(), 
							stSoftVer.getSendLen() 
							);
		} catch ( Exception err ) {
			err.printStackTrace();
		}
		stSoftVer.dataPaser( receiv_data_byte );
		
		return stSoftVer;
	}
}