package org.dgso.programbuilder;

import java.util.ArrayList;

public class ANTLRv4GrammarClass {
    private String identifier;
    private ANTLRv4GrammarClass parent;
    private ANTLRv4GrammarType type;
    private ArrayList<ANTLRv4GrammarClass> children;

    public ANTLRv4GrammarClass(String identifier, ANTLRv4GrammarClass parent, ANTLRv4GrammarType type) {
        this.setIdentifier(identifier);
        this.setParent(parent);
        this.setType(type);
        children = new ArrayList<ANTLRv4GrammarClass>();
    }

    public String toString() {
        return "{" + this.getIdentifier() + ", type = " + this.getType() + ", parent = " + this.getParent() + "}";
    }

    public void addChild(ANTLRv4GrammarClass grammarClass) {
        getChildren().add(grammarClass);
    }

    public String getIdentifier() {
        return identifier.replace("'", "");
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public ANTLRv4GrammarType getType() {
        return type;
    }

    public void setType(ANTLRv4GrammarType type) {
        this.type = type;
    }

    public ANTLRv4GrammarClass getParent() {
        return parent;
    }

    public void setParent(ANTLRv4GrammarClass parent) {
        this.parent = parent;
    }

    public ArrayList<ANTLRv4GrammarClass> getChildren() {
        return children;
    }
}

