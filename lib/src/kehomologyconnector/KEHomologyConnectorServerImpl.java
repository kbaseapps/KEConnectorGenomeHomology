package kehomologyconnector;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import kbaserelationengine.FeatureSequence;
import kbaserelationengine.GetFeatureSequencesParams;
import kbaserelationengine.KBaseRelationEngineClient;
import kehomologyconnector.util.BlastStarter;
import kehomologyconnector.util.FastaWriter;

public class KEHomologyConnectorServerImpl {
	
	private static final String TMP_DIR = null;
	private static final String BLAST_BIN_PATH = "";
	
	private final String[] BASE_ORTHOLOG_GUIDS = new String[]{};
	
	private final String SOURCE_GENOME = "";
	private final String BASE_ORTHOLOG = "";
	private final String BLAST_OUTPUT_1 = "";
	
	
	private final String REFERENCE_GENOME = "";
	private final String BLAST_OUTPUT_2 = "";
	
	
	public RunOutput run(RunParams params) throws Exception {
		exportWSGenomeFasta(params.getGenomeWsRef(), SOURCE_GENOME);
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

	private void exportReferenceGenomeFasta(String taxGuid, String targetName) {
		KBaseRelationEngineClient reClient = reClient();
		List<FeatureSequence> sequences = reClient.getFeatureSequences(
				new GetFeatureSequencesParams().withTaxonomyGuid(taxGuid), 
				jsonRpcContext);
		
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

	private void exportWSGenomeFasta(String genomeWsRef, String sourceName) {
		String fileName = getFastaFileName(sourceName);
		FastaWriter fw = new FastaWriter(new File(getFastaFileName(fileName)));
		try{
			// Get sequences
			// Iterate and write to fw
		}finally{
			fw.close();	
		}		
	}

	private void exportBaseOrthologFasta(String sourceName) {
		KBaseRelationEngineClient reClient = reClient();

		
		String fileName = getFastaFileName(sourceName);
		FastaWriter fw = new FastaWriter(new File(fileName));
		try{
			for(String ortGuid: BASE_ORTHOLOG_GUIDS){
				List<FeatureSequence> sequences = reClient.getFeatureSequences(
						new GetFeatureSequencesParams().withOrthologGuid(ortGuid), 
						jsonRpcContext);
				
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
