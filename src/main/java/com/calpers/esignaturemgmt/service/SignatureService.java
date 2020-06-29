package com.calpers.esignaturemgmt.service;

import com.calpers.esignaturemgmt.model.Signature;

public interface SignatureService {
	Signature addSignature(Signature sign);
	void deleteSignature(Long id);
	public void saveDBSignature(String fileName, int signatureType, String preferredName);
	public Signature findSignatureByUserIdAndStatus(long userId, int status);
}
