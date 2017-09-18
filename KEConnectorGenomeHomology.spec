/*
A KBase module: KEHomologyConnector
*/

module KEConnectorGenomeHomology {

    typedef structure {
    	string genome_ws_ref;
    } RunParams;

    typedef structure {
    } RunOutput;

    funcdef run(RunParams params) returns (RunOutput) authentication required;
};
