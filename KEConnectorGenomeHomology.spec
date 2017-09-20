/*
A KBase module: KEHomologyConnector
*/

module KEConnectorGenomeHomology {

    typedef structure {
    	string app_guid;
    	string obj_ref;
    } RunParams;

    typedef structure {
		int new_re_nodes;    
		int updated_re_nodes;
		int new_re_links;
		int properties_set;			
		string message;    
    } KEAppOutput;


    funcdef run(RunParams params) returns (KEAppOutput) authentication required;
};
