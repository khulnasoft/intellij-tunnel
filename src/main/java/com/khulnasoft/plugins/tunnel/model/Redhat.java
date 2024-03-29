package com.khulnasoft.plugins.tunnel.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Redhat {
    @JsonProperty("V2Vector")
    public String v2Vector;
    @JsonProperty("V3Vector")
    public String v3Vector;
    @JsonProperty("V2Score")
    public double v2Score;
    @JsonProperty("V3Score")
    public double v3Score;
}
