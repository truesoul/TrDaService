/*
 * The MIT License
 *
 * Copyright 2015 Christian Wahlmann.
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
package com.mtag.trafficservice;

import com.mtag.trafficservice.model.TrafficData;
import com.mtag.trafficservice.model.TrafficItem;
import com.mtag.trafficservice.model.TrafficType;
import com.mtag.trafficservice.model.TrafficTypeFilter;
import com.mtag.trafficservice.tools.XmlServiceException;
import com.mtag.trafficservice.tools.XmlTools;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author christian
 */
public class TrafficService {
    
    public static enum ServiceType {wdr,freiefahrt};
    public final static String wdrTrafficServiceUrl="http://www.wdr.de/verkehrslage/rss.xml";
    public final static String wdrCopyright="www.wdr.de";
    public final static String freiefahrtTrafficServiceUrl="http://www.freiefahrt.info/upload/lmst.de_DE.xml";
    public final static String freieFahrtCopyright="www.freiefahrt.info";

    public TrafficData requestTraffic(ServiceType type) throws XmlServiceException {
        switch (type)
        {
            case freiefahrt:
                return requestFreiefahrtTraffic();
            case wdr:
            default:
                return requestWdrTraffic();
        }
    }
    
    public String getCopyright(ServiceType type)
    {
        switch (type)
        {
            case freiefahrt:
                return freieFahrtCopyright;
            case wdr:
            default:
                return wdrCopyright;
        }        
    }
    
    public TrafficData requestWdrTraffic() throws XmlServiceException {
        TrafficData result = new TrafficData();
        Document document = XmlTools.requestXmlService(wdrTrafficServiceUrl);
        NodeList nodeList = document.getElementsByTagName("item");
        for (int i=0; i<nodeList.getLength(); i++)
        {
            TrafficItem trafficItem = new TrafficItem();
            Node node = nodeList.item(i).getFirstChild();
            while (node!=null)
            {
                if (node.getNodeName().equals("title"))
                {
                    trafficItem.setStreet(node.getTextContent());
                }
                if (node.getNodeName().equals("description"))
                {
                    trafficItem.setDescription(
                            node.getTextContent()
                                    .trim().replace("<br />", " ")
                                    .replace("  "," "));
                }
                node = node.getNextSibling();
            }
            result.addTrafficItem(trafficItem);
        }
        result.setCopyright(wdrCopyright);
        return result;
    }

    private int parseNumber(String summary, String pattern)
    {
        int index=summary.toLowerCase().indexOf(pattern);
        if (index>=0)
        {
            int index0=index-2;
            while (index0>=0 && Character.isDigit(summary.charAt(index0)))
            {
                index0--;
            }
            if (index0>=0)
            {
                String n=summary.substring(index0+1, index-1);
                try {
                    return new Integer(n);
                }
                catch (NumberFormatException ex) {
                    System.out.println("Number format exception: ["+n+"]");
                }
            }
        }
        return -1;        
    }
    
    public TrafficData requestFreiefahrtTraffic() throws XmlServiceException {
        TrafficData result = new TrafficData();
        Document document = XmlTools.requestXmlService(freiefahrtTrafficServiceUrl);
        
        NodeList nodesUpdated = document.getElementsByTagName("updated");
        if (nodesUpdated.getLength()>0)
        {
            try {
                Node node=nodesUpdated.item(0);
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
                Date date = format.parse(node.getTextContent());
                result.setLastUpdated(date.getTime());
            } catch (ParseException ex) {
                throw new XmlServiceException("error parsing updated-tag in xml-file", ex);
            }
        }
        
        NodeList nodeList = document.getElementsByTagName("entry");
        for (int i=0; i<nodeList.getLength(); i++)
        {
            TrafficItem trafficItem = new TrafficItem();
            Node node = nodeList.item(i).getFirstChild();
            while (node!=null)
            {
                if (node.getNodeName().equals("title"))
                {
//                    System.out.println(node.getTextContent());
                    String[] its=node.getTextContent().split("\\:");
                    trafficItem.setStreet(its.length>0?its[0].trim():"");
                    if (its.length>1)
                    {
                        if (its[1].contains("Richtung"))
                        {
                            String[] d=its[1].split("Richtung");
                            trafficItem.setDirection(d[d.length-1].trim());
                        }
                        else if (its[1].contains("-"))
                        {
                            String[] d=its[1].split("-");
                            trafficItem.setDirection(d[d.length-1].trim());
                        }
                        else
                        {
                            trafficItem.setDirection(its[1].trim());
                        }
                    }
                    else {
                        trafficItem.setDirection("");
                    }
                }
                if (node.getNodeName().equals("summary"))
                {
                    String summary=node.getTextContent().trim();
                    trafficItem.setDescription(summary);
                    trafficItem.setMaxSpeedKmh(parseNumber(summary,"km/h"));
                    trafficItem.setDelayMinutes(parseNumber(summary,"minuten"));
                    trafficItem.setLengthKm(parseNumber(summary,"km stau"));
                    
                    for (TrafficType type: TrafficType.values())
                    {
                        if (TrafficTypeFilter.matches(type, summary))
                        {
                            trafficItem.setType(type);
                            break;
                        }
                    }
                }
                if (node.getNodeName().equals("link"))
                {
                    String[] its=node.getAttributes().getNamedItem("href").getTextContent().split("&");
                    for (String it:its)
                    {
//                        System.out.println(it);
                        if (it.startsWith("lon=") && it.length()>4)
                        {
                            trafficItem.setLongitude(new Double(it.substring(4)));
                        }
                        else if (it.startsWith("lat=") && it.length()>4)
                        {
                            trafficItem.setLatitude(new Double(it.substring(4)));
                        }
                    }
                }
                node = node.getNextSibling();
            }
            result.addTrafficItem(trafficItem);
        }
        result.setCopyright(freieFahrtCopyright);
        return result;
    }
}
