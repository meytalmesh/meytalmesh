package Model;

import javafx.util.Pair;
import java.util.HashMap;

/**
 * This class present every term in the corpus.
 * the fields of term is:
 * term- witch is the name of the term
 * and also hashMap that contains all the docs contain the term. this hashMap have string that present the docNo and Integer that present the number of time that the term appera in the doc
 */
public class Term {
    private HashMap<String, Integer> docs;
    String term;

    /**
     * constuctor
     * @param name
     * @param docNo
     */
    public Term(String name, String docNo) {
        this.docs = new HashMap<>();
        term = name;
        setTerm(docNo, name);

    }

    /**
     * constuctor
     * @param name
     */
    public Term(String name){
        this.docs =  new HashMap<>();
        term =name;
    }

    /**
     * getter
     * @return
     */
    public HashMap<String, Integer> getDocs() {
        return docs;
    }
    //setter
    public void setDocs(HashMap<String, Integer> docs) { this.docs = docs; }

    //getter
    public String getTerm() {
        return term;
    }

    public void setTerm(String docNo, String term) {
        if (docs.containsKey(docNo)) {
            docs.put(docNo, docs.get(docNo) + 1);
        } else {
            docs.put(docNo, 1);
        }
        this.term = term;
    }

}