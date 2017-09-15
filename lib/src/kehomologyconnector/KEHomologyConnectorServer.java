package kehomologyconnector;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.JsonServerMethod;
import us.kbase.common.service.JsonServerServlet;
import us.kbase.common.service.JsonServerSyslog;
import us.kbase.common.service.RpcContext;

//BEGIN_HEADER
import java.net.URL;
//END_HEADER

/**
 * <p>Original spec-file module name: KEHomologyConnector</p>
 * <pre>
 * A KBase module: KEHomologyConnector
 * </pre>
 */
public class KEHomologyConnectorServer extends JsonServerServlet {
    private static final long serialVersionUID = 1L;
    private static final String version = "0.0.1";
    private static final String gitUrl = "https://github.com/psnovichkov/KEHomologyConnector.git";
    private static final String gitCommitHash = "9b572f1a238f5084732953033261d974292be437";

    //BEGIN_CLASS_HEADER
    KEHomologyConnectorServerImpl impl;
    private URL wsUrl = null;
    //END_CLASS_HEADER

    public KEHomologyConnectorServer() throws Exception {
        super("KEHomologyConnector");
        //BEGIN_CONSTRUCTOR
        impl = new KEHomologyConnectorServerImpl();
        wsUrl = new URL(config.get("workspace-url"));
        //END_CONSTRUCTOR
    }

    /**
     * <p>Original spec-file function name: run</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link kehomologyconnector.RunParams RunParams}
     * @return   instance of type {@link kehomologyconnector.RunOutput RunOutput}
     */
    @JsonServerMethod(rpc = "KEHomologyConnector.run", async=true)
    public RunOutput run(RunParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        RunOutput returnVal = null;
        //BEGIN run
        returnVal = impl.run(params, authPart, wsUrl);
        //END run
        return returnVal;
    }
    @JsonServerMethod(rpc = "KEHomologyConnector.status")
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
            new KEHomologyConnectorServer().startupServer(Integer.parseInt(args[0]));
        } else if (args.length == 3) {
            JsonServerSyslog.setStaticUseSyslog(false);
            JsonServerSyslog.setStaticMlogFile(args[1] + ".log");
            new KEHomologyConnectorServer().processRpcCall(new File(args[0]), new File(args[1]), args[2]);
        } else {
            System.out.println("Usage: <program> <server_port>");
            System.out.println("   or: <program> <context_json_file> <output_json_file> <token>");
            return;
        }
    }
}
