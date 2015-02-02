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

/**
 *
 * @author christian
 */
public class TrafficItem implements Serializable {
    private String street;
    private String direction;
    private String description;
    private double longitude, latitude;
    private TrafficType type;
    private int lengthKm;
    private int delayMinutes;
    private int maxSpeedKmh;

    public TrafficItem() {
        street="";
        direction="";
        description="";
        longitude=0;
        latitude=0;
        type=TrafficType.NONE;
        lengthKm=0;
        delayMinutes=0;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public TrafficType getType() {
        return type;
    }

    public void setType(TrafficType type) {
        this.type = type;
    }

    public int getLengthKm() {
        return lengthKm;
    }

    public void setLengthKm(int lengthKm) {
        this.lengthKm = lengthKm;
    }

    public int getDelayMinutes() {
        return delayMinutes;
    }

    public void setDelayMinutes(int delayMinutes) {
        this.delayMinutes = delayMinutes;
    }

    public int getMaxSpeedKmh() {
        return maxSpeedKmh;
    }

    public void setMaxSpeedKmh(int maxSpeedKmh) {
        this.maxSpeedKmh = maxSpeedKmh;
    }

    @Override
    public String toString() {
        return "TrafficItem{" + "street=" + street + ", direction=" + direction + ", description=" + description + ", longitude=" + longitude + ", altitude=" + latitude + ", type=" + type + ", km=" + lengthKm + ", delayMinutes=" + delayMinutes + '}';
    }
    
}
