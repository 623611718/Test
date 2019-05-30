package tvos.multiscreen.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;














import org.tvos.multiscreen.IMultiScreenCallBack;
import org.tvos.multiscreen.MultiScreenCallBack.MultiScreenCallBackListener;
import org.tvos.multiscreen.MultiScreenService;
import org.tvos.multiscreen.ServiceProvideInfo;

import tvos.multiscreen.impl.MultiScreenCallBackImpl;
import android.annotation.SuppressLint;
import android.os.IBinder;
import android.os.Parcel;
import android.os.ServiceManager;
import android.test.AndroidTestCase;
import android.util.Log;
import android.webkit.WebView.FindListener;



@SuppressLint("NewApi") public class IMultiScreenCallBackTestAuto extends AndroidTestCase implements MultiScreenCallBackListener{
	public final static String FILE_RES = "/data/Test/data.txt";
	String str = "";
	File file = null;
	Date begindate;
	Date enddate;
	long s;
	int sum = 0;
	ArrayList timeDiffrence = new ArrayList();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSSS");
	Utils utils = new Utils();
	int lineNumber = 0;

	private static final String SERVICEDESCRIPTOR = "tvos.multiscreen";
	private IBinder mBinder;
	private static MultiScreenService mService;
	private static final String DID = android.os.Build.SERIAL;
	private MultiScreenCallBackImpl mCallBack = null;
	String mUUID = null;
	List<ServiceProvideInfo> mList = null;
	ServiceProvideInfo info = null;
	String spName = "Sev:" + DID;
	String spVersion = "0.0.1";
	String deviceType = "TV";
	String clientName = "Cli:" + DID;

	@Override
	protected void setUp() throws Exception {
		
		try {
			mBinder = ServiceManager.getService(SERVICEDESCRIPTOR);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mService = new MultiScreenService();
		mCallBack = new MultiScreenCallBackImpl(this);
		mUUID = UUID.randomUUID().toString();
		file = new File(FILE_RES);
		if (!file.exists()) {
			file.createNewFile();
		}
		super.setUp();
	}
	
	public void execCommand(String command) throws Exception {
        Runtime runtime = Runtime.getRuntime();
        Process proc = runtime.exec(command);
        //runtime.exec(command);
        Thread.sleep(3000);
        Log.i("tag", "link is down ");
        try {
            if (proc.waitFor() != 0) {
                System.err.println("exit value = " + proc.exitValue());
            }
          /*  BufferedReader in = new BufferedReader(new InputStreamReader(
                    proc.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String line = null;
            while ((line = in.readLine()) != null) {
                stringBuffer.append(line+"-");
            }
            System.out.println(stringBuffer.toString());
            Log.i("tag", stringBuffer.toString()+"-------------------testShell--------------------------");*/
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }

	private ServiceProvideInfo getInfo() throws Exception, InterruptedException {
		
		mService.setCallBack(mCallBack);
		mService.startMultiScreenServer("TVOS_MULTI_SCREEN", "TVOS", "Service Info", "1.0", "ip address", 33333, "Host Name");
        mService.startMultiScreenClient("Cli:" + DID);
        waitOnSpFound();
        return info;
        
	}

	public void calculatTiming(Date enddate, Date begindate)
			throws ParseException {
		s = sdf.parse(sdf.format(enddate)).getTime()
				- sdf.parse(sdf.format(begindate)).getTime();
		sum += s;
		timeDiffrence.add(s);
	}

	private void waitOnSpFound() throws Exception {
		int findSPs = mService.findSPs();
		Log.i("aa", "findSPs-->"+findSPs);
		mList = mCallBack.getServiceProvideList();
		int count=0;
		while(mList.size()==0&&count<10000){
			Thread.sleep(100);
			count+=100;
			Log.i("aa", "count-->"+count);
		}
		if (mList.size()!=0) {
			Log.i("aa", "mList.size()-->"+mList.size());
			info = mList.get(0);
			//Sev:unknown-TV-ServerService-0.0.1-192.168.2.57-35464-192.168.2.57
		}
	}
	
	private void waitOnConnected() throws Exception {
		
		int count=0;
		while(!"onConnected".equals(LogString.str_Connect)&&count<3000){
			Thread.sleep(100);
			count+=100;
			Log.i("aa", "waitOnConnected-->"+count);
		};
		
		Log.i("aa", "LogString.str_Connect-->"+LogString.str_Connect);
	}
	
	private void waitOnInputKeyCode() throws Exception {
		
		int count=0;
		while(!"onInputKeyCode".equals(LogString.str_Input)&&count<3000){
			Thread.sleep(100);
			count+=100;
			Log.i("aa", "waitOnInputKeyCode-->"+count);
		};
		
		Log.i("aa", "LogString.str_Input-->"+LogString.str_Input);
	}
	
	private void waitOnExecute() throws Exception {
		
		int count=0;
		while(!"onExecute".equals(LogString.str_Execute)&&count<3000){
			Thread.sleep(100);
			count+=100;
			Log.i("aa", "waitOnExecute-->"+count);
		};
		
		Log.i("aa", "LogString.str_Execute-->"+LogString.str_Execute);
	}
	
	private void waitOnNotify() throws Exception {
		
		int count=0;
		while(!"onNotify".equals(LogString.str_Notify)&&count<3000){
			Thread.sleep(100);
			count+=100;
			Log.i("aa", "waitOnNotify-->"+count);
		};
		
		Log.i("aa", "LogString.str_Notify-->"+LogString.str_Notify);
	}
	
	
	public void testIMultiScreenCallBack_onSpFounded_iNorm_010() throws Exception {
		String name = "testIMultiScreenCallBack_onSpFounded_iNorm_010";
		String method = "IMultiScreenCallBack.onSpFounded";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		mService.setCallBack(mCallBack);
		int ret1 = mService.startMultiScreenServer("TVOS_MULTI_SCREEN", "TVOS", "Service Info", "1.0", "ip address", 33333, "Host Name");
		int ret2 =mService.startMultiScreenClient("Cli:" + DID);
        waitOnSpFound();
        execCommand("busybox ifconfig eth0 dwon\n");
        Thread.sleep(3000);
		if ("onSpFounded".equals(LogString.str)) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		}else {
			str = utils.returnResultoFail(name, method, "false", "true", lineNumber);
		}
		mService.stopMultiScreenClient();
		mService.stopMultiScreenServer();
	}
	
	public void testIMultiScreenCallBack_onSpFounded_iAbnm_010() throws Exception {
		String name = "testIMultiScreenCallBack_onSpFounded_iAbnm_010";
		String method = "IMultiScreenCallBack.onSpFounded";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		mService.stopMultiScreenServer();
		mService.setCallBack(mCallBack);
        mService.startMultiScreenClient("Cli:" + DID);
        waitOnSpFound();
		if ("onSpFounded".equals(LogString.str)) {
			str = utils.returnResultoFail(name, method, "false", "true", lineNumber);
		}else {
			str = utils.returnResultofTrue(name, method, lineNumber);
		}
		mService.stopMultiScreenClient();
	}
	
	public void testIMultiScreenCallBack_onSpFounded_iPerf_010() throws Exception {
		String name = "testIMultiScreenCallBack_onSpFounded_iPerf_010";
		String method = "IMultiScreenCallBack.onSpFounded";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		mService.setCallBack(mCallBack);
		mService.startMultiScreenServer("TVOS_MULTI_SCREEN", "TVOS", "Service Info", "1.0", "ip address", 33333, "Host Name");
        mService.startMultiScreenClient("Cli:" + DID);
        for (int i = 0; i < 10; i++) {
			begindate = new Date();
			int ret = mService.findSPs();
			enddate = new Date();
			calculatTiming(enddate, begindate);
		}
        waitOnSpFound();
		if ("onSpFounded".equals(LogString.str)) {
			if (utils.isNormal(timeDiffrence)) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			}else {
				str = utils.returnResultoFail(name, method, utils.getINromDate(timeDiffrence)+"", "1000", lineNumber);
			}
		}else {
			str = utils.returnResultoFail(name, method,"false", "true", lineNumber);
		}
		mService.stopMultiScreenServer();
	}
	
