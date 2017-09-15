package keconnectorgenomehomology;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.JsonServerMethod;
import us.kbase.common.service.JsonServerServlet;
import us.kbase.common.service.JsonServerSyslog;
import us.kbase.common.service.RpcContext;

//BEGIN_HEADER
//END_HEADER

/**
 * <p>Original spec-file module name: KEConnectorGenomeHomology</p>
 * <pre>
 * A KBase module: KEHomologyConnector
 * </pre>
 */
public class KEConnectorGenomeHomologyServer extends JsonServerServlet {
    private static final long serialVersionUID = 1L;
    private static final String version = "";
    private static final String gitUrl = "";
    private static final String gitCommitHash = "";

    //BEGIN_CLASS_HEADER
    KEConnectorGenomeHomologyServerImpl impl;
    //END_CLASS_HEADER

    public KEConnectorGenomeHomologyServer() throws Exception {
        super("KEConnectorGenomeHomology");
        //BEGIN_CONSTRUCTOR
        impl = new KEConnectorGenomeHomologyServerImpl();
        //END_CONSTRUCTOR
    }

    /**
     * <p>Original spec-file function name: run</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link keconnectorgenomehomology.RunParams RunParams}
     * @return   instance of type {@link keconnectorgenomehomology.RunOutput RunOutput}
     */
    @JsonServerMethod(rpc = "KEConnectorGenomeHomology.run", async=true)
    public RunOutput run(RunParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        RunOutput returnVal = null;
        //BEGIN run
        returnVal = impl.run(params);
        //END run
        return returnVal;
    }
    @JsonServerMethod(rpc = "KEConnectorGenomeHomology.status")
    public Map<String, Object> status() {
        Map<String, Object> returnVal = null;
        //BEGIN_STATUS
        returnVal = new LinkedHashMap<String, Object>();
        returnVal.put("state", "OK");
        returnVal.put("message", "");
        returnVal.put("version", version);
        returnVal.put("git_url", gitUrl);
        returnVal.put("git_commit_hash", gitCommitHash);
        //END_STATUS
        return returnVal;
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 1) {
            new KEConnectorGenomeHomologyServer().startupServer(Integer.parseInt(args[0]));
        } else if (args.length == 3) {
            JsonServerSyslog.setStaticUseSyslog(false);
            JsonServerSyslog.setStaticMlogFile(args[1] + ".log");
            new KEConnectorGenomeHomologyServer().processRpcCall(new File(args[0]), new File(args[1]), args[2]);
        } else {
            System.out.println("Usage: <program> <server_port>");
            System.out.println("   or: <program> <context_json_file> <output_json_file> <token>");
            return;
        }
    }
}
