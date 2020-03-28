package Model;

import javafx.util.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.util.*;

/**
 * this class is for the readFile, the readFile responsible to the reading of the corpus, saving the paths and if there is neeed to stemming.
 * this class identify the start of each document and read the docs.
 *
 */
public class ReadFile {
    private String corpusPath;
    private String postingPath;
    public Boolean stem;
    private Parse parse;
    private Indexer indexer;
    private TreeMap<String, Pair<String,String>> sorted;
    private HashMap<String, Doc> docsList;
    int countDocs = 0;
    public HashSet<String> stopWords;


    /**
     * constructor of the readFile class
     * init the parse, the indexer and fill the stop_words
     * in addition,
     * @param corpusPath
     * @param postingPath
     * @param isStemming
     * @throws Exception
     */
    public ReadFile(String corpusPath, String postingPath, boolean isStemming ) throws Exception {
        parse=  new Parse();
        docsList = new HashMap<>();
        this.corpusPath = corpusPath;
        this.stem = isStemming;

        if(!Files.exists(Paths.get(postingPath+"//posting")) && !stem){
            this.postingPath = postingPath+"//posting//";
            File dir = new File(this.postingPath);
            dir.mkdir();
        }

        else if(!Files.exists(Paths.get(postingPath+"//stemmer")) && stem){
            this.postingPath = postingPath+"//stemmer//";
            File dir = new File(this.postingPath);
            dir.mkdir();
            this.postingPath = this.postingPath + "//Stemmer_";
        }

        indexer = new Indexer();
        stopWords = new HashSet();


    }

    /**
     * getter for the indexer
     * @return
     */
    public Indexer getIndexer() {
        return indexer;
    }

    /**
     * setter for the stem
     * @param stem
     */
    public void setStem(Boolean stem) {
        this.stem = stem;
    }

    /**
     * this function responsible for the reading of the corpus, the function run on the files and  read the folders in the folder after reading 40 files we index the terms in posting files
     * after all the temporary posting file created we merge them into one posting file for each letter that the term star.t with
     * @throws Exception
     */
    public void readCorpus() throws Exception {
        int countPosting = 0;
        int countFiles =0;

        File corpus = new File(corpusPath);
        File[] directories = corpus.listFiles();
        readStopWords(corpusPath + "/stop_words.txt");
        for (File dFile:  directories ) {
            if(!dFile.isDirectory() || dFile.getName().equals("stop_words.txt") ){
                continue;
            }
            File[] filesInFile = dFile.listFiles();
            for (File f: filesInFile){
                ReadJsoup(f);
            }
            countFiles++;
            if (countFiles >= 40){
                countPosting++;

                indexer.indexingEntities(parse.getEntities(), postingPath + countPosting, stem, docsList);
                indexer.indexing(parse.getTerms(), postingPath + countPosting, stem);

                parse.clearTermList();
                parse.clearEntitiesList();
                countFiles = 0;
            }
        }
        if(countFiles!=0){
            countPosting++;

            indexer.indexingEntities(parse.getEntities(), postingPath + countPosting, stem, docsList);
            indexer.indexing(parse.getTerms(), postingPath + countPosting, stem);

            parse.clearTermList();
            parse.clearEntitiesList();
        }


        indexer.lastPosting(postingPath,"a",countPosting, stem);
        indexer.lastPosting(postingPath,"b",countPosting, stem);
        indexer.lastPosting(postingPath,"c",countPosting, stem);
        indexer.lastPosting(postingPath,"d",countPosting, stem);
        indexer.lastPosting(postingPath,"e_f",countPosting, stem);
        indexer.lastPosting(postingPath,"g_h",countPosting, stem);
        indexer.lastPosting(postingPath,"i_k",countPosting, stem);
        indexer.lastPosting(postingPath,"l",countPosting, stem);
        indexer.lastPosting(postingPath,"m",countPosting, stem);
        indexer.lastPosting(postingPath,"n_o",countPosting, stem);
        indexer.lastPosting(postingPath,"p",countPosting, stem);
        indexer.lastPosting(postingPath,"q_r",countPosting, stem);
        indexer.lastPosting(postingPath,"s",countPosting, stem);
        indexer.lastPosting(postingPath,"t",countPosting, stem);
        indexer.lastPosting(postingPath,"u_z",countPosting, stem);
        indexer.lastPosting(postingPath,"percents",countPosting, stem);
        indexer.lastPosting(postingPath,"prices",countPosting, stem);
        indexer.lastPosting(postingPath,"numbers",countPosting, stem);
        indexer.lastPosting(postingPath,"entities_names",countPosting, stem);
        BufferedWriter writer_docs = new BufferedWriter(new FileWriter(postingPath + "docs.txt", true));


        for (Doc currDoc: docsList.values() ) {
            writer_docs.append(currDoc.getDocInfo());
        }




        writer_docs.flush();
        writer_docs.close();

        // sfter the program end to read the corpus we sort the dictionary
        sortDictionary(indexer.getDictionary());
    }

