package Model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class present  the Documents in the corpus.
 * the fields of documents is its:
 * title,
 * docNo,
 * date,
 * text
 * max_tf- is the frequency of the term that have the max frequency in this document.
 * and also hashMap that contains all the terms in this doc. this hashMap have string that present the term and Integer that present the frequency oc the term in this doc
 *
 */
public class Doc {

    private String title;
    private String docNo;
    private String date;
    private int max_tf;
    private HashMap<String,Integer> docTerms;
    // private HashMap<String,Integer> docEntites;
    private TreeMap<Integer, String> topFiveEntites;
    private int counterForTopFive;
    private int docLength;


    /**
     * constructor of this class that present document in the corpus.
     * max_tf init with 0.
     * init docTerms as hashMap
     *
     * @param title
     * @param docNo
     * @param date
     */
    public Doc(String title, String docNo, String date) {
        this.title = title;
        this.docNo = docNo;

        this.max_tf= 0;
        this.date= date;
        docTerms=new HashMap<>();
        topFiveEntites=new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2-o1;
            }
        });
        counterForTopFive=1;
        //docLength=0;
    }

    /**
     * getter for the doc number
     * @return doc number
     */
    public String getDocNo(){return docNo; }

    /**
     * getter for the docTerms
     * @return hashMap that contain the terms in this doc
     */
    public HashMap<String, Integer> getDocTerms(){return docTerms; }

    public void clearDocTerm(){docTerms.clear();}


    /**
     * getter
     * @return
     */
    public TreeMap<Integer, String> getDocEntites(){
        return topFiveEntites;
    }




    /**
     * getter of the maximum frequency term in this doc
     * @return max_tf of the doc
     */
    public int getMax_tf(){return max_tf; }

    //public int getNumOfDifferentTerms(){return numOfDifferentTerms; }

    public String getDocInfo(){
        return docNo + "#" + title + "#" + docLength+"#" + max_tf+ "#" + date + "#" + getTopFiveEntitesInPrintFormat() + "\n";
    }


    /**
     * this function add term to the list of the terms in this doc (docTerms)
     * in insert the function check if the max_tf is smaller them the frequency of the term that added,
     * --->if yes, we update the max_tf
     * @param k - the term that added to the docTerms
     * @param v - the frequency of the this term in this doc.
     */
    public void addTermToDoc(String k,Integer v){
        this.docTerms.put(k,v);
        if(max_tf < docTerms.get(k)){
            this.max_tf = docTerms.get(k);
        }

        docLength++;
    }

    /**
     * add until 5 top entites to the tree map
     * @param frequancy
     * @param key
     */
    public void addToTopFiveEntites(Integer frequancy, String key) {

        if(counterForTopFive <5){
            topFiveEntites.put(frequancy,key);
            counterForTopFive++;
        }
        else{
            if(topFiveEntites.firstKey()<frequancy){
                topFiveEntites.remove(topFiveEntites.firstKey());
                topFiveEntites.put(frequancy,key);
            }

        }

    }

    /**
     * getter
     * @return
     */
    public int getDocLength() {
        return docLength;
    }




    // return String of the 5 top entities in the format of : entity&rank
    public String getTopFiveEntitesInPrintFormat(){
        String topFiveToReturn = "";
        for (Map.Entry<Integer,String> entityEntry : topFiveEntites.entrySet()) {
            topFiveToReturn += entityEntry.getValue() + "&" +entityEntry.getKey() + ",";
        }
        return topFiveToReturn;
    }


    /**
     * getter
     * @return
     */
    public String getDate() {
        return date;
    }

    /**
     * getter
     * @return
     */
    public String getTitle() { return title; }

    /**
     * setter
     * @param docLength
     */
    public void setDocLength(int docLength) { this.docLength = docLength;  }

    /**
     * setter
     * @param max_tf
     */
    public void setMax_tf(int max_tf) { this.max_tf = max_tf; }

    /**
     * setter
     * @param topFiveEntites
     */
    public void setTopFiveEntites(TreeMap<Integer, String> topFiveEntites) {
        if(topFiveEntites.size()<=5)
            this.topFiveEntites = topFiveEntites;
    }

}
