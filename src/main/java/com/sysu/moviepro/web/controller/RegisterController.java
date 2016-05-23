package com.sysu.moviepro.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sysu.moviepro.business.entity.User;
import com.sysu.moviepro.business.service.UserService;

@Controller
public class RegisterController {
	
	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/register", method= RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createUser(@ModelAttribute User user) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		// �˴���user�ĺϷ���Ӧ���Ѿ���ǰ�˱�֤,��ֻ̨������֤�ظ���
		boolean exist = isUserExist(user);
		if (!exist) {
			logger.info("Creating User. Data: "+user);
			int id = userService.createUser(user);
			modelMap.put("code", 1);  // 1�������ɹ�
			return modelMap;
		} else {
			logger.info(""+user.getName()+" is already exist");
			modelMap.put("code", 0);  // 0������ʧ��
			return modelMap;
		}
	}
	
	private boolean isUserExist(User user) {
		User result = userService.getUserByName(user.getName());
		if (result.getId() == 0) return false;
		else return true;
	}
}
