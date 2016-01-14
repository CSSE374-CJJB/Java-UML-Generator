package edu.rosehulman.cjjb.javaModel.visitor;

import java.io.IOException;

public interface ITraverser {

	public void accept(IVisitor v) throws IOException;
}
