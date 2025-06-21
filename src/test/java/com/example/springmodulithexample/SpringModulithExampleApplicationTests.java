package com.example.springmodulithexample;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

@SpringBootTest
class SpringModulithExampleApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void verifiesModuleStructure() {
		ApplicationModules modules = ApplicationModules.of(SpringModulithExampleApplication.class);
		modules.verify();
	}

	@Test
	void createModuleDocumentation() {
		ApplicationModules modules = ApplicationModules.of(SpringModulithExampleApplication.class);
		new Documenter(modules).writeDocumentation();
	}

}
