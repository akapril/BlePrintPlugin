module.exports = {
    blePrint : function (arg0, success, error) {
        cordova.exec(success, error, 'BlePrintPlugin', 'blePrint', [arg0]);
    },
    bleConnect : function (arg0, success, error) {
        cordova.exec(success, error, 'BlePrintPlugin', 'bleConnect', [arg0]);
    },
    bleGetPermiss : function (arg0, success, error) {
        cordova.exec(success, error, 'BlePrintPlugin', 'bleGetPermiss', [arg0]);
    },
    bleGetStatus : function (arg0, success, error) {
        cordova.exec(success, error, 'BlePrintPlugin', 'bleGetStatus', [arg0]);
    }
}