package tvos.multiscreen.impl;


import java.util.ArrayList;
import java.util.List;

import org.tvos.multiscreen.MultiScreenCallBack;
import org.tvos.multiscreen.ServiceProvideInfo;

import tvos.multiscreen.test.LogString;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

public class MultiScreenCallBackImpl extends  MultiScreenCallBack{
	private static final String TAG = "MULTISCREEN";
	private List<ServiceProvideInfo> infoList = new ArrayList<ServiceProvideInfo>();
	private ServiceProvideInfo connectedInfo = new ServiceProvideInfo();
	private MultiScreenCallBackListener mCBListner;
	
	public MultiScreenCallBackImpl(MultiScreenCallBackListener listener)
	{
		super(listener);
		mCBListner = listener;	
	}
	
	public List<ServiceProvideInfo> getServiceProvideList()
	{
		return infoList;
	}
	
	public ServiceProvideInfo getServiceProvideInfo()
	{
		return connectedInfo;
	}
	
	@Override
	public IBinder asBinder() {
		return this;
	}
	
	public String getInterfaceDescriptor() {
		return DESCRIPTOR;
	}
	
	public int onSpFounded(String spName, String spDeviceType, String spServiceInfo, String spVersion,String ipaddress, int port, String hostname) throws RemoteException
	{
		LogString.str="onSpFounded";
		Log.i("aa", "***s********::"+LogString.str);
		Log.i(TAG, "java MultiScreenCallBack onSpFounded "+ spName + "  , " +spDeviceType +" , "   +spServiceInfo +"  , " +ipaddress  +"  , " +port+"  , " +hostname   );
		ServiceProvideInfo info = new ServiceProvideInfo();
		info.spName = spName;
		info.spDeviceType = spDeviceType;
		info.spServiceInfo = spServiceInfo;
		info.spVersion = spVersion;
		info.ipaddress = ipaddress;
		info.port = port;
		info.hostname = hostname;
		
		boolean found = false;
		for(ServiceProvideInfo sInfo:infoList)
		{
			if(sInfo.ipaddress == ipaddress)
			{
				found = true;
				break;
			}
		}
		if(!found)
		{
			infoList.add(info);
		}
		
		return 0;
	}
	
	public int onConnected(String spName, String spDeviceType, String spServiceInfo, String spVersion,String ipaddress, int port, String hostname) throws RemoteException
	{
		LogString.str_Connect="onConnected";
		Log.i("aa", "************::"+LogString.str_Connect);
		Log.i(TAG, "java MultiScreenCallBack onConnected "+ spName + "  , " +spDeviceType +" , "   +spServiceInfo +"  , " +ipaddress  +"  , " +port+"  , " +hostname   );
		connectedInfo.spName = spName;
		connectedInfo.spDeviceType = spDeviceType;
		connectedInfo.spServiceInfo = spServiceInfo;
		connectedInfo.spVersion = spVersion;
		connectedInfo.ipaddress = ipaddress;
		connectedInfo.port = port;
		connectedInfo.hostname = hostname;
		
		return 0;
	}
	
	public int onServiceActivited(String ipaddress, int port, String hostname) throws RemoteException
	{
		LogString.str_Activit="onServiceActivited";
		Log.i(TAG, "java MultiScreenCallBack onServiceActivited "+ ipaddress + "  , " +port +" , "   +hostname);
		return 0;

	}
	
	public int onServiceDeactived(String ipaddress, int port, String hostname) throws RemoteException
	{
		LogString.str_Deactive="onServiceDeactived";
		Log.i(TAG, "java MultiScreenCallBack onServiceDeactived "+ ipaddress + "  , " +port +" , "   +hostname);
		return 0;
	}
	
	public int onQueryInfo(String ipaddress, int port, String hostname,String  id, String  attribute, String  param) throws RemoteException
	{
		LogString.str_Query="onQueryInfo";
		Log.i(TAG, "java MultiScreenCallBack onQueryInfo from "+ ipaddress + "  , " +port +" , "   +hostname +"  , " + id + "  , " + attribute + "  , " + param);
		return 0;
	}
	
