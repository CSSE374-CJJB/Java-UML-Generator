package edu.rosehulman.cjjb;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import edu.rosehulman.cjjb.asm.ClassDeclarationVisitor;
import edu.rosehulman.cjjb.asm.ClassFieldVisitor;
import edu.rosehulman.cjjb.asm.ClassMethodVisitor;
import edu.rosehulman.cjjb.javaModel.JavaModel;
import edu.rosehulman.cjjb.javaModel.visitor.UMLVisitor;

public class JavaModelClassVisitor {

	public Set<String> classes;
	public OutputStream out;
	public static final String boilerPlate = "digraph G { fontname = \"Bitstream Vera Sans\" fontsize = 8 node [ fontname = \"Bitstream Vera Sans\" fontsize = 8 shape = \"record\" ] edge [ fontname = \"Bitstream Vera Sans\" fontsize = 8 ]";

	private JavaModel model;
	
	public JavaModelClassVisitor(Set<String> classes, OutputStream out) {
		this.classes = classes;
		this.out = out;
		
		this.model = new JavaModel(classes);
	}

	public void buildUML() throws IOException {
		for (String className : this.classes) {
			
			ClassReader reader = new ClassReader(className);
			ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, model);
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, className, model);
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, className, model);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);


		}
		UMLVisitor visitor = new UMLVisitor(out);
		model.accept(visitor);
		
		/*out.write(boilerPlate.getBytes());
		out.write("}\"\n]".getBytes());

		Map<String, String> childParrentRelations = relations.getChildParentIncludedRelations();
		Set<Relation> interfaces = relations.getIncludedInterfaceRelations();
		Set<Relation> uses = relations.getIncludedUsesRelations();
		Set<Relation> associations = relations.getIncludedAssociationsRelations();
		writeChildParrentRelations(childParrentRelations, out);
		writeInterfaceRelations(interfaces, out);
		writeUsesRelations(uses, out);
		writeAssociationRelations(associations, out);
		out.write("}".getBytes());*/
	}

//	private void writeChildParrentRelations(Map<String, String> childParrentRelations, OutputStream out)
//			throws IOException {
//		for (String child : childParrentRelations.keySet()) {
//			String toWrite = "\"" + child + "\"" + " -> " + "\"" + childParrentRelations.get(child) + "\""
//					+ " [arrowhead=\"onormal\", style=\"filled\"]";
//
//			out.write(toWrite.getBytes());
//		}
//	}
//
//	private void writeInterfaceRelations(Set<Relation> interfaces, OutputStream out) throws IOException {
//		// WeatherData -> Subject [arrowhead="onormal", style="dashed"];
//
//		for (Relation array : interfaces) {
//			String toWrite = "\"" + array.base + "\"" + " -> " + "\"" + array.relatedTo + "\""
//					+ " [arrowhead=\"onormal\", style=\"dashed\"]";
//
//			out.write(toWrite.getBytes());
//		}
//	}
//
//	private void writeUsesRelations(Set<Relation> uses, OutputStream out) throws IOException {
//		for (Relation r : uses) {
//			String toWrite = "\"" + r.base + "\"" + " -> " + "\"" + r.relatedTo + "\""
//					+ " [arrowhead=\"vee\", style=\"dashed\"]";
//
//			out.write(toWrite.getBytes());
//		}
//	}
//
//	private void writeAssociationRelations(Set<Relation> uses, OutputStream out) throws IOException {
//		for (Relation r : uses) {
//			String toWrite = "\"" + r.base + "\"" + " -> " + "\"" + r.relatedTo + "\""
//					+ " [arrowhead=\"vee\", style=\"filled\"]";
//
//			out.write(toWrite.getBytes());
//		}
//	}
	
}
