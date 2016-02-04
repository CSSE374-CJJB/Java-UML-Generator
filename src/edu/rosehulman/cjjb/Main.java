package edu.rosehulman.cjjb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.rosehulman.cjjb.asm.QualifiedMethod;
import edu.rosehulman.cjjb.javaModel.visitor.ISequenceVisitor;
import edu.rosehulman.cjjb.javaModel.visitor.IUMLVisitor;
import edu.rosehulman.cjjb.javaModel.visitor.SDSequenceVisitor;
import edu.rosehulman.cjjb.javaModel.visitor.UMLDotVisitor;

public class Main {

	public static final String[] CLASSES = { 
		//"org.objectweb.asm.ClassVisitor", "java.util.Set"
		/*
		 * "problem.AppLauncher", "problem.HtmlWatcher",
		 * "problem.JarWatcher", "problem.TextPrinterWatcher",
		 * "problem.TxtWatcher", "problem.IWatcher"
		 */
		//	"java.lang.Runtime",
//			"java.io.FilterInputStream",
//			"java.io.InputStream",
//			"java.io.FileInputStream",
//			"java.io.ByteArrayInputStream",
//			"java.io.ObjectInputStream"
		//	"java.awt.Desktop",
		//	"java.util.Calendar"
		//	"java.util.Enumeration",
		//	"java.util.Iterator"
		//	"org.objectweb.asm.ClassVisitor",
		//	"org.objectweb.asm.MethodVisitor"
			"headfirst.composite.menu.Menu",
			"headfirst.composite.menu.MenuItem",
			"headfirst.composite.menu.MenuComponent",
			
			"headfirst.composite.menuiterator.Menu",
			"headfirst.composite.menuiterator.MenuItem",
			"headfirst.composite.menuiterator.MenuComponent"
		
	};
	
	public static final String[] PACKAGES = {
		"headfirst.composite.menu", "headfirst.composite.menuiterator"
			/*
		"edu.rosehulman.cjjb", "edu.rosehulman.asm", "edu.rosehulman.cjjb.javaModel",  
		"edu.rosehulman.cjjb.javaModel.checks", "edu.rosehulman.cjjb.javaModel.modifier", 
		"edu.rosehulman.cjjb.javaModel.visitor", "edu.rosehulman.cjjb.javaModel.pattern"
		*/
//		"headfirst.factory.pizzaaf", "headfirst.factory.pizzafm"
		//	 "headfirst.decorator.io",
		//	 "headfirst.decorator.starbuzz"
		//	"problem.client",
		//	"problem.lib"
	};


	public static void main(String[] args) throws IOException, ClassNotFoundException {
		if(args.length == 0) {
			System.out.println("Calls shown bellow");
			System.out.println("UML -c <List of Classes> -p <List of Packages>");
			System.out.println("SEQ -c <Class> -m <Method> -d <ASM Desc>");
			System.out.println("EXAMPLE");
			return;
		}
		OutputStream out = new FileOutputStream("output.txt");
		switch (args[0]) {
		case "SEQ":
			QualifiedMethod qm = new QualifiedMethod(getMethodFromArgs(args), getDescFromArgs(args));
			// QualifiedMethod qm = new QualifiedMethod("shuffle", "(Ljava/util/List;)V");
			String clazz = getClassFromArgs(args);
			JavaModelClassVisitor visitor = new JavaModelClassVisitor(clazz, qm, 2);
			
			visitor.buildSeqModel();
			ISequenceVisitor seqVisitor = new SDSequenceVisitor(clazz, qm, 2, out);
			visitor.getModel().accept(seqVisitor);
			break;
		case "UML":
			Set<String> classesToVisit = new HashSet<String>();
			classesToVisit.addAll(getClassesFromArgs(args));
		
			for (String s : getPackagesFromArgs(args)) {
				classesToVisit.addAll(getClasses(s));
			}
			System.out.println(classesToVisit);
			
			visitor = new JavaModelClassVisitor(classesToVisit, out);
			visitor.buildUMLModel();
			IUMLVisitor umlVisitor = new UMLDotVisitor(out, visitor.getModel());
			visitor.getModel().accept(umlVisitor);
			break;
		case "EXAMPLE":
			exampleCall(new FileOutputStream("umlOutput.txt"), new FileOutputStream("seqOutput.txt"));
			break;
		default:
			System.out.println("Not a valid diagram type. Valid Types: SEQ|UML|EXAMPLE");
		}	
	}