	public int onQueryResponse(String ipaddress, int port, String hostname,String  id, String  attribute, String  param) throws RemoteException
	{
		LogString.str_Response="onQueryResponse";
		Log.i(TAG, "java MultiScreenCallBack onQueryResponse from"+ ipaddress + "  , " +port +" , "   +hostname +"  , " + id + "  , " + attribute + "  , " + param);
		if(mCBListner!=null)
		{
			mCBListner.onQueryResponse(id, attribute, param);
		}
		return 0;
	}
	
	public int onExecute(String ipaddress, int port, String hostname,String  cmd, String  param) throws RemoteException
	{
		LogString.str_Execute="onExecute";
		Log.i(TAG, "java MultiScreenCallBack onExecute from"+ ipaddress + "  , " +port +" , "   +hostname + "  , " + cmd + "  , " + param);
		return 0;
	}
	
	public int onInputKeyCode(String ipaddress, int port, String hostname,String  action, String  param) throws RemoteException
	{
		LogString.str_Input="onInputKeyCode";
		Log.i(TAG, "java onInputKeyCode onInputKeyCode from"+ ipaddress + "  , " +port +" , "   +hostname + "  , " + action + "  , " + param);
		return 0;
	}
	
	public int onNotify(String ipaddress, int port, String hostname,String  cmd, String  param) throws RemoteException
	{
		LogString.str_Notify="onNotify";
		Log.i("aa", "************::"+LogString.str_Notify);
		Log.i(TAG, "java onInputKeyCode onNotify from "+ ipaddress + "  , " +port +" , "   +hostname + "  , " + cmd + "  , " + param);
		return 0;
	}
	
	public int onConnectRefused(String spName, String spDeviceType, String spServiceInfo, String spVersion,String ipaddress, int port, String hostname)  throws RemoteException
	{
		LogString.str_Refuse="onConnectRefused";
		Log.i(TAG, "java MultiScreenCallBack onConnectRefused "+ spName + "  , " +spDeviceType +" , "   +spServiceInfo +"  , " +ipaddress  +"  , " +port+"  , " +hostname   );
		return 0;
	}
	
