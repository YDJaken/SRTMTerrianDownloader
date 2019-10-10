package com.dy.ImageFileProcessor;

import java.io.File;

public class FilePackage {
	private File file;
	private String[] config;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String[] getConfig() {
		return config;
	}

	public void setConfig(String[] config) {
		this.config = config;
	}

	public FilePackage(File file, String[] config) {
		super();
		this.file = file;
		this.config = config;
	}

}
