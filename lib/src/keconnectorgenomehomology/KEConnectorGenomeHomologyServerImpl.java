package keconnectorgenomehomology;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import kbaserelationengine.FeatureSequence;
import kbaserelationengine.GetFeatureSequencesParams;
import kbaserelationengine.KBaseRelationEngineServiceClient;
import keconnectorgenomehomology.util.BlastStarter;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.JsonClientException;
import us.kbase.common.service.UnauthorizedException;
import us.kbase.common.utils.FastaReader;
import us.kbase.kbasegenomes.Feature;
import us.kbase.kbasegenomes.Genome;
import workspace.GetObjects2Params;
import workspace.ObjectSpecification;
import workspace.WorkspaceClient;

public class KEConnectorGenomeHomologyServerImpl {

    private URL wsUrl = null;
    private URL srvWizUrl = null;
	
	private File tmpDir = null;
//	private File blastBinDir = new File("bin");
	private File blastBinDir = new File("/kb/deps/ncbi-blast-2.2.29+/bin/");
	
	private static final String MAX_EVALUE = "" + 1.0e-10;
		
	private final String[] BASE_ORTHOLOG_GUIDS = new String[]{"KBaseHgp54930"};
		
    public KEConnectorGenomeHomologyServerImpl(Map<String, String> config) throws MalformedURLException {
    	tmpDir = new File(config.get("scratch"));
        wsUrl = new URL(config.get("workspace-url"));
        srvWizUrl = new URL(config.get("srv-wiz-url"));
	}	
	
	public RunOutput run(RunParams params, AuthToken token) throws Exception {	

		long timeStart = System.currentTimeMillis();
		
		System.out.print("Loading genome...");
//		Genome wsGenome = getWSGenome(params.getGenomeWsRef(), token);
//		Map<String,String> wsProteome = toProteome(wsGenome);
		Map<String,String> wsProteome = loadTestFasta(params.getGenomeWsRef());
		System.out.println("Done! " + wsProteome.size());
		
		
		System.out.print("Loading base orthologs...");
		Map<String,String> baseOrtologs = getBaseOrtohlogs(token);
		System.out.println("Done  " + baseOrtologs.size());				
		
		System.out.print("Doing blastp...");
		ClosestGenomeFinder taxFinder = new ClosestGenomeFinder();
		BlastStarter.run(tmpDir, wsProteome, baseOrtologs, blastBinDir, MAX_EVALUE, taxFinder);
		System.out.println("Done");		

		String taxGuid = taxFinder.getBestTaxGuid();
		System.out.println("taxGuid = " + taxGuid);
		
		System.out.print("Loading ref proteome...");		
		Map<String,String> refProteome = getRefProteome(taxGuid, token);
		System.out.println("Done  " + refProteome.size());				
		
		System.out.print("Doing blastp...");		
		BBHsFinder bbhFinder = new BBHsFinder();
		BlastStarter.run(tmpDir, wsProteome, refProteome, blastBinDir, MAX_EVALUE, bbhFinder);
		List<BBH> bbhs = bbhFinder.getBBHs();
		System.out.println("Done");		
		
		storeBBHs(bbhs, params);
		
		System.out.println( (System.currentTimeMillis() - timeStart)/1000 );

		return new RunOutput();
	}

	private Map<String, String> loadTestFasta(String genomeWsRef) {
		Map<String,String> proteome = new Hashtable<String,String>();
		Map<String,String> id2seq = FastaReader.readFromFile(new File(genomeWsRef));
		
		for(Entry<String, String> entry: id2seq.entrySet()){
			String id = entry.getKey();
			String seq = entry.getValue();
			
			String locusId = id.split(" ")[0].split(":")[1]; 
			proteome.put(locusId, seq);
		}		
		return proteome;
	}

