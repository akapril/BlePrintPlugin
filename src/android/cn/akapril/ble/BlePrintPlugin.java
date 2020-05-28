package cn.akapril.ble;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cpcl.PrinterHelper;


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
            this.bleGetPermiss(callbackContext);
            return true;
        }
        else if(action.equals("bleGetStatus")){
            this.bleGetStatus(callbackContext);
            return true;
        }
        return false;
    }

    private void blePrint(JSONObject params, CallbackContext callbackContext) {
            if (params != null) {
                cordova.getThreadPool().execute(new Runnable() {
                    public void run() {
                        String statusStr = "false";
                        try {

                            PrinterHelper.printAreaSize(params.getString("offset"),
                                    params.getString("horizontal"), params.getString("vertical"),
                                    params.getString("height"), params.getString("qty"));
                            PrinterHelper.Align(params.getString("align"));
                            PrinterHelper.Barcode(params.getString("command"), params.getString("type"),
                                    params.getString("width"), params.getString("ratio"),
                                    params.getString("vHeight"), params.getString("vX"),
                                    params.getString("vY"), params.getBoolean("undertext"),
                                    params.getString("number"), params.getString("size"),
                                    params.getString("tOffset"), params.getString("data"));
                            PrinterHelper.Form();
                            PrinterHelper.Print();
                            statusStr = "true";
                        } catch (Exception e) {
                            statusStr = "false";
                            callbackContext.success(statusStr);
                            e.printStackTrace();
                        }

                        callbackContext.success(statusStr); // Thread-safe.
                    }
                });
            } else {
                callbackContext.error("Expected one non-empty string argument.");
            }

    }
    private void bleConnect(String selectedBDAddress, CallbackContext callbackContext) {
        String statusStr = "false";
        if (selectedBDAddress != null && selectedBDAddress.length() > 0) {
            try {

                int cstatus = PrinterHelper.PortOpenBT(selectedBDAddress);
                if(cstatus==0){
                    statusStr = "true";
                }else{
                    statusStr = "false";
                }
            } catch (Exception e) {
                statusStr = "false";
                callbackContext.success(statusStr);
                e.printStackTrace();
            }
            callbackContext.success(statusStr); // Thread-safe.
        }else{
            statusStr = "false";
            callbackContext.success(statusStr);

        }

    }
    private void bleGetPermiss( CallbackContext callbackContext){

            try {
                PrinterHelper.PortClose();
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                cordova.getActivity().startActivityForResult(enableBtIntent,1);

            } catch (Exception e) {
                e.printStackTrace();
            }
            callbackContext.success(); // Thread-safe.

    }
    private void bleGetStatus(CallbackContext callbackContext) {
        String statusStr = "false";
        try{
            int getstatus = PrinterHelper.getstatus();
            Log.d("params", String.valueOf(getstatus));
            if (PrinterHelper.IsOpened()){
                statusStr = "true";
            }else{
                statusStr = "false";
            }
        }catch (Exception e){
            statusStr = "false";
            callbackContext.success(statusStr);
            e.printStackTrace();
        }

        callbackContext.success(statusStr); // Thread-safe.
    }
}
