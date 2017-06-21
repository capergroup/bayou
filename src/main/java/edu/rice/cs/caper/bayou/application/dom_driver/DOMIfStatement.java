package edu.rice.cs.caper.bayou.application.dom_driver;

import edu.rice.cs.caper.bayou.core.dsl.DBranch;
import edu.rice.cs.caper.bayou.core.dsl.DSubTree;
import org.eclipse.jdt.core.dom.IfStatement;

public class DOMIfStatement implements Handler {

    final IfStatement statement;

    public DOMIfStatement(IfStatement statement) {
        this.statement = statement;
    }

    @Override
    public DSubTree handle() {
        DSubTree tree = new DSubTree();

        DSubTree Tcond = new DOMExpression(statement.getExpression()).handle();
        DSubTree Tthen = new DOMStatement(statement.getThenStatement()).handle();
        DSubTree Telse = new DOMStatement(statement.getElseStatement()).handle();

        boolean branch = (Tcond.isValid() && Tthen.isValid()) || (Tcond.isValid() && Telse.isValid())
                || (Tthen.isValid() && Telse.isValid());

        if (branch)
            tree.addNode(new DBranch(Tcond.getNodesAsCalls(), Tthen.getNodes(), Telse.getNodes()));
        else {
            // only one of these will add nodes, the rest will add nothing
            tree.addNodes(Tcond.getNodes());
            tree.addNodes(Tthen.getNodes());
            tree.addNodes(Telse.getNodes());
        }
        return tree;
    }
}
