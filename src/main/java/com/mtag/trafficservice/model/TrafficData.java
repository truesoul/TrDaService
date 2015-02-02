/*
 * The MIT License
 *
 * Copyright 2015 christian.
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
package com.mtag.trafficservice.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author christian
 */
public class TrafficData implements Serializable {
    private long lastUpdated;
    private final List<TrafficItem> trafficItems;
    private String copyright;
    private String errorMessage;
    
    public TrafficData() {
        lastUpdated=0;
        copyright = "";
        this.trafficItems = new ArrayList<>();
        errorMessage = "";
    }
    
    public TrafficData(Exception ex) {
        this();
        errorMessage = ex.getClass().getName()+": "+ex.getMessage();
    }
    
    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
    public List<TrafficItem> getTrafficItems()
    {
        return this.trafficItems;
    }
    
    public void addTrafficItem(TrafficItem trafficItem)
    {
        this.trafficItems.add(trafficItem);
    }
    
    public int getCount()
    {
        return this.trafficItems.size();
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    @Override
    public String toString() {
        String result = "TrafficData{" + "lastUpdated=" + lastUpdated + ", trafficItems={";
        for (TrafficItem item: trafficItems)
        {
            result+= "\n"+trafficItems;
        }
        return result+ "}}";
    }


}
