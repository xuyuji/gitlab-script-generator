package org.xuyuji.gitlab.script.generator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "gitlab")
public class GitlabConfig {

	private String loginUrl;

	private String projectsUrl;

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getProjectsUrl() {
		return projectsUrl;
	}

	public void setProjectsUrl(String projectsUrl) {
		this.projectsUrl = projectsUrl;
	}

}
