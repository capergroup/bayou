package edu.rice.cs.caper.bayou.core.synthesizer;

import org.eclipse.jdt.core.dom.ASTNode;

public interface Synthesizable {
    ASTNode synthesize(Environment env) throws SynthesisException;
}
