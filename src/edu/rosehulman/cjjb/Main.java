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

public class Main {

	public static final String[] CLASSES = { "org.objectweb.asm.ClassVisitor", "java.util.Set"
			/*
			 * "problem.AppLauncher", "problem.HtmlWatcher",
			 * "problem.JarWatcher", "problem.TextPrinterWatcher",
			 * "problem.TxtWatcher", "problem.IWatcher"
			 */
	};

	public static final String[] PACKAGES = { "edu.rosehulman.cjjb", "edu.rosehulman.asm"
			/*
			 * "headfirst.factory.pizzaaf", "headfirst.factory.pizzafm"
			 */
	};

	public static final String boilerPlate = "digraph G { fontname = \"Bitstream Vera Sans\" fontsize = 8 node [ fontname = \"Bitstream Vera Sans\" fontsize = 8 shape = \"record\" ] edge [ fontname = \"Bitstream Vera Sans\" fontsize = 8 ]";

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		OutputStream out = new FileOutputStream("output.txt");

		Set<String> classesToVisit = new HashSet<String>();
		classesToVisit.addAll(Arrays.asList(CLASSES));

		for (String s : PACKAGES) {
			classesToVisit.addAll(getClasses(s));
		}

		JavaModelClassVisitor visitor = new JavaModelClassVisitor(classesToVisit, out);
		visitor.buildUML();
	}

	// From
	// http://stackoverflow.com/questions/520328/can-you-find-all-classes-in-a-package-using-reflection?lq=1
	// by user Amit
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
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
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
