package com.nsnc.massdriver.fuse.easy;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.nsnc.massdriver.fuse.easy.EasyDirectory;

public class EasyNodeTest {

	// Basic functionality around Easy* Heirarchy.
	@Test
	public void testEasyNodePathAndName() {

		String name = "me";
		EasyDirectory easyDirectory = new EasyDirectory(name);
		EasyDirectory root = EasyDirectory.root(0555)
				.children(new EasyDirectory("path", 0555)
					.children(new EasyDirectory("to", 0555)
						.children(easyDirectory)));

		assertEquals(name, easyDirectory.getName());
		// Path should be full
		assertEquals("/path/to/" + name, easyDirectory.getPath());
	}
}
