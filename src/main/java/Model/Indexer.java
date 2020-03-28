
package Model;

import javafx.util.Pair;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * This class index the dictionary in txt files, for each letter of several letter there is a file.
 * each file contain all the words in the corpus after parsing that start with the letters that the file present.
 * the field if hashMap for the dictionary:
 * -the key of the hashMap is string that present the term name
 * -the value is pair of the path of the posting file and the number of the line that contain the term.
 * the dictionary contain all the term in the corpus after parsing
 *
 */
public class Indexer {
    //Dictionary -> the key is the term name and the value is the path of the posting file and the number of the line that contain the term
    private HashMap<String, Pair<String, String>> dictionary;
    private HashMap<String, String> termList;
    private StringBuilder uniteText;

    /**
     * constructor of the class.
     * init new hashMap for the dictionary.
     */
    public Indexer() {
        dictionary = new HashMap<>();
        termList = new HashMap<>();
        uniteText = new StringBuilder();

    }

    /**
     * getter of the dictionary
     * @return the dictionary
     */
    public HashMap<String, Pair<String, String>> getDictionary() {
        return dictionary;
    }

    /**
     * setter for the dictionary, receive new dictionary and set the dictionary with that
     * and return true or false if the set success
     * @param newDict- new dictionary to set
     * @return true or false if the setting success
     */
    public  boolean setDictionary(HashMap<String, Pair<String, String>> newDict) {
        this.dictionary = newDict;
        if(dictionary!=null)
            return true;

        return false;
    }


