/*
 * The MIT License
 *
 * Copyright 2015 Pivotal Software, Inc..
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
package com.mtag.trafficservice.controller;

import com.mtag.trafficservice.model.BufferedTrafficService;
import com.mtag.trafficservice.model.TrafficData;
import com.mtag.trafficservice.parser.ParseItem;
import com.mtag.trafficservice.parser.Parser;
import com.mtag.trafficservice.tools.XmlServiceException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author cwahlmann
 */
@RestController
public class TrafficController {
    
    @Autowired
    private BufferedTrafficService bufferedTrafficService;

    public BufferedTrafficService getBufferedTrafficService() {
        return bufferedTrafficService;
    }

    public void setBufferedTrafficService(BufferedTrafficService bufferedTrafficService) {
        this.bufferedTrafficService = bufferedTrafficService;
    }

    
    @RequestMapping(value="/restTrafficService")
    public TrafficData RestTrafficService (
            @RequestParam(value = "userInput", required=false, defaultValue = "") String userInput)
    {
        try {
            Parser parser = new Parser();
            List<ParseItem> input = parser.parseUserInput(userInput);
            TrafficData data = parser.filter(bufferedTrafficService.getTrafficData(),input);
            return data;
        } catch (XmlServiceException ex) {
            return new TrafficData(ex);
        }
    }    
}
