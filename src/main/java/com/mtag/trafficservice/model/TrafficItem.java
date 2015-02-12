package com.mtag.trafficservice.model;

import java.io.Serializable;

public class TrafficItem implements Serializable, Comparable {

	private static final long serialVersionUID = 365503745315195851L;

	private String street;
	private String directionTo;
	private String directionFrom;
	private String description;
	private double longitude, latitude;
	private Integer kilometer;
	private String state;
	private TrafficType type;
	private int delayMinutes;
	private int maxSpeed;

	public TrafficItem() {
		this("", "", "", "", 0.0, 0.0, -1, "", TrafficType.NONE, -1, -1);
	}

	public TrafficItem(String street, String directionTo, String directionFrom,
			String description, double longitude, double altitude,
			Integer kilometer, String state, TrafficType type,
			int delayMinutes, int maxSpeed) {
		this.street = street;
		this.directionTo = directionTo;
		this.description = description;
		this.directionFrom = directionFrom;
		this.longitude = longitude;
		this.latitude = altitude;
		this.kilometer = kilometer;
		this.state = state;
		this.type = type;
		this.delayMinutes = delayMinutes;
		this.maxSpeed = maxSpeed;
	}

	public TrafficType getType() {
		return type;
	}

	public void setType(TrafficType type) {
		this.type = type;
	}

	public String getDirectionTo() {
		return directionTo;
	}

	public void setDirectionTo(String directionTo) {
		this.directionTo = directionTo;
	}

	public String getDirectionFrom() {
		return directionFrom;
	}

	public void setDirectionFrom(String directionFrom) {
		this.directionFrom = directionFrom;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
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

	public void setKilometer(Integer kilometer) {
		this.kilometer = kilometer;
	}

	public Integer getKilometer() {
		return kilometer;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setDelayMinutes(int delayMinutes) {
		this.delayMinutes = delayMinutes;
	}

	public int getDelayMinutes() {
		return delayMinutes;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	@Override
	public int compareTo(Object o) {
		if (o == null || !(o instanceof TrafficItem)) {
			return 1;
		}
		return this.getStreet().compareToIgnoreCase(
				((TrafficItem) o).getStreet());
	}

	@Override
	public String toString() {
		return "TrafficItem{" + "street=" + street + ", directionTo="
				+ directionTo + ", directionFrom=" + directionFrom
				+ ", description=" + description + ", longitude=" + longitude
				+ ", latitude=" + latitude + ", kilometer=" + kilometer
				+ ", state=" + state + ", type=" + type + ", delayMinutes="
				+ delayMinutes + ", maxSpeed=" + maxSpeed + '}';
	}

}
