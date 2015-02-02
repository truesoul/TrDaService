package com.mtag.trafficservice.model;

public enum TrafficType {
    
    ROAD_WORKS(".*baustellen?.*", ".*straßenarbeiten.*"), 
    TRAFFIC_JAM(".*staus?.*",".*gestaut.*"), 
    SLOW_MOVING(".*stockend(er)?.*"), 
    PARTICAL_CLOSURE(".*teilsperrung(en)?.*",".*teil(s\\w+)?gesperrt.*"),
    CLOSURE(".*sperrung(en)?.*",".*vollsperrung(en)?.*",".*gesperrt.*"), 
    DANGER(".*gefahr(en)?.*"),
    NONE();
    
    private final String pattern[];

    private TrafficType(String... pattern) {
        this.pattern = pattern;
    }
        
    public boolean matches(String s)
    {
        for (String p:pattern)
        {
            if (s.toLowerCase().matches(p))
            {
                return true;
            }
        }
        return false;
    }
}
