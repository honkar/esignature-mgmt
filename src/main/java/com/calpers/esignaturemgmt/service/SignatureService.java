package com.calpers.esignaturemgmt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calpers.esignaturemgmt.model.Signature;
import com.calpers.esignaturemgmt.repository.SignatureRepository;

@Service
public class SignatureService {

	@Autowired
	private SignatureRepository signatureRepository;
	
	public void addSignature(Signature sign) {
		signatureRepository.save(sign);
	}
	
	public void deleteSignature(Long id) {
		signatureRepository.deleteById(id);
	}
	
	
}
