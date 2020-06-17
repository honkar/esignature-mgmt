package com.calpers.esignaturemgmt.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.calpers.esignaturemgmt.service.SignatureService;

@Controller
public class SignatureController {
	public static String uploadDirectory = System.getProperty("user.dir")+"/uploads";
	public static final int ACTIVE_SIGNATURE = 1;
	public static final int INACTIVE_SIGNATURE = 2;
	public static final int DRAW_SIGNATURE = 1;
	public static final int UPLOAD_SIGNATURE = 2;
	
	@Autowired
	private SignatureService signatureService;

	@PostMapping("/uploadSignatureCall")
    public String upload(Model model, @RequestParam("signatureFile") MultipartFile file) {
  	  
		signatureService.uploadSignature(uploadDirectory, file);
  	 
		model.addAttribute("successfulUploadMsg", "Signature Uploaded Successfully "+file.getOriginalFilename());
		return "uploadStatusView";
    }

}
