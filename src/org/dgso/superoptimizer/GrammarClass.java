package org.dgso.superoptimizer;

import java.util.ArrayList;

public class GrammarClass {
    private String identifier;
    private GrammarClass parent;
    private GrammarType type;
    private ArrayList<GrammarClass> children;

    public GrammarClass(String identifier, GrammarClass parent, GrammarType type) {
        this.setIdentifier(identifier);
        this.setParent(parent);
        this.setType(type);
        children = new ArrayList<GrammarClass>();
    }

    public String toString() {
        return "{" + this.getIdentifier() + ", type = " + this.getType() + ", parent = " + this.getParent() + "}";
    }

    public void addChild(GrammarClass grammarClass) {
        children.add(grammarClass);
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public GrammarType getType() {
        return type;
    }

    public void setType(GrammarType type) {
        this.type = type;
    }

    public GrammarClass getParent() {
        return parent;
    }

    public void setParent(GrammarClass parent) {
        this.parent = parent;
    }
}

