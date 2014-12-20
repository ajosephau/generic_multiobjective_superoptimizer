package org.dgso.superoptimizer;

public class GrammarClass {
    private String identifier;
    private String parent;
    private GrammarType type;

    public GrammarClass(String identifier, String parent, GrammarType type) {
        this.setIdentifier(identifier);
        this.setParent(parent);
        this.setType(type);
    }

    public String toString() {
        return "{" + this.getIdentifier() + ", type = " + this.getType() + ", parent = " + this.getParent() + "}";
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

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}

