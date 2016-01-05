package edu.rosehulman.cjjb;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import edu.rosehulman.cjjb.asm.ClassDeclarationVisitor;
import edu.rosehulman.cjjb.asm.ClassFieldVisitor;
import edu.rosehulman.cjjb.asm.ClassMethodVisitor;
import edu.rosehulman.cjjb.asm.Relations;

public class Main {

	public static final String[] CLASSES = {
			"edu.rosehulman.cjjb.Main",
			"edu.rosehulman.cjjb.asm.ClassDeclarationVisitor",
			"edu.rosehulman.cjjb.asm.ClassFieldVisitor",
			"edu.rosehulman.cjjb.asm.ClassMethodVisitor",
			"edu.rosehulman.cjjb.asm.Relations",
			"org.objectweb.asm.ClassVisitor"
		};
	
	public static final String boilerPlate = "digraph G { fontname = \"Bitstream Vera Sans\" fontsize = 8 node [ fontname = \"Bitstream Vera Sans\" fontsize = 8 shape = \"record\" ] edge [ fontname = \"Bitstream Vera Sans\" fontsize = 8 ]";
	
	public static void main(String[] args) throws IOException {
		OutputStream out = new FileOutputStream("output.txt");
		
		Relations relations = new Relations();
		
		out.write(boilerPlate.getBytes());
		for (String className : CLASSES) {
			
			ClassReader reader = new ClassReader(className);
			ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, out, relations);
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, out);
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, out);
			// TODO: add more DECORATORS here in later milestones to accomplish
			// specific tasks
			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

			out.write("}\"\n]".getBytes());
			
		}
		
		Map<String,String> childParrentRelations = relations.getChildParentIncludedRelations();
		List<String[]> interfaces = relations.getIncludedInterfaceRelations();
		writeChildParrentRelations(childParrentRelations,out);
		writeInterfaceRelations(interfaces, out);
		out.write("}".getBytes());
	}

	private static void writeChildParrentRelations(Map<String, String> childParrentRelations, OutputStream out) throws IOException {
		for(String child: childParrentRelations.keySet()) {
			String toWrite = child + " -> " + childParrentRelations.get(child) + " [arrowhead=\"onormal\", style=\"filled\"]";
			
			out.write(toWrite.getBytes());
		}
	}
	
	private static void writeInterfaceRelations(List<String[]> interfaces, OutputStream out) throws IOException {
		//WeatherData -> Subject [arrowhead="onormal", style="dashed"];
		
		for(String[] array: interfaces) {
			String toWrite = array[0] + " -> " + array[1] + " [arrowhead=\"onormal\", style=\"dashed\"]";
			
			out.write(toWrite.getBytes());
		}
	}


}
