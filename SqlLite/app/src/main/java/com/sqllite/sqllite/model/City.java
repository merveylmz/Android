
package com.sqllite.sqllite.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "findname",
    "country",
    "coord",
    "zoom",
    "langs"
})
public class City {

    // Labels table name
    public static final String TABLE_CITY = "city";

    // Labels Table Columns names
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_FINDNAME = "findname";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_ZOOM = "zoom";

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("findname")
    private String findname;
    @JsonProperty("country")
    private String country;
    @JsonProperty("coord")
    private Coord coord;
    @JsonProperty("zoom")
    private Integer zoom;
    @JsonProperty("langs")
    private List<Lang> langs = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("findname")
    public String getFindname() {
        return findname;
    }

    @JsonProperty("findname")
    public void setFindname(String findname) {
        this.findname = findname;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("coord")
    public Coord getCoord() {
        return coord;
    }

    @JsonProperty("coord")
    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    @JsonProperty("zoom")
    public Integer getZoom() {
        return zoom;
    }

    @JsonProperty("zoom")
    public void setZoom(Integer zoom) {
        this.zoom = zoom;
    }

    @JsonProperty("langs")
    public List<Lang> getLangs() {
        return langs;
    }

    @JsonProperty("langs")
    public void setLangs(List<Lang> langs) {
        this.langs = langs;
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
