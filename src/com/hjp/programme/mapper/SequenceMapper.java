package com.hjp.programme.mapper;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

@Component("sequenceMapper")
public interface SequenceMapper {
	public Long nextval(String name);
	public Long currval(String name);
	public Long setval(String name, Long currentValue);
	public String getSysDate();
	public Timestamp getSysDateTimestamp();
	public String getSysDateFormt();
}