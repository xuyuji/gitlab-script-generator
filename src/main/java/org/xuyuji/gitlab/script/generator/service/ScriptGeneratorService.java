package org.xuyuji.gitlab.script.generator.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ScriptGeneratorService {

	public void write(BufferedWriter writer, Map<String, List<String>> projectsInfo) throws IOException;
}
