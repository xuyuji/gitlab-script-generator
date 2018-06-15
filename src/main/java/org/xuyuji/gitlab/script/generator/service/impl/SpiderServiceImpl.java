package org.xuyuji.gitlab.script.generator.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xuyuji.gitlab.script.generator.config.GitlabConfig;
import org.xuyuji.gitlab.script.generator.constant.Constant;
import org.xuyuji.gitlab.script.generator.service.SpiderService;
import org.xuyuji.gitlab.script.generator.vo.LoginPageInfo;

@Service
public class SpiderServiceImpl implements SpiderService {

	@Autowired
	private GitlabConfig gitlabConfig;

	private String login(String username, String password) throws IOException {
		LoginPageInfo loginPageInfo = getLoginPageInfo();

		Connection con = Jsoup.connect(gitlabConfig.getLoginUrl());
		con.header("User-Agent", Constant.USER_AGENT);

		Map<String, String> param = new HashMap<>();
		param.put("authenticity_token", loginPageInfo.getAuthenticityToken());
		param.put("user[login]", username);
		param.put("user[password]", password);
		con.method(Method.POST).cookies(loginPageInfo.getCookies()).data(param);
		Response rs = con.execute();

		return rs.cookie(Constant.GITLAB_SESSION);
	}

	private LoginPageInfo getLoginPageInfo() throws IOException {
		Connection con = Jsoup.connect(gitlabConfig.getLoginUrl());
		con.header("User-Agent", Constant.USER_AGENT);
		Response rs = con.execute();
		Document d = Jsoup.parse(rs.body());

		LoginPageInfo loginPageInfo = new LoginPageInfo();
		loginPageInfo.setAuthenticityToken(d.select("meta[name=csrf-token]").attr("content"));
		loginPageInfo.setCookies(rs.cookies());
		return loginPageInfo;
	}

	@Override
	public Map<String, List<String>> getProjectsInfo(String username, String password) {
		Map<String, List<String>> projectsInfo = new HashMap<>();
		try {
			String gitlabSession = login(username, password);
			if (StringUtils.isEmpty(gitlabSession)) {
				System.out.println("登陆失败");
				return projectsInfo;
			}

			int page = 1;
			while (true) {
				Connection con = Jsoup.connect(String.format("%s?page=%d", gitlabConfig.getProjectsUrl(), page++));
				con.header("User-Agent", Constant.USER_AGENT);
				Response rs = con.cookie(Constant.GITLAB_SESSION, gitlabSession).execute();
				Document d = Jsoup.parse(rs.body());

				Elements projects = d.select("ul.projects-list");
				if (projects.isEmpty()) {
					break;
				} else {
					for (Element e : projects.select("span.project-full-name")) {
						String strs[] = e.text().split("/");
						String module = strs[0].trim();
						String project = strs[1].trim();
						if (projectsInfo.containsKey(module)) {
							projectsInfo.get(module).add(project);
						} else {
							List<String> list = new ArrayList<>();
							list.add(project);
							projectsInfo.put(module, list);
						}
					}
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return projectsInfo;
	}
}
