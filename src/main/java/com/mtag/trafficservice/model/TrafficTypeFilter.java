package com.mtag.trafficservice.model;

import java.util.HashMap;
import java.util.Map;

public class TrafficTypeFilter {
    
    public final static Map<TrafficType, String[]> filterPattern;
    
    static {
        filterPattern = new HashMap<>();
        filterPattern.put(TrafficType.ROAD_WORKS, new String[] {".*baustellen?.*", ".*straßenarbeiten.*"});
        filterPattern.put(TrafficType.TRAFFIC_JAM, new String[] {".*staus?.*",".*gestaut.*"});
        filterPattern.put(TrafficType.SLOW_MOVING, new String[] {".*stockend(er)?.*"});
        filterPattern.put(TrafficType.PARTICAL_CLOSURE, new String[] {".*teilsperrung(en)?.*",".*teil(s\\w+)?gesperrt.*"});
        filterPattern.put(TrafficType.CLOSURE, new String[] {".*sperrung(en)?.*",".*vollsperrung(en)?.*",".*gesperrt.*"});
        filterPattern.put(TrafficType.DANGER, new String[] {".*gefahr(en)?.*"});
        filterPattern.put(TrafficType.NONE, new String[] {".*"});
    }
    
    public static boolean matches(TrafficType type, String s)
    {
        if (filterPattern.containsKey(type))
        {
            for (String p:filterPattern.get(type))
            {
                if (s.toLowerCase().matches(p))
                {
                    return true;
                }
            }
        }
        return false;
    }
}
