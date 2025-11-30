package org.sheasepherd.ghostnet.ghostnetfishing.dto;

public class GeisternetzMeldungDTO {

	private double latitude;
	private double longitude;
	private int groesse;

	public GeisternetzMeldungDTO() {
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getGroesse() {
		return groesse;
	}

	public void setGroesse(int groesse) {
		this.groesse = groesse;
	}
}