	public int onDisconnected(String spName, String spDeviceType, String spServiceInfo, String spVersion,String ipaddress, int port, String hostname) throws RemoteException
	{
		LogString.str_Disconnect="onDisconnected";
		Log.i(TAG, "java MultiScreenCallBack onDisconnected "+ spName + "  , " +spDeviceType +" , "   +spServiceInfo +"  , " +ipaddress  +"  , " +port+"  , " +hostname   );
		connectedInfo = null;
		connectedInfo = new ServiceProvideInfo();
		return 0;
	}
	
	
	public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
			throws RemoteException
	{
		LogString.str_Transact="onTransact";
		Log.i("aa", "********t****::"+LogString.str_Transact);
		Log.i(TAG, "java MultiScreenCallBack onTransact ");
		Log.i("aa", "********code****::"+code);
		switch(code)
		{
			case ON_SP_FOUNDED:
			{
				data.enforceInterface(DESCRIPTOR);
				Log.i(TAG, "java MultiScreenCallBack onTransac ON_SP_FOUNDED");
				String spName = data.readString();
				String spDeviceType = data.readString();
				String spServiceInfo = data.readString();
				String spVersionInfo = data.readString();
				String ipaddress = data.readString();
				int port = data.readInt();
				String hostname = data.readString();
				int _result = this.onSpFounded(spName, spDeviceType, spServiceInfo,spVersionInfo, ipaddress, port, hostname);
				Log.i(TAG, "onTransact ON_SP_FOUNDED writeNoException");
				reply.writeNoException();
				Log.i(TAG, "onTransact ON_SP_FOUNDED writeInt");
				reply.writeInt(_result);
				return _result ==0?true:false;
			}
			case ON_CONNECTED:
			{
				data.enforceInterface(DESCRIPTOR);
				Log.i(TAG, "java MultiScreenCallBack onTransac ON_CONNECTED");
				String spName = data.readString();
				String spDeviceType = data.readString();
				String spServiceInfo = data.readString();
				String spVersionInfo = data.readString();
				String ipaddress = data.readString();
				int port = data.readInt();
				String hostname = data.readString();
				int _result = this.onConnected(spName, spDeviceType, spServiceInfo,spVersionInfo, ipaddress, port, hostname);
				Log.i(TAG, "onTransact ON_CONNECTED writeNoException");
				reply.writeNoException();
				Log.i(TAG, "onTransact ON_CONNECTED writeInt");
				reply.writeInt(_result);
				return _result ==0?true:false;
			}
			case ON_CONNECTEDREFUSED:
			{
				data.enforceInterface(DESCRIPTOR);
				Log.i(TAG, "java MultiScreenCallBack onTransac ON_CONNECTEDREFUSED");
				String spName = data.readString();
				String spDeviceType = data.readString();
				String spServiceInfo = data.readString();
				String spVersionInfo = data.readString();
				String ipaddress = data.readString();
				int port = data.readInt();
				String hostname = data.readString();
				int _result = this.onConnectRefused(spName, spDeviceType, spServiceInfo, spVersionInfo, ipaddress, port, hostname);
				Log.i(TAG, "onTransact ON_CONNECTEDREFUSED writeNoException");
				reply.writeNoException();
				Log.i(TAG, "onTransact ON_CONNECTEDREFUSED writeInt");
				reply.writeInt(_result);
				return _result ==0?true:false;
			}
			case ON_DISCONNECTED:
			{
				data.enforceInterface(DESCRIPTOR);
				Log.i(TAG, "java MultiScreenCallBack onTransac ON_DISCONNECTED");
				String spName = data.readString();
				String spDeviceType = data.readString();
				String spServiceInfo = data.readString();
				String spVersionInfo = data.readString();
				String ipaddress = data.readString();
				int port = data.readInt();
				String hostname = data.readString();
				int _result = this.onDisconnected(spName, spDeviceType, spServiceInfo, spVersionInfo, ipaddress, port, hostname);
				Log.i(TAG, "onTransact ON_DISCONNECTED writeNoException");
				reply.writeNoException();
				Log.i(TAG, "onTransact ON_DISCONNECTED writeInt");
				reply.writeInt(_result);
				return _result ==0?true:false;
			}
			case ON_SERVICE_ACTIVITED:
			{
				data.enforceInterface(DESCRIPTOR);
				Log.i(TAG, "java MultiScreenCallBack onTransac ON_SERVICE_ACTIVITED");
				String ipaddress = data.readString();
				int port = data.readInt();
				String hostname = data.readString();
				int _result = this.onServiceActivited(ipaddress, port, hostname);
				Log.i(TAG, "onTransact ON_SERVICE_ACTIVITED writeNoException");
				reply.writeNoException();
				Log.i(TAG, "onTransact ON_SERVICE_ACTIVITED writeInt");
				reply.writeInt(_result);
				return _result ==0?true:false;
			}
			case ON_SERVICE_DEACTIVITED:
			{
				data.enforceInterface(DESCRIPTOR);
				Log.i(TAG, "java MultiScreenCallBack onTransac ON_SERVICE_DEACTIVITED");
				String ipaddress = data.readString();
				int port = data.readInt();
				String hostname = data.readString();
				int _result = this.onServiceDeactived(ipaddress, port, hostname);
				Log.i(TAG, "onTransact ON_SERVICE_DEACTIVITED writeNoException");
				reply.writeNoException();
				Log.i(TAG, "onTransact ON_SERVICE_DEACTIVITED writeInt");
				reply.writeInt(_result);
				return _result ==0?true:false;
			}
			case ON_QUERY_INFO:
			{
				data.enforceInterface(DESCRIPTOR);
				Log.i(TAG, "java MultiScreenCallBack onTransac ON_QUERY_INFO");
				String ipaddress = data.readString();
				int port = data.readInt();
				String hostname = data.readString();
				String id = data.readString();
				String attribute = data.readString();
				String param = data.readString();
				int _result = this.onQueryInfo(ipaddress, port, hostname, id, attribute, param);
				Log.i(TAG, "onTransact ON_QUERY_INFO writeNoException");
				reply.writeNoException();
				Log.i(TAG, "onTransact ON_QUERY_INFO writeResponse");
				String responseResult = "";
				if(mCBListner!=null)
				{
					responseResult = mCBListner.onQuery(id, attribute, param);
				}
				Log.i(TAG, "onTransact ON_QUERY_INFO writeString"+responseResult);
				reply.writeString(responseResult);
				return _result ==0?true:false; 
			}
			case ON_QUERY_RESPONSE:
			{
				data.enforceInterface(DESCRIPTOR);
				Log.i(TAG, "java MultiScreenCallBack onTransac ON_QUERY_RESPONSE");
				String ipaddress = data.readString();
				int port = data.readInt();
				String hostname = data.readString();
				String id = data.readString();
				String attribute = data.readString();
				String param = data.readString();
				int _result = this.onQueryResponse(ipaddress, port, hostname, id, attribute, param);
				Log.i(TAG, "onTransact ON_QUERY_RESPONSE writeNoException");
				reply.writeNoException();
				Log.i(TAG, "onTransact ON_QUERY_RESPONSE writeInt");
				reply.writeInt(_result);
				return _result ==0?true:false; 
			}
			case ON_EXECUTE:
			{
				data.enforceInterface(DESCRIPTOR);
				Log.i(TAG, "java MultiScreenCallBack onTransac ON_EXECUTE");
				String ipaddress = data.readString();
				int port = data.readInt();
				String hostname = data.readString();
				String cmd = data.readString();
				String param = data.readString();
				int _result = this.onExecute(ipaddress, port, hostname, cmd, param);
				Log.i(TAG, "onTransact ON_EXECUTE writeNoException");
				reply.writeNoException();
				Log.i(TAG, "onTransact ON_EXECUTE writeInt");
				reply.writeInt(_result);
				return _result ==0?true:false; 
			}
			case ON_INPUT:
			{
				data.enforceInterface(DESCRIPTOR);
				Log.i(TAG, "java MultiScreenCallBack onTransac ON_INPUT");
				String ipaddress = data.readString();
				int port = data.readInt();
				String hostname = data.readString();
				String action = data.readString();
				String param = data.readString();
				int _result = this.onInputKeyCode(ipaddress, port, hostname, action, param);
				Log.i("aa", _result+"---------------------ON_INPUT-----------------------");
				Log.i(TAG, "onTransact ON_INPUT writeNoException");
				reply.writeNoException();
				Log.i(TAG, "onTransact ON_INPUT writeInt");
				reply.writeInt(_result);
				return _result ==0?true:false; 
			}
			case ON_NOTIFY:
			{
				data.enforceInterface(DESCRIPTOR);
				Log.i(TAG, "java MultiScreenCallBack onTransac ON_NOTIFY");
				String ipaddress = data.readString();
				int port = data.readInt();
				String hostname = data.readString();
				String cmd = data.readString();
				String param = data.readString();
				int _result = this.onNotify(ipaddress, port, hostname, cmd, param);
				Log.i(TAG, "onTransact ON_NOTIFY writeNoException");
				reply.writeNoException();
				Log.i(TAG, "onTransact ON_NOTIFY writeInt");
				reply.writeInt(_result);
				return _result ==0?true:false; 
			}
			default:
				break;
		}
		 
		return super.onTransact(code, data, reply, flags);
	}

}
