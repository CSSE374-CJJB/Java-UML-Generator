package edu.rosehulman.cjjb.javaModel.visitor;

import java.io.IOException;

public interface IUMLTraverser {

	public void accept(IUMLVisitor v) throws IOException;
}
