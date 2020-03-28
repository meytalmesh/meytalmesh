package Model;

import com.medallia.word2vec.Word2VecModel;
import javafx.util.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

/**
 * This class represent the searcher that get the dictionary the stopwords doc list the post path and find all the relevant docs to the query, and send to the ranker to get the rank for each doc.
 * this rank and sort the document and get the 50 top relevant.
 * in this class we read the posting file and get the info that we need for the parse words from the query
 */
public class Searcher {

    private Parse parse;
    private Ranker ranker;
    private HashMap<String, Pair<String, String>> dictionary;
    private HashSet<String> stopWords;
    private boolean stem;
    private HashSet<String> relevantDocName;
    private HashMap<String, Pair<Term, Integer>> queryTermList;
    private HashMap<String, Doc> docsList;  //all docs in the corpus
    private HashMap<Doc, Double> docByRank; //all relevent docs
    private String postP;
    private List<String> fiftySortedDocs;  //docs that return
    private List<String> listOfAllQueryResults;
    private HashSet<String> allDocForQueriesFile;


    /**
     * Enum for the state of the query
     */
    public enum QUERY_STATE{
        singleQuery, queryFile;
    }

    /**
     * constructor for the searcher init the parse, dictionary and all needed lists
     * @param dictionary
     * @param stopWords
     * @param docsList
     * @param postPath
     * @param isStemming
     */
    public Searcher(HashMap<String, Pair<String, String>> dictionary, HashSet<String> stopWords, HashMap<String, Doc> docsList, String postPath, boolean isStemming) {
        parse = new Parse();
        postP = postPath;
        this.dictionary = dictionary;
        this.stopWords = stopWords;
        queryTermList = new HashMap<>();
        relevantDocName = new HashSet<>();
        this.docsList = docsList;
        fiftySortedDocs = new ArrayList<>();
        docByRank= new HashMap<>();
        listOfAllQueryResults = new ArrayList<>();
        allDocForQueriesFile = new HashSet<>();
        stem = isStemming;
    }

    /**
     * this function called for single query and for queryFile.
     * if it Single query we add to list the format to write to the results file, if it query file if didn't add the format(it heppand in other place)
     * clear all the lists
     * and calles foreach query in file
     * send to parse to get the query word after parse from the parser
     * get the rank for each relevant doc from all the rankers (measures)
     * and calculate the total rank of the doc and finally sort them
     * @param query
     * @param semantic
     * @param isOnline
     * @param isStemming
     * @param query_state
     */
    public void search(String query, boolean semantic, boolean isOnline, boolean isStemming, final QUERY_STATE query_state) {
        fiftySortedDocs.clear();
        docByRank.clear();
        relevantDocName.clear();
        stem = isStemming;
        parse.getTerms().clear();
        parse.getEntities().clear();
        queryTermList.clear();
        Doc newDoc = new Doc("queryDoc", "queryDoc", "");


        parse.parser(newDoc, query, stopWords, stem);
        HashMap<String, Term> parseTerms = parse.getTerms();
        HashMap<String, Term> parseEntites = parse.getEntities();
        if(semantic){
            parseTerms = semanticSynonymTerm(parseTerms,isOnline);
        }
        createListOfTerms(parseTerms, postP);
        ranker = new Ranker(parseTerms,queryTermList, docsList, dictionary, parseEntites);
        for (String docName: relevantDocName  ) {
            //     System.out.println(docName);
            if(docsList.containsKey(docName)){
                Double titalRank = ranker.rankByTitle(docsList.get(docName));
                Double bm25= ranker.BM25Function(docsList.get(docName));
                Double dateRank = ranker.rankByDate(docsList.get(docName).getDate());
                Double entityRank = ranker.rankByEntity(docsList.get(docName));
                //Double docRank = 0.7*bm25+ 0.2 * titalRank+ 0.05 * dateRank + 0.05* entityRank;
                Double docRank =  bm25+ titalRank + dateRank + entityRank;

                docByRank.put(docsList.get(docName), docRank);
            }
        }
        sortFiftyDocs();

        if(query_state.equals(QUERY_STATE.singleQuery)){
            for (String doc : fiftySortedDocs ) {
                listOfAllQueryResults.add("55555 0 " + doc + " 1 42.38 mt");
            }
        }



    }

