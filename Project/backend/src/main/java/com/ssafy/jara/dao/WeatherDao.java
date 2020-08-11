package com.ssafy.jara.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.jara.dto.Location;

@Mapper
public interface WeatherDao {

	public List<Location> selectLocation();
	
	public int updateLocationWeather(Location location);
	
}
