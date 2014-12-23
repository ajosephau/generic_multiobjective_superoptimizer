package org.dgso.superoptimizer;

import java.util.ArrayList;
import java.util.TreeMap;

public class ANTLRv4Grammar {
    private TreeMap<ANTLRv4GrammarType, ArrayList<ANTLRv4GrammarClass>> grammarObjects;

    public ANTLRv4Grammar() {
        setGrammarObjects(new TreeMap<ANTLRv4GrammarType, ArrayList<ANTLRv4GrammarClass>>());
    }

    public void addEntry(ANTLRv4GrammarType grammarType, ANTLRv4GrammarClass grammarClass) {
        ArrayList<ANTLRv4GrammarClass> value;
        if(grammarObjects.containsKey(grammarType)) {
            value = grammarObjects.get(grammarType);
        }
        else {
            value = new ArrayList<ANTLRv4GrammarClass>();
            grammarObjects.put(grammarType, value);
        }
        value.add(grammarClass);
    }

    public TreeMap<ANTLRv4GrammarType, ArrayList<ANTLRv4GrammarClass>> getGrammarObjects() {
        return grammarObjects;
    }

    public void setGrammarObjects(TreeMap<ANTLRv4GrammarType, ArrayList<ANTLRv4GrammarClass>> grammarObjects) {
        this.grammarObjects = grammarObjects;
    }
}
