package edu.rosehulman.cjjb.asm;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.JavaModel;
import edu.rosehulman.cjjb.javaModel.JavaMethod;

public class ClassSequnceClassVisitor extends ClassDeclarationVisitor {

	private JavaModel model;
	private Set<QualifiedMethod> methodsToFind;
	private int depth;
	private SequenceStructure seqStructure;
	private String className;
	
	public ClassSequnceClassVisitor(int api, JavaModel model, String className, Set<QualifiedMethod> methodsToFind, int depth, SequenceStructure seqStructure) {
		super(api, model);
		
		this.model = model;
		this.methodsToFind = methodsToFind;
		this.depth = depth;
		this.seqStructure = seqStructure;
		this.className = className;
	}

	public ClassSequnceClassVisitor(int asm5, JavaModel model2, String className, QualifiedMethod method, int depth2,
			SequenceStructure seqStructure2) {
		this(asm5, model2, className, (Set<QualifiedMethod>)null, depth2, seqStructure2);
		this.methodsToFind = new HashSet<QualifiedMethod>();
		methodsToFind.add(method);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);

		System.out.println(name + " " + desc);
		
		boolean isConstructor = false;
		if(name.contains("<init>")) {
			isConstructor = true;
			name = name.replace("<init>", Utils.shortName(className));
		}
		
		QualifiedMethod qmeth = new QualifiedMethod(name, desc);
		if(methodsToFind.contains(qmeth)) {
			MethodCallGroup method = new MethodCallGroup(className, qmeth);
			toDecorate = new ClassSequnceMethodVisitor(this.api, toDecorate, method, this.model, this.depth, seqStructure);
			
			
			AbstractJavaStructure structure = model.getStructure(Utils.getCleanName(this.className));

			AbstractJavaStructure returnType = Utils.getInstanceOrJavaStructure(model, Utils.getReturnType(desc));
			List<AbstractJavaStructure> arguments = Utils.getInstanceOrJavaStructures(model,
					Utils.getListOfArgs(desc).toArray(new String[0]));

			structure.addSubElement(new JavaMethod(Utils.getInstanceOrJavaStructure(model, className), name, Utils.getAccessModifier(access),
					Utils.getModifiers(access), returnType, arguments, isConstructor ));
			
		}
		
		return toDecorate;
	}
	
	@Override
	public void visitEnd() {
		super.visitEnd();
		
		if(depth > 0)
			try {
				Map<String, Set<QualifiedMethod>> methods = seqStructure.getClassMethods();
				seqStructure.vistedAll();
				for(String s: methods.keySet()) {
					ClassReader reader = new ClassReader(s);
					ClassVisitor decVisitor = new ClassSequnceClassVisitor(this.api, model, s, methods.get(s), this.depth - 1, seqStructure);
					reader.accept(decVisitor, ClassReader.EXPAND_FRAMES);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}
