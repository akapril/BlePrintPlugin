var exec = require('cordova/exec');

exports.blePrint = function (arg0, success, error) {
    exec(success, error, 'BlePrintPlugin', 'blePrint', [arg0]);
};
