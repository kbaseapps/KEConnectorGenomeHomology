
package kbasegenomes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import us.kbase.common.service.Tuple7;


/**
 * <p>Original spec-file type: Genome</p>
 * <pre>
 * Genome object holds much of the data relevant for a genome in KBase
 *         Genome publications should be papers about the genome, not
 *         papers about certain features of the genome (which go into the
 *         Feature object)
 *         Should the Genome object have a list of feature ids? (in
 *         addition to having a list of feature_refs)
 *         Should the Genome object contain a list of contig_ids too?
 * @optional cdss mrnas assembly_ref quality close_genomes analysis_events features source_id source contigs contig_ids publications md5 taxonomy gc_content complete dna_size num_contigs contig_lengths contigset_ref
 * @optional taxon_ref assembly_ref gff_handle_ref genbank_handle_ref external_source_origination_date release original_source_file_name notes environmental_comments reference_annotation quality_score methodology type
 * @metadata ws gc_content as GC content
 * @metadata ws taxonomy as Taxonomy
 * @metadata ws md5 as MD5
 * @metadata ws dna_size as Size
 * @metadata ws genetic_code as Genetic code
 * @metadata ws domain as Domain
 *     @metadata ws source_id as Source ID
 *     @metadata ws source as Source
 *     @metadata ws scientific_name as Name
 *     @metadata ws length(close_genomes) as Close genomes
 *     @metadata ws length(features) as Number features
 *     @metadata ws num_contigs as Number contigs
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "id",
    "scientific_name",
    "domain",
    "genetic_code",
    "dna_size",
    "num_contigs",
    "contigs",
    "contig_lengths",
    "contig_ids",
    "source",
    "source_id",
    "md5",
    "taxonomy",
    "gc_content",
    "complete",
    "publications",
    "features",
    "cdss",
    "mrnas",
    "contigset_ref",
    "assembly_ref",
    "taxon_ref",
    "genbank_handle_ref",
    "gff_handle_ref",
    "external_source_origination_date",
    "release",
    "original_source_file_name",
    "notes",
    "environmental_comments",
    "reference_annotation",
    "quality_score",
    "methodology",
    "type",
    "quality",
    "close_genomes",
    "analysis_events"
})
public class Genome {

    @JsonProperty("id")
    private java.lang.String id;
    @JsonProperty("scientific_name")
    private java.lang.String scientificName;
    @JsonProperty("domain")
    private java.lang.String domain;
    @JsonProperty("genetic_code")
    private java.lang.Long geneticCode;
    @JsonProperty("dna_size")
    private java.lang.Long dnaSize;
    @JsonProperty("num_contigs")
    private java.lang.Long numContigs;
    @JsonProperty("contigs")
    private List<Contig> contigs;
    @JsonProperty("contig_lengths")
    private List<Long> contigLengths;
    @JsonProperty("contig_ids")
    private List<String> contigIds;
    @JsonProperty("source")
    private java.lang.String source;
    @JsonProperty("source_id")
    private java.lang.String sourceId;
    @JsonProperty("md5")
    private java.lang.String md5;
    @JsonProperty("taxonomy")
    private java.lang.String taxonomy;
    @JsonProperty("gc_content")
    private Double gcContent;
    @JsonProperty("complete")
    private java.lang.Long complete;
    @JsonProperty("publications")
    private List<Tuple7 <Long, String, String, String, String, String, String>> publications;
    @JsonProperty("features")
    private List<Feature> features;
    @JsonProperty("cdss")
    private List<CDS> cdss;
    @JsonProperty("mrnas")
    private List<MRNA> mrnas;
    @JsonProperty("contigset_ref")
    private java.lang.String contigsetRef;
    @JsonProperty("assembly_ref")
    private java.lang.String assemblyRef;
    @JsonProperty("taxon_ref")
    private java.lang.String taxonRef;
    @JsonProperty("genbank_handle_ref")
    private java.lang.String genbankHandleRef;
    @JsonProperty("gff_handle_ref")
    private java.lang.String gffHandleRef;
    @JsonProperty("external_source_origination_date")
    private java.lang.String externalSourceOriginationDate;
    @JsonProperty("release")
    private java.lang.String release;
    @JsonProperty("original_source_file_name")
    private java.lang.String originalSourceFileName;
    @JsonProperty("notes")
    private java.lang.String notes;
    @JsonProperty("environmental_comments")
    private java.lang.String environmentalComments;
    @JsonProperty("reference_annotation")
    private java.lang.Long referenceAnnotation;
    @JsonProperty("quality_score")
    private Double qualityScore;
    @JsonProperty("methodology")
    private java.lang.String methodology;
    @JsonProperty("type")
    private java.lang.String type;
    /**
     * <p>Original spec-file type: Genome_quality_measure</p>
     * <pre>
     * @optional frameshift_error_rate sequence_error_rate
     * </pre>
     * 
     */
    @JsonProperty("quality")
    private GenomeQualityMeasure quality;
    @JsonProperty("close_genomes")
    private List<CloseGenome> closeGenomes;
    @JsonProperty("analysis_events")
    private List<AnalysisEvent> analysisEvents;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("id")
    public java.lang.String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(java.lang.String id) {
        this.id = id;
    }

    public Genome withId(java.lang.String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("scientific_name")
    public java.lang.String getScientificName() {
        return scientificName;
    }

    @JsonProperty("scientific_name")
    public void setScientificName(java.lang.String scientificName) {
        this.scientificName = scientificName;
    }

    public Genome withScientificName(java.lang.String scientificName) {
        this.scientificName = scientificName;
        return this;
    }

    @JsonProperty("domain")
    public java.lang.String getDomain() {
        return domain;
    }

    @JsonProperty("domain")
    public void setDomain(java.lang.String domain) {
        this.domain = domain;
    }

    public Genome withDomain(java.lang.String domain) {
        this.domain = domain;
        return this;
    }

    @JsonProperty("genetic_code")
    public java.lang.Long getGeneticCode() {
        return geneticCode;
    }

    @JsonProperty("genetic_code")
    public void setGeneticCode(java.lang.Long geneticCode) {
        this.geneticCode = geneticCode;
    }

    public Genome withGeneticCode(java.lang.Long geneticCode) {
        this.geneticCode = geneticCode;
        return this;
    }

    @JsonProperty("dna_size")
    public java.lang.Long getDnaSize() {
        return dnaSize;
    }

    @JsonProperty("dna_size")
    public void setDnaSize(java.lang.Long dnaSize) {
        this.dnaSize = dnaSize;
    }

    public Genome withDnaSize(java.lang.Long dnaSize) {
        this.dnaSize = dnaSize;
        return this;
    }

    @JsonProperty("num_contigs")
    public java.lang.Long getNumContigs() {
        return numContigs;
    }

    @JsonProperty("num_contigs")
    public void setNumContigs(java.lang.Long numContigs) {
        this.numContigs = numContigs;
    }

    public Genome withNumContigs(java.lang.Long numContigs) {
        this.numContigs = numContigs;
        return this;
    }

    @JsonProperty("contigs")
    public List<Contig> getContigs() {
        return contigs;
    }

    @JsonProperty("contigs")
    public void setContigs(List<Contig> contigs) {
        this.contigs = contigs;
    }

    public Genome withContigs(List<Contig> contigs) {
        this.contigs = contigs;
        return this;
    }

    @JsonProperty("contig_lengths")
    public List<Long> getContigLengths() {
        return contigLengths;
    }

    @JsonProperty("contig_lengths")
    public void setContigLengths(List<Long> contigLengths) {
        this.contigLengths = contigLengths;
    }

    public Genome withContigLengths(List<Long> contigLengths) {
        this.contigLengths = contigLengths;
        return this;
    }

    @JsonProperty("contig_ids")
    public List<String> getContigIds() {
        return contigIds;
    }

    @JsonProperty("contig_ids")
    public void setContigIds(List<String> contigIds) {
        this.contigIds = contigIds;
    }

    public Genome withContigIds(List<String> contigIds) {
        this.contigIds = contigIds;
        return this;
    }

    @JsonProperty("source")
    public java.lang.String getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(java.lang.String source) {
        this.source = source;
    }

    public Genome withSource(java.lang.String source) {
        this.source = source;
        return this;
    }

    @JsonProperty("source_id")
    public java.lang.String getSourceId() {
        return sourceId;
    }

    @JsonProperty("source_id")
    public void setSourceId(java.lang.String sourceId) {
        this.sourceId = sourceId;
    }

    public Genome withSourceId(java.lang.String sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    @JsonProperty("md5")
    public java.lang.String getMd5() {
        return md5;
    }

    @JsonProperty("md5")
    public void setMd5(java.lang.String md5) {
        this.md5 = md5;
    }

    public Genome withMd5(java.lang.String md5) {
        this.md5 = md5;
        return this;
    }

    @JsonProperty("taxonomy")
    public java.lang.String getTaxonomy() {
        return taxonomy;
    }

    @JsonProperty("taxonomy")
    public void setTaxonomy(java.lang.String taxonomy) {
        this.taxonomy = taxonomy;
    }

    public Genome withTaxonomy(java.lang.String taxonomy) {
        this.taxonomy = taxonomy;
        return this;
    }

    @JsonProperty("gc_content")
    public Double getGcContent() {
        return gcContent;
    }

    @JsonProperty("gc_content")
    public void setGcContent(Double gcContent) {
        this.gcContent = gcContent;
    }

    public Genome withGcContent(Double gcContent) {
        this.gcContent = gcContent;
        return this;
    }

    @JsonProperty("complete")
    public java.lang.Long getComplete() {
        return complete;
    }

    @JsonProperty("complete")
    public void setComplete(java.lang.Long complete) {
        this.complete = complete;
    }

    public Genome withComplete(java.lang.Long complete) {
        this.complete = complete;
        return this;
    }

    @JsonProperty("publications")
    public List<Tuple7 <Long, String, String, String, String, String, String>> getPublications() {
        return publications;
    }

    @JsonProperty("publications")
    public void setPublications(List<Tuple7 <Long, String, String, String, String, String, String>> publications) {
        this.publications = publications;
    }

    public Genome withPublications(List<Tuple7 <Long, String, String, String, String, String, String>> publications) {
        this.publications = publications;
        return this;
    }

    @JsonProperty("features")
    public List<Feature> getFeatures() {
        return features;
    }

    @JsonProperty("features")
    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public Genome withFeatures(List<Feature> features) {
        this.features = features;
        return this;
    }

    @JsonProperty("cdss")
    public List<CDS> getCdss() {
        return cdss;
    }

    @JsonProperty("cdss")
    public void setCdss(List<CDS> cdss) {
        this.cdss = cdss;
    }

    public Genome withCdss(List<CDS> cdss) {
        this.cdss = cdss;
        return this;
    }

    @JsonProperty("mrnas")
    public List<MRNA> getMrnas() {
        return mrnas;
    }

    @JsonProperty("mrnas")
    public void setMrnas(List<MRNA> mrnas) {
        this.mrnas = mrnas;
    }

    public Genome withMrnas(List<MRNA> mrnas) {
        this.mrnas = mrnas;
        return this;
    }

    @JsonProperty("contigset_ref")
    public java.lang.String getContigsetRef() {
        return contigsetRef;
    }

    @JsonProperty("contigset_ref")
    public void setContigsetRef(java.lang.String contigsetRef) {
        this.contigsetRef = contigsetRef;
    }

    public Genome withContigsetRef(java.lang.String contigsetRef) {
        this.contigsetRef = contigsetRef;
        return this;
    }

    @JsonProperty("assembly_ref")
    public java.lang.String getAssemblyRef() {
        return assemblyRef;
    }

    @JsonProperty("assembly_ref")
    public void setAssemblyRef(java.lang.String assemblyRef) {
        this.assemblyRef = assemblyRef;
    }

    public Genome withAssemblyRef(java.lang.String assemblyRef) {
        this.assemblyRef = assemblyRef;
        return this;
    }

    @JsonProperty("taxon_ref")
    public java.lang.String getTaxonRef() {
        return taxonRef;
    }

    @JsonProperty("taxon_ref")
    public void setTaxonRef(java.lang.String taxonRef) {
        this.taxonRef = taxonRef;
    }

    public Genome withTaxonRef(java.lang.String taxonRef) {
        this.taxonRef = taxonRef;
        return this;
    }

    @JsonProperty("genbank_handle_ref")
    public java.lang.String getGenbankHandleRef() {
        return genbankHandleRef;
    }

    @JsonProperty("genbank_handle_ref")
    public void setGenbankHandleRef(java.lang.String genbankHandleRef) {
        this.genbankHandleRef = genbankHandleRef;
    }

    public Genome withGenbankHandleRef(java.lang.String genbankHandleRef) {
        this.genbankHandleRef = genbankHandleRef;
        return this;
    }

    @JsonProperty("gff_handle_ref")
    public java.lang.String getGffHandleRef() {
        return gffHandleRef;
    }

    @JsonProperty("gff_handle_ref")
    public void setGffHandleRef(java.lang.String gffHandleRef) {
        this.gffHandleRef = gffHandleRef;
    }

    public Genome withGffHandleRef(java.lang.String gffHandleRef) {
        this.gffHandleRef = gffHandleRef;
        return this;
    }

    @JsonProperty("external_source_origination_date")
    public java.lang.String getExternalSourceOriginationDate() {
        return externalSourceOriginationDate;
    }

    @JsonProperty("external_source_origination_date")
    public void setExternalSourceOriginationDate(java.lang.String externalSourceOriginationDate) {
        this.externalSourceOriginationDate = externalSourceOriginationDate;
    }

    public Genome withExternalSourceOriginationDate(java.lang.String externalSourceOriginationDate) {
        this.externalSourceOriginationDate = externalSourceOriginationDate;
        return this;
    }

    @JsonProperty("release")
    public java.lang.String getRelease() {
        return release;
    }

    @JsonProperty("release")
    public void setRelease(java.lang.String release) {
        this.release = release;
    }

    public Genome withRelease(java.lang.String release) {
        this.release = release;
        return this;
    }

    @JsonProperty("original_source_file_name")
    public java.lang.String getOriginalSourceFileName() {
        return originalSourceFileName;
    }

    @JsonProperty("original_source_file_name")
    public void setOriginalSourceFileName(java.lang.String originalSourceFileName) {
        this.originalSourceFileName = originalSourceFileName;
    }

    public Genome withOriginalSourceFileName(java.lang.String originalSourceFileName) {
        this.originalSourceFileName = originalSourceFileName;
        return this;
    }

    @JsonProperty("notes")
    public java.lang.String getNotes() {
        return notes;
    }

    @JsonProperty("notes")
    public void setNotes(java.lang.String notes) {
        this.notes = notes;
    }

    public Genome withNotes(java.lang.String notes) {
        this.notes = notes;
        return this;
    }

    @JsonProperty("environmental_comments")
    public java.lang.String getEnvironmentalComments() {
        return environmentalComments;
    }

    @JsonProperty("environmental_comments")
    public void setEnvironmentalComments(java.lang.String environmentalComments) {
        this.environmentalComments = environmentalComments;
    }

    public Genome withEnvironmentalComments(java.lang.String environmentalComments) {
        this.environmentalComments = environmentalComments;
        return this;
    }

    @JsonProperty("reference_annotation")
    public java.lang.Long getReferenceAnnotation() {
        return referenceAnnotation;
    }

    @JsonProperty("reference_annotation")
    public void setReferenceAnnotation(java.lang.Long referenceAnnotation) {
        this.referenceAnnotation = referenceAnnotation;
    }

    public Genome withReferenceAnnotation(java.lang.Long referenceAnnotation) {
        this.referenceAnnotation = referenceAnnotation;
        return this;
    }

    @JsonProperty("quality_score")
    public Double getQualityScore() {
        return qualityScore;
    }

    @JsonProperty("quality_score")
    public void setQualityScore(Double qualityScore) {
        this.qualityScore = qualityScore;
    }

    public Genome withQualityScore(Double qualityScore) {
        this.qualityScore = qualityScore;
        return this;
    }

    @JsonProperty("methodology")
    public java.lang.String getMethodology() {
        return methodology;
    }

    @JsonProperty("methodology")
    public void setMethodology(java.lang.String methodology) {
        this.methodology = methodology;
    }

    public Genome withMethodology(java.lang.String methodology) {
        this.methodology = methodology;
        return this;
    }

    @JsonProperty("type")
    public java.lang.String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(java.lang.String type) {
        this.type = type;
    }

    public Genome withType(java.lang.String type) {
        this.type = type;
        return this;
    }

    /**
     * <p>Original spec-file type: Genome_quality_measure</p>
     * <pre>
     * @optional frameshift_error_rate sequence_error_rate
     * </pre>
     * 
     */
    @JsonProperty("quality")
    public GenomeQualityMeasure getQuality() {
        return quality;
    }

    /**
     * <p>Original spec-file type: Genome_quality_measure</p>
     * <pre>
     * @optional frameshift_error_rate sequence_error_rate
     * </pre>
     * 
     */
    @JsonProperty("quality")
    public void setQuality(GenomeQualityMeasure quality) {
        this.quality = quality;
    }

    public Genome withQuality(GenomeQualityMeasure quality) {
        this.quality = quality;
        return this;
    }

    @JsonProperty("close_genomes")
    public List<CloseGenome> getCloseGenomes() {
        return closeGenomes;
    }

    @JsonProperty("close_genomes")
    public void setCloseGenomes(List<CloseGenome> closeGenomes) {
        this.closeGenomes = closeGenomes;
    }

    public Genome withCloseGenomes(List<CloseGenome> closeGenomes) {
        this.closeGenomes = closeGenomes;
        return this;
    }

    @JsonProperty("analysis_events")
    public List<AnalysisEvent> getAnalysisEvents() {
        return analysisEvents;
    }

    @JsonProperty("analysis_events")
    public void setAnalysisEvents(List<AnalysisEvent> analysisEvents) {
        this.analysisEvents = analysisEvents;
    }

    public Genome withAnalysisEvents(List<AnalysisEvent> analysisEvents) {
        this.analysisEvents = analysisEvents;
        return this;
    }

    @JsonAnyGetter
    public Map<java.lang.String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(java.lang.String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public java.lang.String toString() {
        return ((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((("Genome"+" [id=")+ id)+", scientificName=")+ scientificName)+", domain=")+ domain)+", geneticCode=")+ geneticCode)+", dnaSize=")+ dnaSize)+", numContigs=")+ numContigs)+", contigs=")+ contigs)+", contigLengths=")+ contigLengths)+", contigIds=")+ contigIds)+", source=")+ source)+", sourceId=")+ sourceId)+", md5=")+ md5)+", taxonomy=")+ taxonomy)+", gcContent=")+ gcContent)+", complete=")+ complete)+", publications=")+ publications)+", features=")+ features)+", cdss=")+ cdss)+", mrnas=")+ mrnas)+", contigsetRef=")+ contigsetRef)+", assemblyRef=")+ assemblyRef)+", taxonRef=")+ taxonRef)+", genbankHandleRef=")+ genbankHandleRef)+", gffHandleRef=")+ gffHandleRef)+", externalSourceOriginationDate=")+ externalSourceOriginationDate)+", release=")+ release)+", originalSourceFileName=")+ originalSourceFileName)+", notes=")+ notes)+", environmentalComments=")+ environmentalComments)+", referenceAnnotation=")+ referenceAnnotation)+", qualityScore=")+ qualityScore)+", methodology=")+ methodology)+", type=")+ type)+", quality=")+ quality)+", closeGenomes=")+ closeGenomes)+", analysisEvents=")+ analysisEvents)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
