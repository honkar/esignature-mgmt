package com.calpers.esignaturemgmt.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.calpers.esignaturemgmt.model.Signature;
import com.calpers.esignaturemgmt.model.User;
import com.calpers.esignaturemgmt.repository.SignatureRepository;
import com.calpers.esignaturemgmt.web.SignatureController;

@Service
public class SignatureServiceImpl implements SignatureService{

	@Autowired
	private SignatureRepository signatureRepository;
	
	@Autowired
	User user;
	
	@Autowired
	HttpSession session;
	
	public Signature addSignature(Signature sign) {
		return signatureRepository.save(sign);
	}
	
	public void deleteSignature(Long id) {
		signatureRepository.deleteById(id);
	}
	
	/*
	 * Upload signature to the Uploads folder.
	 */
	public void uploadSignature(String uploadDirectory, MultipartFile signatureFile){
		Path fileNameAndPath = Paths.get(uploadDirectory, signatureFile.getOriginalFilename());  
		user = (User) session.getAttribute("User");
		try {
			// Upload File to "Uploads" folder
			Files.write(fileNameAndPath, signatureFile.getBytes());
			// Set signature values
			Signature sign = new Signature();
			sign.setFileName(signatureFile.getOriginalFilename());
			sign.setStatus(SignatureController.ACTIVE_SIGNATURE);
			sign.setType(SignatureController.UPLOAD_SIGNATURE);
			sign.setUserId(user.getId()); // TODO Take user from UI
			sign.setVersion(1); // TODO set latest version
			
			// Add signature to database.
			addSignature(sign);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
