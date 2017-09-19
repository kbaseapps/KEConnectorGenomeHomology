
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
    "app_guid",
    "obj_ref"
})
public class RunParams {

    @JsonProperty("app_guid")
    private String appGuid;
    @JsonProperty("obj_ref")
    private String objRef;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("app_guid")
    public String getAppGuid() {
        return appGuid;
    }

    @JsonProperty("app_guid")
    public void setAppGuid(String appGuid) {
        this.appGuid = appGuid;
    }

    public RunParams withAppGuid(String appGuid) {
        this.appGuid = appGuid;
        return this;
    }

    @JsonProperty("obj_ref")
    public String getObjRef() {
        return objRef;
    }

    @JsonProperty("obj_ref")
    public void setObjRef(String objRef) {
        this.objRef = objRef;
    }

    public RunParams withObjRef(String objRef) {
        this.objRef = objRef;
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
        return ((((((("RunParams"+" [appGuid=")+ appGuid)+", objRef=")+ objRef)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
