package com.calpers.esignaturemgmt.web;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.calpers.esignaturemgmt.helper.ImageDecoder;
import com.calpers.esignaturemgmt.helper.RemoveBackground;
import com.calpers.esignaturemgmt.model.Signature;
import com.calpers.esignaturemgmt.service.SignatureService;
import com.calpers.esignaturemgmt.web.dto.SignatureAjax;
import com.calpers.esignaturemgmt.web.dto.UserSessionDto;

@RestController
public class SignatureController {
	public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/resources/static/uploads";
	public static final int ACTIVE_SIGNATURE = 1;
	public static final int INACTIVE_SIGNATURE = 2;
	public static final int DRAW_SIGNATURE = 1;
	public static final int UPLOAD_SIGNATURE = 2;
	public static final int SIGNATURE_VERSION_FIRST = 1;
	
	@Autowired
	private SignatureService signatureService;
	
	@Autowired
	private HttpSession session;
	
	@RequestMapping(value = "/uploadSignatureCall", method = RequestMethod.POST)
    public ResponseEntity<Object> upload(@RequestBody String signImg) {
		boolean uploadStatus = true;
		if(uploadStatus) {
			return new ResponseEntity<>("Success",HttpStatus.OK);
		}
		return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
		
    }
	
	/**
	 * Save Signature
	 * @param signatureObject
	 * @return
	 */
	@RequestMapping(value = "/saveSignature", method = RequestMethod.POST)
    public ResponseEntity<Object> saveSignature(@RequestBody SignatureAjax signatureObject) {
		ImageDecoder imgDecoder = new ImageDecoder();
		RemoveBackground remBackground = new RemoveBackground();
		UserSessionDto user = (UserSessionDto) session.getAttribute("userDetails");
		int signatureType = DRAW_SIGNATURE;
		
		// Set File Name (Signature_userId_version)
		StringBuilder fileName = new StringBuilder();
		fileName.append("Signature_").append(user.getId());
		
		Signature latestSignature = signatureService.findSignatureByUserIdAndStatus(user.getId(), SignatureController.ACTIVE_SIGNATURE);
		if(latestSignature != null) {
			fileName.append("_");
			fileName.append(latestSignature.getVersion() + 1);
		} else {
			fileName.append("_");
			fileName.append(SignatureController.SIGNATURE_VERSION_FIRST);
		}
		fileName.append(".png");
	
		// Get Signature type upload(2)/draw(1)
		String str[] = signatureObject.getSignImg().split(",");
		String uploadType = signatureObject.getUploadType();
		if (uploadType.contentEquals("upload")) {
			signatureType = UPLOAD_SIGNATURE;
		}
		
		// Get preferred name if provided
		String preferredName = signatureObject.getPreferredName();
		if (preferredName.isBlank()) {
			preferredName = null; 
		}
		
		// Store signature to upload folder
		boolean uploadStatus = imgDecoder.decodeToImage(str[1],fileName.toString());
		
		// Remove background from signature 
		boolean removeBackgroundStatus = remBackground.removeBackground(fileName.toString());
		
		// Save signature to database
		if(uploadStatus && removeBackgroundStatus) {
			signatureService.saveDBSignature(fileName.toString(), signatureType, preferredName);
		}
		
		
		if(uploadStatus && removeBackgroundStatus) {
			return new ResponseEntity<>("Success",HttpStatus.OK);
		}
		return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
		
    }
	

}