    /**
     * this function indexing the termList in the posting file that given.
     * every several reading iteration we write to posting file
     * @param termList- every several files that we read we write the terms to posting file
     * @param tempPostPath- path for the posting directory + the number of the iteration
     * @param stem - to know if to insert the posting file to stemmer directory
     * @throws IOException
     */
    public void indexing(HashMap<String, Term> termList, String tempPostPath, boolean stem) throws IOException {
        BufferedWriter writer_a_post = new BufferedWriter(new FileWriter(tempPostPath + "a.txt", true));
        BufferedWriter writer_b_post = new BufferedWriter(new FileWriter(tempPostPath + "b.txt", true));
        BufferedWriter writer_c_post = new BufferedWriter(new FileWriter(tempPostPath + "c.txt", true));
        BufferedWriter writer_d_post = new BufferedWriter(new FileWriter(tempPostPath + "d.txt", true));
        BufferedWriter writer_e_f_post = new BufferedWriter(new FileWriter(tempPostPath + "e_f.txt", true));
        BufferedWriter writer_g_h_post = new BufferedWriter(new FileWriter(tempPostPath + "g_h.txt", true));
        BufferedWriter writer_i_k_post = new BufferedWriter(new FileWriter(tempPostPath + "i_k.txt", true));
        BufferedWriter writer_l_post = new BufferedWriter(new FileWriter(tempPostPath + "l.txt", true));
        BufferedWriter writer_m_post = new BufferedWriter(new FileWriter(tempPostPath + "m.txt", true));

        BufferedWriter writer_n_o_post = new BufferedWriter(new FileWriter(tempPostPath + "n_o.txt", true));
        BufferedWriter writer_p_post = new BufferedWriter(new FileWriter(tempPostPath + "p.txt", true));
        BufferedWriter writer_q_r_post = new BufferedWriter(new FileWriter(tempPostPath + "q_r.txt", true));
        BufferedWriter writer_s_post = new BufferedWriter(new FileWriter(tempPostPath + "s.txt", true));
        BufferedWriter writer_t_post = new BufferedWriter(new FileWriter(tempPostPath + "t.txt", true));
        BufferedWriter writer_u_z_post = new BufferedWriter(new FileWriter(tempPostPath + "u_z.txt", true));
        BufferedWriter writer_prices_post = new BufferedWriter(new FileWriter(tempPostPath + "prices.txt", true));
        BufferedWriter writer_percents_post = new BufferedWriter(new FileWriter(tempPostPath + "percents.txt", true));
        BufferedWriter writer_numbers_post = new BufferedWriter(new FileWriter(tempPostPath + "numbers.txt", true));

        String postPath = "//posting//";
        if(stem)
            postPath = "//stemmer//Stemmer_";

       // System.out.println("hello :)");

        //foreach entry in the termList check where to write this term information (depend on the letter that the term is start with)
        for (HashMap.Entry<String, Term> lineInTermList : termList.entrySet()) {
            StringBuilder fullString = termInfo(lineInTermList.getKey(), lineInTermList.getValue());
            if (lineInTermList.getKey().charAt(0) == 'a' || lineInTermList.getKey().charAt(0) == 'A') {
                insertToDictionary(lineInTermList.getKey(), lineInTermList.getValue(), postPath + "a.txt");
                writer_a_post.append(fullString.toString() + "\n");
                continue;
            }
            if (lineInTermList.getKey().charAt(0) == 'b' || lineInTermList.getKey().charAt(0) == 'B') {
                insertToDictionary(lineInTermList.getKey(), lineInTermList.getValue(), postPath + "b.txt");
                writer_b_post.append(fullString.toString() + "\n");
                continue;
            }
            if (lineInTermList.getKey().charAt(0) == 'c' || lineInTermList.getKey().charAt(0) == 'C') {
                insertToDictionary(lineInTermList.getKey(), lineInTermList.getValue(), postPath + "c.txt");
                writer_c_post.append(fullString.toString() + "\n");
                continue;
            }
            if (lineInTermList.getKey().charAt(0) == 'd' || lineInTermList.getKey().charAt(0) == 'D') {
                insertToDictionary(lineInTermList.getKey(), lineInTermList.getValue(), postPath + "d.txt");
                writer_d_post.append(fullString.toString() + "\n");
                continue;
            }
            if (lineInTermList.getKey().charAt(0) == 'e' || lineInTermList.getKey().charAt(0) == 'E'|| lineInTermList.getKey().charAt(0) == 'f'
                    || lineInTermList.getKey().charAt(0) == 'F') {
                insertToDictionary(lineInTermList.getKey(), lineInTermList.getValue(), postPath + "e_f.txt");
                writer_e_f_post.append(fullString.toString() + "\n");
                continue;
            }
            if (lineInTermList.getKey().charAt(0) == 'g' || lineInTermList.getKey().charAt(0) == 'G'|| lineInTermList.getKey().charAt(0) == 'h'
                    || lineInTermList.getKey().charAt(0) == 'H') {
                insertToDictionary(lineInTermList.getKey(), lineInTermList.getValue(), postPath + "g_h.txt");
                writer_g_h_post.append(fullString.toString() + "\n");
                continue;
            }
            if (lineInTermList.getKey().charAt(0) == 'i' || lineInTermList.getKey().charAt(0) == 'I' || lineInTermList.getKey().charAt(0) == 'j'
                    || lineInTermList.getKey().charAt(0) == 'J' || lineInTermList.getKey().charAt(0) == 'k' || lineInTermList.getKey().charAt(0) == 'K') {
                insertToDictionary(lineInTermList.getKey(), lineInTermList.getValue(), postPath + "i_k.txt");
                writer_i_k_post.append(fullString.toString() + "\n");
                continue;
            }

            if (lineInTermList.getKey().charAt(0) == 'l' || lineInTermList.getKey().charAt(0) == 'L') {
                insertToDictionary(lineInTermList.getKey(), lineInTermList.getValue(), postPath + "l.txt");
                writer_l_post.append(fullString.toString() + "\n");
                continue;
            }
            if (lineInTermList.getKey().charAt(0) == 'm' || lineInTermList.getKey().charAt(0) == 'M') {
                insertToDictionary(lineInTermList.getKey(), lineInTermList.getValue(), postPath + "m.txt");
                writer_m_post.append(fullString.toString() + "\n");
                continue;
            }

            if (lineInTermList.getKey().charAt(0) == 'n' || lineInTermList.getKey().charAt(0) == 'N'
                    || lineInTermList.getKey().charAt(0) == 'o' || lineInTermList.getKey().charAt(0) == 'O' ) {
                insertToDictionary(lineInTermList.getKey(), lineInTermList.getValue(), postPath + "n_o.txt");
                writer_n_o_post.append(fullString.toString() + "\n");
                continue;
            }
            if (lineInTermList.getKey().charAt(0) == 'p' || lineInTermList.getKey().charAt(0) == 'P') {
                insertToDictionary(lineInTermList.getKey(), lineInTermList.getValue(), postPath + "p.txt");
                writer_p_post.append(fullString.toString() + "\n");
                continue;
            }
            if (lineInTermList.getKey().charAt(0) == 'q' || lineInTermList.getKey().charAt(0) == 'Q'
                    || lineInTermList.getKey().charAt(0) == 'r' || lineInTermList.getKey().charAt(0) == 'R') {
                insertToDictionary(lineInTermList.getKey(), lineInTermList.getValue(), postPath + "q_r.txt");
                writer_q_r_post.append(fullString.toString() + "\n");
                continue;
            }
            if (lineInTermList.getKey().charAt(0) == 's' || lineInTermList.getKey().charAt(0) == 'S') {
                insertToDictionary(lineInTermList.getKey(), lineInTermList.getValue(), postPath + "s.txt");
                writer_s_post.append(fullString.toString() + "\n");
                continue;
            }
            if (lineInTermList.getKey().charAt(0) == 't' || lineInTermList.getKey().charAt(0) == 'T') {
                insertToDictionary(lineInTermList.getKey(), lineInTermList.getValue(), postPath + "t.txt");
                writer_t_post.append(fullString.toString() + "\n");
                continue;
            }
            if (lineInTermList.getKey().charAt(0) == 'u' || lineInTermList.getKey().charAt(0) == 'U' || lineInTermList.getKey().charAt(0) == 'v'
                    || lineInTermList.getKey().charAt(0) == 'V' || lineInTermList.getKey().charAt(0) == 'w' || lineInTermList.getKey().charAt(0) == 'W'
                    || lineInTermList.getKey().charAt(0) == 'x' || lineInTermList.getKey().charAt(0) == 'X' || lineInTermList.getKey().charAt(0) == 'y'
                    || lineInTermList.getKey().charAt(0) == 'Y' || lineInTermList.getKey().charAt(0) == 'z' || lineInTermList.getKey().charAt(0) == 'Z') {
                insertToDictionary(lineInTermList.getKey(), lineInTermList.getValue(), postPath + "u_z.txt");
                writer_u_z_post.append(fullString.toString() + "\n");
                continue;
            }
            if(lineInTermList.getKey().contains("Dollars") || lineInTermList.getKey().contains("dollars") ||lineInTermList.getKey().contains("dollar") ) {
                insertToDictionary(lineInTermList.getKey(), lineInTermList.getValue(), postPath + "prices.txt");
                writer_prices_post.append(fullString.toString() + "\n");
                continue;
            }
            if(lineInTermList.getKey().contains("%")) {
                insertToDictionary(lineInTermList.getKey(), lineInTermList.getValue(), postPath + "percents.txt");
                writer_percents_post.append(fullString.toString() + "\n");
                continue;
            }
            if(Character.isDigit(lineInTermList.getKey().charAt(0))) {
                insertToDictionary(lineInTermList.getKey(), lineInTermList.getValue(), postPath + "numbers.txt");
                writer_numbers_post.append(fullString.toString() + "\n");
                continue;
            }
        }
        writer_a_post.flush();
        writer_b_post.flush();
        writer_c_post.flush();
        writer_d_post.flush();
        writer_e_f_post.flush();
        writer_g_h_post.flush();
        writer_i_k_post.flush();
        writer_l_post.flush();
        writer_m_post.flush();

        writer_n_o_post.flush();
        writer_p_post.flush();
        writer_q_r_post.flush();
        writer_s_post.flush();
        writer_t_post.flush();
        writer_u_z_post.flush();
        writer_prices_post.flush();
        writer_percents_post.flush();
        writer_numbers_post.flush();
        writer_a_post.close();
        writer_b_post.close();
        writer_c_post.close();
        writer_d_post.close();
        writer_e_f_post.close();
        writer_g_h_post.close();
        writer_i_k_post.close();
        writer_l_post.close();
        writer_m_post.close();

        writer_n_o_post.close();
        writer_p_post.close();
        writer_q_r_post.close();
        writer_s_post.close();
        writer_t_post.close();
        writer_u_z_post.close();
        writer_prices_post.close();
        writer_percents_post.close();
        writer_numbers_post.close();
    }

