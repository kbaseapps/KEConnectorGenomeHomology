
package keconnectorgenomehomology;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * <p>Original spec-file type: RunParams</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "genome_ws_ref"
})
public class RunParams {

    @JsonProperty("genome_ws_ref")
    private String genomeWsRef;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("genome_ws_ref")
    public String getGenomeWsRef() {
        return genomeWsRef;
    }

    @JsonProperty("genome_ws_ref")
    public void setGenomeWsRef(String genomeWsRef) {
        this.genomeWsRef = genomeWsRef;
    }

    public RunParams withGenomeWsRef(String genomeWsRef) {
        this.genomeWsRef = genomeWsRef;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return ((((("RunParams"+" [genomeWsRef=")+ genomeWsRef)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