	private void storeBBHs(List<BBH> bbhs, RunParams params) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(params.getGenomeWsRef() + ".bbhs"));
		try{
			for(BBH bbh: bbhs){
				bw.write(bbh.qGuid + "\t" + bbh.tGuid + "\t" + bbh.bitScore + "\n");
			}			
		} finally{
			bw.close();
		}
	}

	private Genome getWSGenome(String genomeWsRef, AuthToken token) throws IOException, JsonClientException{
	    WorkspaceClient wsCl = new WorkspaceClient(wsUrl, token);
	    wsCl.setAllSSLCertificatesTrusted(true);
	    wsCl.setIsInsecureHttpConnectionAllowed(true);
	    
	    return wsCl.getObjects2(new GetObjects2Params().withObjects(
	            Arrays.asList(new ObjectSpecification().withRef(genomeWsRef))))
	            .getData().get(0).getData().asClassInstance(Genome.class);		
	}
	
	private Map<String, String> toProteome(Genome wsGenome) {
		Map<String, String> id2seq = new Hashtable<String,String>();
	    for (Feature ft : wsGenome.getFeatures()) {
	        String id = ft.getId();
	        String protSeq = ft.getProteinTranslation();
	        if (protSeq == null) {
	            continue;
	        }
	        id2seq.put(id, protSeq);
	    }		
		return id2seq;
	}	
	
	private Map<String, String> getBaseOrtohlogs(AuthToken token) throws IOException, JsonClientException {
		Map<String, String> id2seq = new Hashtable<String,String>();
		KBaseRelationEngineServiceClient reClient = reClient(token);

		for(String ortGuid: BASE_ORTHOLOG_GUIDS){
			List<FeatureSequence> sequences = reClient.getFeatureSequences(
					new GetFeatureSequencesParams().withOrthologGuid(ortGuid));
			
			for(FeatureSequence seq: sequences){
				String id = seq.getFeatureGuid() 
						+ "|" + ortGuid 
						+ "|" + seq.getTaxonomyGuid();
				String sequence = seq.getProteinSequence(); 
				id2seq.put(id, sequence);
			}					
		}		
		return id2seq;
	}
	
	class ClosestGenomeFinder implements BlastStarter.ResultCallback{
		double maxBitScore = 0;
		String bestTName = "";
		double bestIdentity = 0;
		
		@Override
		public void proteinPair(String qname, String tname, double ident, int alnLen, int mismatch, int gapopens,
				int qstart, int qend, int tstart, int tend, String eval, double bitScore) {

			if(bitScore > maxBitScore){
				maxBitScore = bitScore;
				bestTName = tname;
				bestIdentity = ident;
			}
		}
		
		public String getBestTaxGuid(){
			return bestTName.split("\\|")[2].trim();
		}
	}	
	
	
	private Map<String, String> getRefProteome(String taxGuid,AuthToken token) throws IOException, JsonClientException {
		Map<String, String> id2seq = new Hashtable<String,String>();
		
		KBaseRelationEngineServiceClient reClient = reClient(token);
		List<FeatureSequence> sequences = reClient.getFeatureSequences(
				new GetFeatureSequencesParams().withTaxonomyGuid(taxGuid));
		
		for(FeatureSequence seq: sequences){
			id2seq.put(seq.getFeatureGuid(), seq.getProteinSequence());
		}
		return id2seq;		
	}	
	
	class BestHit{
		String guid;
		double bitScore;
		public BestHit(String guid, double bitScore) {
			setHit(guid, bitScore);
		}
		public void setHit(String guid, double bitScore){
			this.guid = guid;
			this.bitScore = bitScore;			
		}
	}
	class BBH{
		String qGuid;
		String tGuid;
		double bitScore;
		public BBH(String qGuid, String tGuid, double bitScore) {
			this.qGuid = qGuid;
			this.tGuid = tGuid;
			this.bitScore = bitScore;
		}
	}
	
	class BBHsFinder implements BlastStarter.ResultCallback{
		Hashtable<String, BestHit> q2t = new Hashtable<String, BestHit>();
		Hashtable<String, BestHit> t2q = new Hashtable<String, BestHit>();
				
		@Override
		public void proteinPair(String qname, String tname, double ident, int alnLen, int mismatch, int gapopens,
				int qstart, int qend, int tstart, int tend, String eval, double bitScore) {
			BestHit hit;
			
			// Do q2t
			hit = q2t.get(qname);
			if(hit == null){
				hit = new BestHit(tname,0);
				q2t.put(qname, hit);
			}
			if(bitScore > hit.bitScore){
				hit.setHit(tname, bitScore);
			}
			
			// Do t2q
			hit = t2q.get(tname);
			if(hit == null){
				hit = new BestHit(qname,0);
				t2q.put(tname, hit);
			}
			if(bitScore > hit.bitScore){
				hit.setHit(qname, bitScore);
			}			
		}	
		
		public List<BBH> getBBHs(){
			List<BBH> bbhs = new ArrayList<BBH>();
			
			for(Entry<String, BestHit> entry :q2t.entrySet()){
				String qGuid = entry.getKey();
				String tGuid = entry.getValue().guid;
				BestHit hit = t2q.get(tGuid);
				if(hit != null && hit.guid.equals(qGuid)){
					bbhs.add(new BBH(qGuid,tGuid,hit.bitScore));
				}
			}
			return bbhs;
		}
	}	
	
	private KBaseRelationEngineServiceClient reClient(AuthToken token) throws UnauthorizedException, IOException{
        KBaseRelationEngineServiceClient client = new KBaseRelationEngineServiceClient(srvWizUrl, token);
        client.setIsInsecureHttpConnectionAllowed(true);
        client.setServiceVersion("dev");
        return client;    	
	}
	
	public static void main(String[] args) throws Exception {
		KEConnectorGenomeHomologyServerImpl	impl = new KEConnectorGenomeHomologyServerImpl(System.getenv());
		String token = System.getenv("token");
		String user = System.getenv("user");
		
//		impl.run(new RunParams().withGenomeWsRef("25582/2/1"), new AuthToken(token, user));		
		impl.run(new RunParams().withGenomeWsRef("/kb/module/work/tmp/organism_Miya.faa"), new AuthToken(token, user));		
	}
	
}