  //////////////////////////////////////////////////////////////////////

    /**
     * this function indexing the entities in the corpus in entities posting file
     * every several reading iteration we write to posting file
     * in this function we check if there sre at least 2 docs that contain this term
     * ---if yes, this therm is entities and we insert the term in entities file
     * @param entityList- list of the entities of the corpus
     * @param tempPostPath
     * @param stem
     * @throws IOException
     */
    public void indexingEntities(HashMap<String, Term> entityList, String tempPostPath, boolean stem, HashMap<String, Doc> docList) throws IOException {

        BufferedWriter writer_entities_post = new BufferedWriter(new FileWriter(tempPostPath + "entities_names.txt", true));

        String postPath = "//posting//";
        if(stem)
            postPath = "//stemmer//Stemmer_";

        for (HashMap.Entry<String, Term> lineInEntityList : entityList.entrySet()) {
            StringBuilder fullString = termInfo(lineInEntityList.getKey(), lineInEntityList.getValue());
            if(lineInEntityList.getValue().getDocs().size()>2){
                insertToDictionary(lineInEntityList.getKey(), lineInEntityList.getValue(), postPath + "entities_names.txt");
                writer_entities_post.append(fullString.toString() + "\n");

                for (String docID: lineInEntityList.getValue().getDocs().keySet()) {
                    if(docList.containsKey(docID)){
                        if( docList.get(docID). getDocTerms().containsKey(lineInEntityList.getKey())){
                            docList.get(docID).addToTopFiveEntites(docList.get(docID). getDocTerms().get(lineInEntityList.getKey()), lineInEntityList.getKey());

                          //  docList.get(docID).getDocEntites().put(docList.get(docID). getDocTerms().get(lineInEntityList.getKey()), lineInEntityList.getKey());

                        }

                    }
                }

                /////////////////////////////////////////////////////////////////////////////
            }


        }

        writer_entities_post.flush();
        writer_entities_post.close();

        for (String docId: docList.keySet() ) {
            docList.get(docId).clearDocTerm();
        }
    }


