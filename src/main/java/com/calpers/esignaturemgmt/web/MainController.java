package com.calpers.esignaturemgmt.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;
import java.sql.Timestamp;

import javax.servlet.http.HttpSession;

import com.calpers.esignaturemgmt.model.Signature;
import com.calpers.esignaturemgmt.service.SignatureService;
import com.calpers.esignaturemgmt.web.dto.UserSessionDto;

@Controller
public class MainController {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private SignatureService signatureService;

	/*
	 * If user signature added, show signature dashboard
	 * else show Upload dashboard
	 */
    @GetMapping("/")
    public String root(Model model) {
    	UserSessionDto user = (UserSessionDto) session.getAttribute("userDetails");
    	
    	Signature signature = signatureService.findSignatureByUserIdAndStatus(user.getId(), SignatureController.ACTIVE_SIGNATURE);
    	
    	if (signature != null) {
    		String sig = signature.getFileName();
    		
    		model.addAttribute("signatureName", sig);
    		if (signature.getPreferredName() != null) {
    			model.addAttribute("preferredName", signature.getPreferredName());
    		} else {
    			model.addAttribute("preferredName", "Not Provided");
    		}
    		Timestamp uploadTimeStamp = signature.getUploadDate();
    		Date uploadDate = new Date(uploadTimeStamp.getTime());
    		model.addAttribute("uploadDate", uploadDate.toString());
            return "dashboardViewSignature";
    	} else {
    		return "dash_v4";
    	}
        
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "calperslogin";
    }

    @GetMapping("/user")
    public String userIndex() {
        return "user/index";
    }
    
    @RequestMapping("/upload")
	public String UploadSignature(Model model) {
		return "signature_v2";
	}
    
    @RequestMapping("/editDashboard")
	public String editDashboard() {
		return "dash_v4";
	}
    
    /*
	 * If user signature added, show signature dashboard
	 * else show Upload dashboard
	 */
    @RequestMapping("/dashboard")
	public String dashboard(Model model) {
    	UserSessionDto user = (UserSessionDto) session.getAttribute("userDetails");
    	
    	Signature signature = signatureService.findSignatureByUserIdAndStatus(user.getId(), SignatureController.ACTIVE_SIGNATURE);
    	
    	if (signature != null) {
    		String sig = signature.getFileName();
    		
    		model.addAttribute("signatureName", sig);
    		if (signature.getPreferredName() != null) {
    			model.addAttribute("preferredName", signature.getPreferredName());
    		} else {
    			model.addAttribute("preferredName", "Not Provided");
    		}
    		Timestamp uploadTimeStamp = signature.getUploadDate();
    		Date uploadDate = new Date(uploadTimeStamp.getTime());
    		model.addAttribute("uploadDate", uploadDate.toString());
            return "dashboardViewSignature";
    	} else {
    		return "dash_v4";
    	}
	}
    
    @RequestMapping("/changepwd")
	public String changepwd() {
		return "change_pwd";
	}
    
    
}
