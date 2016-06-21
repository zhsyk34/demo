package com.cat.demo.entity;

import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class House extends Model<House> {

	// private int id;
	//
	// private String name;
	//
	// private Date buildtime;

	public final static House dao = new House();
}