	private static String getClassFromArgs(String[] args) {
		int index = 0;
		while(index < args.length && !args[index++].equals("-c")) { }
		
		return args[index];
	}

	
	private static String getDescFromArgs(String[] args) {
		int index = 0;
		while(index < args.length && !args[index++].equals("-d")) { }
		
		return args[index];
	}

	private static String getMethodFromArgs(String[] args) {
		int index = 0;
		while(index < args.length && !args[index++].equals("-m")) { }
		
		return args[index];
	}

	private static List<String> getPackagesFromArgs(String[] args) {
		List<String> toReturn = new ArrayList<String>();
		
		int index = 0;
		while(index < args.length && !args[index++].equals("-p")) { }
		
		for(int i = index; i < args.length; i++) {
			if(args[i].equals("-c")) {
				break;
			}
			toReturn.add(args[i]);
		}
		return toReturn;
	}

	private static List<String> getClassesFromArgs(String[] args) {
		List<String> toReturn = new ArrayList<String>();
		
		int index = 0;
		while(index < args.length && !args[index++].equals("-c")) {	}
		
		for(int i = index; i < args.length; i++) {
			if(args[i].equals("-p")) {
				break;
			}
			
			toReturn.add(args[i]);
		}
		
		return toReturn;
	}

	private static void exampleCall(OutputStream umlOut, OutputStream seqOut) throws IOException, ClassNotFoundException {
		Set<String> classesToVisit = new HashSet<String>();
		classesToVisit.addAll(Arrays.asList(CLASSES));
	
		for (String s : PACKAGES) {
			classesToVisit.addAll(getClasses(s));
		}
	
		QualifiedMethod qm = new QualifiedMethod("shuffle", "(Ljava/util/List;)V");
		JavaModelClassVisitor visitor = new JavaModelClassVisitor(classesToVisit, "java.util.Collections", qm, 2);
		
		visitor.buildUMLModel();
		IUMLVisitor umlVisitor = new UMLDotVisitor(umlOut, visitor.getModel());
		visitor.getModel().accept(umlVisitor);
	
//		visitor.buildSeqModel();
//		ISequenceVisitor seqVisitor = new SDSequenceVisitor("java.util.Collections", qm, 2, seqOut);
//		visitor.getModel().accept(seqVisitor);
	}

	/* From
	 * http://stackoverflow.com/questions/520328/can-you-find-all-classes-in-a-package-using-reflection?lq=1
	 * by user Amit
	/**
	 * Scans all classes accessible from the context class loader which belong
	 * to the given package and subpackages.
	 *
	 * @param packageName
	 *            The base package
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */	
	private static List<String> getClasses(String packageName) throws ClassNotFoundException, IOException {
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = ClassLoader.getSystemClassLoader().getResources(path);
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		ArrayList<String> classes = new ArrayList<String>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes;
	}

	/**
	 * Recursive method used to find all classes in a given directory and
	 * subdirs.
	 *
	 * @param directory
	 *            The base directory
	 * @param packageName
	 *            The package name for classes found inside the base directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	private static List<String> findClasses(File directory, String packageName) throws ClassNotFoundException {
		List<String> classes = new ArrayList<String>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(packageName + '.' + file.getName().substring(0, file.getName().length() - 6));
			}
		}
		return classes;
	}

}
