
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
    "langId",
    "bg",
    "el",
    "en",
    "es",
    "ja",
    "link",
    "no",
    "ru",
    "tr",
    "zh"
})
public class Lang {

    // Labels table name
    public static final String TABLE_LANG = "lang";

    // Labels Table Columns names
    public  static final String COLUMN_LANG_ID = "langId";
    public static final String COLUMN_BG = "bg";
    public static final String COLUMN_EL = "el";
    public static final String COLUMN_EN = "en";
    public static final String COLUMN_ES = "es";
    public static final String COLUMN_JA = "ja";
    public static final String COLUMN_LINK= "link";
    public static final String COLUMN_NO = "no";
    public static final String COLUMN_RU = "ru";
    public static final String COLUMN_TR = "tr";
    public static final String COLUMN_ZH = "zh";

    @JsonProperty("langId")
    private String langId;
    @JsonProperty("bg")
    private String bg;
    @JsonProperty("el")
    private String el;
    @JsonProperty("en")
    private String en;
    @JsonProperty("es")
    private String es;
    @JsonProperty("ja")
    private String ja;
    @JsonProperty("link")
    private String link;
    @JsonProperty("no")
    private String no;
    @JsonProperty("ru")
    private String ru;
    @JsonProperty("tr")
    private String tr;
    @JsonProperty("zh")
    private String zh;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("bg")
    public String getBg() {
        return bg;
    }

    @JsonProperty("bg")
    public void setBg(String bg) {
        this.bg = bg;
    }

    @JsonProperty("el")
    public String getEl() {
        return el;
    }

    @JsonProperty("el")
    public void setEl(String el) {
        this.el = el;
    }

    @JsonProperty("en")
    public String getEn() {
        return en;
    }

    @JsonProperty("en")
    public void setEn(String en) {
        this.en = en;
    }

    @JsonProperty("es")
    public String getEs() {
        return es;
    }

    @JsonProperty("es")
    public void setEs(String es) {
        this.es = es;
    }

    @JsonProperty("ja")
    public String getJa() {
        return ja;
    }

    @JsonProperty("ja")
    public void setJa(String ja) {
        this.ja = ja;
    }

    @JsonProperty("link")
    public String getLink() {
        return link;
    }

    @JsonProperty("link")
    public void setLink(String link) {
        this.link = link;
    }

    @JsonProperty("no")
    public String getNo() {
        return no;
    }

    @JsonProperty("no")
    public void setNo(String no) {
        this.no = no;
    }

    @JsonProperty("ru")
    public String getRu() {
        return ru;
    }

    @JsonProperty("ru")
    public void setRu(String ru) {
        this.ru = ru;
    }

    @JsonProperty("tr")
    public String getTr() {
        return tr;
    }

    @JsonProperty("tr")
    public void setTr(String tr) {
        this.tr = tr;
    }

    @JsonProperty("zh")
    public String getZh() {
        return zh;
    }

    @JsonProperty("zh")
    public void setZh(String zh) {
        this.zh = zh;
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
