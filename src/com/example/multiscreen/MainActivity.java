package com.example.multiscreen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

import org.tvos.multiscreen.IMultiScreenService;
import org.tvos.multiscreen.MultiScreenCallBack;
import org.tvos.multiscreen.MultiScreenCallBack.MultiScreenCallBackListener;
import org.tvos.multiscreen.MultiScreenService;
import org.tvos.multiscreen.ServiceProvideInfo;

@SuppressLint("NewApi") public class MainActivity extends Activity implements View.OnClickListener,MultiScreenCallBackListener {

    private static final String SERVICEDESCRIPTOR = "tvos.multiscreen";
    private static final String TAG = "dsa";

    private IBinder mBinder;
    private static MultiScreenService mService;


    private TextView textTv = null;
    private Button startBt = null;
    private Button stopBt = null;
    private Button findBt = null;
    private Button connectBt = null;
    private Button disconnectBt = null;
    private Button setCbBt = null;
    private Button startCliBt = null;
    private Button stopCliBt = null;
    private Button queryInfoBt = null;
    private Button execCmdBt = null;
    private Button inputKeyCodeBt = null;
    private Button broadCastBt = null;

    private static final String DID =android.os.Build.SERIAL;
    private StringBuilder stringBuild;
    private IMultiScreenService screenService = null;
    private MultiScreenCallBack mCallBack = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screenService = new MultiScreenService();
        textTv = (TextView) findViewById(R.id.content);
        startBt = (Button) findViewById(R.id.button_start);
        stopBt = (Button) findViewById(R.id.button_stop);
        findBt = (Button) findViewById(R.id.button_find);
        connectBt = (Button) findViewById(R.id.button_connect);
        disconnectBt = (Button) findViewById(R.id.button_disconnect);
        setCbBt = (Button) findViewById(R.id.button_setcb);
        startCliBt = (Button)findViewById(R.id.button_startcli);
        stopCliBt = (Button)findViewById(R.id.button_stopcli);
        queryInfoBt = (Button)findViewById(R.id.button_queryinfo);
        execCmdBt = (Button)findViewById(R.id.button_execmd);
        inputKeyCodeBt = (Button)findViewById(R.id.button_inputkeycode);
        broadCastBt = (Button)findViewById(R.id.button_broadcast);

        startBt.setOnClickListener(this);
        stopBt.setOnClickListener(this);
        findBt.setOnClickListener(this);
        connectBt.setOnClickListener(this);
        disconnectBt.setOnClickListener(this);
        setCbBt.setOnClickListener(this);
        startCliBt.setOnClickListener(this);
        stopCliBt.setOnClickListener(this);
        queryInfoBt.setOnClickListener(this);
        execCmdBt.setOnClickListener(this);
        inputKeyCodeBt.setOnClickListener(this);
        broadCastBt.setOnClickListener(this);

        stringBuild = new StringBuilder(DID);
        stringBuild.append("\r\n");
        try {
            mBinder =  ServiceManager.getService(SERVICEDESCRIPTOR);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mService = new MultiScreenService();
        if (null == mService || null == mBinder) {
            appendStringBuild("ServiceManager.getService MultiScreenService Null , Please Check If the TVOS MultiScreen Component is Ready!");
        }

        textTv.setText(stringBuild.toString());

        mCallBack = new MultiScreenCallBack(this);
        Log.i("dsa","mcallBack  "+mCallBack);
    }

    private void appendStringBuild(String appendString)
    {
        stringBuild.append(appendString);
        stringBuild.append("\r\n");
    }

    private void cleanAndAppendString(String appendString)
    {
        stringBuild.delete(0, stringBuild.length());
        stringBuild.append(DID);
        stringBuild.append("\r\n");
        stringBuild.append(appendString);
        stringBuild.append("\r\n");
    }

