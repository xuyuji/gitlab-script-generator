package org.xuyuji.gitlab.script.generator.vo;

import java.util.Map;

public class LoginPageInfo {

	private String authenticityToken;

	private Map<String, String> cookies;

	public String getAuthenticityToken() {
		return authenticityToken;
	}

	public void setAuthenticityToken(String authenticityToken) {
		this.authenticityToken = authenticityToken;
	}

	public Map<String, String> getCookies() {
		return cookies;
	}

	public void setCookies(Map<String, String> cookies) {
		this.cookies = cookies;
	}

}
