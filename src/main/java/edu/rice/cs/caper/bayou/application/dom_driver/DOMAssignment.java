package edu.rice.cs.caper.bayou.application.dom_driver;

import edu.rice.cs.caper.bayou.core.dsl.DSubTree;
import org.eclipse.jdt.core.dom.Assignment;

public class DOMAssignment implements Handler {

    final Assignment assignment;

    public DOMAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    @Override
    public DSubTree handle() {
        return new DOMExpression(assignment.getRightHandSide()).handle();
    }
}