    public void onClick(View v) {
        if (v.equals(startBt)) {
           String spName = "Sev:" + DID;
        	//String spName = "TVOS DMR";
            String spVersion = "0.0.1";
            String deviceType = "TV";
            int status = -1;
            Log.i("dsa","spName  "+spName);
            try {
                     screenService.startMultiScreenServer("TVOS_MULTI_SCREEN", "TVOS", "Service Info", "1.0", "ip address", 33333, "Host Name");
                      status = screenService.setCallBack(new MultiScreenCallBack(new MultiScreenCallBack.MultiScreenCallBackListener() {
                         @Override
                         public String onQuery(String s, String s1, String s2) {
                             return null;
                         }

                         @Override
                         public void onQueryResponse(String s, String s1, String s2) {
                             //handleCmd(s, s1, s2);
                         }
                     }));
                Log.i("dsa","status  "+status);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            cleanAndAppendString("mService  startMultiScreenServer  status = " + status + " that status 0 is ok,otherwise is bad.");
            textTv.setText(stringBuild.toString());
        	
        } else if (v.equals(stopBt)) {
            int status = -1;
            try {
                status = mService.stopMultiScreenServer();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            cleanAndAppendString("mService  stopMultiScreenServer  status = " + status + " that status 0 is ok,otherwise is bad.");
            textTv.setText(stringBuild.toString());
        } else if (v.equals(findBt)) {

            int status = -1;
            try {
                status = mService.findSPs();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Log.i("dsa","status  "+status);
            cleanAndAppendString("mService  findSPs  status = " + status + " that status 0 is ok,otherwise is bad.");
            textTv.setText(stringBuild.toString());

        } else if (v.equals(connectBt)) {

            List<ServiceProvideInfo> mList =  mCallBack.getServiceProvideList();
            Log.i("dsa","mList  "+mList);
            if(mList.size()>0)
            {
                ServiceProvideInfo info = mList.get(0);
                Log.i("dsa","connectbt info "+info);
                int status = -1;
                try {
                	Log.i("dsa","info.spName  "+info.spName);
                    //String spName, String spDeviceType, String spServiceInfo, String ipaddress, int port, String hostname)
                    status = mService.connect(info.spName,info.spDeviceType,info.spServiceInfo,info.spVersion,info.ipaddress,info.port,info.hostname);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                cleanAndAppendString("mService  connect  status = " + status + " that status 0 is ok,otherwise is bad.");
                textTv.setText(stringBuild.toString());
            }
            else
            {
                cleanAndAppendString("mService  connect  list is empty!");
                textTv.setText(stringBuild.toString());
            }

        } else if (v.equals(disconnectBt)) {

        } else if(v.equals(setCbBt))
        {
            int status = -1;
            try {
                status = mService.setCallBack(mCallBack);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            cleanAndAppendString("mService  setServerCallBack  status = " + status + " that status 0 is ok,otherwise is bad.");
            textTv.setText(stringBuild.toString());
        }else if(v.equals(startCliBt))
        {
            String clientName = "Cli:" + DID;
            int status = -1;
            try {
                status = mService.startMultiScreenClient(clientName);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            cleanAndAppendString("mService  startMultiScreenClient  status = " + status + " that status 0 is ok,otherwise is bad.");
            textTv.setText(stringBuild.toString());

        }else if(v.equals(stopCliBt))
        {
            int status = -1;
            try {
                status = mService.stopMultiScreenClient();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            cleanAndAppendString("mService  stopMultiScreenClient  status = " + status + " that status 0 is ok,otherwise is bad.");
            textTv.setText(stringBuild.toString());
        }else if (v.equals(queryInfoBt))
        {
            if(mCallBack.getServiceProvideInfo()!=null && mCallBack.getServiceProvideInfo().ipaddress!=null &&mCallBack.getServiceProvideInfo().ipaddress.length()>0)
            {
                int status = -1;
                try {
                    String mUUID = UUID.randomUUID().toString();
                    status = mService.queryInfo( mCallBack.getServiceProvideInfo().ipaddress,mCallBack.getServiceProvideInfo().port, mCallBack.getServiceProvideInfo().hostname, mUUID, "queryname", "fuck?");
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                cleanAndAppendString("mService  queryInfo  status = " + status + " that status 0 is ok,otherwise is bad.");
                textTv.setText(stringBuild.toString());
            }
        }else if(v.equals(execCmdBt))
        {
            if(mCallBack.getServiceProvideInfo()!=null && mCallBack.getServiceProvideInfo().ipaddress!=null &&mCallBack.getServiceProvideInfo().ipaddress.length()>0)
            {
                int status = -1;
                try {
                    status = mService.execCmd( mCallBack.getServiceProvideInfo().ipaddress,mCallBack.getServiceProvideInfo().port, mCallBack.getServiceProvideInfo().hostname, "ִ��Զ������", "��������");
                    Log.i("dsa","ipaddress  "+mCallBack.getServiceProvideInfo().ipaddress+"  "+"port  "+mCallBack.getServiceProvideInfo().port
        					+"  "+"hostname  "+mCallBack.getServiceProvideInfo().hostname);
                    Log.i("dsa","status  "+status);
                    
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                cleanAndAppendString("mService  execCmd  status = " + status + " that status 0 is ok,otherwise is bad.");
                textTv.setText(stringBuild.toString());
            }
        }else if(v.equals(inputKeyCodeBt))
        {
            if(mCallBack.getServiceProvideInfo()!=null && mCallBack.getServiceProvideInfo().ipaddress!=null &&mCallBack.getServiceProvideInfo().ipaddress.length()>0)
            {
                int status = -1;
                try {
                    status = mService.inputKeyCode( mCallBack.getServiceProvideInfo().
                    		ipaddress,mCallBack.getServiceProvideInfo().port, mCallBack.
                    		getServiceProvideInfo().hostname, "inputkeycode", "fuck?");
                    
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                cleanAndAppendString("mService  inputKeyCode  status = " + status + " that status 0 is ok,otherwise is bad.");
                textTv.setText(stringBuild.toString());
            }
        }else if (v.equals(broadCastBt))
        {
            int status = -1;
            try {
                status = mService.boardCastAllDevice("broadCast", "what a fuck");
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            cleanAndAppendString("mService  broadCastBt  status = " + status + " that status 0 is ok,otherwise is bad.");
            textTv.setText(stringBuild.toString());
        }
    }

    @Override
    public String onQuery(String queryId, String attribute, String param) {
        if("queryname".equals(attribute.trim()))
        {
            return "coocaa";
        }
        Log.v(TAG, param+"-------------------------------------");
        return null;
    }

    @Override
    public void onQueryResponse(String queryId, String attribute, String param) {
        if("queryname".equals(attribute.trim()))
        {
            Log.v(TAG, param+"***********************************");
        }
    }
}
