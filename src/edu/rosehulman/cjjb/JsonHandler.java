package edu.rosehulman.cjjb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
		Map<String,InputStream> extra = loadClassesInFolder(config.InputFolder);
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
			
			Set<String> toReAdd = new HashSet<String>();
			
			for(String s: extra.keySet()) {
				classesToVisit.remove(s);
				toReAdd.add(s);
			}
			
			JavaModelClassVisitor visitor = new JavaModelClassVisitor(classesToVisit);
			
			visitor.buildUMLModelOnly();
			
			
			for(String s: extra.keySet()) {
				visitor.extendUMLModelFile(s, extra.get(s));
			}

			for(String s: toReAdd) {
				visitor.getModel().addToIncluded(s);				
			}
			
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
	
	private Map<String,InputStream> loadClassesInFolder(String inputFolder) {
		Map<String, InputStream> map = new HashMap<String,InputStream>();
		
		File folder = new File(inputFolder);
		
		loadFilesRecur(map, "", folder.listFiles());
		
		return map;
	}
	
	private void loadFilesRecur(Map<String,InputStream> map, String path, File[] files) {
		for(File name: files) {
			if(name.isDirectory()) {
				loadFilesRecur(map, path + name.getName() + ".", name.listFiles());
			} else {
				if(name.getName().endsWith(".class")) {
					try {
						String className = path + name.getName().replace(".class", "");
						map.put(className, new FileInputStream(name));
					} catch (FileNotFoundException e) {
						System.out.println("File was deleted in middle of reading");
						System.exit(0);
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
