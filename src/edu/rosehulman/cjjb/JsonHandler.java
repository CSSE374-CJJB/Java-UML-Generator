package edu.rosehulman.cjjb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;

import edu.rosehulman.cjjb.javaModel.JavaModel;
import edu.rosehulman.cjjb.javaModel.checks.PatternFindingFactory;
import edu.rosehulman.cjjb.javaModel.visitor.IUMLVisitor;
import edu.rosehulman.cjjb.javaModel.visitor.UMLDotVisitor;

public class JsonHandler {
	
	private JsonConfig jsonConfig;
	
	public JsonHandler(String[] arguments) {
		Gson gson = new Gson();
		FileReader reader;
		jsonConfig = null;
		try {
			reader = new FileReader(arguments[1]);
			jsonConfig = gson.fromJson(reader, JsonConfig.class);
		} catch (FileNotFoundException e) {
			System.out.println("Json File not found");
			System.exit(0);
		}
		
	}
	
	public void run() {
		run(jsonConfig);
		
	}
	
	public void run(JsonConfig config) {
		loadClassesInFolder(config.InputFolder);
 		Set<String> classesToVisit = getClassList(config);
	
		File file = new File(config.OutputDirectory);
		String folder = null;
		try {
			folder = file.getCanonicalPath();
			if(!folder.endsWith(File.separator)) {
				folder += File.separator ;
			}
			folder += "umlOutput.txt";
		} catch (IOException e1) {
			System.out.println("Unable to find output folder.");
			e1.printStackTrace();
			System.exit(0);
		}
		
		FileOutputStream stream;
		try {
			List<String> phaseList = Arrays.asList(config.Phases);
			
			stream = new FileOutputStream(folder);
			JavaModelClassVisitor visitor = new JavaModelClassVisitor(classesToVisit);
			visitor.buildUMLModelOnly();
			visitor.runPatternDetection(PatternFindingFactory.getPatternChecks(config), PatternFindingFactory.getStructureVisitors(config));
			
			JavaModel model = visitor.getModel();
			
			model.setExclusionList(config.exclusion);
			
			if(phaseList.contains("DOT-Generation")) {
				IUMLVisitor umlVisitor = new UMLDotVisitor(stream, model);
				model.accept(umlVisitor);				
			}
		} catch (FileNotFoundException e) {
			System.out.println("Unable to create file at output directory");
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			System.out.println("IO Exception durring writing");
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	private void loadClassesInFolder(String inputFolder) {
		File folder = new File(inputFolder);
		URL[] urls = null;
		try {
			urls = new URL[] {folder.toURI().toURL()};
		} catch (MalformedURLException e1) {
			System.out.println("Input Folder in bad format.");
			e1.printStackTrace();
			System.exit(0);
		}
		
		URLClassLoader cl = URLClassLoader.newInstance(urls, this.getClass().getClassLoader());// new URLClassLoader(urls, );
		loadFilesRecur("", folder.listFiles(), cl);
		
//		try {
//			//cl.close();
//		} catch (IOException e) {
//		}
	
	}
	
	private void loadFilesRecur(String path, File[] files, URLClassLoader UCL) {
		for(File name: files) {
			if(name.isDirectory()) {
				loadFilesRecur(path + name.getName() + ".", name.listFiles(), UCL);
			} else {
				if(name.getName().endsWith(".class")) {
					try {
						String className = path + name.getName().replace(".class", "");
						Class<?> o = UCL.loadClass(className);
						System.out.println(o.getName());
						//System.out.println("Loaded Class: " + className);
					} catch (ClassNotFoundException e) {
						System.out.println("Class does not exist in folder or class path.");
						e.printStackTrace();
						//System.exit(0);
					} catch (NoClassDefFoundError e) {
						System.out.println("Class Could not be loaded");
						e.printStackTrace();
					}
				}
			}
		}
	}

	public Set<String> getClassList(JsonConfig config) {
		Set<String> classesToVisit = new HashSet<String>();
		
		for(String name: config.InputClasses) {
			classesToVisit.add(name);		
		}
		
		return classesToVisit;
	}
}
