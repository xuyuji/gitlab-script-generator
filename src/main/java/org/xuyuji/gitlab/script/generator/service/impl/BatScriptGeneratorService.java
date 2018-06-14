package org.xuyuji.gitlab.script.generator.service.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xuyuji.gitlab.script.generator.config.GitlabConfig;
import org.xuyuji.gitlab.script.generator.service.ScriptGeneratorService;

@Service
public class BatScriptGeneratorService implements ScriptGeneratorService {

	@Autowired
	private GitlabConfig gitlabConfig;

	@Override
	public void write(BufferedWriter writer, Map<String, List<String>> projectsInfo) throws IOException {
		writer.append("@echo off");
		writer.newLine();
		writer.newLine();

		for (String module : projectsInfo.keySet()) {
			writer.append(String.format("if not exist %s mkdir %s", module, module));
			writer.newLine();
		}

		writer.newLine();
		writer.append("SETLOCAL ENABLEDELAYEDEXPANSION");
		writer.newLine();

		writer.newLine();
		for (String module : projectsInfo.keySet()) {
			List<String> list = projectsInfo.get(module);
			Collections.sort(list);

			StringBuilder sb = new StringBuilder();
			for (String s : list) {
				sb.append(s).append(" ");
			}
			writer.append("SET var=" + sb.toString());
			writer.newLine();
			writer.append("cd " + module);
			writer.newLine();

			writer.append("FOR %%a IN (%var%) DO (");
			writer.newLine();
			writer.append("    SET dir=%%a");
			writer.newLine();
			writer.append(String.format("    SET url=%s/%s/", gitlabConfig.getAddress(), module) + "%%a.git");
			writer.newLine();
			writer.newLine();
			writer.append("    if exist !dir! (");
			writer.newLine();
			writer.append("      cd !dir! & git pull");
			writer.newLine();
			writer.append("      echo !cd! finished & cd ..");
			writer.newLine();
			writer.append("    ) else (");
			writer.newLine();
			writer.append("      git clone !url!");
			writer.newLine();
			writer.append("    )");
			writer.newLine();
			writer.append(")");
			writer.newLine();
			writer.append("cd ..");
			writer.newLine();
			writer.newLine();
			writer.newLine();
		}
	}

}
