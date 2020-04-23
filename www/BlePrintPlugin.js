var exec = require('cordova/exec');

exports.blePrint = function (arg0, success, error) {
    exec(success, error, 'BlePrintPlugin', 'blePrint', [arg0]);
};
exports.bleConnect = function (arg0, success, error) {
    exec(success, error, 'BlePrintPlugin', 'bleConnect', [arg0]);
};