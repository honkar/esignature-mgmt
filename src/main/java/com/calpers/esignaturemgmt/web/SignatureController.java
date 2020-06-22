package com.calpers.esignaturemgmt.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.calpers.esignaturemgmt.helper.ImageDecoder;
import com.calpers.esignaturemgmt.service.SignatureService;

@RestController
public class SignatureController {
	public static String uploadDirectory = System.getProperty("user.dir")+"/uploads";
	public static final int ACTIVE_SIGNATURE = 1;
	public static final int INACTIVE_SIGNATURE = 2;
	public static final int DRAW_SIGNATURE = 1;
	public static final int UPLOAD_SIGNATURE = 2;
	
	@Autowired
	private SignatureService signatureService;
	
	

	/*@PostMapping("/uploadSignatureCall")
    public String upload(Model model, @RequestParam("signatureFile") MultipartFile file) {
  	  
		signatureService.uploadSignature(uploadDirectory, file);
  	 
		model.addAttribute("successfulUploadMsg", "Signature Uploaded Successfully "+file.getOriginalFilename());
		return "uploadStatusView";
    }*/
	
	@RequestMapping(value = "/uploadSignatureCall", method = RequestMethod.POST)
    public ResponseEntity<Object> upload(@RequestBody String signImg) {
		boolean uploadStatus = true;
		if(uploadStatus) {
			return new ResponseEntity<>("Success",HttpStatus.OK);
		}
		return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
		
    }
	
	@RequestMapping(value = "/saveSignature", method = RequestMethod.POST)
    public ResponseEntity<Object> saveSignature(@RequestBody String signImg) {
		ImageDecoder imgDecoder = new ImageDecoder();
		String str[] = signImg.split(",");
		boolean uploadStatus = imgDecoder.decodeToImage(str[1]);
		if(uploadStatus) {
			return new ResponseEntity<>("Success",HttpStatus.OK);
		}
		return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
		
    }
	

}
