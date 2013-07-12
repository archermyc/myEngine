package com.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 加载asserts目录下的png图片 生成类似Android 里面的R文件
 * @author YCMO
 *
 */
public class ResLoad {

	private final static String PRI_STRING = "	public static String %s = \"%s\";\n";
	StringBuffer buffer = new StringBuffer();
	String assertPathString = "";

	public void createResFile() {
		File file = new File("AssertRes.java");
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new FileWriter(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			buffer.append("package com.assetsres;\n");
			buffer.append("public class AssertRes {\n");
			loadFile("");
			buffer.append("}");
			output.write(buffer.toString());
			output.flush();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadFile(String filePath) {
		File file = new File(filePath);
		String root = file.getAbsolutePath();
		File file2 = new File(root);
		String rootName = file2.getName();
		if (!rootName.equals("assets")) {
			buffer.append(String
					.format("	public static class %s {\n", rootName));
		} else {
			assertPathString = file2.getAbsolutePath();
		}
		File[] files = file2.listFiles();
		for (int i = 0; i < files.length; i++) {
			File tFile = files[i];
			String path = files[i].getAbsolutePath();
			if (tFile.isDirectory() && !tFile.getName().startsWith(".")) {
				loadFile(tFile.getAbsolutePath());
			}
			if (path.endsWith(".png")) {
				buffer.append(String.format(PRI_STRING, tFile.getName()
						.replace(".", "_"), getFilePathEx(tFile
						.getAbsolutePath())));
			}
		}
		if (!rootName.equals("assets")) {
			buffer.append("}\n");
		}
	}

	public String getFilePathEx(String src) {
		int index = assertPathString.length();
		String temp = src.substring(index+1);
		return temp.replace("\\", "/");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ResLoad resLoad = new ResLoad();
		resLoad.createResFile();
	}

}
