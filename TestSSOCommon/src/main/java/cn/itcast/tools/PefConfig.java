package cn.itcast.tools;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PefConfig {

	@Value("${sso.url}")
	private String ssoUrl;//服务器域名
	
	@Value("${domain.url}")
	private String domainUrl;//第三方网站域名

	public String getSsoUrl() {
		return ssoUrl;
	}

	public void setSsoUrl(String ssoUrl) {
		this.ssoUrl = ssoUrl;
	}

	public String getDomainUrl() {
		return domainUrl;
	}

	public void setDomainUrl(String domainUrl) {
		this.domainUrl = domainUrl;
	}
	
	
}