    /**
     * this function add all the stop words into hashSet
     * then we can find if the word is in stop words easily
     * @param path
     * @throws IOException
     */
    public void readStopWords(String path) throws IOException {
        File f = new File(path);
        StringBuilder Text = new StringBuilder();
        FileReader fileReader = new FileReader(f);
        try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null)
                Text = Text.append(line + System.lineSeparator());
        }

        String[] stopWordsLines = Text.toString().split("\n");
        for (int i = 0; i < stopWordsLines.length; i++) {
            stopWords.add(stopWordsLines[i]);

        }
    }

    /**
     * this function read the file in each file there are several docs, so this function read the each docs in the file.
     * in addition this function get the docNO, title, and date from the docs and save that.
     * after that this function parse the doc.
     * @param f
     * @throws Exception
     */
    public void ReadJsoup(File f) throws Exception{
        Document doc = null;
        try {
            doc = Jsoup.parse(f, "UTF-8");
            Elements docs = doc.select("DOC"); //get element by tag
            for (Element element : docs) {
                String text = "";
                text = element.select("TEXT").text();
                if(!text.equals("")){
                    countDocs ++;
                }

                String docNo = element.select("DOCNO").text();
                String header = element.select("TI").text().replace("#","");
                String date = element.select("DATE1").text();
                Doc newDOC = new Doc(header, docNo, date);  //new doc
                parse.parser(newDOC, text, stopWords, stem);
                newDOC.setDocLength(newDOC.getDocTerms().size());
                docsList.put(docNo, newDOC);

            }
        } catch (Exception e){
           // e.printStackTrace();
        }
    }

    /**
     * this function get unsortedDictionary and sort the dictionary
     * @param unsortedDictionary
     * @throws IOException
     */
    public void sortDictionary(HashMap<String, Pair<String,String>> unsortedDictionary) {


       try{
           StringBuilder insertToFile=new StringBuilder();
           sorted = new TreeMap<>();
           sorted.putAll(unsortedDictionary);
           for (HashMap.Entry<String, Pair<String,String>> entry : sorted.entrySet()) {
               String []info = entry.getValue().getValue().split(",");
               insertToFile.append(entry.getKey()+"="+info[1]+"\r"+"\n");
           }

           FileOutputStream fout = new FileOutputStream(postingPath+"dictionary.txt");
           ObjectOutputStream oos = new ObjectOutputStream(fout);
           oos.writeObject(unsortedDictionary);
           fout.flush();
           fout.close();
           oos.flush();
           oos.close();


           BufferedWriter display = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(postingPath+"sorted_Dictionary.txt")));
           display.write(insertToFile.toString());
           display.flush();
           display.close();





           postingPath = postingPath.substring(0, postingPath.length()-9);
           if(stem){
               postingPath = postingPath.substring(0, postingPath.length()-11);
           }

           if(!postingPath.equals("") && Files.exists(Paths.get(postingPath))&& !Files.exists(Paths.get(postingPath + "/stop_words.txt"))){
               BufferedWriter writer_stop_words = new BufferedWriter(new FileWriter(postingPath + "/stop_words.txt", true));


               File f = new File(corpusPath + "/stop_words.txt");
               StringBuilder Text = new StringBuilder();
               FileReader fileReader = new FileReader(f);
               try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                   String line;
                   while ((line = bufferedReader.readLine()) != null)
                       Text = Text.append(line + System.lineSeparator());
               }

               writer_stop_words.write(Text.toString());

               writer_stop_words.flush();
               writer_stop_words.close();
           }


       }
       catch (Exception e){
         //  e.printStackTrace();
       }




    }


    /**
     * this function reset the readFile -> init new dictionary, new sorted dictionary,
     * init new parse, indexer, stopWprds
     */
    public void resetReadFile() {
        getIndexer().setDictionary(new HashMap<String, Pair<String, String>>());
        setSortedSictionary(new HashMap<String, Pair<String, String>>());
        parse=  new Parse();
        indexer = new Indexer();
        stopWords = new HashSet();

    }

    /**
     * getter for the sorted dictionary
     * @return
     */
    public ArrayList<String> getSortedSictionary() {
        ArrayList<String> sortedDictionary = new ArrayList<String>();
        String value;
        for(Map.Entry<String,Pair<String,String>> entry : sorted.entrySet()) {
            String[] values = entry.getValue().getValue().split(",");
            if (values.length>=2){
                sortedDictionary.add(entry.getKey() +" = "+ values[1]);
            }
        }
        return sortedDictionary;
    }

    /**
     * setter for the sorted dictionary this function given the unsorted dictionary and set the new sorted dictionary
     * @param ourDict
     * @return true if success or false
     */
    public  boolean setSortedSictionary( HashMap<String, Pair<String, String>> ourDict  ) {

        TreeMap<String, Pair<String,String>> newSorted = new TreeMap<>();
        newSorted.putAll(ourDict);
        this.sorted = newSorted;
        if(sorted!=null)
            return true;

        return false;
    }

    /**
     * getter for the docSize- the count of the documents
     * @return
     */
    public int getDocSize() {
        return countDocs;
    }




    /**
     * getter for the terms size- hoe much terms the parse save.
     * @return
     */
    public int getTermsSize() {
        return sorted.size();
    }

    public HashSet<String> getStopWords(){return stopWords;}

    public HashMap<String, Doc> getDocsList(){return docsList;}

    public String getPostingPath() {
        return postingPath;
    }


    public void loadDocsList(List<String> docsFileLines) {
        for (String docLine : docsFileLines ) {
            String[] docValue = docLine.split("#");

            String docNo = docValue[0];
            String docTitle = docValue[1];
            int docSize = Integer.parseInt(docValue[2]);
            int docMax_tf = Integer.parseInt(docValue[3]);
            String docDate="";
            if(docValue.length > 4){
                docDate = docValue[4];
            }

            Doc newDoc = new Doc(docTitle, docNo, docDate );

            newDoc.setDocLength(docSize);
            newDoc.setMax_tf(docMax_tf);


            TreeMap<Integer, String> topFiveEntites =new TreeMap<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2-o1;
                }
            });

            if(docValue.length==6 && docValue[5] != null && !docValue[5].equals("")){
                String[] topEntitesValues =docValue[5].split(",");
                for (String entity_and_value: topEntitesValues ) {
                    String entityName = entity_and_value.split("&")[0];
                    int entityfreq = Integer.parseInt(entity_and_value.split("&")[1]);
                    topFiveEntites.put(entityfreq, entityName);
                }
            }

            newDoc.setTopFiveEntites(topFiveEntites);
            docsList.put(docNo, newDoc);
            countDocs = docsList.size();
        }


    }
}