    /**
     * this function sort the relevant docs ad get the top 50 docs and update in this list
     */
    private void sortFiftyDocs() {
        List<HashMap.Entry<Doc, Double>> listToSort = new ArrayList<>(docByRank.entrySet());
        Collections.sort(listToSort, new Comparator<HashMap.Entry<Doc, Double>>() {
            @Override
            public int compare(Map.Entry<Doc, Double> o1, Map.Entry<Doc, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        //get the top 50
        int count =1;
        for (HashMap.Entry<Doc, Double> docLine: listToSort ) {
            if(count<=50){
                fiftySortedDocs.add(docLine.getKey().getDocNo());
                count++;
            }
            else
                break;

        }
    }


    /**
     * this function add to list all the terms that add all the relevant do fron the posts file
     * @param queryWordsList
     * @param postPath
     */
    public void createListOfTerms(HashMap<String, Term> queryWordsList, String postPath) {
        postPath = postPath.substring(0,postPath.length()-8);
        if(stem){
            postPath = postPath.substring(0,postPath.length()-8);

        }

        for (String queryWord : queryWordsList.keySet()) {
            if (dictionary.containsKey(queryWord.toLowerCase())) {
                addToQueryTermList(queryWord.toLowerCase(), postPath);
            }
            if(dictionary.containsKey(queryWord.toUpperCase())){
                addToQueryTermList(queryWord.toUpperCase(), postPath);

            }
        }
    }

    /**
     * this function get the info of the docs from the posting files
     * get the path of the posting file and the pointer of the term this writen in the dictionary
     * @param queryWord
     * @param postPath
     */
    private void addToQueryTermList(String queryWord, String postPath) {
        String wordPostPath = "";
        Long wordPointer;

        if(!queryTermList.containsKey(queryWord)){

            if( dictionary.containsKey(queryWord) && dictionary.get(queryWord).getKey().split("#").length>=2 ){
                wordPostPath = dictionary.get(queryWord).getKey().split("#")[0];
                wordPointer = Long.valueOf(dictionary.get(queryWord).getKey().split("#")[1]);
                try {

                    File file = new File(postPath + "\\" + wordPostPath);
                    RandomAccessFile raf = new RandomAccessFile(file, "r");

                    raf.seek(wordPointer);
                    String termValue = raf.readLine();

                    if(termValue==null){

                    }

                    if(termValue!=null){
                        String[] termDocsSTR = termValue.split(",");
                        String docName = "";
                        int docValue ;
                        Term newTerm = new Term(queryWord);
                        HashMap<String, Integer> docsListForTerm = new HashMap<>();

                        for( int i=0; i<termDocsSTR.length; i++){
                            ///////////////////////////////////////////////////
                            //  System.out.println(termDocsSTR[i]);
                            ///////////////////////////////////////////////////
                            String dvalue = termDocsSTR[i].split(Pattern.quote("|"))[1];
                            //  System.out.println(dvalue);
                            docValue = Integer.parseInt(dvalue);
                            docName = termDocsSTR[i].split(Pattern.quote("|"))[0];

                            docsListForTerm.put(docName, docValue);
                            relevantDocName.add(docName);
                        }

                        newTerm.setDocs(docsListForTerm);
                        Pair<Term,Integer> newPair = new Pair<>(newTerm, 1);
                        queryTermList.put(queryWord, newPair);
                    }


                } catch (IOException e) {
                  //  e.printStackTrace();
                }
            }

        }
        else {
            Term t = queryTermList.get(queryWord).getKey();
            Integer v = queryTermList.get(queryWord).getValue()+1;
            Pair<Term,Integer> newPair = new Pair<>(t, v);
            //queryTermList.get(queryWord).getValue().
            queryTermList.put(queryWord,newPair);
        }
    }

    /**
     * this function get the query terms anf find a semantic word with
     * if online id true -> use online from the url : "https://api.datamuse.com/words?rel_syn="  and returm hash map of the terms that are semantic eith this term.
     *
     * else we use jar and find the semantic word without online anf use -> word2vec
     *
     * returm hash map of the terms that are semantic eith this term.
     *
     * @param termsAfterParse
     * @param online
     * @return
     */
    public HashMap<String, Term> semanticSynonymTerm(HashMap<String, Term> termsAfterParse, boolean online){

        HashMap<String, Term> toReturn = new HashMap<>();
        toReturn.putAll(termsAfterParse);
        if (online) {
            try {
                for (HashMap.Entry<String, Term> term : termsAfterParse.entrySet()) {
                    URL glove = new URL("https://api.datamuse.com/words?rel_syn=" + term.getKey());
                    BufferedReader br = new BufferedReader(new InputStreamReader(glove.openStream()));
                    String line = br.readLine();
                    br.close();
                    if (line.equals("[]"))
                        continue;
                    String[] synWords = line.split("\":\"");
                    for (int i = 0; i < synWords.length; i++) {
                        String word = synWords[i].split(",")[0];
                        word = word.substring(0, word.length() - 1);
                        Term newTerm = new Term(word, "queryDoc");
                        toReturn.put(word, newTerm);
                    }
                }
                //return termsAfterParse;
            } catch (Exception e) {
              //  e.printStackTrace();
            }
        }
        //offline
        else {
            try {
                String filename = "src/main/resources/word2vec.c.output.model.txt";
                File file = new File(filename);
                Word2VecModel model = Word2VecModel.fromTextFile(file);
                com.medallia.word2vec.Searcher semanticSearcher = model.forSearch();
                int numOfResult = 2;
                for (String term : termsAfterParse.keySet()) {
                    if(semanticSearcher.contains(term)){
                        List<com.medallia.word2vec.Searcher.Match> matches = semanticSearcher.getMatches(term.toLowerCase(), numOfResult);
                        for (com.medallia.word2vec.Searcher.Match matche : matches) {
                            if (dictionary.containsKey(matche.match())) {
                                Term newTerm = new Term(matche.match(), "queryDoc");
                                //System.out.println(term + " ------" + matche.match());
                                toReturn.put(matche.match(), newTerm);
                            }
                        }
                    }
                }
            }catch (Exception e) {
              //  e.printStackTrace();
            }
        }
        return toReturn;
    }


    /**
     * getter
     * @return
     */
    public List<String> getFiftySortedDocs() {
        return fiftySortedDocs;
    }

    /**
     * this function called from the controller if the input is file of query
     * it parse the queries and send to search
     * @param QueriesPath
     * @param isStem
     * @param semantic
     * @param isOnline
     * @throws IOException
     */
    public void readQueries(String QueriesPath, boolean isStem, boolean semantic , boolean isOnline)throws IOException{

        StringBuilder queriesText = new StringBuilder();
        File f = new File(QueriesPath + "/queries.txt");
        Document doc = null;
        //Elements elements=null;
        doc = Jsoup.parse(f, "UTF-8");
        Elements elements = doc.getElementsByTag("top");
        for (Element element : elements) {
            String[] line = element.outerHtml().split("\n");
            //String[] line = element.text().split("\n");
            try{
                String qNum = line[2].split(":")[1].substring(1, line[2].split(":")[1].length() - 1);
                queriesText.append(element.getElementsByTag("title").text());
                String[] description = element.getElementsByTag("desc").text().replace("Description:", "").split("Narrative:");
                queriesText.append(description[0]);
                search(queriesText.toString(), semantic, isOnline, isStem,  QUERY_STATE.queryFile);

                for (String result: fiftySortedDocs ) {
                    listOfAllQueryResults.add(qNum + " 0 " + result + " 1 42.38 mt");
                }

                allDocForQueriesFile.addAll(fiftySortedDocs);
                queriesText = new StringBuilder();  //reset

            }catch (Exception e){
           //     e.printStackTrace();
            }

        }
        // System.out.println(queriesText);

    }

    /**
     * this function write to resuls file all the results line by line to specific path
     * @param listOfAllQueryResults
     * @param pathForResults
     */
    public void writeToQueryResultsFile(List<String> listOfAllQueryResults, String pathForResults) {
        //String pathForResults = "d:\\documents\\users\\pazyo\\Downloads\\";
        BufferedWriter resultsQueriesFile = null;
        try {
            resultsQueriesFile = new BufferedWriter( new OutputStreamWriter( new FileOutputStream(pathForResults + "\\results.txt")));
            String lineSep = System.getProperty("line.separator");
            for (String resultsToWrite: listOfAllQueryResults ) {
                resultsQueriesFile.write(resultsToWrite + lineSep);
            }
            resultsQueriesFile.flush();
            resultsQueriesFile.close();

        } catch (IOException e) {
        //    e.printStackTrace();
        }

    }


    /**
     * getter
     * @return
     */
    public List<String> getListOfAllQueryResults() {
        return listOfAllQueryResults;
    }


    /**
     * getter
     * @return
     */
    public HashSet<String> getAllDocForQueriesFile() {
        return allDocForQueriesFile;
    }





}