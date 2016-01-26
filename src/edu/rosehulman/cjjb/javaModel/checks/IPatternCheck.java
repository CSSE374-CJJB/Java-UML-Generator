package edu.rosehulman.cjjb.javaModel.checks;

import java.util.List;

import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.JavaModel;

public interface IPatternCheck {
	public List<IPattern> check(JavaModel model);
}
