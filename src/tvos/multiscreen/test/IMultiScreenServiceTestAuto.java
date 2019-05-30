package tvos.multiscreen.test;

import java.io.File;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.ngb.system.DataAccess;
import org.tvos.multiscreen.IMultiScreenService;
import org.tvos.multiscreen.MultiScreenCallBack;
import org.tvos.multiscreen.MultiScreenCallBack.MultiScreenCallBackListener;
import org.tvos.multiscreen.MultiScreenService;
import org.tvos.multiscreen.ServiceProvideInfo;

import tvos.multiscreen.impl.MultiScreenCallBackImpl;
import android.annotation.SuppressLint;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.test.AndroidTestCase;
/**
 * 正常測試
 */
//am instrument -w -r -e class tvos.multiscreen.test.PaymentServiceTestAuto#testPaymentService_PM_sendPay_iNorm_010 com.example.java_payment_tvostest/android.test.InstrumentationTestRunner
import android.util.Log;

/**
 * 性能測試
 */
//am instrument -w -r -e class tvos.multiscreen.test.PaymentServiceTestAuto#testPaymentService_PM_sendPay_iPerf_010 com.example.java_payment_tvostest/android.test.InstrumentationTestRunner

/**
 * 压力测试
 */
// am instrument -w -r -e class
// tvos.multiscreen.test.PaymentServiceTestAuto#testPaymentService_PM_sendPay_iPress_010
// com.example.java_payment_tvostest/android.test.InstrumentationTestRunner
@SuppressLint("NewApi") public class IMultiScreenServiceTestAuto extends AndroidTestCase implements
		MultiScreenCallBackListener {
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
	DataAccess dataAccess;
	private boolean accpect = false;

	private static final String SERVICEDESCRIPTOR = "tvos.multiscreen";
	private IBinder mBinder;
	private static MultiScreenService mService;
	private static MultiScreenService mClient;
	private static final String DID = android.os.Build.SERIAL;
	private MultiScreenCallBack mCallBack = null;
	String mUUID = null;
	List<ServiceProvideInfo> mList = null;
	ServiceProvideInfo info = null;

	@Override
	protected void setUp() throws Exception {

		try {
			mBinder = ServiceManager.getService(SERVICEDESCRIPTOR);
			Log.i("dsa","mBinder ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		mService = new MultiScreenService();
		mClient  = new MultiScreenService();
		mCallBack = new MultiScreenCallBackImpl(this);
		mService.setCallBack(mCallBack);
		// info=getInfo();
		mUUID = UUID.randomUUID().toString();
		file = new File(FILE_RES);
		if (!file.exists()) {
			file.createNewFile();
		}
		super.setUp();
	}

	private ServiceProvideInfo getInfo() throws Exception {
		mService.startMultiScreenServer("TVOS_MULTI_SCREEN", "TVOS", "Service Info", "1.0", "ip address", 33333, "Host Name");
		mClient.startMultiScreenClient("Cli:" + DID);
		mService.findSPs();
		mList = mCallBack.getServiceProvideList();
		int count = 0;
		while (mList.size() == 0 && count < 10000) {
			Thread.sleep(100);		
			count += 100;
		}
		Log.i("tag", mList.size()+"--------------------------");
		if (mList.size() != 0) {
			info = mList.get(0);
		}
		return info;

	}

	public void testIMultiScreenService_startMultiScreenServer_iNorm_010()
			throws Exception {
		String name = "testIMultiScreenService_startMultiScreenServer_iNorm_010";
		String method = "IMultiScreenService.startMultiScreenServer";
		int status = -1;
		//info = getInfo();
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		status = mService.startMultiScreenServer("TVOS_MULTI_SCREEN", "TVOS", "Service Info", "1.0", "ip address", 33333, "Host Name");
		 status = mService.setCallBack(new MultiScreenCallBack(new MultiScreenCallBack.MultiScreenCallBackListener() {
              @Override
              public String onQuery(String s, String s1, String s2) {
            	  Log.i("dsa","onQuery");
                  return null;
              }

              @Override
              public void onQueryResponse(String s, String s1, String s2) {
                  //handleCmd(s, s1, s2);
            	  Log.i("dsa","onQueryResponse");
              }
          }));
		Log.i("dsa","status startMultiScreenServer_iNorm_010 "+status);
		if (status == 0) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str = utils.returnResultoFail(name, method, status + "", "0",
					lineNumber);
		}
	}

	public void testIMultiScreenService_startMultiScreenServer_iAbnm_010()
			throws Exception {
		String name = "testIMultiScreenService_startMultiScreenServer_iAbnm_010";
		String method = "IMultiScreenService.startMultiScreenServer";
		int status = -1;
		int sc = mService.stopMultiScreenClient();
		int ss = mService.stopMultiScreenServer();
		Log.i("dsa","sc  "+sc+"  "+"ss  "+ss);
		Thread.sleep(3000);
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		status = mService.startMultiScreenServer("TVOS_MULTI_SCREEN", "TVOS", "Service Info", "1.0", "ip address", 33333, "Host Name");
			
		//status = mService.startMultiScreenClient("Cli:" + DID);
		Log.i("dsa","status  "+status);
		if (status != 0) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			} else {
				str = utils.returnResultoFail(name, method, "0", "error code",
						lineNumber);
			}
	}

	public void testIMultiScreenService_startMultiScreenServer_iAbnm_011() throws Exception {
		String name = "testIMultiScreenService_startMultiScreenServer_iAbnm_011";
		String method = "IMultiScreenService.startMultiScreenServer";
		int status = -1;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		info =getInfo();
		if (info != null) {			
			try {
				mService.stopMultiScreenServer();
				lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
				status = mService.startMultiScreenServer("TVOS_MULTI_SCREEN", "TVOS", "Service Info", "1.0", "ip address", 33333, "Host Name");
				mService.connect(info.spName, info.spDeviceType,
						info.spServiceInfo, info.spVersion, info.ipaddress,
						info.port, info.hostname);
				mService.stopMultiScreenServer();
				if (status != 0) {
					str = utils.returnResultofTrue(name, method, lineNumber);
				} else {
					str = utils.returnResultoFail(name, method, "0", "error code",
							lineNumber);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			str = utils.returnResultoFail(name, method, info+"", "SPInfo", lineNumber);
		}
	}

	public void testIMultiScreenService_startMultiScreenServer_iPerf_010()
			throws Exception {
		String name = "testIMultiScreenService_startMultiScreenServer_iPerf_010";
		String method = "IMultiScreenService.startMultiScreenServer";
		String spName = "Sev:" + DID;
		String spVersion = "0.0.1";
		String deviceType = "TV";
		info = getInfo();
		int status = -1;
		try {
			for (int i = 0; i < 10; i++) {
				begindate = new Date();
				status = mService.startMultiScreenServer("TVOS_MULTI_SCREEN", "TVOS", "Service Info", "1.0", "ip address", 33333, "Host Name");
				lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
				enddate = new Date();
				calculatTiming(enddate, begindate);
				int stop_status = mService.stopMultiScreenServer();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String actural = "";
		String expect = true + "";
		String actural2 = utils.getINromDate(timeDiffrence) + "";
		String expect2 = "1000";
		boolean returnresult = utils.isNormal(timeDiffrence);
		if (status == 0) {
			if (returnresult) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			} else {
				str = utils.returnResultoFail(name, method, actural2, expect2,
						lineNumber);
			}

		} else {
			actural = false + "";
			str = utils.returnResultoFail(name, method, actural, expect,
					lineNumber);
		}

	}

	public void testIMultiScreenService_startMultiScreenServer_iPress_010()
			throws Exception {
		String name = "testIMultiScreenService_startMultiScreenServer_iPress_010";
		String method = "IMultiScreenService.startMultiScreenServer";
		String spName = "Sev:" + DID;
		String spVersion = "0.0.1";
		String deviceType = "TV";
		int status = -1;
		info = getInfo();
		try {
			for (int i = 0; i < 10000; i++) {
				status = mService.startMultiScreenServer("TVOS_MULTI_SCREEN", "TVOS", "Service Info", "1.0", "ip address", 33333, "Host Name");
				lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
				mService.stopMultiScreenServer();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String actural = "";
		String expect = true + "";
		if (status == 0) {
			str = utils.returnResultofTrue(name, method, lineNumber - 5);
		} else {
			actural = false + "";
			str = utils.returnResultoFail(name, method, actural, expect,
					lineNumber - 5);
		}
	}

	public void testIMultiScreenService_stopMultiScreenServer_iNorm_010()
			throws Exception {
		String name = "testIMultiScreenService_stopMultiScreenServer_iNorm_010";
		String method = "IMultiScreenService.stopMultiScreenServer";
		String spName = "Sev:" + DID;
		String spVersion = "0.0.1";
		String deviceType = "TV";
		int status = -1;
		int stop_status = -1;
		info = getInfo();
		try {
			status = mService.startMultiScreenServer("TVOS_MULTI_SCREEN", "TVOS", "Service Info", "1.0", "ip address", 33333, "Host Name");
			stop_status = mService.stopMultiScreenServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		if (stop_status == 0) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str = utils.returnResultoFail(name, method, stop_status + "", "0",
					lineNumber);
		}
	}

	public void testIMultiScreenService_stopMultiScreenServer_iAbnm_010() {
		String name = "testIMultiScreenService_stopMultiScreenServer_iAbnm_010";
		String method = "IMultiScreenService.stopMultiScreenServer";
		int stop_status = -1;
		try {
			lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
			stop_status = mService.stopMultiScreenServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (stop_status != 0) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str = utils.returnResultoFail(name, method, "0", "error code",
					lineNumber);
		}
	}

	public void testIMultiScreenService_stopMultiScreenServer_iPerf_010()
			throws Exception {
		String name = "testIMultiScreenService_stopMultiScreenServer_iPerf_010";
		String method = "IMultiScreenService.stopMultiScreenServer";
		String spName = "Sev:" + DID;
		String spVersion = "0.0.1";
		String deviceType = "TV";
		int status = -1;
		int stop_status = -1;
		info = getInfo();
		try {
			for (int i = 0; i < 10; i++) {
				status = mService.startMultiScreenServer("TVOS_MULTI_SCREEN", "TVOS", "Service Info", "1.0", "ip address", 33333, "Host Name");
				begindate = new Date();
				stop_status = mService.stopMultiScreenServer();
				Log.i("aa", "stop_status:: " + stop_status);
				lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
				enddate = new Date();
				calculatTiming(enddate, begindate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String actural2 = utils.getINromDate(timeDiffrence) + "";
		String expect2 = "1000";
		boolean returnresult = utils.isNormal(timeDiffrence);
		Log.i("aa", "stop_status*******:: " + stop_status);
		if (stop_status == 0) {
			if (returnresult) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			} else {
				str = utils.returnResultoFail(name, method, actural2, expect2,
						lineNumber);
			}

		} else {
			str = utils.returnResultoFail(name, method, "-1", "0", lineNumber);
		}

	}

	public void testIMultiScreenService_stopMultiScreenServer_iPress_010()
			throws Exception {
		String name = "testIMultiScreenService_stopMultiScreenServer_iPress_010";
		String method = "IMultiScreenService.stopMultiScreenServer";
		String spName = "Sev:" + DID;
		String spVersion = "0.0.1";
		String deviceType = "TV";
		int status = -1;
		int stop_status = -1;
		info = getInfo();
		try {
			lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
			for (int i = 0; i < 10000; i++) {
				status = mService.startMultiScreenServer("TVOS_MULTI_SCREEN", "TVOS", "Service Info", "1.0", "ip address", 33333, "Host Name");
				stop_status = mService.stopMultiScreenServer();
				Log.i("aa", "stop_status*******:: " + stop_status);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (stop_status == 0) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str = utils.returnResultoFail(name, method, stop_status + "", "0",
					lineNumber);
		}
	}

	public void testIMultiScreenService_startMultiScreenClient_iNorm_010()
			throws Exception {
		String name = "testIMultiScreenService_startMultiScreenClient_iNorm_010";
		String method = "IMultiScreenService.startMultiScreenClient";
		int status = -1;
		int stop_status = -1;
		String clientName = "Cli:" + DID;
		info = getInfo();
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
	
				/*mService.connect(info.spName, info.spDeviceType,
						info.spServiceInfo, info.spVersion, info.ipaddress,
						info.port, info.hostname);*/
				lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
				status = mService.startMultiScreenClient(clientName);
				//stop_status = mService.stopMultiScreenClient();
				Log.i("dsa","stop_status  "+stop_status);
		if (status == 0) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str = utils.returnResultoFail(name, method, status + "", "0",
					lineNumber);
		}
	}

	public void testIMultiScreenService_startMultiScreenClient_iAbnm_010()
			throws Exception {
		String name = "testIMultiScreenService_startMultiScreenClient_iAbnm_010";
		String method = "IMultiScreenService.startMultiScreenClient";
		int status = -1;
		int sc = mService.stopMultiScreenClient();
		int ss = mService.stopMultiScreenServer();
		Log.i("dsa","sc  "+sc+"  "+"ss  "+ss);
		Thread.sleep(3000);
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		//status = mService.startMultiScreenServer("TVOS_MULTI_SCREEN", "TVOS", "Service Info", "1.0", "ip address", 33333, "Host Name");
			String s=null;
		status = mService.startMultiScreenClient(s);
		
		Log.i("dsa","status  "+status );
		if (status != 0) {
			str = utils.returnResultofTrue(name, method, lineNumber + 3);
		} else {
			str = utils.returnResultoFail(name, method, "0", "error code",
					lineNumber + 3);
		}
	}

	public void testIMultiScreenService_startMultiScreenClient_iAbnm_011()
			throws Exception {
		String name = "testIMultiScreenService_startMultiScreenClient_iAbnm_011";
		String method = "IMultiScreenService.startMultiScreenClient";
		int status = -1;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		info = getInfo();
		if (info!=null) {			
			try {
				mClient.stopMultiScreenClient();
				lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
				mClient.startMultiScreenClient("tvostes:" + "-1");
				mClient.connect(info.spName, info.spDeviceType,
						info.spServiceInfo, info.spVersion, info.ipaddress,
						info.port, info.hostname);
				mClient.stopMultiScreenClient();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (status != 0) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			} else {
				str = utils.returnResultoFail(name, method, "0", "error code",
						lineNumber);
			}
		}else {
			str = utils.returnResultoFail(name, method, info+"", "SPInfo", lineNumber);
		}
	}

	public void testIMultiScreenService_startMultiScreenClient_iPerf_010()
			throws Exception {
		String name = "testIMultiScreenService_startMultiScreenClient_iPerf_010";
		String method = "IMultiScreenService.startMultiScreenClient";
		int status = -1;
		info = getInfo();
		String clientName = "Cli:" + DID;
		try {
			for (int i = 0; i < 10; i++) {
				begindate = new Date();
				lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
				status = mService.startMultiScreenClient(clientName);
				enddate = new Date();
				calculatTiming(enddate, begindate);
				mService.stopMultiScreenClient();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String actural = "";
		String expect = true + "";
		String actural2 = utils.getINromDate(timeDiffrence) + "";
		String expect2 = "1000";
		boolean returnresult = utils.isNormal(timeDiffrence);
		if (status == 0) {
			if (returnresult) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			} else {
				str = utils.returnResultoFail(name, method, actural2, expect2,
						lineNumber);
			}

		} else {
			actural = false + "";
			str = utils.returnResultoFail(name, method, status + "", "0",
					lineNumber);
		}

	}

	public void testIMultiScreenService_startMultiScreenClient_iPress_010()
			throws Exception {
		String name = "testIMultiScreenService_startMultiScreenClient_iPress_010";
		String method = "IMultiScreenService.startMultiScreenClient";
		int status = -1;
		info = getInfo();
		String clientName = "Cli:" + DID;
		try {
			lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
			for (int i = 0; i < 10000; i++) {
				status = mService.startMultiScreenClient(clientName);
				mService.stopMultiScreenClient();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (status == 0) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str = utils.returnResultoFail(name, method, status + "", "0",
					lineNumber);
		}
	}

	public void testIMultiScreenService_stopMultiScreenClient_iNorm_010()
			throws Exception {
		String name = "testIMultiScreenService_stopMultiScreenClient_iNorm_010";
		String method = "IMultiScreenService.stopMultiScreenClient";
		int status = -1;
		int stop_status = -1;
		String clientName = "Cli:" + DID;
		info = getInfo();
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		try {
			// mService.startMultiScreenClient(clientName);
			//if (info != null) {
				mService.connect(info.spName, info.spDeviceType,
						info.spServiceInfo, info.spVersion, info.ipaddress,
						info.port, info.hostname);
				lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
				status = mService.startMultiScreenClient(clientName);
				stop_status = mService.stopMultiScreenClient();
				Log.i("dsa","stop_status  "+stop_status);
			//	stop_status = mService.stopMultiScreenClient();
				if (stop_status == 0) {
					str = utils.returnResultofTrue(name, method, lineNumber);
				} else {
					str = utils.returnResultoFail(name, method, stop_status
							+ "", "0", lineNumber);
				}
			/*} else {
				str = utils.returnResultoFail(name, method, "NULL", "SPData",
						lineNumber);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testIMultiScreenService_stopMultiScreenClient_iAbnm_010()
			throws Exception {
		String name = "testIMultiScreenService_stopMultiScreenClient_iAbnm_010";
		String method = "IMultiScreenService.stopMultiScreenClient";
		int stop_status = -1;
		mClient.stopMultiScreenClient();
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		info = getInfo();
		if (info!=null) {
			mService.startMultiScreenServer("TVOS_MULTI_SCREEN", "TVOS", "Service Info", "1.0", "ip address", 33333, "Host Name");
			mService.connect(info.spName, info.spDeviceType,
					info.spServiceInfo, info.spVersion, info.ipaddress,
					info.port, info.hostname);
			try {
				lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
				stop_status = mClient.stopMultiScreenClient();
				stop_status = mClient.stopMultiScreenClient();
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (stop_status != 0) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			} else {
				str = utils.returnResultoFail(name, method, "0", "error code",
						lineNumber);
			}	
		}else {
			str = utils.returnResultoFail(name, method, info+"", "SPInfo", lineNumber);
		}
	}

	public void testIMultiScreenService_stopMultiScreenClient_iPerf_010() {
		String name = "testIMultiScreenService_stopMultiScreenClient_iPerf_010";
		String method = "IMultiScreenService.stopMultiScreenClient";
		int status = -1;
		int stop_status = -1;
		String clientName = "Cli:" + DID;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		try {
			for (int i = 0; i < 10; i++) {
				status = mService.startMultiScreenClient(clientName);
				begindate = new Date();
				stop_status = mService.stopMultiScreenClient();
				enddate = new Date();
				calculatTiming(enddate, begindate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String actural2 = utils.getINromDate(timeDiffrence) + "";
		String expect2 = "1000";
		boolean returnresult = utils.isNormal(timeDiffrence);
		if (stop_status == 0) {
			if (returnresult) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			} else {
				str = utils.returnResultoFail(name, method, actural2, expect2,
						lineNumber);
			}

		} else {
			str = utils.returnResultoFail(name, method, stop_status + "", "0",
					lineNumber);
		}

	}

	public void testIMultiScreenService_stopMultiScreenClient_iPress_010() {
		String name = "testIMultiScreenService_stopMultiScreenClient_iPress_010";
		String method = "IMultiScreenService.stopMultiScreenClient";
		int status = -1;
		int stop_status = -1;
		String clientName = "Cli:" + DID;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		try {
			for (int i = 0; i < 10000; i++) {
				status = mService.startMultiScreenClient(clientName);
				stop_status = mService.stopMultiScreenClient();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (stop_status == 0) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str = utils.returnResultoFail(name, method, stop_status + "", "0",
					lineNumber);
		}
	}

	public void testIMultiScreenService_findSPs_iNorm_010() throws Exception {
		String name = "testIMultiScreenService_findSPs_iNorm_010";
		String method = "IMultiScreenService.findSPs";
		int status = -1;
		 getInfo();
		mService.startMultiScreenServer("TVOS_MULTI_SCREEN", "TVOS", "Service Info", "1.0", "ip address", 33333, "Host Name");
		mService.startMultiScreenClient("Cli:" + DID);
		
		//try {
			lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
			status = mService.findSPs();
			Log.i("dsa","status findSPs "+status);
			Thread.sleep(6000);
		/*} catch (Exception e) {
			e.printStackTrace();
		}*/
          int   status2 = mService.connect("TVOS_MULTI_SCREEN", "TVOS", "Service Info", "1.0", "ip address", 33333, "Host Name");

          Log.i("dsa","status2  "+status2);
		if (status == 0) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str = utils.returnResultoFail(name, method, status + "", "0",
					lineNumber);
		}
	}

	public void testIMultiScreenService_findSPs_iAbnm_010() throws Exception,
			Exception {
		String name = "testIMultiScreenService_findSPs_iAbnm_010";
		String method = "IMultiScreenService.findSPs";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		int status = -1;
		//mService.stopMultiScreenServer();
		//mService.startMultiScreenClient("Cli:" + DID);
		try {
			status = mService.findSPs();
			Log.i("aa", "******" + status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.i("dsa","status  "+status);
		if (status != 0) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str = utils.returnResultoFail(name, method, "0", "error code",
					lineNumber);
		}
	}

	public void testIMultiScreenService_findSPs_iPerf_010() throws Exception,
			Exception {
		String name = "testIMultiScreenService_findSPs_iPerf_010";
		String method = "IMultiScreenService.findSPs";
		int status = -1;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		getInfo();
		try {
			for (int i = 0; i < 10; i++) {
				begindate = new Date();
				status = mService.findSPs();
				enddate = new Date();
				calculatTiming(enddate, begindate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String actural = "";
		String expect = true + "";
		String actural2 = utils.getINromDate(timeDiffrence) + "";
		String expect2 = "1000";
		boolean returnresult = utils.isNormal(timeDiffrence);
		if (status == 0) {
			if (returnresult) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			} else {
				str = utils.returnResultoFail(name, method, actural2, expect2,
						lineNumber);
			}

		} else {
			actural = false + "";
			str = utils.returnResultoFail(name, method, status + "", "0",
					lineNumber);
		}

	}

	public void testIMultiScreenService_findSPs_iPress_010() throws Exception,
			Exception {
		String name = "testIMultiScreenService_findSPs_iPress_010";
		String method = "IMultiScreenService.findSPs";
		int status = -1;
		getInfo();
		try {
			for (int i = 0; i < 10000; i++) {
				status = mService.findSPs();
				lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (status == 0) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str = utils.returnResultoFail(name, method, status + "", "0",
					lineNumber);
		}
	}

	public void testIMultiScreenService_setCallBack_iNorm_010()
			throws Exception {
		String name = "testIMultiScreenService_setCallBack_iNorm_010";
		String method = "IMultiScreenService.setCallBack";
		int status = -1;
		getInfo();
		try {
			lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
			status = mService.setCallBack(mCallBack);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (status == 0) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str = utils.returnResultoFail(name, method, status + "", "0",
					lineNumber);
		}
	}

	public void testIMultiScreenService_setCallBack_iPerf_010() {
		String name = "testIMultiScreenService_setCallBack_iPerf_010";
		String method = "IMultiScreenService.setCallBack";
		int status = -1;
		try {
			for (int i = 0; i < 10; i++) {
				begindate = new Date();
				status = mService.setCallBack(mCallBack);
				lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
				enddate = new Date();
				calculatTiming(enddate, begindate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String actural2 = utils.getINromDate(timeDiffrence) + "";
		String expect2 = "1000";
		boolean returnresult = utils.isNormal(timeDiffrence);
		if (status == 0) {
			if (returnresult) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			} else {
				str = utils.returnResultoFail(name, method, actural2, expect2,
						lineNumber);
			}

		} else {
			str = utils.returnResultoFail(name, method, status + "", "0",
					lineNumber);
		}

	}

	public void testIMultiScreenService_setCallBack_iPress_010() {
		String name = "testIMultiScreenService_setCallBack_iPress_010";
		String method = "IMultiScreenService.setCallBack";
		int status = -1;
		try {
			for (int i = 0; i < 10000; i++) {
				status = mService.setCallBack(mCallBack);
				lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (status == 0) {
			str = utils.returnResultofTrue(name, method, lineNumber + 3);
		} else {
			str = utils.returnResultoFail(name, method, status + "", "",
					lineNumber + 3);
		}
	}

	public void testIMultiScreenService_connect_iNorm_010() throws Exception {
		String name = "testIMultiScreenService_connect_iNorm_010";
		String method = "IMultiScreenService.connect";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		int status = -1;
		info = getInfo();
		Log.i("dsa","info  "+info);
		if (info != null) {
			status=	mService.connect(info.spName, info.spDeviceType,
					info.spServiceInfo, info.spVersion, info.ipaddress,
					info.port, info.hostname);
			Log.i("dsa","info.spName  "+info.spName);
			
			if (status == 0) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			} else {
				str = utils.returnResultoFail(name, method, status + "", "0",
						lineNumber);
			}
			Log.i("dsa","status connect  "+status);
		}
	}

	public void testIMultiScreenService_connect_iAbnm_010() throws Exception {
		String name = "testIMultiScreenService_connect_iAbnm_010";
		String method = "IMultiScreenService.connect";
		int status = -1;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		info = getInfo();
		if (info != null) {
			mService.stopMultiScreenClient();
			Thread.sleep(3000);
			lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
			status = mService.connect(info.spName, info.spDeviceType,
					info.spServiceInfo, info.spVersion, info.ipaddress,
					info.port, info.hostname);
			Thread.sleep(3000);
			if (status != 0) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			} else {
				str = utils.returnResultoFail(name, method, status + "", "-1",
						lineNumber);
			}
		} else {
			str = utils.returnResultoFail(name, method, "null", "info",
					lineNumber);
		}

	}

	public void testIMultiScreenService_connect_iAbnm_011() throws Exception {
		String name = "testIMultiScreenService_connect_iAbnm_011";
		String method = "IMultiScreenService.connect";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		info = getInfo();
		int status = 0;
		if (info!=null) {			
			lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
			status = mClient.connect("", "",
					"","", "",
					-1, "tvostest");
			Thread.sleep(3000);
			if (status != 0) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			} else {
				str = utils.returnResultoFail(name, method, status + "",
						"-1", lineNumber);
			}
		}else {
			str = utils.returnResultoFail(name, method, info+"", "SPInfo", lineNumber);
		}
	}

	public void testIMultiScreenService_connect_iPerf_010() throws Exception,
			Exception {
		String name = "testIMultiScreenService_connect_iPerf_010";
		String method = "IMultiScreenService.connect";
		int status = -1;
		info = getInfo();
		try {
			lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
			if (info != null) {
				for (int i = 0; i < 10; i++) {
					begindate = new Date();
					status = mService.connect(info.spName, info.spDeviceType,
							info.spServiceInfo, info.spVersion, info.ipaddress,
							info.port, info.hostname);
					lineNumber = new Throwable().getStackTrace()[0]
							.getLineNumber();
					enddate = new Date();
					calculatTiming(enddate, begindate);
				}

				String actural = "";
				String expect = true + "";
				String actural2 = utils.getINromDate(timeDiffrence) + "";
				String expect2 = "1000";
				boolean returnresult = utils.isNormal(timeDiffrence);
				if (status == 0) {
					if (returnresult) {
						str = utils
								.returnResultofTrue(name, method, lineNumber);
					} else {
						str = utils.returnResultoFail(name, method, actural2,
								expect2, lineNumber);
					}

				} else {
					actural = false + "";
					str = utils.returnResultoFail(name, method, status + "",
							"0", lineNumber);
				}
			} else {
				str = utils.returnResultoFail(name, method, "0", "1",
						lineNumber);
			}
		} catch (Exception e) {
			str = utils.returnResultoFail(name, method, "Exception", "NULL",
					lineNumber);
			e.printStackTrace();
		}

	}

	public void testIMultiScreenService_connect_iPress_010() throws Exception,
			Exception {
		String name = "testIMultiScreenService_connect_iPress_010";
		String method = "IMultiScreenService.connect";
		int status = -1;
		info = getInfo();
		try {
			lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
			if (info != null) {
				for (int i = 0; i < 10000; i++) {
					status = mService.connect(info.spName, info.spDeviceType,
							info.spServiceInfo, info.spVersion, info.ipaddress,
							info.port, info.hostname);
					lineNumber = new Throwable().getStackTrace()[0]
							.getLineNumber();
				}
				if (status == 0) {
					str = utils.returnResultofTrue(name, method, lineNumber);
				} else {
					str = utils.returnResultoFail(name, method, status + "",
							"0", lineNumber);
				}
			} else {
				str = utils.returnResultoFail(name, method, "0", "1",
						lineNumber);
			}
		} catch (Exception e) {
			str = utils.returnResultoFail(name, method, "Exception", "NULL",
					lineNumber);
			e.printStackTrace();
		}

	}

	public void testIMultiScreenService_queryInfo_iNorm_010() throws Exception,
			Exception {
		String name = "testIMultiScreenService_queryInfo_iNorm_010";
		String method = "IMultiScreenService.queryInfo";
		int status = -1;
		info = getInfo();
		try {
			if (info != null) {
				mService.connect(info.spName, info.spDeviceType,
						info.spServiceInfo, info.spVersion, info.ipaddress,
						info.port, info.hostname);
				Thread.sleep(3000);
				lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
				status = mService.queryInfo(
						mCallBack.getServiceProvideInfo().ipaddress,
						mCallBack.getServiceProvideInfo().port,
						mCallBack.getServiceProvideInfo().hostname, mUUID,
						"queryname", "hello tvos?");
				if (status == 0) {
					str = utils.returnResultofTrue(name, method, lineNumber);
				} else {
					str = utils.returnResultoFail(name, method, status + "",
							"0", lineNumber);
				}
			} else {
				str = utils.returnResultoFail(name, method, "null",
						"ServiceProvideInfo", lineNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
			str = utils.returnResultoFail(name, method, "Exception", "Norm",
					lineNumber);
		}

	}

	public void testIMultiScreenService_queryInfo_iAbnm_010() throws Exception,
			Exception {
		String name = "testIMultiScreenService_queryInfo_iAbnm_010";
		String method = "IMultiScreenService.queryInfo";
		int status = -1;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		try {
			status = mService.queryInfo("192.168.2.57", 35464, "192.168.2.57",
					mUUID, "queryname", "fuck?");
			if (status != 0) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			} else {
				str = utils.returnResultoFail(name, method, status + "",
						"error code", lineNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
			str = utils.returnResultoFail(name, method, "Exception", "Norm",
					lineNumber);
		}

	}

	public void testIMultiScreenService_queryInfo_iAbnm_011() throws Exception,
			Exception {
		String name = "testIMultiScreenService_queryInfo_iAbnm_011";
		String method = "IMultiScreenService.queryInfo";
		int status = -1;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		info = getInfo();
		if (info != null) {
			try {
				mClient.connect(info.spName, info.spDeviceType,
						info.spServiceInfo, info.spVersion, info.ipaddress,
						info.port, info.hostname);
				Thread.sleep(3000);
				lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
				status = mClient.queryInfo("", -1, "", "-1", "ccmmdd", "test");
				if (status != 0) {
					str = utils.returnResultofTrue(name, method, lineNumber);
				} else {
					str = utils.returnResultoFail(name, method, status + "",
							"error code", lineNumber);
				}

			} catch (Exception e) {
				e.printStackTrace();
				str = utils.returnResultoFail(name, method, "Exception",
						"Norm", lineNumber);
			}
		} else {
			str = utils.returnResultoFail(name, method, "NULL", "SPInfo",
					lineNumber);
		}
	}

	public void testIMultiScreenService_queryInfo_iPerf_010()
			throws ParseException, Exception, Exception {
		String name = "testIMultiScreenService_queryInfo_iPerf_010";
		String method = "IMultiScreenService.queryInfo";
		int status = -1;
		info = getInfo();
		try {
			if (info != null) {
				mService.connect(info.spName, info.spDeviceType,
						info.spServiceInfo, info.spVersion, info.ipaddress,
						info.port, info.hostname);
				for (int i = 0; i < 10; i++) {
					begindate = new Date();
					lineNumber = new Throwable().getStackTrace()[0]
							.getLineNumber();
					status = mService.queryInfo(
							mCallBack.getServiceProvideInfo().ipaddress,
							mCallBack.getServiceProvideInfo().port,
							mCallBack.getServiceProvideInfo().hostname, mUUID,
							"queryname", "fuck?");
					enddate = new Date();
					calculatTiming(enddate, begindate);
				}

				String actural2 = utils.getINromDate(timeDiffrence) + "";
				String expect2 = "1000";
				boolean returnresult = utils.isNormal(timeDiffrence);
				if (status == 0) {
					if (returnresult) {
						str = utils
								.returnResultofTrue(name, method, lineNumber);
					} else {
						str = utils.returnResultoFail(name, method, actural2,
								expect2, lineNumber);
					}

				} else {
					str = utils.returnResultoFail(name, method, status + "",
							"0", lineNumber);
				}
			} else {
				str = utils.returnResultoFail(name, method, "null",
						"ServiceProvideInfo", lineNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
			str = utils.returnResultoFail(name, method, "Exception", "Norm",
					lineNumber);
		}

	}

	public void testIMultiScreenService_queryInfo_iPress_010()
			throws Exception, Exception {
		String name = "testIMultiScreenService_queryInfo_iPress_010";
		String method = "IMultiScreenService.queryInfo";
		int status = -1;
		info = getInfo();
		try {
			if (info != null) {
				mService.connect(info.spName, info.spDeviceType,
						info.spServiceInfo, info.spVersion, info.ipaddress,
						info.port, info.hostname);
				lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
				for (int i = 0; i < 10000; i++) {
					status = mService.queryInfo(
							mCallBack.getServiceProvideInfo().ipaddress,
							mCallBack.getServiceProvideInfo().port,
							mCallBack.getServiceProvideInfo().hostname, mUUID,
							"queryname", "fuck?");
				}
				if (status == 0) {
					str = utils.returnResultofTrue(name, method, lineNumber);
				} else {
					str = utils.returnResultoFail(name, method, status + "",
							"0", lineNumber);
				}
			} else {
				str = utils.returnResultoFail(name, method, "null",
						"ServiceProvideInfo", lineNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
			str = utils.returnResultoFail(name, method, "Exception", "Norm",
					lineNumber);
		}

	}

	public void testIMultiScreenService_execCmd_iNorm_010() throws Exception {
		String name = "testIMultiScreenService_execCmd_iNorm_010";
		String method = "IMultiScreenService.execCmd";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		int status = -1;
		info = getInfo();
		mCallBack = null;
		mCallBack = new MultiScreenCallBack(this);
		String clientName = "Cli:" + DID;
		mService.startMultiScreenClient(clientName);
		 Log.i("dsa","mcallBack  "+mCallBack+"  "+"clientName  "+clientName);
		Log.i("dsa","info  "+info);
		if (info != null) {
			mService.connect(info.spName, info.spDeviceType,
					info.spServiceInfo, info.spVersion, info.ipaddress,
					info.port, info.hostname);
			Log.i("dsa","info.spName  "+info.spName+"  "+"info.ipaddress  "+info.ipaddress);
			status = mService.execCmd(info.ipaddress, info.port, info.hostname, "cmd",
					"param");
			Log.i("dsa","ipaddress  "+mCallBack.getServiceProvideInfo().ipaddress+"  "+"port  "+mCallBack.getServiceProvideInfo().port
					+"  "+"hostname  "+mCallBack.getServiceProvideInfo().hostname);
			if (status == 0) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			} else {
				str = utils.returnResultoFail(name, method, status + "", "0",
						lineNumber);
			}

		} else {
			str = utils.returnResultoFail(name, method, "null", "info",
					lineNumber);
		}
	}

	public void testIMultiScreenService_execCmd_iAbnm_010() throws Exception {
		String name = "testIMultiScreenService_execCmd_iAbnm_010";
		String method = "IMultiScreenService.execCmd";
		int status = -1;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		info = getInfo();
		Log.i("dsa","info  "+info);
		if (info != null) {
			mService.stopMultiScreenClient();
			mService.stopMultiScreenServer();
			status = mService.execCmd(info.ipaddress, info.port, info.hostname,
					"cmd", "param");
			Log.i("dsa","status  "+status);
		} else {
			str = utils.returnResultoFail(name, method, "null",
					"ServiceProvideInfo", lineNumber);
		}
		if (status != 0) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str = utils.returnResultoFail(name, method, status + "",
					"error code", lineNumber);
		}
	}

	public void testIMultiScreenService_execCmd_iAbnm_011() throws Exception,
			Exception {
		String name = "testIMultiScreenService_execCmd_iAbnm_011";
		String method = "IMultiScreenService.execCmd";
		int status = -1;
		info = getInfo();
		try {
			if (info != null) {
				mClient.connect(info.spName, info.spDeviceType,
						info.spServiceInfo, info.spVersion, info.ipaddress,
						info.port, info.hostname);
				lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
				status = mClient.execCmd("168.126.121.1", 65539, "tvostets",
						"tvostest", "tvostest");
				if (status != 0) {
					str = utils.returnResultofTrue(name, method, lineNumber);
				} else {
					str = utils.returnResultoFail(name, method, status + "",
							"error code", lineNumber);
				}
			} else {
				str = utils.returnResultoFail(name, method, "null",
						"ServiceProvideInfo", lineNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testIMultiScreenService_execCmd_iPerf_010() throws Exception,
			Exception {
		String name = "testIMultiScreenService_execCmd_iPerf_010";
		String method = "IMultiScreenService.execCmd";
		int status = -1;
		info = getInfo();
		try {

			if (info != null) {
				mService.connect(info.spName, info.spDeviceType,
						info.spServiceInfo, info.spVersion, info.ipaddress,
						info.port, info.hostname);
				for (int i = 0; i < 10; i++) {
					begindate = new Date();
					lineNumber = new Throwable().getStackTrace()[0]
							.getLineNumber();
					status = mService.execCmd(
							mCallBack.getServiceProvideInfo().ipaddress,
							mCallBack.getServiceProvideInfo().port,
							mCallBack.getServiceProvideInfo().hostname, "cmd",
							"param");
					enddate = new Date();
					calculatTiming(enddate, begindate);
				}

			} else {
				str = utils.returnResultoFail(name, method, "null",
						"ServiceProvideInfo", lineNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String actural2 = utils.getINromDate(timeDiffrence) + "";
		String expect2 = "1000";
		boolean returnresult = utils.isNormal(timeDiffrence);
		if (status == 0) {
			if (returnresult) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			} else {
				str = utils.returnResultoFail(name, method, actural2, expect2,
						lineNumber);
			}

		} else {
			str = utils.returnResultoFail(name, method, status + "", "0",
					lineNumber);
		}

	}

	public void testIMultiScreenService_execCmd_iPress_010() throws Exception,
			Exception {
		String name = "testIMultiScreenService_execCmd_iPress_010";
		String method = "IMultiScreenService.execCmd";
		int status = -1;
		info = getInfo();
		try {

			if (info != null) {
				mService.connect(info.spName, info.spDeviceType,
						info.spServiceInfo, info.spVersion, info.ipaddress,
						info.port, info.hostname);
				for (int i = 0; i < 10000; i++) {
					lineNumber = new Throwable().getStackTrace()[0]
							.getLineNumber();
					status = mService.execCmd(info.ipaddress, info.port,
							info.hostname, "cmd", "param");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (status == 0) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str = utils.returnResultoFail(name, method, status + "", "0",
					lineNumber);
		}
	}

	public void testIMultiScreenService_inputKeyCode_iNorm_010()
			throws Exception {
		String name = "testIMultiScreenService_inputKeyCode_iNorm_010";
		String method = "IMultiScreenService.inputKeyCode";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		int status = -1;
		info = getInfo();
		if (info != null) {
			mService.connect(info.spName, info.spDeviceType,
					info.spServiceInfo, info.spVersion, info.ipaddress,
					info.port, info.hostname);
		int status_execCmd=	mService.execCmd(info.ipaddress, info.port, info.hostname, "cmd",
					"param");
		Log.i("dsa","status_execCmd  "+status_execCmd);
			status = mService.inputKeyCode(info.ipaddress, info.port,
					info.hostname, "inputkeycode", "cmdparam");
			if (status == 0) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			} else {
				str = utils.returnResultoFail(name, method, status + "", "0",
						lineNumber);
			}

		} else {
			str = utils.returnResultoFail(name, method, "null", "info",
					lineNumber);
		}

	}

	public void testIMultiScreenService_inputKeyCode_iAbnm_010()
			throws Exception, Exception {
		String name = "testIMultiScreenService_inputKeyCode_iAbnm_010";
		String method = "IMultiScreenService.inputKeyCode";
		int status = -1;
		info = getInfo();
		try {

			if (info != null) {
				mService.stopMultiScreenClient();
				mService.stopMultiScreenServer();
				mService.connect(info.spName, info.spDeviceType,
						info.spServiceInfo, info.spVersion, info.ipaddress,
						info.port, info.hostname);
				lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
				status = mService.inputKeyCode(info.ipaddress, info.port,
						info.hostname, "inputkeycode", "cmdparam");

			} else {
				str = utils.returnResultoFail(name, method, "null",
						"ServiceProvideInfo", lineNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.i("dsa","status  "+status);

		if (status != 0) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str = utils.returnResultoFail(name, method, status + "",
					"error code", lineNumber);
		}
	}

	public void testIMultiScreenService_inputKeyCode_iAbnm_011()
			throws Exception, Exception {
		String name = "testIMultiScreenService_inputKeyCode_iAbnm_011";
		String method = "IMultiScreenService.inputKeyCode";
		int status = -1;
		info = getInfo();
		try {
			if (info != null) {
				mClient.connect(info.spName, info.spDeviceType,
						info.spServiceInfo, info.spVersion, info.ipaddress,
						info.port, info.hostname);
				status = mService.execCmd(info.ipaddress, info.port,
						info.hostname, "input keyevent", "10");
				Log.i("dsa","status execCmd "+status);
				lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
				status = mClient.inputKeyCode("dsa", info.port,
						info.hostname, "ccmmdd", "test");
				Log.i("dsa","status inputKeyCode "+status);
			//	Thread.sleep(60000);
				
				if (status != 0) {
					str = utils.returnResultofTrue(name, method, lineNumber);
				} else {
					str = utils.returnResultoFail(name, method, status + "",
							"error code", lineNumber);
				}
			} else {
				str = utils.returnResultoFail(name, method, "null",
						"ServiceProvideInfo", lineNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void testIMultiScreenService_inputKeyCode_iPerf_010()
			throws ParseException, Exception, Exception {
		String name = "testIMultiScreenService_inputKeyCode_iPerf_010";
		String method = "IMultiScreenService.inputKeyCode";
		int status = -1;
		info = getInfo();
		try {

			if (info != null) {
				mService.connect(info.spName, info.spDeviceType,
						info.spServiceInfo, info.spVersion, info.ipaddress,
						info.port, info.hostname);
				status = mService.execCmd(info.ipaddress, info.port,
						info.hostname, "cmd", "param");
				for (int i = 0; i < 10; i++) {
					begindate = new Date();
					lineNumber = new Throwable().getStackTrace()[0]
							.getLineNumber();
					status = mService.inputKeyCode(info.ipaddress, info.port,
							info.hostname, "inputkeycode", "cmdparam");
					enddate = new Date();
					calculatTiming(enddate, begindate);
				}
			} else {
				str = utils.returnResultoFail(name, method, "null",
						"ServiceProvideInfo", lineNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String actural2 = utils.getINromDate(timeDiffrence) + "";
		String expect2 = "1000";
		boolean returnresult = utils.isNormal(timeDiffrence);
		if (status == 0) {
			if (returnresult) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			} else {
				str = utils.returnResultoFail(name, method, actural2, expect2,
						lineNumber);
			}

		} else {
			str = utils.returnResultoFail(name, method, status + "", "0",
					lineNumber);
		}

	}

	public void testIMultiScreenService_inputKeyCode_iPress_010()
			throws Exception {
		String name = "testIMultiScreenService_inputKeyCode_iPress_010";
		String method = "IMultiScreenService.inputKeyCode";
		int status = -1;
		try {
			info = getInfo();
			if (info != null) {
				mService.connect(info.spName, info.spDeviceType,
						info.spServiceInfo, info.spVersion, info.ipaddress,
						info.port, info.hostname);
				status = mService.execCmd(info.ipaddress, info.port,
						info.hostname, "cmd", "param");
				for (int i = 0; i < 10000; i++) {
					lineNumber = new Throwable().getStackTrace()[0]
							.getLineNumber();
					status = mService.inputKeyCode(info.ipaddress, info.port,
							info.hostname, "inputkeycode", "cmdparam");
				}

			} else {
				str = utils.returnResultoFail(name, method, "null",
						"ServiceProvideInfo", lineNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (status == 0) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str = utils.returnResultoFail(name, method, status + "", "0",
					lineNumber);
		}
	}

	public void testIMultiScreenService_boardCastAllDevice_iNorm_010()
			throws Exception {
		String name = "testIMultiScreenService_boardCastAllDevice_iNorm_010";
		String method = "IMultiScreenService.boardCastAllDevice";
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		int status = -1;
		info = getInfo();
		if (info != null) {
			mService.connect(info.spName, info.spDeviceType,
					info.spServiceInfo, info.spVersion, info.ipaddress,
					info.port, info.hostname);
			status = mService.boardCastAllDevice("Radio instruction",
					"Attach param");
			if (status == 0) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			} else {
				str = utils.returnResultoFail(name, method, status + "", "0",
						lineNumber);
			}

		} else {
			str = utils.returnResultoFail(name, method, "null", "info",
					lineNumber);
		}

		mService.stopMultiScreenClient();
		mService.stopMultiScreenServer();
	}

	public void testIMultiScreenService_boardCastAllDevice_iAbnm_010()
			throws Exception {
		String name = "testIMultiScreenService_boardCastAllDevice_iAbnm_010";
		String method = "IMultiScreenService.boardCastAllDevice";
		int status = -1;
		try {
			info = getInfo();
			if (info != null) {
				mService.stopMultiScreenClient();
				mService.stopMultiScreenServer();
				lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
				status = mService.boardCastAllDevice("Radio instruction",
						"Attach param");

			} else {
				str = utils.returnResultoFail(name, method, "null",
						"ServiceProvideInfo", lineNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (status != 0) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str = utils.returnResultoFail(name, method, status + "",
					"error code", lineNumber);
		}
	}

	public void testIMultiScreenService_boardCastAllDevice_iAbnm_011()
			throws Exception {
		String name = "testIMultiScreenService_boardCastAllDevice_iAbnm_011";
		String method = "IMultiScreenService.boardCastAllDevice";
		int status = -1;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		info = getInfo();
		if (info != null) {
			mClient.connect(info.spName, info.spDeviceType,
					info.spServiceInfo, info.spVersion, info.ipaddress,
					info.port, info.hostname);
			Thread.sleep(5000);
			String d=null;
			status = mService.boardCastAllDevice(d, "tvostest");
			Log.i("dsa","status  "+status);
			if (status != 0) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			} else {
				str = utils.returnResultoFail(name, method, status + "",
						"error code", lineNumber);
			}
		} else {
			str = utils.returnResultoFail(name, method, "null",
					"ServiceProvideInfo", lineNumber);
		}
	}

	public void testIMultiScreenService_boardCastAllDevice_iPerf_010() {
		String name = "testIMultiScreenService_boardCastAllDevice_iPerf_010";
		String method = "IMultiScreenService.boardCastAllDevice";
		int status = -1;
		try {
			info = getInfo();
			if (info != null) {
				mService.connect(info.spName, info.spDeviceType,
						info.spServiceInfo, info.spVersion, info.ipaddress,
						info.port, info.hostname);
				for (int i = 0; i < 10; i++) {
					begindate = new Date();
					lineNumber = new Throwable().getStackTrace()[0]
							.getLineNumber();
					status = mService.boardCastAllDevice("Radio instruction",
							"Attach param");
					enddate = new Date();
					calculatTiming(enddate, begindate);
				}

			} else {
				str = utils.returnResultoFail(name, method, "null",
						"ServiceProvideInfo", lineNumber);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		String actural2 = utils.getINromDate(timeDiffrence) + "";
		String expect2 = "1000";
		boolean returnresult = utils.isNormal(timeDiffrence);
		if (status == 0) {
			if (returnresult) {
				str = utils.returnResultofTrue(name, method, lineNumber);
			} else {
				str = utils.returnResultoFail(name, method, actural2, expect2,
						lineNumber);
			}

		} else {
			str = utils.returnResultoFail(name, method, status + "", "0",
					lineNumber);
		}

	}

	public void testIMultiScreenService_boardCastAllDevice_iPress_010() {
		String name = "testIMultiScreenService_boardCastAllDevice_iPress_010";
		String method = "IMultiScreenService.boardCastAllDevice";
		int status = -1;
		try {
			info = getInfo();
			if (info != null) {
				mService.connect(info.spName, info.spDeviceType,
						info.spServiceInfo, info.spVersion, info.ipaddress,
						info.port, info.hostname);
				lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
				for (int i = 0; i < 10000; i++) {
					status = mService.boardCastAllDevice("Radio instruction",
							"Attach param");
				}
			} else {
				str = utils.returnResultoFail(name, method, "null",
						"ServiceProvideInfo", lineNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (status == 0) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str = utils.returnResultoFail(name, method, status + "", "0",
					lineNumber);
		}
	}

	public void calculatTiming(Date enddate, Date begindate)
			throws ParseException {
		s = sdf.parse(sdf.format(enddate)).getTime()
				- sdf.parse(sdf.format(begindate)).getTime();
		sum += s;
		timeDiffrence.add(s);
	}

	public void testIMultiScreenService_START_MULTISCREENSERVER_iNorm_010() {
		String name = "testIMultiScreenService_START_MULTISCREENSERVER_iNorm_010";
		String method = "IMultiScreenService.START_MULTISCREENSERVER";
		int actur = IMultiScreenService.START_MULTISCREENSERVER;
		int expec = mBinder.FIRST_CALL_TRANSACTION + 0;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		if (actur == expec) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str = utils.returnResultoFail(name, method, actur + "", expec + "",
					lineNumber);
		}
	}

	public void testIMultiScreenService_STOP_MULTISCREENSERVER_iNorm_010() {
		String name = "testIMultiScreenService_STOP_MULTISCREENSERVER_iNorm_010";
		String method = "IMultiScreenService.STOP_MULTISCREENSERVER";
		int actur = IMultiScreenService.STOP_MULTISCREENSERVER;
		int expec = mBinder.FIRST_CALL_TRANSACTION + 1;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		if (actur == expec) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str = utils.returnResultoFail(name, method, actur + "", expec + "",
					lineNumber);
		}
	}

	public void testIMultiScreenService_START_MULTISCREENCLIENT_iNorm_010() {
		String name = "testIMultiScreenService_START_MULTISCREENCLIENT_iNorm_010";
		String method = "IMultiScreenService.START_MULTISCREENCLIENT";
		int actur = IMultiScreenService.START_MULTISCREENCLIENT;
		int expec = mBinder.FIRST_CALL_TRANSACTION + 2;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		if (actur == expec) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str = utils.returnResultoFail(name, method, actur + "", expec + "",
					lineNumber);
		}
	}

	public void testIMultiScreenService_STOP_MULTISCREENCLIENT_iNorm_010() {
		String name = "testIMultiScreenService_STOP_MULTISCREENCLIENT_iNorm_010";
		String method = "IMultiScreenService.STOP_MULTISCREENCLIENT";
		int actur = IMultiScreenService.STOP_MULTISCREENCLIENT;
		int expec = mBinder.FIRST_CALL_TRANSACTION + 3;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		if (actur == expec) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str = utils.returnResultoFail(name, method, actur + "", expec + "",
					lineNumber);
		}
	}

	public void testIMultiScreenService_FIND_SPS_iNorm_010() {
		String name = "testIMultiScreenService_FIND_SPS_iNorm_010";
		String method = "IMultiScreenService.FIND_SPS";
		int actur = IMultiScreenService.FIND_SPS;
		int expec = mBinder.FIRST_CALL_TRANSACTION + 4;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		if (actur == expec) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str = utils.returnResultoFail(name, method, actur + "", expec + "",
					lineNumber);
		}
	}

	public void testIMultiScreenService_CONNECT_iNorm_010() {
		String name = "testIMultiScreenService_CONNECT_iNorm_010";
		String method = "IMultiScreenService.CONNECT";
		int actur = IMultiScreenService.CONNECT;
		int expec = mBinder.FIRST_CALL_TRANSACTION + 5;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		if (actur == expec) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str = utils.returnResultoFail(name, method, actur + "", expec + "",
					lineNumber);
		}
	}

	public void testIMultiScreenService_SET_CALLBACK_iNorm_010() {
		String name = "testIMultiScreenService_SET_CALLBACK_iNorm_010";
		String method = "IMultiScreenService.SET_CALLBACK";
		int actur = IMultiScreenService.SET_CALLBACK;
		int expec = mBinder.FIRST_CALL_TRANSACTION + 6;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		if (actur == expec) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str = utils.returnResultoFail(name, method, actur + "", expec + "",
					lineNumber);
		}
	}

	public void testIMultiScreenService_QUERY_INFO_iNorm_010() {
		String name = "testIMultiScreenService_QUERY_INFO_iNorm_010";
		String method = "IMultiScreenService.QUERY_INFO";
		int actur = IMultiScreenService.QUERY_INFO;
		int expec = mBinder.FIRST_CALL_TRANSACTION + 7;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		if (actur == expec) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str = utils.returnResultoFail(name, method, actur + "", expec + "",
					lineNumber);
		}
	}

	public void testIMultiScreenService_EXEC_CMD_iNorm_010() {
		String name = "testIMultiScreenService_EXEC_CMD_iNorm_010";
		String method = "IMultiScreenService.EXEC_CMD";
		int actur = IMultiScreenService.EXEC_CMD;
		int expec = mBinder.FIRST_CALL_TRANSACTION + 8;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		if (actur == expec) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str = utils.returnResultoFail(name, method, actur + "", expec + "",
					lineNumber);
		}
	}

	public void testIMultiScreenService_INPUT_KEYCODE_iNorm_010() {
		String name = "testIMultiScreenService_INPUT_KEYCODE_iNorm_010";
		String method = "IMultiScreenService.INPUT_KEYCODE";
		int actur = IMultiScreenService.INPUT_KEYCODE;
		int expec = mBinder.FIRST_CALL_TRANSACTION + 9;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		if (actur == expec) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str = utils.returnResultoFail(name, method, actur + "", expec + "",
					lineNumber);
		}
	}

	public void testIMultiScreenService_NOTIFY_ALL_REMOTE_iNorm_010() {
		String name = "testIMultiScreenService_NOTIFY_ALL_REMOTE_iNorm_010";
		String method = "IMultiScreenService.NOTIFY_ALL_REMOTE";
		int actur = IMultiScreenService.NOTIFY_ALL_REMOTE;
		int expec = mBinder.FIRST_CALL_TRANSACTION + 10;
		lineNumber = new Throwable().getStackTrace()[0].getLineNumber();
		if (actur == expec) {
			str = utils.returnResultofTrue(name, method, lineNumber);
		} else {
			str = utils.returnResultoFail(name, method, actur + "", expec + "",
					lineNumber);
		}
	}

	@Override
	protected void tearDown() throws Exception {
		FileWriter fw = new FileWriter(file);
		fw.write(str);
		fw.write("\r\n");
		fw.flush();
		fw.close();
		str = "";
		super.tearDown();
	}

	@Override
	public String onQuery(String queryId, String attribute, String param) {
		accpect = true;
		return null;
	}

	@Override
	public void onQueryResponse(String queryId, String attribute, String param) {
		// TODO Auto-generated method stub

	}

}
