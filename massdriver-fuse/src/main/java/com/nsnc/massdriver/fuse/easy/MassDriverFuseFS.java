package com.nsnc.massdriver.fuse.easy;

import java.net.UnknownHostException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.nsnc.massdriver.asset.Asset;
import com.nsnc.massdriver.driver.Driver;

public class MassDriverFuseFS extends EasyFuseFS {
	private final Driver driver;

	private final Set<EasyNode> content;

	public MassDriverFuseFS(Driver driver) throws UnknownHostException {
		this.driver = driver;
		this.content = EasyDirectory.root(0555)
				.children(
						new EasyDirectory("assets")
								.children(driver.findAssetsByTraits()
										.sorted(Comparator.comparing(Asset::getName))
										.map(asset -> new EasyDirectory(asset.getName(), 0555))),
						new EasyDirectory("chunks")
				).flatten();
	}


	@Override
	public EasyNode ofPath(String path) throws UnknownNodeException {
		return content.stream()
				.filter(easyNode -> Objects.equals(path, easyNode.getPath()))
				.findFirst()
				.orElseThrow(() -> new UnknownNodeException(path));
	}
}
