package Model;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * this class represent the ranker, it rank by some measurs ---> title, BM25, entities, date
 * M- the corpus size
 * k- 0.8
 * B- 2
 */
public class Ranker {

    private HashMap<String, Pair<Term,Integer>> queryWords;
    private HashMap<String,Pair<String, String>> dictionary;
    private HashMap<String, Term> parseTermInRanker;
    private double avdl;
    private double b;
    private double k;
    private double M;
    private HashMap<String, Integer> dates;


    /**
     * constuctor - init all the fields, add fill the dates values
     * @param parseTerms
     * @param queryWords
     * @param docList
     * @param dictionary
     * @param parseEntites
     */
    public Ranker(HashMap<String, Term> parseTerms, HashMap<String, Pair<Term,Integer>> queryWords, HashMap<String, Doc>docList ,HashMap<String,Pair<String, String>> dictionary, HashMap<String, Term> parseEntites){
        this.parseTermInRanker = new HashMap<>(parseTerms);
        this.queryWords = queryWords;
        if(!parseEntites.isEmpty()){
            for (Map.Entry<String, Term> entity : parseEntites.entrySet()) {
                parseTermInRanker.put(entity.getKey(), entity.getValue());
                Pair<Term,Integer> pair = new Pair<>(entity.getValue(), 1);
                this.queryWords.put(entity.getKey(),pair);
            }
        }


        this.dictionary = dictionary;
        this.M = docList.size();
        this.b = 0.8;
        this.k = 2;
        int totalDocLength=0;
        dates = new HashMap<>();
        for (HashMap.Entry<String, Doc> doc : docList.entrySet()) {
            totalDocLength += doc.getValue().getDocLength();
        }

        //avdl = 230;
        this.avdl = totalDocLength / M;


        fillDates();
    }

    /**
     * fill al the date values-> foreach month the is a rank
     * this is in  purpose to check how much the doc is newer
     */
    private void fillDates() {
        this.dates.put("January", 32); this.dates.put("February", 64); this.dates.put("March", 96); this.dates.put("April", 128);
        this.dates.put("May", 160); this.dates.put("June", 192); this.dates.put("July", 224); this.dates.put("August", 256);
        this.dates.put("September", 288); this.dates.put("October", 320); this.dates.put("November", 352);
        this.dates.put("December", 384);
        this.dates.put("JANUARY", 32); this.dates.put("FEBRUARY", 64); this.dates.put("MARCH", 96); this.dates.put("APRIL", 128);
        this.dates.put("MAY", 160); this.dates.put("JUNE", 192); this.dates.put("JULY", 224); this.dates.put("AUGUST", 256);
        this.dates.put("SEPTEMBER", 288); this.dates.put("OCTOBER", 320); this.dates.put("NOVEMBER", 352);
        this.dates.put("DECEMBER", 384);
        this.dates.put("Jan", 32); this.dates.put("Feb", 64); this.dates.put("Mar", 96); this.dates.put("Apr", 128);
        this.dates.put("Jun", 192); this.dates.put("Jul", 224); this.dates.put("Aug", 256); this.dates.put("Sep", 288);
        this.dates.put("Oct", 320); this.dates.put("Nov", 352); this.dates.put("Dec", 388);
        this.dates.put("JAN", 32);this.dates.put("FEB", 64); this.dates.put("MAR", 96); this.dates.put("APR", 128);
        this.dates.put("JUN", 192);this.dates.put("JUL", 224);this.dates.put("AUG", 256);this.dates.put("SEP", 288);
        this.dates.put("OCT", 320);this.dates.put("NOV", 352);this.dates.put("DEC", 388);
        this.dates.put("january", 32);this.dates.put("february", 64);this.dates.put("march", 96);this.dates.put("april", 128);
        this.dates.put("may", 160);this.dates.put("june", 192);this.dates.put("july", 224);this.dates.put("august", 256);
        this.dates.put("september", 288);this.dates.put("october", 320);this.dates.put("november", 352);
        this.dates.put("december", 388);
        this.dates.put("jan", 32);this.dates.put("feb", 64);this.dates.put("mar", 96);this.dates.put("apr", 128);
        this.dates.put("jun", 192);this.dates.put("jul", 224);this.dates.put("aug", 256);this.dates.put("sep", 288);
        this.dates.put("oct", 320);this.dates.put("nov", 352);this.dates.put("dec", 388);
    }

