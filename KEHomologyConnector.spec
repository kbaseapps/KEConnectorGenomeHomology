/*
A KBase module: KEHomologyConnector
*/

module KEHomologyConnector {

    typedef structure {
    } RunParams;

    typedef structure {
    } RunOutput;

    funcdef run(RunParams params) returns (RunOutput) authentication required;
};
