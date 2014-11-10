package com.sec.ax.restful.validate.user;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.sec.ax.restful.common.Constant;
import com.sec.ax.restful.pojo.User;
import com.sec.ax.restful.service.UserService;
import com.sec.ax.restful.validate.Error;

/**
 *
 * @author heesik.jeon
 *
 */

public class ValidatingName extends AbstractValidatedByUser {
	

    @Autowired
    private UserService service;
    
	/* 
	 * @see com.sec.ax.restful.validate.user.AbstractValidatedByUser#validate(com.sec.ax.restful.pojo.User, java.util.List)
	 */
	@Override
	public void validate(User target, List<Error> error) {
    	
		if (service.getUser(target.getName(), new Object()) != null) {
			error.add(new Error(Constant.ERR_USER_NAME_DUPLICATED, new Object[] {target.getName()}));
    	} else if (target.getName().length() < Constant.USER_NAME_MIN_LENGTH || target.getName().length() > Constant.USER_NAME_MAX_LENGTH) {
			error.add(new Error(Constant.ERR_USER_NAME_LENGTH, new Object[] {Constant.USER_NAME_MIN_LENGTH, Constant.USER_NAME_MAX_LENGTH}));
    	} else if (!chkPatternName(target.getName())) {
			error.add(new Error(Constant.ERR_USER_NAME_PATTERN, null));
    	}
    	
	}
	
	private boolean chkPatternName(String name) {
		
		String regex = "^[a-z0-9.-]{3,15}+$";
		
		Pattern pattern = Pattern.compile(regex);
		
		Matcher matcher = pattern.matcher(name);
		
		if (matcher.find()) {
			return true;
		} else {
			return false;
		}

	}
	
}