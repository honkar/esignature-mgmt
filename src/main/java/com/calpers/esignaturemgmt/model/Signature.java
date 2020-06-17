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
	private int type;
	@Getter @Setter
	private int version;
	@Getter @Setter
	private int status;
	
	public Signature(Long userId, String filename, int type, int version, int status) {
		super();
		this.userId = userId;
		this.filename = filename;
		this.type = type;
		this.version = version;
		this.status = status;
	}
	
	public Signature() {}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
        this.id = id;
    }
	
	public Long getUserId() {
		return id;
	}
	
	public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFileName() {
        return filename;
    }

    public void setFileName(String fileName) {
        this.filename = fileName;
    }
    
    public int getType() {
		return type;
	}
	
	public void setType(int type) {
        this.type = type;
    }
	
	public int getVersion() {
		return type;
	}
	
	public void setVersion(int version) {
        this.version = version;
    }
	
	public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
