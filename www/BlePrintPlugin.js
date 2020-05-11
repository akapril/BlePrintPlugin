module.exports = {
    blePrint : function (arg0, success, error) {
        cordova.exec(success, error, 'BlePrintPlugin', 'blePrint', [arg0]);
    },
    bleConnect : function (arg0, success, error) {
        cordova.exec(success, error, 'BlePrintPlugin', 'bleConnect', [arg0]);
    },
    bleGetPermiss : function ( success, error) {
        cordova.exec(success, error, 'BlePrintPlugin', 'bleGetPermiss', []);
    },
    bleGetStatus : function (success, error) {
        cordova.exec(success, error, 'BlePrintPlugin', 'bleGetStatus', []);
    }
}