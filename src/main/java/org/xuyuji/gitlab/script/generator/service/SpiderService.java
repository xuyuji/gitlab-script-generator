package org.xuyuji.gitlab.script.generator.service;

import java.util.List;
import java.util.Map;

public interface SpiderService {

	public Map<String, List<String>> getProjectsInfo(String username, String password);
}
