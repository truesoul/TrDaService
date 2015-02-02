/*
 * The MIT License
 *
 * Copyright 2015 cwahlmann.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.mtag.trafficservice.parser;

import com.mtag.traffic.model.TrafficItem;
import com.mtag.traffic.model.TrafficType;
import com.mtag.trafficservice.model.TrafficData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author cwahlmann
 */
public class Parser {
    public static final Map<String,String> countries;
    static {
        countries = new HashMap<>();        
        countries.put("BW","baden-württemberg");
        countries.put("BY","bayern");
        countries.put("BE","berlin");
        countries.put("BB","brandenburg");
        countries.put("HB","bremen");
        countries.put("HH","hamburg");
        countries.put("HE","hessen");
        countries.put("MV","mecklenburg-vorpommern");
        countries.put("NI","niedersachsen");
        countries.put("NW","nordrhein-westfalen");
        countries.put("RP","rheinland-pfalz");
        countries.put("SL","saarland");
        countries.put("SN","sachsen");
        countries.put("ST","sachsen-anhalt");
        countries.put("SH","schleswig-holstein");
        countries.put("TH","thüringen");
    }
    
    public List<ParseItem> tokenize(String s)
    {
        List<ParseItem> result=new ArrayList<>();
        String[] words = s.split("\\s");
        Token[] tokens = Token.values();
        for (String w: words)
        {
            String word = w.trim().replaceAll("[\\,\\.\\-\\;\\_\\+\\*\\!\\?]", "");
            int i=0;
            while (i<tokens.length && !tokens[i].matches(w))
            {
                i++;
            }
            if (i<tokens.length)
            {
                result.add(new ParseItem(tokens[i], word));
            }
            else
            {
                result.add(new ParseItem(Token.OTHER, word));
            }
        }
        return result;
    }
    
    public List<ParseItem> parseUserInput(String s)
    {
        
        ParseItem actualItem=null;
        List<ParseItem> result=new ArrayList<>();
        for (ParseItem item:tokenize(s))
        {
            switch (item.getToken())
            {
                case DIRECTION:
                case PARTIAL:
                    actualItem = item;
                    break;
                case OTHER:
                    if (actualItem!=null)
                    {
                        actualItem.setSrcString(item.getSrcString());
                        result.add(actualItem);
                        actualItem=null;
                    }
                    break;
                case CLOSED:
                    if (actualItem!=null && actualItem.getToken()==Token.PARTIAL)
                    {
                        item.setToken(Token.PARTIAL_CLOSURE);
                        result.add(item);
                    }
                    else
                    {
                        item.setToken(Token.CLOSURE);
                        result.add(item);
                    }
                    actualItem=null;
                    break;
                case JAMMED:
                    item.setToken(Token.TRAFFIC_JAM);
                    result.add(item);
                    actualItem=null;
                    break;
                default:
                    result.add(item);
                    actualItem=null;
            }
        }
        return result;
    }
   
    public boolean matchesTrafficItem(TrafficItem item, List<ParseItem> parseItems)
    {
        for (ParseItem parseItem: parseItems)
        {
            switch (parseItem.getToken())
            {
                case AUTOBAHN:
                case BUNDESSTR:
                    if (!item.getStreet().equalsIgnoreCase(parseItem.getSrcString()))
                    {
                        return false;
                    }
                    break;
                case COUNTRY:
                    break;
                case PARTIAL_CLOSURE:
                    if (item.getType()!=TrafficType.PARTICAL_CLOSURE)
                    {
                        return false;
                    }
                    break;
                case CLOSURE:
                    if (item.getType()!=TrafficType.CLOSURE)
                    {
                        return false;
                    }
                    break;
                case DIRECTION:
                    if (!item.getDirection().toLowerCase().contains(parseItem.getSrcString().toLowerCase()))
                    {
                        return false;
                    }
                    break;
                case ROADWORKS:
                    if (item.getType()!=TrafficType.ROAD_WORKS)
                    {
                        return false;
                    }
                    break;
                case SLOW_MOVING:
                    if (item.getType()!=TrafficType.SLOW_MOVING)
                    {
                        return false;
                    }
                    break;
                case TRAFFIC_JAM:
                    if (item.getType()!=TrafficType.TRAFFIC_JAM)
                    {
                        return false;
                    }
                    break;
            }
        }
        return true;
    }
    
    public TrafficData filter(TrafficData trafficData, List<ParseItem> parseItems)
    {
        TrafficData result = new TrafficData();
        result.setCopyright(trafficData.getCopyright());
        result.setLastUpdated(trafficData.getLastUpdated());
        for (TrafficItem trafficItem: trafficData.getTrafficItems())
        {
            if (matchesTrafficItem(trafficItem, parseItems))
            {
                result.addTrafficItem(trafficItem);
            }
        }
        return result;
    }
    
//    public static void main(String[] args)
//    {
//        try {
//            BufferedTrafficService ts=new BufferedTrafficService(1000);
//            ts.setTrafficService(new TrafficService());
//
//            TrafficData d=ts.getTrafficData();
//            
//            Parser parser = new Parser();
//            List<ParseItem> l = parser.parseUserInput(
////                    "Stau auf der A46"
//                    "Stau auf der A46 Richtung Düsseldorf"
////                    "Staus und stockender Verkehr auf der B52 in Richtung Dortmund, Gefahr durch Bäume auf der Fahrbahn!"
//            );
//            for (ParseItem p:l)
//            {
//                System.out.println(p.getToken()+" - "+p.getSrcString());
//            }
//            
//            TrafficData df=parser.filter(d, l);
//            for (TrafficItem it: df.getTrafficItems())
//            {
//                System.out.println(it);
//            }
//        } catch (XmlServiceException ex) {
//            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    
//    }
}
