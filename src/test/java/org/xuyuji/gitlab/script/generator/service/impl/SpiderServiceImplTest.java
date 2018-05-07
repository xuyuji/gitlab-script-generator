package org.xuyuji.gitlab.script.generator.service.impl;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.xuyuji.gitlab.script.generator.service.SpiderService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpiderServiceImplTest {

	@Autowired
	private SpiderService spiderService;

	@Test
	public void testGetProjectsInfo() {
		assertFalse(spiderService.getProjectsInfo("xuyj@13322ty.com", "xuyuji@374").isEmpty());
	}
}