    ///////////////////////////

    /**
     * This function insert the terms to the dictionary, before the insert it check how to save the term in the dictionary(upperCase or lowerCase).
     * the way to save this term is depend in how the term is saved before.
     * @param termName
     * @param term
     * @param postPath
     */
    private void insertToDictionary(String termName, Term term, String postPath) {
        int numberOfDocs = term.getDocs().size();
        int numberOfThisTermInCorpus = 0;
        String addToDic="";
        //counting the number of this term in the corpus
        for (HashMap.Entry<String, Integer> doc : term.getDocs().entrySet()) {
            numberOfThisTermInCorpus = numberOfThisTermInCorpus + doc.getValue();
        }
        //the term is already in the dictionary
        //lower letters
        if(dictionary.containsKey(termName.toLowerCase()) && !isUpperLetters(termName)){
            String[] temp = dictionary.get(termName.toLowerCase()).getValue().split(",");
            int doc = Integer.parseInt(temp[0]) + numberOfDocs;
            int corpus = Integer.parseInt(temp[1]) + numberOfThisTermInCorpus;
            addToDic = doc + "," + corpus ;
            Pair<String, String> details = new Pair<>(postPath, addToDic);
            dictionary.put(termName, details);
            return;
        }
        //the term is in upper letters but the match term is in lower letter
        else if(dictionary.containsKey(termName.toLowerCase()) && isUpperLetters(termName)){
            String[] temp = dictionary.get(termName.toLowerCase()).getValue().split(",");
            int doc = Integer.parseInt(temp[0]) + numberOfDocs;
            int corpus = Integer.parseInt(temp[1]) + numberOfThisTermInCorpus;
            addToDic = doc + "," + corpus;
            Pair<String, String> details = new Pair<>(postPath, addToDic);
            dictionary.put(termName.toLowerCase(), details);
            return;
        }
        //upper letter
        else if(dictionary.containsKey(termName.toUpperCase()) && isUpperLetters(termName)){
            String[] temp = dictionary.get(termName.toUpperCase()).getValue().split(",");
            int doc = Integer.parseInt(temp[0]) + numberOfDocs;
            int corpus = Integer.parseInt(temp[1]) + numberOfThisTermInCorpus;
            addToDic = doc + "," + corpus ;
            Pair<String, String> details = new Pair<>(postPath, addToDic);
            dictionary.put(termName, details);

            return;
        }
        //the term is in lowwer letters but the match term is in upper letter
        else if(dictionary.containsKey(termName.toUpperCase()) && !isUpperLetters(termName)){
            String[] temp = dictionary.get(termName.toUpperCase()).getValue().split(",");
            int doc = Integer.parseInt(temp[0]) + numberOfDocs;
            int corpus = Integer.parseInt(temp[1]) + numberOfThisTermInCorpus;
            addToDic = doc + "," + corpus ;
            Pair<String, String> details = new Pair<>(postPath, addToDic);
            dictionary.put(termName.toLowerCase(), details);
            dictionary.remove((termName.toUpperCase()));
            return;
        }
        //adding new term to the dictionary
        else {
            addToDic = numberOfDocs + "," + numberOfThisTermInCorpus;
            Pair<String, String> details = new Pair<>(postPath, addToDic);
            dictionary.put(termName, details);
        }
    }



