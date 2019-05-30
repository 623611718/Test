package tvos.multiscreen.test;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.R.integer;
import android.content.Context;

public class Utils {
	private String str = null;
	private DecimalFormat nf = new DecimalFormat("000");


	public String[][] getCaseNameSort() {
		return CaseNameSort;
	}

	public void setCaseNameSort(String[][] caseNameSort) {
		CaseNameSort = caseNameSort;
	}

	public boolean isNormal(ArrayList arrayList) {

		boolean result = false;
		for (int i = 1; i < arrayList.size(); i++) {
			int timing = Integer.parseInt(String.valueOf(arrayList.get(i)));
			if (timing <= 1000) {
				result = true;
			} else {
				result = false;
				break;
			}
		}
		return result;
	}

	// 用例的数组及编号
	String[][] CaseNameSort = {
			{"testIMultiScreenService_START_MULTISCREENSERVER_iNorm_010","0","1"},
			{"testIMultiScreenService_STOP_MULTISCREENSERVER_iNorm_010","0","1"},
			{"testIMultiScreenService_START_MULTISCREENCLIENT_iNorm_010","0","1"},
			{"testIMultiScreenService_STOP_MULTISCREENCLIENT_iNorm_010","0","1"},
			{"testIMultiScreenService_FIND_SPS_iNorm_010","0","1"},
			{"testIMultiScreenService_CONNECT_iNorm_010","0","1"},
			{"testIMultiScreenService_SET_CALLBACK_iNorm_010","0","1"},
			{"testIMultiScreenService_QUERY_INFO_iNorm_010","0","1"},
			{"testIMultiScreenService_EXEC_CMD_iNorm_010","0","1"},
			{"testIMultiScreenService_INPUT_KEYCODE_iNorm_010","0","1"},
			{"testIMultiScreenService_NOTIFY_ALL_REMOTE_iNorm_010","0","1"},
			{"testIMultiScreenService_startMultiScreenServer_iNorm_010","0","1"},
			{"testIMultiScreenService_stopMultiScreenServer_iNorm_010","0","1"},
			{"testIMultiScreenService_startMultiScreenClient_iNorm_010","0","1"},
			{"testIMultiScreenService_stopMultiScreenClient_iNorm_010","0","1"},
			{"testIMultiScreenService_findSPs_iNorm_010","0","1"},
			{"testIMultiScreenService_setCallBack_iNorm_010","0","1"},
			{"testIMultiScreenService_queryInfo_iNorm_010","0","1"},
			{"testIMultiScreenService_connect_iNorm_010","0","1"},
			{"testIMultiScreenService_execCmd_iNorm_010","0","1"},
			{"testIMultiScreenService_inputKeyCode_iNorm_010","0","1"},
			{"testIMultiScreenService_boardCastAllDevice_iNorm_010","0","1"},
			{"testIMultiScreenCallBack_ON_SP_FOUNDED_iNorm_010","0","1"},
			{"testIMultiScreenCallBack_ON_CONNECTED_iNorm_010","0","1"},
			{"testIMultiScreenCallBack_ON_CONNECTEDREFUSED_iNorm_010","0","1"},
			{"testIMultiScreenCallBack_ON_DISCONNECTED_iNorm_010","0","1"},
			{"testIMultiScreenCallBack_ON_SERVICE_ACTIVITED_iNorm_010","0","1"},
			{"testIMultiScreenCallBack_ON_SERVICE_DEACTIVITED_iNorm_010","0","1"},
			{"testIMultiScreenCallBack_ON_QUERY_INFO_iNorm_010","0","1"},
			{"testIMultiScreenCallBack_ON_QUERY_RESPONSE_iNorm_010","0","1"},
			{"testIMultiScreenCallBack_ON_EXECUTE_iNorm_010","0","1"},
			{"testIMultiScreenCallBack_ON_INPUT_iNorm_010","0","1"},
			{"testIMultiScreenCallBack_ON_NOTIFY_iNorm_010","0","1"},
			{"testIMultiScreenCallBack_onQueryInfo_iNorm_010","0","1"},
			{"testIMultiScreenCallBack_onQueryResponse_iNorm_010","0","1"},
			{"testIMultiScreenCallBack_onSpFounded_iNorm_010","0","1"},
			{"testIMultiScreenCallBack_onConnected_iNorm_010","0","1"},
			{"testIMultiScreenCallBack_onConnectRefused_iNorm_010","0","1"},
			{"testIMultiScreenCallBack_onDisconnected_iNorm_010","0","1"},
			{"testIMultiScreenCallBack_onServiceActivited_iNorm_010","0","1"},
			{"testIMultiScreenCallBack_onServiceDeactived_iNorm_010","0","1"},
			{"testIMultiScreenCallBack_onExecute_iNorm_010","0","1"},
			{"testIMultiScreenCallBack_onInputKeyCode_iNorm_010","0","1"},
			{"testIMultiScreenCallBack_onNotify_iNorm_010","0","1"},
			{"testIMultiScreenCallBack_onTransact_iNorm_010","0","1"},
			
			{"testIMultiScreenService_startMultiScreenServer_iAbnm_010","0","1"},
			{"testIMultiScreenService_startMultiScreenServer_iAbnm_011","0","1"},
			{"testIMultiScreenService_stopMultiScreenServer_iAbnm_010","0","1"},
			{"testIMultiScreenService_startMultiScreenClient_iAbnm_010","0","1"},
			{"testIMultiScreenService_startMultiScreenClient_iAbnm_011","0","1"},
			{"testIMultiScreenService_stopMultiScreenClient_iAbnm_010","0","1"},
			{"testIMultiScreenService_findSPs_iAbnm_010","0","1"},
			{"testIMultiScreenService_connect_iAbnm_010","0","1"},
			{"testIMultiScreenService_connect_iAbnm_011","0","1"},
			{"testIMultiScreenService_queryInfo_iAbnm_010","0","1"},
			{"testIMultiScreenService_queryInfo_iAbnm_011","0","1"},
			{"testIMultiScreenService_execCmd_iAbnm_010","0","1"},
			{"testIMultiScreenService_execCmd_iAbnm_011","0","1"},
			{"testIMultiScreenService_inputKeyCode_iAbnm_010","0","1"},
			{"testIMultiScreenService_inputKeyCode_iAbnm_011","0","1"},
			{"testIMultiScreenService_boardCastAllDevice_iAbnm_010","0","1"},
			{"testIMultiScreenService_boardCastAllDevice_iAbnm_011","0","1"},
			{"testIMultiScreenCallBack_onSpFounded_iAbnm_010","0","1"},
			{"testIMultiScreenCallBack_onConnected_iAbnm_010","0","1"},
			{"testIMultiScreenCallBack_onConnectRefused_iAbnm_010","0","1"},
			{"testIMultiScreenCallBack_onDisconnected_iAbnm_010","0","1"},
			{"testIMultiScreenCallBack_onServiceActivited_iAbnm_010","0","1"},
			{"testIMultiScreenCallBack_onServiceDeactived_iAbnm_010","0","1"},
			{"testIMultiScreenCallBack_onQueryInfo_iAbnm_010","0","1"},
			{"testIMultiScreenCallBack_onQueryResponse_iAbnm_010","0","1"},
			{"testIMultiScreenCallBack_onExecute_iAbnm_010","0","1"},
			{"testIMultiScreenCallBack_onInputKeyCode_iAbnm_010","0","1"},
			{"testIMultiScreenCallBack_onNotify_iAbnm_010","0","1"},
			{"testIMultiScreenCallBack_onTransact_iAbnm_010","0","1"},
			
			{"testIMultiScreenService_startMultiScreenServer_iPerf_010","0","1"},
			{"testIMultiScreenService_stopMultiScreenServer_iPerf_010","0","1"},
			{"testIMultiScreenService_startMultiScreenClient_iPerf_010","0","1"},
			{"testIMultiScreenService_stopMultiScreenClient_iPerf_010","0","1"},
			{"testIMultiScreenService_findSPs_iPerf_010","0","1"},
			{"testIMultiScreenService_setCallBack_iPerf_010","0","1"},
			{"testIMultiScreenService_queryInfo_iPerf_010","0","1"},
			{"testIMultiScreenService_connect_iPerf_010","0","1"},
			{"testIMultiScreenService_execCmd_iPerf_010","0","1"},
			{"testIMultiScreenService_inputKeyCode_iPerf_010","0","1"},
			{"testIMultiScreenService_boardCastAllDevice_iPerf_010","0","1"},
			{"testIMultiScreenCallBack_onQueryInfo_iPerf_010","0","1"},
			{"testIMultiScreenCallBack_onQueryResponse_iPerf_010","0","1"},
			{"testIMultiScreenCallBack_onSpFounded_iPerf_010","0","1"},
			{"testIMultiScreenCallBack_onConnected_iPerf_010","0","1"},
			{"testIMultiScreenCallBack_onConnectRefused_iPerf_010","0","1"},
			{"testIMultiScreenCallBack_onDisconnected_iPerf_010","0","1"},
			{"testIMultiScreenCallBack_onServiceActivited_iPerf_010","0","1"},
			{"testIMultiScreenCallBack_onServiceDeactived_iPerf_010","0","1"},
			{"testIMultiScreenCallBack_onExecute_iPerf_010","0","1"},
			{"testIMultiScreenCallBack_onInputKeyCode_iPerf_010","0","1"},
			{"testIMultiScreenCallBack_onNotify_iPerf_010","0","1"},
			{"testIMultiScreenCallBack_onTransact_iPerf_010","0","1"},
			
			{"testIMultiScreenService_startMultiScreenServer_iPress_010","0","1"},
			{"testIMultiScreenService_stopMultiScreenServer_iPress_010","0","1"},
			//{"testIMultiScreenService_startMultiScreenClient_iPress_010","0","1"},
			{"testIMultiScreenService_stopMultiScreenClient_iPress_010","0","1"},
			{"testIMultiScreenService_findSPs_iPress_010","0","1"},
			{"testIMultiScreenService_setCallBack_iPress_010","0","1"},
			{"testIMultiScreenService_queryInfo_iPress_010","0","1"},
			{"testIMultiScreenService_connect_iPress_010","0","1"},
			{"testIMultiScreenService_execCmd_iPress_010","0","1"},
			{"testIMultiScreenService_inputKeyCode_iPress_010","0","1"},
			{"testIMultiScreenService_boardCastAllDevice_iPress_010","0","1"},
			{"testIMultiScreenCallBack_onQueryInfo_iPress_010","0","1"},
			{"testIMultiScreenCallBack_onQueryResponse_iPress_010","0","1"},
			{"testIMultiScreenCallBack_onSpFounded_iPress_010","0","1"},
			{"testIMultiScreenCallBack_onConnected_iPress_010","0","1"},
			{"testIMultiScreenCallBack_onConnectRefused_iPress_010","0","1"},
			{"testIMultiScreenCallBack_onDisconnected_iPress_010","0","1"},
			{"testIMultiScreenCallBack_onServiceActivited_iPress_010","0","1"},
			{"testIMultiScreenCallBack_onServiceDeactived_iPress_010","0","1"},
			{"testIMultiScreenCallBack_onExecute_iPress_010","0","1"},
			{"testIMultiScreenCallBack_onInputKeyCode_iPress_010","0","1"},
			{"testIMultiScreenCallBack_onNotify_iPress_010","0","1"},
			{"testIMultiScreenCallBack_onTransact_iPress_010","0","1"}
			};

