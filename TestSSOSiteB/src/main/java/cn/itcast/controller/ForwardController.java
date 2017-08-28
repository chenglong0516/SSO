package cn.itcast.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.tools.PefConfig;

/*
 * ����ת������Ӧjspҳ��
 */

@Controller
@RequestMapping("/forward")
public class ForwardController {

	@RequestMapping("/toRegist.action")
	public String toRegist(){
		return "regist";
	}
	
	@RequestMapping("/toAccount.action")
	public String toAccount(){
		return "account";
	}
	
}
