#### use
```JavaScript
cordova.plugins.BlePrintPlugin.bleGetPermiss(function(data){console.log(data);},function(data){console.log(data);});
cordova.plugins.BlePrintPlugin.bleConnect("ble mac address or id",function(data){console.log(data);},function(data){console.log(data);});
cordova.plugins.BlePrintPlugin.bleGetStatus(function(data){console.log(data);},function(data){console.log(data);});
var data = var data = {
        offset: "0",
        horizontal: "200",
        vertical: "200",
        height: "320",
        qty: "1",
        align: "CENTER",
        command: "BARCODE",
        type: "128",
        width: "200",
        ratio: "0",
        vHeight: "170",
        vX: "0",
        vY: "75",
        undertext: true,
        number: "7",
        size: "0",
        tOffset: "5",
        data: message
      };
cordova.plugins.BlePrintPlugin.blePrint(data,function(data){console.log(data); },function(data){console.log(data);});
```