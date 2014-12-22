package org.dgso.superoptimizer;

import java.util.ArrayList;
import java.util.TreeMap;

public class Grammar {
    private TreeMap<GrammarType, ArrayList<GrammarClass>> grammarObjects;

    public Grammar() {
        setGrammarObjects(new TreeMap<GrammarType, ArrayList<GrammarClass>>());
    }

    public void addEntry(GrammarType grammarType, GrammarClass grammarClass) {
        ArrayList<GrammarClass> value;
        if(grammarObjects.containsKey(grammarType)) {
            value = grammarObjects.get(grammarType);
        }
        else {
            value = new ArrayList<GrammarClass>();
            grammarObjects.put(grammarType, value);
        }
        value.add(grammarClass);
    }

    public TreeMap<GrammarType, ArrayList<GrammarClass>> getGrammarObjects() {
        return grammarObjects;
    }

    public void setGrammarObjects(TreeMap<GrammarType, ArrayList<GrammarClass>> grammarObjects) {
        this.grammarObjects = grammarObjects;
    }
}
