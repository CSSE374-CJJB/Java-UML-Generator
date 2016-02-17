package edu.rosehulman.cjjb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;

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
			reader = new FileReader(arguments[0]);
			jsonConfig = gson.fromJson(reader, JsonConfig.class);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			System.exit(0);
		}
		
	}
	
	public void run() {
		run(jsonConfig);
		
	}
	
	public void run(JsonConfig config) {
		Set<String> classesToVisit = getClassList(config);
	
		File file = new File(config.OutputDirectory);
		String folder = file.getAbsoluteFile().getName();
		if(folder.endsWith(File.separator)) {
			folder += File.separator + "umlOutput.txt";
		}
		
		FileOutputStream stream;
		try {
			stream = new FileOutputStream(folder);
			JavaModelClassVisitor visitor = new JavaModelClassVisitor(classesToVisit);
			
			visitor.buildUMLModelOnly();
			visitor.runPatternDetection(PatternFindingFactory.getPatternChecks(config.Phases), PatternFindingFactory.getStructureVisitors(config.Phases));
			
			if(config.Phases.contains("DOT-Generation")) {
				IUMLVisitor umlVisitor = new UMLDotVisitor(stream, visitor.getModel());
				visitor.getModel().accept(umlVisitor);				
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
	
	public Set<String> getClassList(JsonConfig config) {
		Set<String> classesToVisit = new HashSet<String>();
		
		File folder = new File(config.InputFolder);
		URL[] urls = null;
		try {
			urls = new URL[] {folder.toURI().toURL()};
		} catch (MalformedURLException e1) {
			System.out.println("Input Folder in bad format.");
			e1.printStackTrace();
			System.exit(0);
		}
		
		URLClassLoader cl = new URLClassLoader(urls);
		
		for(String name: config.InputClasses) {
			try {
				try {
					ClassLoader.getSystemClassLoader().loadClass(name);
				} catch (ClassNotFoundException e1) {
					cl.loadClass(name);
				}
				classesToVisit.add(name);
			} catch (ClassNotFoundException e) {
				System.out.println("Class does not exist in folder or class path.");
				e.printStackTrace();
				System.exit(0);
			}			
		}
		try {
			cl.close();
		} catch (IOException e) {
		}
		
		return classesToVisit;
	}
}
