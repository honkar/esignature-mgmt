package com.calpers.esignaturemgmt.service;

import org.springframework.web.multipart.MultipartFile;
import com.calpers.esignaturemgmt.model.Signature;

public interface SignatureService {
	Signature addSignature(Signature sign);
	void deleteSignature(Long id);
	public void uploadSignature(String uploadDirectory, MultipartFile signatureFile);
}
