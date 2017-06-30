
package com.sqllite.sqllite.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "speed",
    "gust",
    "deg"
})
public class Wind {

    // Labels table name
    public static final String TABLE_WIND = "wind";

    // Labels Table Columns names
    public static final String COLUMN_SPEED = "speed";
    public static final String COLUMN_GUST = "gust";
    public static final String COLUMN_DEG = "deg";



    @JsonProperty("speed")
    private Double speed;
    @JsonProperty("gust")
    private Double gust;
    @JsonProperty("deg")
    private Integer deg;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("speed")
    public Double getSpeed() {
        return speed;
    }

    @JsonProperty("speed")
    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    @JsonProperty("gust")
    public Double getGust() {
        return gust;
    }

    @JsonProperty("gust")
    public void setGust(Double gust) {
        this.gust = gust;
    }

    @JsonProperty("deg")
    public Integer getDeg() {
        return deg;
    }

    @JsonProperty("deg")
    public void setDeg(Integer deg) {
        this.deg = deg;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
