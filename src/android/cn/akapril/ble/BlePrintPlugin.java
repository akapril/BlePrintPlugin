package cn.akapril.ble;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.tbruyelle.rxpermissions.RxPermissions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cpcl.PrinterHelper;
import rx.functions.Action1;

import static com.google.zxing.client.android.Intents.Scan.RESULT;

/**
 * This class echoes a string called from JavaScript.
 */
public class BlePrintPlugin extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Log.d("params",String.valueOf(args));
        if (action.equals("blePrint")) {
            JSONObject message = args.getJSONObject(0);
            this.blePrint(message, callbackContext);
            return true;
        }else if(action.equals("bleConnect")){
            String selectedBDAddress = args.getString(0);
            this.bleConnect(selectedBDAddress, callbackContext);
            return true;
        }else if(action.equals("bleGetPermiss")){
            String selectedBDAddress = args.getString(0);
            this.bleGetPermiss(selectedBDAddress, callbackContext);
            return true;
        }
        else if(action.equals("bleGetStatus")){
            this.bleGetStatus(callbackContext);
            return true;
        }
        return false;
    }

    private void blePrint(JSONObject params, CallbackContext callbackContext) {
        if (params != null ) {
            cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    try {
                        PrinterHelper.printAreaSize(params.getString("offset"),
                                params.getString("horizontal"),params.getString("vertical"),
                                params.getString("height"),params.getString("qty"));
                        PrinterHelper.Align(params.getString("align"));
                        PrinterHelper.Barcode(params.getString("command"), params.getString("type"),
                                params.getString("width"),params.getString("ratio"),
                                params.getString("vHeight"), params.getString("vX"),
                                params.getString("vY"),params.getBoolean("undertext"),
                                params.getString("number"),params.getString("size"),
                                params.getString("tOffset"),params.getString("data"));
                        PrinterHelper.Form();
                        PrinterHelper.Print();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    callbackContext.success(); // Thread-safe.
                }
            });
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
    private void bleConnect(String selectedBDAddress, CallbackContext callbackContext) {
        if (selectedBDAddress != null && selectedBDAddress.length() > 0) {
            try {
                PrinterHelper.PortOpenBT(selectedBDAddress);
            } catch (Exception e) {
                e.printStackTrace();
            }
            callbackContext.success(); // Thread-safe.
        }else{
            callbackContext.error("Expected one non-empty string argument.");

        }

    }
    private void bleGetPermiss(String data, CallbackContext callbackContext){

        if (data != null && data.length() > 0) {
            try {
                PrinterHelper.PortClose();
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                cordova.getActivity().startActivityForResult(enableBtIntent,1);

            } catch (Exception e) {
                e.printStackTrace();
            }
            callbackContext.success(); // Thread-safe.
        }else{
            callbackContext.error("Expected one non-empty string argument.");

        }

    }
    private void bleGetStatus(CallbackContext callbackContext) {
        String statusStr = "";

        int getstatus = 0;
        try {
            if(!PrinterHelper.IsOpened())
            {
                Toast.makeText(cordova.getActivity(), "请链接打印机", Toast.LENGTH_SHORT).show();
                statusStr = "请链接打印机";
                return;
            }
            getstatus = PrinterHelper.getstatus();

        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (getstatus) {
            case 0:
                statusStr = "就绪";
                break;
            case 2:
                statusStr = "缺纸";
                break;
            case 6:
                statusStr = "开盖";
                break;
            default:
                statusStr = "错误";
                break;
        }
        callbackContext.success(statusStr); // Thread-safe.
    }
}
