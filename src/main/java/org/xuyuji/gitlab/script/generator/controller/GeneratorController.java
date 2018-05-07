package org.xuyuji.gitlab.script.generator.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.xuyuji.gitlab.script.generator.service.ScriptGeneratorService;
import org.xuyuji.gitlab.script.generator.service.SpiderService;

@Controller
public class GeneratorController {

	@Autowired
	private SpiderService spiderService;

	@Autowired
	private ScriptGeneratorService scriptGeneratorService;

	@RequestMapping("/")
	public String index(Model model) {
		return "index";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/gen")
	public void generate(HttpServletResponse resp, @RequestParam(name = "username", required = true) String username,
			@RequestParam(name = "password", required = true) String password) {

		resp.setContentType("application/x-download");
		resp.addHeader("Content-Disposition", "attachment;filename=CheckOut.bat");

		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(resp.getOutputStream(), "GBK"))) {
			scriptGeneratorService.write(writer, spiderService.getProjectsInfo(username, password));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
