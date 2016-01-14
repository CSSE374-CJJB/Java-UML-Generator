package edu.rosehulman.cjjb.javaModel.visitor;

import java.io.IOException;

public interface ITraverser {

	public void accept(IUMLVisitor v) throws IOException;
}