	public void testIMultiScreenCallBack_onSpFounded_iPress_010() throws Exception {
		String name = "testIMultiScreenCallBack_onSpFounded_iPress_010";
		String method = "IMultiScreenCallBack.onSpFounded";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		mService.setCallBack(mCallBack);
		mService.startMultiScreenServer("TVOS_MULTI_SCREEN", "TVOS", "Service Info", "1.0", "ip address", 33333, "Host Name");
        mService.startMultiScreenClient("Cli:" + DID);
        for (int i = 0; i < 1000; i++) {
			begindate = new Date();
			int ret = mService.findSPs();
			enddate = new Date();
			calculatTiming(enddate, begindate);
		}
        waitOnSpFound();
		if ("onSpFounded".equals(LogString.str)) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		}else {
			str = utils.returnResultoFail(name, method, "false", "true", lineNumber);
		}
		mService.stopMultiScreenServer();
	}

	
	public void testIMultiScreenCallBack_onConnected_iNorm_010() throws Exception {
		String name = "testIMultiScreenCallBack_onConnected_iNorm_010";
		String method = "IMultiScreenCallBack.onConnected";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
		if (info!=null) {
			mService.startMultiScreenServer("TVOS_MULTI_SCREEN", "TVOS", "Service Info", "1.0", "ip address", 33333, "Host Name");
			mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,
					info.ipaddress,info.port,info.hostname);
			mService.setCallBack(mCallBack);
			int status = mCallBack.onConnected(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,
					info.ipaddress,info.port,info.hostname);
			Thread.sleep(6000);
			Log.i("dsa","LogString.str_Connect  "+LogString.str_Connect+"  "+"status1  "+status);
			if (status == 0) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			}else {
				str = utils.returnResultoFail(name, method, "false", "true", lineNumber);
			}
		}else {
			str = utils.returnResultoFail(name, method, "null", "info", lineNumber);
			
		}
		mService.stopMultiScreenServer();

	}
	
	public void testIMultiScreenCallBack_onConnected_iAbnm_010() throws Exception {
		String name = "testIMultiScreenCallBack_onConnected_iAbnm_010";
		String method = "IMultiScreenCallBack.onConnected";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
		if (info!=null) {
			mService.stopMultiScreenServer();
			mService.stopMultiScreenClient();
			int status =mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,
					info.ipaddress,info.port,info.hostname);
			Thread.sleep(6000);
			if (status ==0) {
				str = utils.returnResultoFail(name, method, "false", "true", lineNumber);
			}else {
				str = utils.returnResultofTrue(name, method, lineNumber);
			}
		}else {
			str = utils.returnResultoFail(name, method, "null", "info", lineNumber);
			
		}
		
	}
	
	
	public void testIMultiScreenCallBack_onConnected_iPerf_010() throws Exception {
		String name = "testIMultiScreenCallBack_onConnected_iPerf_010";
		String method = "IMultiScreenCallBack.onConnected";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
		if (info!=null) {
			for (int i = 0; i < 10; i++) {
        		begindate = new Date();
        		int status =mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,
    					info.ipaddress,info.port,info.hostname);
        		enddate = new Date();
        		calculatTiming(enddate, begindate);
        	}
			Thread.sleep(6000);
			if ("onConnected".equals(LogString.str_Connect)) {
        		if (utils.isNormal(timeDiffrence)) {						
        			str = utils.returnResultofTrue(name, method, lineNumber);
        		}else {
        			str = utils.returnResultoFail(name, method, utils.getINromDate(timeDiffrence)+"", "1000", lineNumber);
        		}
        	}else {
        		str = utils.returnResultoFail(name, method, "false", "true", lineNumber);
        	}
		}else {
			str = utils.returnResultoFail(name, method, "null", "info", lineNumber);
			
		}
		mService.stopMultiScreenServer();
		
	}
	
	public void testIMultiScreenCallBack_onConnected_iPress_010() throws Exception{
		String name = "testIMultiScreenCallBack_onConnected_iPress_010";
		String method = "IMultiScreenCallBack.onConnected";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
        if (info!=null) {
        	for (int i = 0; i < 1000; i++) {
        		int status =mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,
        				info.ipaddress,info.port,info.hostname);
        	}
        	Thread.sleep(6000);
        	if ("onConnected".equals(LogString.str_Connect)) {
        		str = utils.returnResultofTrue(name, method, lineNumber);
        	}else {
        		str = utils.returnResultoFail(name, method, "false", "true", lineNumber);
        	}
		}else {
			str = utils.returnResultoFail(name, method, "null", "info", lineNumber);
		}
		mService.stopMultiScreenServer();

	}
	
	
	public void testIMultiScreenCallBack_onConnectRefused_iNorm_010() throws Exception {
		String name = "testIMultiScreenCallBack_onConnectRefused_iNorm_010";
		String method = "IMultiScreenCallBack.onConnectRefused";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
		if (info!=null) {
			mService.startMultiScreenServer("TVOS_MULTI_SCREEN", "TVOS", "Service Info", "1.0", "ip address", 33333, "Host Name");
			int status =mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,
					info.ipaddress,info.port,info.hostname);
			if (status == 0) {			
				mService.setCallBack(mCallBack);
				int result =mCallBack.onConnectRefused(info.spName, info.spDeviceType,info.spServiceInfo,info.spVersion,info.ipaddress,info.port,info.hostname);
				if (result==0) {
					str = utils.returnResultofTrue(name, method, lineNumber);
				}else {
					str = utils.returnResultoFail(name, method, "-1", "0", lineNumber);
				}
			}else {
				str = utils.returnResultoFail(name, method, "-1", "0", lineNumber);
			}
		} else {
			str = utils.returnResultoFail(name, method,  "null", "info", lineNumber);
		}
	}
	
	public void testIMultiScreenCallBack_onConnectRefused_iAbnm_010() throws Exception {
		String name = "testIMultiScreenCallBack_onConnectRefused_iAbnm_010";
		String method = "IMultiScreenCallBack.onConnectRefused";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
        if (info!=null) {
        	int status =mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,
        			info.ipaddress,info.port,info.hostname);
        	if ("onConnectRefused".equals(LogString.str_Refuse)) {
        		str = utils.returnResultoFail(name, method, "false", "true", lineNumber);
        	}else {
        		str = utils.returnResultofTrue(name, method, lineNumber);
        	}
		}else {
			str = utils.returnResultoFail(name, method, "null", "info", lineNumber);
		}
		mService.stopMultiScreenServer();
		
	}
	
	

	public void testIMultiScreenCallBack_onConnectRefused_iPerf_010() throws Exception {
		String name = "testIMultiScreenCallBack_onConnectRefused_iPerf_010";
		String method = "IMultiScreenCallBack.onConnectRefused";
		getInfo();
		if (info!=null) {
			int status =mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,
					info.ipaddress,info.port,info.hostname);
			mService.setCallBack(mCallBack);
			mService.stopMultiScreenServer();
			int result = -1;
			for (int i = 0; i <10; i++) {
				begindate = new Date();
				result =mCallBack.onConnectRefused(info.spName, info.spDeviceType,info.spServiceInfo,info.spVersion,info.ipaddress,info.port,info.hostname);
				lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
				enddate = new Date();
				calculatTiming(enddate, begindate);
			}
			if (result==0) {
				if (utils.isNormal(timeDiffrence)) {						
					str = utils.returnResultofTrue(name, method, lineNumber);
				}else {
					str = utils.returnResultoFail(name, method, utils.getINromDate(timeDiffrence)+"", "1000", lineNumber);
				}
			}else {
				str = utils.returnResultoFail(name, method, "-1", "0", lineNumber);
			}
		} else {
			str = utils.returnResultoFail(name, method, "null", "info", lineNumber);
		}
	}

	public void testIMultiScreenCallBack_onConnectRefused_iPress_010() throws Exception {
		String name = "testIMultiScreenCallBack_onConnectRefused_iPress_010";
		String method = "IMultiScreenCallBack.onConnectRefused";
		getInfo();
		if (info!=null) {
			int status =mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,
					info.ipaddress,info.port,info.hostname);
			if (status == 0) {			
				int result = -1;
				mService.setCallBack(mCallBack);
				mService.stopMultiScreenServer();
				for (int i = 0; i <1000; i++) {
					result =mCallBack.onConnectRefused(info.spName, info.spDeviceType,info.spServiceInfo,info.spVersion,info.ipaddress,info.port,info.hostname);
					lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
				}
				if (result==0) {			
					str = utils.returnResultofTrue(name, method, lineNumber);		
				}else {
					str = utils.returnResultoFail(name, method, "-1", "0", lineNumber);
				}
			}else {
				str = utils.returnResultoFail(name, method, "null", "info", lineNumber);
			}
		} else {
			str = utils.returnResultoFail(name, method, "0", "1", lineNumber);
		}
	}
	
	
	public void testIMultiScreenCallBack_onDisconnected_iNorm_010() throws Exception {
		String name = "testIMultiScreenCallBack_onDisconnected_iNorm_010";
		String method = "IMultiScreenCallBack.onDisconnected";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		info = getInfo();
		if (info!=null) {
			mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,
					info.ipaddress,info.port,info.hostname);		
			mService.stopMultiScreenClient();
			mService.setCallBack(mCallBack);
			int status = mCallBack.onDisconnected(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,
					info.ipaddress,info.port,info.hostname);	
			//Thread.sleep(60000);
			Log.i("dsa","status onDisconnected_iNorm_010  "+status);
			if (status  == 0) {
				str = utils.returnResultofTrue(name, method, lineNumber);
				LogString.str_Disconnect = "";
			}else {
				str = utils.returnResultoFail(name, method, "onDisconnectedSuccess", "onDisconnectFiled", lineNumber);
			}
		} else {
			str = utils.returnResultoFail(name, method,"null", "info", lineNumber);
		}
	}

	public void testIMultiScreenCallBack_onDisconnected_iAbnm_010() throws Exception, InterruptedException {
		String name = "testIMultiScreenCallBack_onDisconnected_iAbnm_010";
		String method = "IMultiScreenCallBack.onDisconnected";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
		if (info!=null) {
			//mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,info.ipaddress,info.port,info.hostname);
			//Thread.sleep(6000);
			mService.stopMultiScreenClient();
			mService.stopMultiScreenServer();
			mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,
					info.ipaddress,info.port,info.hostname);		
			mService.stopMultiScreenClient();
			mService.setCallBack(mCallBack);
			int status = mCallBack.onDisconnected(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,
					info.ipaddress,info.port,info.hostname);
			if (status ==0) {
				str = utils.returnResultoFail(name, method, "false", "true", lineNumber);
			}else {
				str = utils.returnResultofTrue(name, method, lineNumber);
			}
		}else{
			str = utils.returnResultoFail(name, method, "null", "info ", lineNumber);
		}
		mService.stopMultiScreenServer();
		
	}
	
	public void testIMultiScreenCallBack_onDisconnected_iPerf_010() throws Exception {
		String name = "testIMultiScreenCallBack_onDisconnected_iPerf_010";
		String method = "IMultiScreenCallBack.onDisconnected";
		getInfo();
		if (info!=null) {
			mService.startMultiScreenServer("TVOS_MULTI_SCREEN", "TVOS", "Service Info", "1.0", "ip address", 33333, "Host Name");
			mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,info.ipaddress,info.port,info.hostname);
			mService.stopMultiScreenServer();
			Thread.sleep(3000);
			int result = -1;
			for (int i = 0; i < 10; i++) {
				begindate = new Date();
				result = mCallBack.onDisconnected(info.spName, info.spDeviceType,info.spServiceInfo,info.spVersion,info.ipaddress,info.port,info.hostname);
				lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
				enddate = new Date();
				calculatTiming(enddate, begindate);
			}
			if (result==0) {
				if (utils.isNormal(timeDiffrence)) {					
					str = utils.returnResultofTrue(name, method, lineNumber);
				}else {
					str = utils.returnResultoFail(name, method, utils.getINromDate(timeDiffrence)+"", "1000", lineNumber);
				}
			}else {
				str = utils.returnResultoFail(name, method, "-1", "0", lineNumber);
			}
		} else {
			str = utils.returnResultoFail(name, method,"null", "info", lineNumber);
		}
	}
	
	public void testIMultiScreenCallBack_onDisconnected_iPress_010() throws Exception {
		String name = "testIMultiScreenCallBack_onDisconnected_iPress_010";
		String method = "IMultiScreenCallBack.onDisconnected";
		getInfo();
		if (info!=null) {
			mService.startMultiScreenServer("TVOS_MULTI_SCREEN", "TVOS", "Service Info", "1.0", "ip address", 33333, "Host Name");
			mService.stopMultiScreenServer();
			Thread.sleep(3000);
			mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,info.ipaddress,info.port,info.hostname);
			int result = -1;
			for (int i = 0; i < 1000; i++) {
				result = mCallBack.onDisconnected(info.spName, info.spDeviceType,info.spServiceInfo,info.spVersion,info.ipaddress,info.port,info.hostname);
				lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
			}
			if (result==0) {					
				str = utils.returnResultofTrue(name, method, lineNumber);
			}else {
				str = utils.returnResultoFail(name, method, "-1", "0", lineNumber);
			}
		} else {
			str = utils.returnResultoFail(name, method,"null", "info", lineNumber);
		}
	}
	
	
	public void testIMultiScreenCallBack_onServiceActivited_iNorm_010() throws Exception {
		String name = "testIMultiScreenCallBack_onServiceActivited_iNorm_010";
		String method = "IMultiScreenCallBack.onServiceActivited";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
		if (info!=null) {
			mService.startMultiScreenServer("TVOS_MULTI_SCREEN", "TVOS", "Service Info", "1.0", "ip address", 33333, "Host Name");
			Thread.sleep(6000);
			int result =mCallBack.onServiceActivited(info.ipaddress,info.port,info.hostname);
			if (result==0) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			}else {
				str = utils.returnResultoFail(name, method, "-1", "0", lineNumber);
			}
		} else {
			str = utils.returnResultoFail(name, method, "null", "info", lineNumber);
		}
	}
	
	
	public void testIMultiScreenCallBack_onServiceActivited_iAbnm_010() throws Exception {
		String name = "testIMultiScreenCallBack_onServiceActivited_iAbnm_010";
		String method = "IMultiScreenCallBack.onServiceActivited";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
        if (info!=null) {
        	mService.startMultiScreenServer("TVOS_MULTI_SCREEN", "TVOS", "Service Info", "1.0", "ip address", 33333, "Host Name");
        	Thread.sleep(6000);
        	if ("onServiceActivited".equals(LogString.str_Activit)) {
        		str = utils.returnResultoFail(name, method, "false", "true", lineNumber);
        	}else {
        		str = utils.returnResultofTrue(name, method, lineNumber);
        	}
		}else {
			str = utils.returnResultoFail(name, method, "null", "info", lineNumber);
		}

	}

	public void testIMultiScreenCallBack_onServiceActivited_iPerf_010() throws Exception, ParseException {
		String name = "testIMultiScreenCallBack_onServiceActivited_iPerf_010";
		String method = "IMultiScreenCallBack.onServiceActivited";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
		if (info!=null) {
			int result =-1;
			for (int i = 0; i < 10; i++) {
				begindate = new Date();
				result = mCallBack.onServiceActivited(info.ipaddress,info.port,info.hostname);
				enddate = new Date();
				calculatTiming(enddate, begindate);
			}
			if (result==0) {
				if (utils.isNormal(timeDiffrence)) {						
					str = utils.returnResultofTrue(name, method, lineNumber);
				}else {
					str = utils.returnResultoFail(name, method, utils.getINromDate(timeDiffrence)+"", "1000", lineNumber);
				}
			}else {
				str = utils.returnResultoFail(name, method, "-1", "0", lineNumber);
			}
		} else {
			str = utils.returnResultoFail(name, method,"null", "info", lineNumber);
		}
	}

	public void testIMultiScreenCallBack_onServiceActivited_iPress_010() throws Exception {
		String name = "testIMultiScreenCallBack_onServiceActivited_iPress_010";
		String method = "IMultiScreenCallBack.onServiceActivited";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
		if (info!=null) {
			int result =-1;
			for (int i = 0; i < 1000; i++) {
				result = mCallBack.onServiceActivited(info.ipaddress,info.port,info.hostname);
			}
			if (result==0) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			}else {
				str = utils.returnResultoFail(name, method, "-1", "0", lineNumber);
			}
		} else {
			str = utils.returnResultoFail(name, method,"null", "info", lineNumber);
		}		 
	}

	
	public void testIMultiScreenCallBack_onServiceDeactived_iNorm_010() throws Exception {
		String name = "testIMultiScreenCallBack_onServiceDeactived_iNorm_010";
		String method = "IMultiScreenCallBack.onServiceDeactived";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
		if (info!=null) {
			mService.setCallBack(mCallBack);
			mService.stopMultiScreenServer();
			int result =mCallBack.onServiceDeactived(info.ipaddress,info.port,info.hostname);
			if (result==0) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			}else {
				str = utils.returnResultoFail(name, method, "-1", "0", lineNumber);
			}
		} else {
			str = utils.returnResultoFail(name, method,"null", "info", lineNumber);
		}
	}
	
	
	public void testIMultiScreenCallBack_onServiceDeactived_iAbnm_010() throws Exception {
		String name = "testIMultiScreenCallBack_onServiceDeactived_iAbnm_010";
		String method = "IMultiScreenCallBack.onServiceDeactived";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
		if (info!=null) {
			mService.setCallBack(mCallBack);
			mService.stopMultiScreenServer();
			if ("onServiceDeactived".equals(LogString.str)) {
				str = utils.returnResultoFail(name, method, "false", "true", lineNumber);
			}else {
				str = utils.returnResultofTrue(name, method, lineNumber);
			}
		} else {
			str = utils.returnResultoFail(name, method,"null", "info", lineNumber);
		}
		
	}

	public void testIMultiScreenCallBack_onServiceDeactived_iPerf_010() throws Exception {
		String name = "testIMultiScreenCallBack_onServiceDeactived_iPerf_010";
		String method = "IMultiScreenCallBack.onServiceDeactived";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
		if (info!=null) {
			mService.setCallBack(mCallBack);
			mService.stopMultiScreenServer();
			int result = -1;
			for (int i = 0; i < 10; i++) {
				begindate = new Date();
				result = mCallBack.onServiceDeactived(info.ipaddress,info.port,info.hostname);
				enddate = new Date();
				calculatTiming(enddate, begindate);
			}
			if (result==0) {
				if (utils.isNormal(timeDiffrence)) {						
					str = utils.returnResultofTrue(name, method, lineNumber);
				}else {
					str = utils.returnResultoFail(name, method, utils.getINromDate(timeDiffrence)+"", "1000", result);
				}
			}else {
				str = utils.returnResultoFail(name, method, "-1", "0", lineNumber);
			}
		} else {
			str = utils.returnResultoFail(name, method,"null", "info", lineNumber);
		}
		
	}

	public void testIMultiScreenCallBack_onServiceDeactived_iPress_010() throws Exception {
		String name = "testIMultiScreenCallBack_onServiceDeactived_iPress_010";
		String method = "IMultiScreenCallBack.onServiceDeactived";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
		if (info!=null) {
			mService.setCallBack(mCallBack);
			mService.stopMultiScreenServer();
			int result = -1;
			for (int i = 0; i < 1000; i++) {
				result = mCallBack.onServiceDeactived(info.ipaddress,info.port,info.hostname);
			}
			if (result==0) {					
				str = utils.returnResultofTrue(name, method, lineNumber);
			}else {
				str = utils.returnResultoFail(name, method, "-1", "0", lineNumber);
			}
		} else {
			str = utils.returnResultoFail(name, method,"null", "info", lineNumber);
		}
		
	}
	
	
	public void testIMultiScreenCallBack_onQueryInfo_iNorm_010()throws  Exception {
		String name = "testIMultiScreenCallBack_onQueryInfo_iNorm_010";
		String method = "IMultiScreenCallBack.onQueryInfo";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		info =getInfo();
		Log.i("dsa","info  "+info);
		if (info!=null) {
			int connect =mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,info.ipaddress,info.port,info.hostname);
			int query =mService.queryInfo( info.ipaddress,info.port, info.hostname, mUUID, "queryname", "hello tvos?");
			int result =mCallBack.onQueryInfo(info.ipaddress,info.port,info.hostname,"tvos","tvos","tvos");
			Log.i("dsa","connect  "+connect+"  "+"query  "+query+"  "+"result  "+result);
			if (result==0) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			}else {
				str = utils.returnResultoFail(name, method, "-1", "0", lineNumber);
			}
		} else {
			str = utils.returnResultoFail(name, method, "null", "info", lineNumber);
		}	
	}

	public void testIMultiScreenCallBack_onQueryInfo_iAbnm_010()throws Exception {
		String name = "testIMultiScreenCallBack_onQueryInfo_iAbnm_010";
		String method = "IMultiScreenCallBack.onQueryInfo";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
		if (info!=null) {
			mService.stopMultiScreenClient();
			mService.stopMultiScreenServer();
			//mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,info.ipaddress,info.port,info.hostname);
			mService.queryInfo( info.ipaddress,info.port, info.hostname, mUUID, "queryname", "hello tvos?");
			if ("onQueryInfo".equals(LogString.str_Query)) {
        		str = utils.returnResultoFail(name, method, "false", "true", lineNumber);
        	}else {
        		str = utils.returnResultofTrue(name, method, lineNumber);
        	}
		} else {
			str = utils.returnResultoFail(name, method, "null", "info", lineNumber);
		}

	}
	
	
	public void testIMultiScreenCallBack_onQueryInfo_iPerf_010()throws Exception {
		String name = "testIMultiScreenCallBack_onQueryInfo_iPerf_010";
		String method = "IMultiScreenCallBack.onQueryInfo";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
		if (info!=null) {
			mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,info.ipaddress,info.port,info.hostname);
			mService.queryInfo( info.ipaddress,info.port, info.hostname, mUUID, "queryname", "hello tvos?");
			int result = -1;
			for (int i = 0; i < 10; i++) {
				begindate = new Date();
				result = mCallBack.onQueryInfo(info.ipaddress,info.port,info.hostname,"tvos","tvos","tvos");
				enddate = new Date();
				calculatTiming(enddate, begindate);
			}
			if (result==0) {
				if (utils.isNormal(timeDiffrence)) {						
					str = utils.returnResultofTrue(name, method, lineNumber);
				}else {
					str = utils.returnResultoFail(name, method, utils.getINromDate(timeDiffrence)+"", "1000", result);
				}
			}else {
				str = utils.returnResultoFail(name, method, "-1", "0", lineNumber);
			}
		} else {
			str = utils.returnResultoFail(name, method, "null", "info", lineNumber);
		}	
		
	}
	
	
	public void testIMultiScreenCallBack_onQueryInfo_iPress_010() throws Exception{
		String name = "testIMultiScreenCallBack_onQueryInfo_iPress_010";
		String method = "IMultiScreenCallBack.onQueryInfo";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
		if (info!=null) {
			mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,info.ipaddress,info.port,info.hostname);
			mService.queryInfo( info.ipaddress,info.port, info.hostname, mUUID, "queryname", "hello tvos?");
			int result = -1;
			for (int i = 0; i < 1000; i++) {
				result = mCallBack.onQueryInfo(info.ipaddress,info.port,info.hostname,"tvos","tvos","tvos");
			}
			if (result==0) {
				str = utils.returnResultofTrue(name, method, lineNumber);	
			}else {
				str = utils.returnResultoFail(name, method, "-1", "0", lineNumber);
			}
		} else {
			str = utils.returnResultoFail(name, method, "null", "info", lineNumber);
		}
		
	}
	
	public void testIMultiScreenCallBack_onQueryResponse_iNorm_010() throws Exception {
		String name = "testIMultiScreenCallBack_onQueryResponse_iNorm_010";
		String method = "IMultiScreenCallBack.onQueryResponse";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
		if (info!=null) {
			mService.setCallBack(mCallBack);
			mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,info.ipaddress,info.port,info.hostname);
			mService.queryInfo( info.ipaddress,info.port, info.hostname, mUUID, "queryname", "hello tvos?");
			int result =mCallBack.onQueryResponse(info.ipaddress,info.port,info.hostname,"tvos","tvos","tvos");
			if (result==0) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			}else {
				str = utils.returnResultoFail(name, method, "-1", "0", lineNumber);
			}
		} else {
			str = utils.returnResultoFail(name, method, "null", "info", lineNumber);
		}
	}

	
	public void testIMultiScreenCallBack_onQueryResponse_iAbnm_010() throws Exception {
		String name = "testIMultiScreenCallBack_onQueryResponse_iAbnm_010";
		String method = "IMultiScreenCallBack.onQueryResponse";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
		if (info!=null) {
			mService.setCallBack(mCallBack);
			mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,info.ipaddress,info.port,info.hostname);
			mService.queryInfo( info.ipaddress,info.port, info.hostname, mUUID, "queryname", "hello tvos?");
			Thread.sleep(3000);
			if ("onQueryResponse".equals(LogString.str_Response)) {
        		str = utils.returnResultoFail(name, method, "false", "true", lineNumber);
        	}else {
        		str = utils.returnResultofTrue(name, method, lineNumber);
        	}
		} else {
			str = utils.returnResultoFail(name, method, "null", "info", lineNumber);
		}
		
	}
	
	
	public void testIMultiScreenCallBack_onQueryResponse_iPerf_010() throws Exception {
		String name = "testIMultiScreenCallBack_onQueryResponse_iPerf_010";
		String method = "IMultiScreenCallBack.onQueryResponse";
		getInfo();
		if (info!=null) {
			mService.setCallBack(mCallBack);
			mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,info.ipaddress,info.port,info.hostname);
			mService.queryInfo( info.ipaddress,info.port, info.hostname, mUUID, "queryname", "hello tvos?");
			int result = -1;
			for (int i = 0; i < 10; i++) {
				begindate = new Date();
				result = mCallBack.onQueryResponse(info.ipaddress,info.port,info.hostname,"tvos","tvos","tvos");
				enddate = new Date();
				calculatTiming(enddate, begindate);
			}
			if (result==0) {
				if (utils.isNormal(timeDiffrence)) {						
					str = utils.returnResultofTrue(name, method, lineNumber);
				}else {
					str = utils.returnResultoFail(name, method, utils.getINromDate(timeDiffrence)+"", "1000", lineNumber);
				}
			}else {
				str = utils.returnResultoFail(name, method, "-1", "0", lineNumber);
			}
		} else {
			str = utils.returnResultoFail(name, method, "null", "info", lineNumber);
		}
		
	}

	public void testIMultiScreenCallBack_onQueryResponse_iPress_010() throws Exception {
		String name = "testIMultiScreenCallBack_onQueryResponse_iPress_010";
		String method = "IMultiScreenCallBack.onQueryResponse";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
		mService.startMultiScreenServer("TVOS_MULTI_SCREEN", "TVOS", "Service Info", "1.0", "ip address", 33333, "Host Name");
		if (info!=null) {
			mService.setCallBack(mCallBack);
			mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,info.ipaddress,info.port,info.hostname);
			mService.queryInfo( info.ipaddress,info.port, info.hostname, mUUID, "queryname", "hello tvos?");
			int result = -1;
			for (int i = 0; i < 1000; i++) {				
				result = mCallBack.onQueryResponse(info.ipaddress,info.port,info.hostname,"tvos","tvos","tvos");
			}
			if (result==0) {	
					str = utils.returnResultofTrue(name, method, lineNumber);
			}else {
				str = utils.returnResultoFail(name, method, "-1", "0", lineNumber);
			}
		} else {
			str = utils.returnResultoFail(name, method, "null", "info", lineNumber);
		}
		
	}

	public void testIMultiScreenCallBack_onExecute_iNorm_010() throws Exception {
		String name = "testIMultiScreenCallBack_onExecute_iNorm_010";
		String method = "IMultiScreenCallBack.onExecute";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
		if (info!=null) {
			mService.setCallBack(mCallBack);
			mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,
					info.ipaddress,info.port,info.hostname);
			int ret=mService.execCmd( info.ipaddress,info.port, info.hostname, "tvoscmd", "hello tvos cmd");  
			int result =mCallBack.onExecute(info.ipaddress,info.port,info.hostname,"0","1");
			Log.i("dsa","execCmd  "+ret+"  "+"result  "+result);
			if (result==0) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			}else {
				str = utils.returnResultoFail(name, method, "-1", "0", lineNumber);
			}
		} else {
			str = utils.returnResultoFail(name, method, "null", "info", lineNumber);
		}
	}
	
	
	public void testIMultiScreenCallBack_onExecute_iAbnm_010() throws Exception {
		String name = "testIMultiScreenCallBack_onExecute_iAbnm_010";
		String method = "IMultiScreenCallBack.onExecute";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
		if (info!=null) {
			mService.setCallBack(mCallBack);
			mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,
					info.ipaddress,info.port,info.hostname);
			int ret=mService.execCmd( info.ipaddress,info.port, info.hostname, "tvoscmd", "hello tvos cmd");    
			if ("onExecute".equals(LogString.str_Execute)) {
        		str = utils.returnResultoFail(name, method, "false", "true", lineNumber);
        	}else {
        		str = utils.returnResultofTrue(name, method, lineNumber);
        	}
		} else {
			str = utils.returnResultoFail(name, method, "null", "info", lineNumber);
		}
		mService.stopMultiScreenServer();
		LogString.str_Execute=null;
	
	}

	public void testIMultiScreenCallBack_onExecute_iPerf_010() throws Exception {
		String name = "testIMultiScreenCallBack_onExecute_iPerf_010";
		String method = "IMultiScreenCallBack.onExecute";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
		if (info!=null) {
			mService.setCallBack(mCallBack);
			mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,
					info.ipaddress,info.port,info.hostname);
			int ret=mService.execCmd( info.ipaddress,info.port, info.hostname, "tvoscmd", "hello tvos cmd");    
			int result = -1;
			for (int i = 0; i < 10; i++) {
				begindate = new Date();
				result =mCallBack.onExecute(info.ipaddress,info.port,info.hostname,"0","1");
				enddate = new Date();
				calculatTiming(enddate, begindate);
			}
			if (result==0) {
				if (utils.isNormal(timeDiffrence)) {						
					str = utils.returnResultofTrue(name, method, lineNumber);
				}else {
					str = utils.returnResultoFail(name, method, utils.getINromDate(timeDiffrence)+"", "1000", lineNumber);
				}
			}else {
				str = utils.returnResultoFail(name, method, "-1", "0", lineNumber);
			}
		} else {
			str = utils.returnResultoFail(name, method, "null", "info", lineNumber);
		}
		
	}

	public void testIMultiScreenCallBack_onExecute_iPress_010() throws Exception {
		String name = "testIMultiScreenCallBack_onExecute_iPress_010";
		String method = "IMultiScreenCallBack.onExecute";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
		if (info!=null) {
			mService.setCallBack(mCallBack);
			mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,
					info.ipaddress,info.port,info.hostname);
			int ret=mService.execCmd( info.ipaddress,info.port, info.hostname, "tvoscmd", "hello tvos cmd");    
			int result = -1;
			for (int i = 0; i < 1000; i++) {
				result =mCallBack.onExecute(info.ipaddress,info.port,info.hostname,"0","1");
				lineNumber = new Throwable().getStackTrace()[0].getLineNumber();		
			}
			if (result==0) {			
				str = utils.returnResultofTrue(name, method, lineNumber);
			}else {
				str = utils.returnResultoFail(name, method, "-1", "0", lineNumber);
			}
		} else {
			str = utils.returnResultoFail(name, method, "null", "info", lineNumber);
		}
		
	}

	public void testIMultiScreenCallBack_onInputKeyCode_iNorm_010() throws Exception {
		String name = "testIMultiScreenCallBack_onInputKeyCode_iNorm_010";
		String method = "IMultiScreenCallBack.onInputKeyCode";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
		if (info!=null) {
			mService.setCallBack(mCallBack);
			mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,
					info.ipaddress,info.port,info.hostname);
			waitOnConnected();
			int ret=mService.execCmd( info.ipaddress,info.port, info.hostname, "tvoscmd", "hello tvos cmd");  
			waitOnExecute();
			int ret2=mService.inputKeyCode( info.ipaddress,info.port, info.hostname, "inputkeycode", "tvos keycode 1");
			waitOnInputKeyCode();
			Log.i("aa", "**Abnm.str_Input::"+LogString.str_Input);
			if ("onInputKeyCode".equals(LogString.str_Input)) {
				str = utils.returnResultofTrue(name, method, lineNumber);
        	}else {
        		str = utils.returnResultoFail(name, method, "false", "true", lineNumber);
        	}
		} else {
			str = utils.returnResultoFail(name, method, "null", "info", lineNumber);
		}
		mService.stopMultiScreenServer();
		LogString.str_Input=null;
		
	}

	public void testIMultiScreenCallBack_onInputKeyCode_iAbnm_010() throws Exception {
		String name = "testIMultiScreenCallBack_onInputKeyCode_iAbnm_010";
		String method = "IMultiScreenCallBack.onInputKeyCode";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		mService.stopMultiScreenServer();
		getInfo();
		if (info!=null) {
			mService.setCallBack(mCallBack);
			int ret=mService.execCmd( info.ipaddress,info.port, info.hostname, "tvoscmd", "hello tvos cmd");  
			waitOnExecute();
			int ret2=mService.inputKeyCode( info.ipaddress,info.port, info.hostname, "inputkeycode", "tvos keycode 1");
			waitOnInputKeyCode();
			Log.i("aa", "**Abnm.str_Input::"+LogString.str_Input);
			if ("onInputKeyCode".equals(LogString.str_Input)) {
        		str = utils.returnResultoFail(name, method, "false", "true", lineNumber);
        	}else {
        		str = utils.returnResultofTrue(name, method, lineNumber);
        	}
		} else {
			str = utils.returnResultoFail(name, method, "null", "info", lineNumber);
		}
		mService.stopMultiScreenServer();
		LogString.str_Input=null;
	}
	
	
	public void testIMultiScreenCallBack_onInputKeyCode_iPerf_010() throws Exception {
		String name = "testIMultiScreenCallBack_onInputKeyCode_iPerf_010";
		String method = "IMultiScreenCallBack.onInputKeyCode";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
		if (info!=null) {
			mService.setCallBack(mCallBack);
			mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,
					info.ipaddress,info.port,info.hostname);
			waitOnConnected();
			int ret=mService.execCmd( info.ipaddress,info.port, info.hostname, "tvoscmd", "hello tvos cmd");    
			int ret2=mService.inputKeyCode( info.ipaddress,info.port, info.hostname, "inputkeycode", "tvos keycode 1");
			int result = -1;
			for (int i = 0; i <10; i++) {
				begindate = new Date();
				result = mCallBack.onInputKeyCode(info.ipaddress,info.port,info.hostname, "inputkeycode", "tvos keycode 1");
				enddate = new Date();
				calculatTiming(enddate, begindate);
			}
			if (result==0) {
				if (utils.isNormal(timeDiffrence)) {						
					str = utils.returnResultofTrue(name, method, lineNumber);
				}else {
					str = utils.returnResultoFail(name, method, utils.getINromDate(timeDiffrence)+"", "1000", result);
				}
			}else {
				str = utils.returnResultoFail(name, method, "-1", "0", lineNumber);
			}
		} else {
			str = utils.returnResultoFail(name, method, "null", "info", lineNumber);
		}
		
	}

	public void testIMultiScreenCallBack_onInputKeyCode_iPress_010() throws Exception {
		String name = "testIMultiScreenCallBack_onInputKeyCode_iPress_010";
		String method = "IMultiScreenCallBack.onInputKeyCode";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
		if (info!=null) {
			mService.setCallBack(mCallBack);
			mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,
					info.ipaddress,info.port,info.hostname);
			int ret=mService.execCmd( info.ipaddress,info.port, info.hostname, "tvoscmd", "hello tvos cmd");    
			int ret2=mService.inputKeyCode( info.ipaddress,info.port, info.hostname, "inputkeycode", "tvos keycode 1");
			int result = -1;
			for (int i = 0; i <1000; i++) {
				result = mCallBack.onInputKeyCode(info.ipaddress,info.port,info.hostname, "inputkeycode", "tvos keycode 1");
			}
			if (result==0) {		
				str = utils.returnResultofTrue(name, method, lineNumber);
			}else {
				str = utils.returnResultoFail(name, method, "-1", "0", lineNumber);
			}
		} else {
			str = utils.returnResultoFail(name, method, "null", "info", lineNumber);
		}
		
	}

	public void testIMultiScreenCallBack_onNotify_iNorm_010() throws Exception {
		String name = "testIMultiScreenCallBack_onNotify_iNorm_010";
		String method = "IMultiScreenCallBack.onNotify";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
		 mService.startMultiScreenServer("TVOS_MULTI_SCREEN", "TVOS", "Service Info", "1.0", "ip address", 33333, "Host Name");
		 mService.startMultiScreenClient("Cli:" + DID);
		if (info!=null) {
			mService.setCallBack(mCallBack);
			mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,
					info.ipaddress,info.port,info.hostname);
			waitOnConnected();
			mService.boardCastAllDevice("inputkeycode", "tvos keycode 1");
			waitOnNotify();
			if ("onNotify".equals(LogString.str_Notify)) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			}else {
				str = utils.returnResultoFail(name, method, "-1", "0", lineNumber);
			}
		} else {
			str = utils.returnResultoFail(name, method, "null", "info", lineNumber);
		}
		mService.stopMultiScreenServer();
		LogString.str_Notify=null;
		
	}
	
	
	public void testIMultiScreenCallBack_onNotify_iAbnm_010() throws Exception {
		String name = "testIMultiScreenCallBack_onNotify_iAbnm_010";
		String method = "IMultiScreenCallBack.onNotify";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
		if (info!=null) {
			mService.setCallBack(mCallBack);
			mService.boardCastAllDevice("inputkeycode", "tvos keycode 1");
			//waitOnNotify();
			mService.stopMultiScreenClient();
			mService.stopMultiScreenServer();
			waitOnNotify();
			Log.i("dsa","LogString.str_Notify  "+LogString.str_Notify);
        	if ("onNotify".equals(LogString.str_Notify)) {
        		str = utils.returnResultoFail(name, method, "false", "true", lineNumber);
        	}else {
        		str = utils.returnResultofTrue(name, method, lineNumber);
        	}
		} else {
			str = utils.returnResultoFail(name, method, "null", "info", lineNumber);
		}
		mService.stopMultiScreenServer();
		LogString.str_Notify=null;
		
	}

	public void testIMultiScreenCallBack_onNotify_iPerf_010() throws Exception {
		String name = "testIMultiScreenCallBack_onNotify_iPerf_010";
		String method = "IMultiScreenCallBack.onNotify";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
		if (info!=null) {
			mService.setCallBack(mCallBack);
			mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,
					info.ipaddress,info.port,info.hostname);
			mService.boardCastAllDevice("inputkeycode", "tvos keycode 1");
			int result = -1;
			for (int i = 0; i < 10; i++) {
				begindate = new Date();
				result = mCallBack.onNotify(info.ipaddress,info.port,info.hostname, "inputkeycode", "tvos keycode 1");
				enddate = new Date();
				calculatTiming(enddate, begindate);
			}
			if (result==0) {
				if (utils.isNormal(timeDiffrence)) {						
					str = utils.returnResultofTrue(name, method, lineNumber);
				}else {
					str = utils.returnResultoFail(name, method, utils.getINromDate(timeDiffrence)+"", "1000", lineNumber);
				}
			}else {
				str = utils.returnResultoFail(name, method, "-1", "0", lineNumber);
			}
		} else {
			str = utils.returnResultoFail(name, method, "null", "info", lineNumber);
		}
		
	}

	public void testIMultiScreenCallBack_onNotify_iPress_010() throws Exception {
		String name = "testIMultiScreenCallBack_onNotify_iPress_010";
		String method = "IMultiScreenCallBack.onNotify";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
		if (info!=null) {
			mService.setCallBack(mCallBack);
			mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,
					info.ipaddress,info.port,info.hostname);
			mService.boardCastAllDevice("inputkeycode", "tvos keycode 1");
			int result = -1;
			for (int i = 0; i < 1000; i++) {
				result = mCallBack.onNotify(info.ipaddress,info.port,info.hostname, "inputkeycode", "tvos keycode 1");
			}
			if (result==0) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			}else {
				str = utils.returnResultoFail(name, method, "-1", "0", lineNumber);
			}
		} else {
			str = utils.returnResultoFail(name, method, "null", "info", lineNumber);
		}
		
	}

	public void testIMultiScreenCallBack_onTransact_iNorm_010() throws Exception {
		String name = "testIMultiScreenCallBack_onTransact_iNorm_010";
		String method = "IMultiScreenCallBack.onTransact";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		mService.setCallBack(mCallBack);
		int ret1 = mService.startMultiScreenServer("TVOS_MULTI_SCREEN", "TVOS", "Service Info", "1.0", "ip address", 33333, "Host Name");
		int ret2 =mService.startMultiScreenClient("Cli:" + DID);
        waitOnSpFound();
		if ("onTransact".equals(LogString.str_Transact)) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		}else {
			str = utils.returnResultoFail(name, method, "false", "true", lineNumber);
		}
		mService.stopMultiScreenClient();
		mService.stopMultiScreenServer();
		
	}

	public void testIMultiScreenCallBack_onTransact_iAbnm_010() throws Exception {
		String name = "testIMultiScreenCallBack_onTransact_iAbnm_010";
		String method = "IMultiScreenCallBack.onTransact";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		mService.stopMultiScreenServer();
		mService.setCallBack(mCallBack);
		int ret2 =mService.startMultiScreenClient("Cli:" + DID);
        waitOnSpFound();
        Log.i("aa", "---------"+LogString.str_Transact);
		if ("onTransact".equals(LogString.str_Transact)) {
			str = utils.returnResultoFail(name, method, "false", "true", lineNumber);
		}else {
			str = utils.returnResultofTrue(name, method, lineNumber);
		}
		mService.stopMultiScreenClient();

	}
	
	
	public void testIMultiScreenCallBack_onTransact_iPerf_010() throws Exception {
		String name = "testIMultiScreenCallBack_onTransact_iPerf_010";
		String method = "IMultiScreenCallBack.onTransact";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		mService.setCallBack(mCallBack);
		mService.startMultiScreenServer("TVOS_MULTI_SCREEN", "TVOS", "Service Info", "1.0", "ip address", 33333, "Host Name");
        mService.startMultiScreenClient("Cli:" + DID);
        for (int i = 0; i < 10; i++) {
			begindate = new Date();
			int ret = mService.findSPs();
			enddate = new Date();
			calculatTiming(enddate, begindate);
		}
        waitOnSpFound();
		if ("onTransact".equals(LogString.str_Transact)) {
			if (utils.isNormal(timeDiffrence)) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			}else {
				str = utils.returnResultoFail(name, method, utils.getINromDate(timeDiffrence)+"", "1000", lineNumber);
			}
		}else {
			str = utils.returnResultoFail(name, method,"false", "true", lineNumber);
		}
		mService.stopMultiScreenServer();
		
	}

	public void testIMultiScreenCallBack_onTransact_iPress_010() throws Exception {
		String name = "testIMultiScreenCallBack_onTransact_iPress_010";
		String method = "IMultiScreenCallBack.onTransact";
		mService.setCallBack(mCallBack);
		mService.startMultiScreenServer("TVOS_MULTI_SCREEN", "TVOS", "Service Info", "1.0", "ip address", 33333, "Host Name");
        mService.startMultiScreenClient("Cli:" + DID);
        for (int i = 0; i < 1000; i++) {
			begindate = new Date();
			int ret = mService.findSPs();
			enddate = new Date();
			calculatTiming(enddate, begindate);
		}
        waitOnSpFound();
		if ("onTransact".equals(LogString.str_Transact)) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		}else {
			str = utils.returnResultoFail(name, method, "false", "true", lineNumber);
		}
		mService.stopMultiScreenServer();
	}

	
	public void testIMultiScreenCallBack_ON_SP_FOUNDED_iNorm_010() {
		String name = "testIMultiScreenCallBack_ON_SP_FOUNDED_iNorm_010";
		String method = "IMultiScreenCallBack.ON_SP_FOUNDED";
		int actur=IMultiScreenCallBack.ON_SP_FOUNDED;
		int expec=mBinder.FIRST_CALL_TRANSACTION+0;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		if (actur ==expec) {
			str=utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str=utils.returnResultoFail(name, method, actur+"", expec+"", lineNumber);
		}
	}
	public void testIMultiScreenCallBack_ON_CONNECTED_iNorm_010() {
		String name = "testIMultiScreenCallBack_ON_CONNECTED_iNorm_010";
		String method = "IMultiScreenCallBack.ON_CONNECTED";
		int actur=IMultiScreenCallBack.ON_CONNECTED;
		int expec=mBinder.FIRST_CALL_TRANSACTION+1;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		if (actur ==expec) {
			str=utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str=utils.returnResultoFail(name, method, actur+"", expec+"", lineNumber);
		}
	}
	public void testIMultiScreenCallBack_ON_CONNECTEDREFUSED_iNorm_010() {
		String name = "testIMultiScreenCallBack_ON_CONNECTEDREFUSED_iNorm_010";
		String method = "IMultiScreenCallBack.ON_CONNECTEDREFUSED";
		int actur=IMultiScreenCallBack.ON_CONNECTEDREFUSED;
		int expec=mBinder.FIRST_CALL_TRANSACTION+2;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		if (actur ==expec) {
			str=utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str=utils.returnResultoFail(name, method, actur+"", expec+"", lineNumber);
		}
	}
	public void testIMultiScreenCallBack_ON_DISCONNECTED_iNorm_010() {
		String name = "testIMultiScreenCallBack_ON_DISCONNECTED_iNorm_010";
		String method = "IMultiScreenCallBack.ON_DISCONNECTED";
		int actur=IMultiScreenCallBack.ON_DISCONNECTED;
		int expec=mBinder.FIRST_CALL_TRANSACTION+3;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		if (actur ==expec) {
			str=utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str=utils.returnResultoFail(name, method, actur+"", expec+"", lineNumber);
		}
	}
	public void testIMultiScreenCallBack_ON_SERVICE_ACTIVITED_iNorm_010() {
		String name = "testIMultiScreenCallBack_ON_SERVICE_ACTIVITED_iNorm_010";
		String method = "IMultiScreenCallBack.ON_SERVICE_ACTIVITED";
		int actur=IMultiScreenCallBack.ON_SERVICE_ACTIVITED;
		int expec=mBinder.FIRST_CALL_TRANSACTION+4;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		if (actur ==expec) {
			str=utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str=utils.returnResultoFail(name, method, actur+"", expec+"", lineNumber);
		}
	}
	public void testIMultiScreenCallBack_ON_SERVICE_DEACTIVITED_iNorm_010() {
		String name = "testIMultiScreenCallBack_ON_SERVICE_DEACTIVITED_iNorm_010";
		String method = "IMultiScreenCallBack.ON_SERVICE_DEACTIVITED";
		int actur=IMultiScreenCallBack.ON_SERVICE_DEACTIVITED;
		int expec=mBinder.FIRST_CALL_TRANSACTION+5;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		if (actur ==expec) {
			str=utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str=utils.returnResultoFail(name, method, actur+"", expec+"", lineNumber);
		}
	}
	public void testIMultiScreenCallBack_ON_QUERY_INFO_iNorm_010() {
		String name = "testIMultiScreenCallBack_ON_QUERY_INFO_iNorm_010";
		String method = "IMultiScreenCallBack.ON_QUERY_INFO";
		int actur=IMultiScreenCallBack.ON_QUERY_INFO;
		int expec=mBinder.FIRST_CALL_TRANSACTION+6;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		if (actur ==expec) {
			str=utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str=utils.returnResultoFail(name, method, actur+"", expec+"", lineNumber);
		}
	}
	public void testIMultiScreenCallBack_ON_QUERY_RESPONSE_iNorm_010() {
		String name = "testIMultiScreenCallBack_ON_QUERY_RESPONSE_iNorm_010";
		String method = "IMultiScreenCallBack.ON_QUERY_RESPONSE";
		int actur=IMultiScreenCallBack.ON_QUERY_RESPONSE;
		int expec=mBinder.FIRST_CALL_TRANSACTION+7;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		if (actur ==expec) {
			str=utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str=utils.returnResultoFail(name, method, actur+"", expec+"", lineNumber);
		}
	}
	public void testIMultiScreenCallBack_ON_EXECUTE_iNorm_010() {
		String name = "testIMultiScreenCallBack_ON_EXECUTE_iNorm_010";
		String method = "IMultiScreenCallBack.ON_EXECUTE";
		int actur=IMultiScreenCallBack.ON_EXECUTE;
		int expec=mBinder.FIRST_CALL_TRANSACTION+8;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		if (actur ==expec) {
			str=utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str=utils.returnResultoFail(name, method, actur+"", expec+"", lineNumber);
		}
	}
	public void testIMultiScreenCallBack_ON_INPUT_iNorm_010() {
		String name = "testIMultiScreenCallBack_ON_INPUT_iNorm_010";
		String method = "IMultiScreenCallBack.ON_INPUT";
		int actur=IMultiScreenCallBack.ON_INPUT;
		int expec=mBinder.FIRST_CALL_TRANSACTION+9;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		if (actur ==expec) {
			str=utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str=utils.returnResultoFail(name, method, actur+"", expec+"", lineNumber);
		}
	}
	
	public void testIMultiScreenCallBack_ON_NOTIFY_iNorm_010() {
		String name = "testIMultiScreenCallBack_ON_NOTIFY_iNorm_010";
		String method = "IMultiScreenCallBack.ON_NOTIFY";
		int actur=IMultiScreenCallBack.ON_NOTIFY;
		int expec=mBinder.FIRST_CALL_TRANSACTION+10;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		if (actur ==expec) {
			str=utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str=utils.returnResultoFail(name, method, actur+"", expec+"", lineNumber);
		}
	}
	
	
	
	@Override
	protected void tearDown() throws Exception {
		FileWriter fw = new FileWriter(file);
		fw.write(str);
		fw.write("\r\n");
		str = "";
		fw.flush();
		fw.close();
		super.tearDown();
	}

	@Override
	public String onQuery(String queryId, String attribute, String param) {

		return "tvos";
	}

	@Override
	public void onQueryResponse(String queryId, String attribute, String param) {

	}

}
