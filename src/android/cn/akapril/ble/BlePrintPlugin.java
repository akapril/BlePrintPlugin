package cn.akapril.ble;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

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
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.blePrint(message, callbackContext);
            return true;
        }
        return false;
    }

    private void blePrint(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    try {
                        PrinterHelper.printAreaSize("0", "200","200","500","1");
                        PrinterHelper.Barcode("BARCODE", "128","1","1","100", "200","200",true,"7","0","5","123123");

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
}