    /**
     * this function check how much the date of the doc is newer and if help to check the rank
     * @param date
     * @return
     */
    public double rankByDate(String date){
        if(date.equals("") || date == null) {
            double first=(2020*390)+64+1;
            double second=(1994 * 390) + 64 + 1;
            return 1.5/(first-second);
        }
        String [] docDate=date.split(" ");
        double today= (2020*390) + 64 + 1;
        double publishDateValue=0;
        try {
            //no year
            if(docDate.length==2){
                publishDateValue= Integer.valueOf(docDate[0]);
                publishDateValue = publishDateValue + (double) dates.get(docDate[1]) +(1994*390);
            }
            else if(dates.containsKey(docDate[0])){
                publishDateValue= Integer.valueOf(docDate[1])+ (Integer.valueOf(docDate[2])*390);
                publishDateValue = publishDateValue + (double) dates.get(docDate[0]);
            }
            else if(dates.containsKey(docDate[1])) {
                publishDateValue = Integer.valueOf(docDate[0].replace("*","")) + (Integer.valueOf(docDate[2].replace("*","")) * 390);
                publishDateValue = publishDateValue + (double) dates.get(docDate[1]);
            }
            else {
                publishDateValue = Integer.valueOf(docDate[0]) + (Integer.valueOf(docDate[2]) * 390);
                publishDateValue=publishDateValue+32;
            }
        }

        catch (Exception e){
        //  e.printStackTrace();
        }

        return 1.5/(today-publishDateValue);
    }


    /**
     * this function calculate the rank by BM25 and rank all docs
     * @param doc
     * @return
     */
    public double BM25Function( Doc doc){
        int d= doc.getDocLength();
        double ans= 0;

        // String - the wordName
        // Integer- the count of the term in the query
        for (HashMap.Entry<String, Pair<Term, Integer>> word : queryWords.entrySet()){
            double cwd = 0;
            double dfw = 0;
            double cwq = 1.0;

            if(parseTermInRanker.containsKey(word.getKey().toLowerCase())){    //
                if (parseTermInRanker.get(word.getKey().toLowerCase()).getDocs()!=null){
                    cwq = parseTermInRanker.get(word.getKey().toLowerCase()).getDocs().get("queryDoc").doubleValue();
                }
            }else if(parseTermInRanker.containsKey(word.getKey().toUpperCase())) {    //
                if (parseTermInRanker.get(word.getKey().toUpperCase()).getDocs() != null) {
                    cwq = parseTermInRanker.get(word.getKey().toUpperCase()).getDocs().get("queryDoc").doubleValue();
                }
            }

            if(word.getValue().getKey().getDocs().containsKey(doc.getDocNo())){
                cwd = word.getValue().getKey().getDocs().get(doc.getDocNo());
            }


            if(dictionary.containsKey(word.getKey().toUpperCase())){
                String[] pairValue = dictionary.get(word.getKey().toUpperCase()).getValue().split(",");
                dfw = Double.valueOf(pairValue[0]);
               // dfw= word.getValue().getKey().getDocs().size();
            }
            else if(dictionary.containsKey(word.getKey().toLowerCase())){
                String[] pairValue = dictionary.get(word.getKey().toLowerCase()).getValue().split(",");
                dfw = Double.valueOf(pairValue[0]);
            }

            double denominator=k*(1 - b + b*(d/ avdl )) + cwd;  //mehane
            double numerator = cwq * (k+1) * cwd ;        //monee

            ans += (numerator/denominator) * Math.log10((M+1)/dfw);
        }
        return ans;
    }

    /**
     *  this class rank the doc by its title, if doc have in its title word that is in the query the counter increase
     * @param doc
     * @return
     */
    public double rankByTitle(Doc doc){
        double counter = 0;
        int f;
        String[] titleText = doc.getTitle().split(" ");

        for( int i=0; i<titleText.length ; i++){
            if(queryWords.keySet().contains(titleText[i].toLowerCase()) || queryWords.keySet().contains(titleText[i].toLowerCase())){
                counter = counter + 1;
            }
        }
        return counter;
    }

    /**
     * this function rank doc by its entites -> if in the doc there is word from the query the counter id increase
     * @param doc
     * @return
     */
    public double rankByEntity(Doc doc){
        double counter =0;
        for(Map.Entry<Integer,String> entityEntry : doc.getDocEntites().entrySet()){
            for (Map.Entry<String, Pair<Term, Integer>> word : queryWords.entrySet()){
                if(entityEntry.getValue().toUpperCase().equals(word.getKey().toUpperCase()) ){
                    counter++;
                }
            }
        }
        return counter;
    }


}