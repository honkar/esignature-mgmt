package com.calpers.esignaturemgmt.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Signature {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Getter @Setter
	private Long userId;
	@Getter @Setter
	private String filename;
	@Getter @Setter
	private String type;
	@Getter @Setter
	private int version;
	@Getter @Setter
	private String status;
	
	public Signature(Long userId, String filename, String type, int version, String status) {
		super();
		this.userId = userId;
		this.filename = filename;
		this.type = type;
		this.version = version;
		this.status = status;
	}
}