    /**
     * This function is put in one string all the term's information
     * it chaining the nameTerm = {Doc_key | the value of the doc in the termdDocs, ..}....
     * @param name - the term name
     * @param term - the term that contain the docs and the frequency of the term in the doc
     * @return
     */
    private StringBuilder termInfo(String name, Term term) {
        StringBuilder temp = new StringBuilder("");
        temp.append(name + "=");
        for (HashMap.Entry<String, Integer> doc : term.getDocs().entrySet()) {
            temp.append(doc.getKey() + "|" + doc.getValue() + ",");
        }
        return temp;
    }


    /**
     * This function checks if the word is in capital letters
     * @param term
     * @return
     */
    private boolean isUpperLetters(String term){
        boolean flag = true;
        for(int i=0 ; i<term.length(); i++){
            if(( Character.isDigit(term.charAt(i)) || Character.isUpperCase(term.charAt(i))) && flag){
                flag= true;
            }
            else {
                return false;
            }
        }
        return true;
    }



    /**
     * This function get a path of one post file and then it Read and return the file content in single string
     * htis function is for use for the merge of the posting files.
     * @param path of the posting file
     * @return string that contain the posting file content
     * @throws FileNotFoundException
     */
    private StringBuilder readOneFile(String path) throws FileNotFoundException {
        File pathFile = new File(path);
        FileReader fileReader = new FileReader(pathFile);
        StringBuilder ans = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String textLine;
            while ((textLine = bufferedReader.readLine()) != null) {
                ans.append(textLine + "\n");
            }
            fileReader.close();
        } catch (IOException e) {
           // e.printStackTrace();
        }
        return ans;
    }



    /**
     * This function Combines postings files to one posting file
     * foreach letter this function is called and merge all the posting file of this letter into one file.
     * this function merge each 2 posting file into one file and etc. until all the posting files foe each
     * letter merge into one posting file foe the letter
     * @param path
     * @param letter - the letter that we merge its posting files
     * @param postingCount - is the count of the posting file that created when we read the corpus
     * @param stemmer
     * @throws IOException
     */
    public void lastPosting(String path, String letter, int postingCount, boolean stemmer) throws IOException {


        int j = 0;
        long pointer = 0;
        String termName = "";
        int numberOfPosting = postingCount;
        int units = postingCount / 2;
        StringBuilder text = new StringBuilder();
        if (postingCount % 2 != 0) {
            units++;
        }
        StringBuilder[] letterText = new StringBuilder[units];
        for (int i = 1; i <= postingCount-1 ; i = i + 2) {
            text = merge(path + i + letter + ".txt", path + (i + 1) + letter + ".txt");
            letterText[j] = text;
            text = new StringBuilder();

            j++;
        }
        if (postingCount % 2 != 0) {
            letterText[j] = readOneFile(path + postingCount + letter + ".txt");
        }
        j = 0;
        postingCount = units;
        while (units > 1) {
            for (int i = 0; i < units - 1; i = i + 2) {
                text.append(letterText[i]);
                text.append(letterText[i + 1]);
                letterText[j] = unite(text);
                text = new StringBuilder();
                j++;
            }
            if (units == 2) {
                letterText[1] = new StringBuilder();
                break;
            }
            units = units / 2;
            if (postingCount % 2 != 0) {
                letterText[j] = new StringBuilder(letterText[postingCount - 1]);
                units++;
                j++;
            }
            for (int i = j; i < letterText.length && i != 0; i++) {
                if (letterText[i].length() != 0) {
                    letterText[i] = new StringBuilder();
                }
                else
                    break;
            }
            j = 0;
            postingCount = units;
        }
        String[] termInfo = letterText[0].toString().split("\n");
        letterText[0] = new StringBuilder();
        StringBuilder ans = new StringBuilder("");
       // BufferedWriter lastWriteToPostFile = new BufferedWriter(new FileWriter( path + letter+ ".txt", true));
        for (int i = 0; i < termInfo.length; i++) {
            int deleteName = termInfo[i].indexOf('=');
            if(deleteName > 0) {
                termName = termInfo[i].substring(0, deleteName);
            }
            else
                continue;
            termInfo[i] = termInfo[i].substring(deleteName + 1);
            try {
                String place = dictionary.get(termName).getKey() + "#" + pointer;
                Pair pair = new Pair(place, dictionary.get(termName).getValue());
                dictionary.put(termName, pair);

            }
            catch (Exception e){
//                System.out.println(termName );
//                e.printStackTrace();
            }
            ans.append(termInfo[i] + "\n");
            //our pointer is the number of bytes that the term is writes from the beginning of the file
            pointer = pointer+ (termInfo[i]).getBytes().length+1;
            termInfo[i] = "";
        }
        FileOutputStream newdoc = new FileOutputStream(path + letter + ".txt");
        OutputStreamWriter outputStream = new OutputStreamWriter(newdoc);
        outputStream.write(ans.toString());
        newdoc.flush();
        outputStream.flush();
        newdoc.close();

        outputStream.close();
        text = new StringBuilder();
        letterText[0] = new StringBuilder();
        deletePosts(numberOfPosting,path,letter);

    }


    /**
     * This function deleting all temporary postings files
     * after the temporary posting files merge into one correct posting file this function is called and delete
     * the temporary posting files for the given letter.
     * @param num
     * @param path
     * @param letter
     */
    private void deletePosts(int num,String path, String letter) {
        for (int i = 1; i <= num; i++) {
            Path temt = Paths.get(path + i + letter + ".txt");
            try {
                Files.delete(temt);
            } catch (Exception e) {
               // e.printStackTrace();
            }
        }
    }


    /**
     * This function merging two text to one
     * insert two posting file and get their text and then chaining the text into one string
     * after the chaining this function return merge text for the two files
     * @param path1 - path of one posting file
     * @param path2 - path of other posting file of the same letter
     * @return merge text for the two files
     * @throws IOException
     */
    private StringBuilder merge(String path1, String path2) throws IOException {
        StringBuilder textA = readOneFile(path1);
        StringBuilder textB = readOneFile(path2);
        StringBuilder mergeText = new StringBuilder();
        mergeText.append(textA.toString() + textB.toString());
        return unite(mergeText);
    }


    /**
     * This function Read 2 files and return the united string with no duplicates.
     * It called by the merge function which send one string of mergeText
     * for each line in the mergeText we insert into hashMap of termList in the correct format, in this way we check that the there are no duplicates in the terms.
     * after that we insert the unDuplicate text into String that called unitedText and contain the merged and correct terms & values in our format
     * @param mergeText
     * @return
     * @throws IOException
     */
    private StringBuilder unite(StringBuilder mergeText) throws IOException {
        termList.clear();
        uniteText = new StringBuilder();
        uniteText.trimToSize();
        if(mergeText.length() < 1)
            return uniteText.append("");
        String[] textLines = mergeText.toString().split("\n");
        String temp = "";
        try {
            for (int i = 0; i < textLines.length; i++) {
                String[] term = textLines[i].split("=");
                if (termList.containsKey(term[0].toLowerCase()) && !isUpperLetters(term[0])) {
                    termList.put(term[0], termList.get(term[0].toLowerCase()) + term[1]);
                    continue;
                } else if (termList.containsKey(term[0].toLowerCase()) && isUpperLetters(term[0])) {
                    termList.put(term[0].toLowerCase(), termList.get(term[0].toLowerCase()) + term[1]);
                    continue;
                } else if (termList.containsKey(term[0].toUpperCase()) && !isUpperLetters(term[0])) {
                    termList.put(term[0].toLowerCase(), termList.get(term[0].toUpperCase()) + term[1]);
                    termList.remove(term[0].toUpperCase());
                    continue;
                } else if (termList.containsKey(term[0].toUpperCase()) && isUpperLetters(term[0])) {
                    termList.put(term[0], termList.get(term[0].toUpperCase()) + term[1]);
                    continue;
                } else if (termList.containsKey(term[0])){
                    termList.put(term[0], termList.get(term[0]) + term[1]);
                    continue;
                }
                else {
                    termList.put(term[0],term[1]);
                    continue;
                }
            }
            for (HashMap.Entry<String, String> entry : termList.entrySet()) {
                uniteText.append(entry.getKey() + "=" + entry.getValue() + "\n");
            }
            termList.clear();
        } catch (Exception e) {
          //  e.printStackTrace();
        }
        return uniteText;
    }

}
