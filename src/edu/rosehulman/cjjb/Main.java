package edu.rosehulman.cjjb;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Main {

	public static final String[] CLASSES = {
			"edu.rosehulman.cjjb.Main",
			"edu.rosehulman.cjjb.asm.ClassDeclarationVisitor",
			"edu.rosehulman.cjjb.asm.ClassFieldVisitor",
			"edu.rosehulman.cjjb.asm.ClassMethodVisitor",
			"edu.rosehulman.cjjb.asm.Relations",
			"org.objectweb.asm.ClassVisitor",
			"edu.rosehulman.cjjb.UMLClassVisitor"
			/*"problem.AppLauncher",
			"problem.HtmlWatcher",
			"problem.JarWatcher",
			"problem.TextPrinterWatcher",
			"problem.TxtWatcher",
			"problem.IWatcher"*/
		};
	
	public static final String boilerPlate = "digraph G { fontname = \"Bitstream Vera Sans\" fontsize = 8 node [ fontname = \"Bitstream Vera Sans\" fontsize = 8 shape = \"record\" ] edge [ fontname = \"Bitstream Vera Sans\" fontsize = 8 ]";
	
	public static void main(String[] args) throws IOException {
		OutputStream out = new FileOutputStream("output.txt");
		
		UMLClassVisitor visitor = new UMLClassVisitor(CLASSES, out);
		visitor.buildUML();
	}


}
