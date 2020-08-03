package com.ssafy.jara.dto;

public class Location {
	
	private int no;
	private String name;
	private double x;
	private double y;
	
	public Location() {}
	
	public Location(int no, String name, double x, double y) {
		super();
		this.no = no;
		this.name = name;
		this.x = x;
		this.y = y;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
}
