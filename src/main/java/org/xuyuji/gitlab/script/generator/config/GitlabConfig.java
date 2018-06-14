package org.xuyuji.gitlab.script.generator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "gitlab")
public class GitlabConfig {

	private String address;

	private String loginPath;

	private String projectsPath;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLoginUrl() {
		return address + loginPath;
	}

	public String getProjectsUrl() {
		return address + projectsPath;
	}

	public String getLoginPath() {
		return loginPath;
	}

	public void setLoginPath(String loginPath) {
		this.loginPath = loginPath;
	}

	public String getProjectsPath() {
		return projectsPath;
	}

	public void setProjectsPath(String projectsPath) {
		this.projectsPath = projectsPath;
	}

}