	public int getINromDate(ArrayList arrayList) {

		int result = 0;
		int timing = 0;
		for (int i = 1; i < arrayList.size(); i++) {
			timing = Integer.parseInt(String.valueOf(arrayList.get(i)));
			if (timing > 1000) {
				result = timing;
			}
		}
		return result;
	}

	public String returnResultofTrue(String name, String method,int lineNumber) {

		for (int i = 0; i < CaseNameSort.length; i++) {
			for (int j = 0; j < CaseNameSort[i].length; j++) {
				if (CaseNameSort[i][j].equals(name)) {
					str = "["
							+nf.format(i+1)
							+"]"
							+ " OK: TestCase_"
							+ name
							+ " Passed"
							+ ", Line "
							+ (lineNumber-1)
							+ ". Mandatory: "
							+ CaseNameSort[i][2] + ". Test Object: " + method
							+ ".";
							break;
				} 
			}
			continue;
		}

		return str;
	}

	public String returnResultoFail(String name, String method, String actural,String expect,
			int lineNumber) {
		for (int i = 0; i < CaseNameSort.length; i++) {
			for (int j = 0; j < CaseNameSort[i].length; j++) {
				if (CaseNameSort[i][j].equals(name)) {
					str = "["
							+nf.format(i+1)
							+"]"
							+" Fail: TestCase_"
							+ name
							+ " Failed"
							+ ", Line "
							+(lineNumber-1)
							+ ". Expect:" + expect
							+ ", Actural:" + actural + ". Mandatory: "
							+ CaseNameSort[i][2] + ". Test Object: " + method
							+ ".";
							break;
				}
			}
			continue;
		}

		return str;

	}
}