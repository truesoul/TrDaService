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
package com.mtag.trafficservice.model;

import org.springframework.beans.factory.annotation.Autowired;

import com.mtag.trafficservice.TrafficService;
import com.mtag.trafficservice.TrafficService.ServiceType;
import com.mtag.trafficservice.tools.XmlServiceException;

/**
 *
 * @author cwahlmann
 */
public class BufferedTrafficService {

	@Autowired
	private TrafficService trafficService;

	// public TrafficService getTrafficService() {
	// return trafficService;
	// }
	//
	// public void setTrafficService(TrafficService trafficService) {
	// this.trafficService = trafficService;
	// }

	private TrafficData currentTrafficData;
	private long expires;
	private long timeLive;

	public BufferedTrafficService() {
		this(1000 * 60);
	}

	public BufferedTrafficService(long timeToLive) {
		this.currentTrafficData = null;
		this.expires = 0;
		this.timeLive = timeToLive;
	}

	public TrafficData getTrafficData(ServiceType type)
			throws XmlServiceException {
		if (currentTrafficData == null || System.currentTimeMillis() >= expires) {
			currentTrafficData = trafficService.requestTraffic(type);
			expires = System.currentTimeMillis() + timeLive;
		}
		return currentTrafficData;
	}

}
