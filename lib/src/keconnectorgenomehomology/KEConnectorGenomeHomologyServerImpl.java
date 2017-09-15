package keconnectorgenomehomology;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import kbaserelationengine.FeatureSequence;
import kbaserelationengine.GetFeatureSequencesParams;
import kbaserelationengine.KBaseRelationEngineClient;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.JsonClientException;
import us.kbase.kbasegenomes.Feature;
import us.kbase.kbasegenomes.Genome;
import workspace.GetObjects2Params;
import workspace.ObjectSpecification;
import workspace.WorkspaceClient;
import keconnectorgenomehomology.util.BlastStarter;
import keconnectorgenomehomology.util.FastaWriter;

public class KEConnectorGenomeHomologyServerImpl {
	
	private static final String TMP_DIR = null;
	private static final String BLAST_BIN_PATH = "bin";
	
	private final String[] BASE_ORTHOLOG_GUIDS = new String[]{};
	
	private final String SOURCE_GENOME = "";
	private final String BASE_ORTHOLOG = "";
	private final String BLAST_OUTPUT_1 = "";
	
	
	private final String REFERENCE_GENOME = "";
	private final String BLAST_OUTPUT_2 = "";
	
	
	public RunOutput run(RunParams params, AuthToken token, URL wsUrl) throws Exception {
		exportWSGenomeFasta(params.getGenomeWsRef(), SOURCE_GENOME, token, wsUrl);
		exportBaseOrthologFasta(SOURCE_GENOME);
		formatDataBase(BASE_ORTHOLOG);
		runBlastP(SOURCE_GENOME, BASE_ORTHOLOG, BLAST_OUTPUT_1);
		String taxGuid = findCLosestGenome(BLAST_OUTPUT_1);
		
		exportReferenceGenomeFasta(taxGuid, REFERENCE_GENOME);
		formatDataBase(REFERENCE_GENOME);
		runBlastP(SOURCE_GENOME, REFERENCE_GENOME, BLAST_OUTPUT_2);
		processBBHs(BLAST_OUTPUT_2);
		
		return new RunOutput();
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
	private void processBBHs(String outputName) throws Exception {
		String fileName = getOutputFileName(outputName);
		BBHsFinder finder = new BBHsFinder();
		BlastStarter.loadData(new File(fileName), finder);
		
		List<BBH> bbhs = finder.getBBHs();
		
		// TODO: Store links between source genes and target orthologous groups in relation engine
		
	}

	private void exportReferenceGenomeFasta(String taxGuid, String targetName) 
	        throws IOException, JsonClientException {
		KBaseRelationEngineClient reClient = reClient();
		List<FeatureSequence> sequences = reClient.getFeatureSequences(
				new GetFeatureSequencesParams().withTaxonomyGuid(taxGuid));
		
		String fileName = getFastaFileName(targetName);
		FastaWriter fw = new FastaWriter(new File(fileName));
		try{
			for(FeatureSequence seq: sequences){
				fw.write(seq.getFeatureGuid(), seq.getProteinSequence());
			}
		} finally{
			fw.close();
		}		
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
			return bestTName.split("|")[2].trim();
		}
	}
	private String findCLosestGenome(String outputName) throws Exception {
		String fileName = getOutputFileName(outputName);
		ClosestGenomeFinder finder = new ClosestGenomeFinder();
		BlastStarter.loadData(new File(fileName), finder);
		return finder.getBestTaxGuid();
	}

	private void runBlastP(String sourceName, String dbName, String outputName) {
		// TODO Auto-generated method stub
		
	}

	private void formatDataBase(String targetName) {
		// TODO Auto-generated method stub
		
	}

	private void exportWSGenomeFasta(String genomeWsRef, String sourceName, AuthToken token,
	        URL wsUrl) throws IOException, JsonClientException {
	    WorkspaceClient wsCl = new WorkspaceClient(wsUrl, token);
	    wsCl.setAllSSLCertificatesTrusted(true);
	    wsCl.setIsInsecureHttpConnectionAllowed(true);
		String fileName = getFastaFileName(sourceName);
		FastaWriter fw = new FastaWriter(new File(getFastaFileName(fileName)));
		try{
			// Get genome
		    Genome genome = wsCl.getObjects2(new GetObjects2Params().withObjects(
		            Arrays.asList(new ObjectSpecification().withRef(genomeWsRef))))
		            .getData().get(0).getData().asClassInstance(Genome.class);
			// Iterate and write to fw
		    for (Feature ft : genome.getFeatures()) {
		        String id = ft.getId();
		        String protSeq = ft.getProteinTranslation();
		        if (protSeq == null) {
		            continue;
		        }
		        fw.write(id, protSeq);
		    }
		}finally{
			fw.close();	
		}		
	}

	private void exportBaseOrthologFasta(String sourceName) 
	        throws IOException, JsonClientException {
		KBaseRelationEngineClient reClient = reClient();

		
		String fileName = getFastaFileName(sourceName);
		FastaWriter fw = new FastaWriter(new File(fileName));
		try{
			for(String ortGuid: BASE_ORTHOLOG_GUIDS){
				List<FeatureSequence> sequences = reClient.getFeatureSequences(
						new GetFeatureSequencesParams().withOrthologGuid(ortGuid));
				
				for(FeatureSequence seq: sequences){
					fw.write(
							seq.getFeatureGuid() 
							+ "|" + ortGuid 
							+ "|" + seq.getTaxonomyGuid()
							, seq.getProteinSequence());
				}					
			}
			
		} finally{
			fw.close();
		}
	}
	
	private KBaseRelationEngineClient reClient(){
		//TODO get it
		return null;
	}
	
	
	private String getFastaFileName(String fileNamePrefix){
		return TMP_DIR + "/" + fileNamePrefix + ".fasta";
	}
	
	private String getDBFileName(String fileNamePrefix){
		return TMP_DIR + "/" + fileNamePrefix + ".fasta";
	}
	
	private String getOutputFileName(String fileNamePrefix){
		return TMP_DIR + "/" + fileNamePrefix + ".out";
	}
}
