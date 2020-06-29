package com.calpers.esignaturemgmt.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.calpers.esignaturemgmt.model.Signature;
import com.calpers.esignaturemgmt.model.User;
import com.calpers.esignaturemgmt.repository.SignatureRepository;
import com.calpers.esignaturemgmt.web.SignatureController;
import com.calpers.esignaturemgmt.web.dto.UserSessionDto;

@Service
public class SignatureServiceImpl implements SignatureService{

	@Autowired
	private SignatureRepository signatureRepository;
	
	@Autowired
	private HttpSession session;
	
	public Signature addSignature(Signature sign) {
		return signatureRepository.save(sign);
	}
	
	public void deleteSignature(Long id) {
		signatureRepository.deleteById(id);
	}
	
	public Signature findSignatureByUserIdAndStatus(long userId, int status) {
		return signatureRepository.findByUserIdAndStatus(userId, status);
	}
	
	/*
	 * Upload signature to the Uploads folder.
	 */
	public void saveDBSignature(String fileName, int signatureType, String preferredName){
		UserSessionDto user = (UserSessionDto) session.getAttribute("userDetails");
		try {
			
			Signature latestSignature = findSignatureByUserIdAndStatus(user.getId(), SignatureController.ACTIVE_SIGNATURE);
			Signature sign = new Signature();
			sign.setFileName(fileName);
			sign.setStatus(SignatureController.ACTIVE_SIGNATURE);
			
			if (signatureType == SignatureController.UPLOAD_SIGNATURE) {
				sign.setType(SignatureController.UPLOAD_SIGNATURE);
			} else {
				sign.setType(SignatureController.DRAW_SIGNATURE);
			}
			
			sign.setUserId(user.getId()); 
			if (latestSignature != null ) {
				int version = latestSignature.getVersion() + 1;
				sign.setVersion(version); 
			} else {
				sign.setVersion(SignatureController.SIGNATURE_VERSION_FIRST); 
			}
			sign.setPreferredName(preferredName);
			Timestamp currentDate = new Timestamp(Calendar.getInstance().getTime().getTime());
			sign.setUploadDate(currentDate);
			
			// Set all other signatures status to inactive of the user
			signatureRepository.setSignatureStatus(SignatureController.INACTIVE_SIGNATURE, user.getId());
			
			// Add signature to database.
			addSignature(sign);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
