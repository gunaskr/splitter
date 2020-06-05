package com.splitter.transactionmanagement.repository;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.splitter.security.constants.SecurityConstants;
import com.splitter.transactionmanagement.model.User;

@FeignClient("user-management")
public interface UserManagementClient {
	
	@RequestMapping(method = RequestMethod.GET, path="/api/roommate/{mobileNo}")
    public List<User> getRoomMates(@PathVariable final String mobileNo, 
    		@RequestHeader(value = SecurityConstants.AUTH_HEADER_NAME, required = true) String authorizationHeader);

}
