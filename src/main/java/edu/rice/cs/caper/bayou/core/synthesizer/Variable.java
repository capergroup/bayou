/*
Copyright 2017 Rice University

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package edu.rice.cs.caper.bayou.core.synthesizer;

import org.eclipse.jdt.core.dom.*;

import java.util.Arrays;

public class Variable {

    /* TODO: add support for arrays (search for isArray) */

    private final String name;
    private final Type type;
    private int refCount;
    private boolean userVar;
    private boolean join;
    private boolean defaultInit;

    Variable(String name, Type type) {
        this.name = name;
        this.type = type;
        refCount = 0;
        join = true;
        userVar = false;
        defaultInit = false;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public void setJoin(boolean join) {
        this.join = join;
    }

    public boolean isJoinVar() {
        return join;
    }

    public void setUserVar(boolean userVar) {
        this.userVar = userVar;
    }

    public boolean isUserVar() {
        return userVar;
    }

    public void setDefaultInit(boolean defaultInit) {
        this.defaultInit = defaultInit;
    }

    public boolean isDefaultInit() {
        return defaultInit;
    }

    public void addRefCount() {
        refCount += 1;
    }

    public int getRefCount() {
        return refCount;
    }

    public Expression createDefaultInitializer(AST ast) {
        CastExpression cast = ast.newCastExpression();
        cast.setType(type.simpleT(ast));

        MethodInvocation invocation = ast.newMethodInvocation();
        invocation.setExpression(ast.newSimpleName("Feedback"));
        invocation.setName(ast.newSimpleName("initialize"));
        cast.setExpression(invocation);

        return cast;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || ! (o instanceof Variable))
            return false;
        Variable v = (Variable) o;
        return v.name.equals(name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name + ":" + type;
    }
}
