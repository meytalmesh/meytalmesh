package View;

import Model.ReadFile;
import Model.Searcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

/**
 * This class is responsible to connect  the classes in the model with the view and all the function in it
 */
public class Controller implements Initializable {
    public TextField corpusPathText;
    public TextField postingPathText;
    public Button corpusPath_btn;
    public Button postingPath_btn;
    public CheckBox stemming_btn;
    public Button reset_btn;
    public Button showDictionary_btn;
    public Button loadDictionary_btn;
    public Button start_btn;

    public ChoiceBox docs_box;
    public TextField resultPathText;
    public Button showEntities_btn;
    public CheckBox semantic_btn;
    public TextField queryText;
    public Button queryPath_btn;
    public TextField queryPathText;
    public Button run_btn;
    public Button resultPath_btn;
    public Button save_btn;
    public CheckBox onLine_btn;

    private String corpusPath;
    private String postingPath;
    private boolean isStemming;
    private String totalTime;
    private static ReadFile rf;
    private static Searcher searcher;
    private boolean semantic;
    private boolean isOnline;
    private String resultPath;
    private String queryPath;
    private String query;


    /**
     * Initializes the start screen
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        corpusPath="";
        postingPath="";
        resultPath="";
        queryPath="";
        query="";
        loadDictionary_btn.setDisable(false);
        showDictionary_btn.setDisable(true);
        reset_btn.setDisable(false);
        stemming_btn.setSelected(false);
        semantic_btn.setSelected(false);
        showEntities_btn.setDisable(true);
        save_btn.setDisable(true);
        run_btn.setDisable(true);
        onLine_btn.setDisable(true);
    }

    /**
     * Run the program after pressing the start button,
     * call the readfile and count the time before and after the run.
     * At the end of the session, we will display a message with the amount of documents that are indexed,
     * the unique terms identified in the repository, the total run time of the process in seconds,
     * reading documents, parsing and indexing of the entire repository.
     * @param actionEvent
     *
     */
    public void start(ActionEvent actionEvent) throws Exception {

        corpusPath = corpusPathText.getText();
        postingPath = postingPathText.getText();
        if(stemming_btn.isSelected()){
            isStemming=true;
        }
        else
            isStemming= false;

        if(!corpusPath.equals("") && !postingPath.equals("") && Files.exists(Paths.get(corpusPath))&& Files.exists(Paths.get(postingPath))){
            if( Files.exists(Paths.get(corpusPath + "/stop_words.txt"))){
                rf = new ReadFile(corpusPath, postingPath +"\\", isStemming);
                long startTime = System.nanoTime();
                rf.readCorpus();
                long endTime = System.nanoTime();
                totalTime = ((endTime - startTime)/1000000000 ) +"";


                int docSize =rf.getDocSize();
                int termsSize = rf.getTermsSize();

                alert("Number of Documents : "+ docSize +"\nNumber of different terms : " +termsSize +"\nTotal Time : " + totalTime+ "second");


                reset_btn.setDisable(false);
                showDictionary_btn.setDisable(false);
               // run_btn.setDisable(false);
                //loadDictionary_btn.setDisable(false);
            }
            else{
                alertWArning("Enter \"stop_words.txt\" File in the Corpus");
            }
        }
        else {
            if(corpusPath.equals("")||!Files.exists(Paths.get(corpusPath))){
                alertWArning("You Must Fill Corpus Path!");
            }
            if(postingPath.equals("") ||!Files.exists(Paths.get(postingPath))){
                alertWArning("You Must Fill Posting Path!");
            }
        }
    }

    /**
     * When you press the Insert Corpus button, this function activates and opens a window for selecting a folder.
     * When the path is selected, the method updates the path in the appropriate field.
     * @param actionEvent
     *
     */
    public void insertCorpusPath(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = new Stage();
        File userDirOfCorpus = directoryChooser.showDialog(stage);
        if(userDirOfCorpus!= null){
            corpusPathText.setText(userDirOfCorpus.getPath());
        }
    }


    /**
     * When you press the Insert post button, this function activates and opens a window for selecting a folder.
     * When the path is selected, the method updates the path in the appropriate field.
     * @param actionEvent
     *
     */
    public void insertPostingPath(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = new Stage();
        File userDirOfPost = directoryChooser.showDialog(stage);
        if(userDirOfPost!= null){
            postingPathText.setText(userDirOfPost.getPath());
        }
    }

    public void insertQueryPath(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = new Stage();
        File userDirOfCorpus = directoryChooser.showDialog(stage);
        if(userDirOfCorpus!= null){
            queryPathText.setText(userDirOfCorpus.getPath());
        }
    }

    public void insertResultPath(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = new Stage();
        File userDirOfCorpus = directoryChooser.showDialog(stage);
        if(userDirOfCorpus!= null){
            resultPathText.setText(userDirOfCorpus.getPath());
        }
    }

    /**
     * When you press the reset method, you will initialize the main memory and delete all the saved
     * posting and dictionary files.
     *
     */
    public void reset() throws Exception {
        if(!postingPath.equals("") && Files.exists(Paths.get(postingPath))){
            if(Files.exists(Paths.get(postingPath+"\\posting")) || Files.exists(Paths.get(postingPath+"\\stemmer"))){
                if(Files.exists(Paths.get(postingPath+"\\posting"))){
                    FileUtils.deleteDirectory(new File(postingPath+"\\posting"));
                }
                if(Files.exists(Paths.get(postingPath+"\\stemmer"))){
                    FileUtils.deleteDirectory(new File(postingPath+"\\stemmer"));
                }

                showDictionary_btn.setDisable(true);
                if(rf!=null)
                    rf.resetReadFile();
                alert("finished to reset");

            }else{
                alertWArning("There isn't Posting Files in This Path");
            }


        }else{
            alertWArning("You Must Enter Posting File to reset");
        }

    }


    public void alert(String alertMessage) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText(alertMessage);
        a.show(); // show the dialog
    }


    public void alertWArning(String alertMessage) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setContentText(alertMessage);
        a.show(); // show the dialog
    }
    //getter
    public static ReadFile getRf(){
        return rf;
    }

    public static Searcher getSearcher(){
        return searcher;
    }

    /**
     * When you click the show dictionary button,
     * the method will display a window displaying the sorted dictionary in ascending form.
     */
    public void showDic(ActionEvent actionEvent) {

        try{
            if(rf.getSortedSictionary()!=null ){
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Parent root = loader.load(getClass().getClassLoader().getResource("DictionaryShow.fxml"));
                stage.setTitle("Our Dictionary");
                stage.setScene(new Scene(root, 546, 487));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();

            }




        }
        catch (Exception e){
         //   e.printStackTrace();
        }
    }

    /**
     * load the dictionary
     * @param actionEvent
     * @throws Exception
     */
    public void loadDic(ActionEvent actionEvent) throws Exception {

        String dictionary_path="";
        String docs_path="";

        corpusPath = corpusPathText.getText();
        postingPath = postingPathText.getText();
        if(stemming_btn.isSelected()){
            isStemming=true;
        }
        else
            isStemming= false;


        if(!postingPathText.getText().equals("") && Files.exists(Paths.get(postingPathText.getText()))){
            rf = new ReadFile(corpusPath, postingPath +"\\", isStemming);
            if(stemming_btn.isSelected()){
                isStemming=true;
                postingPath+="\\stemmer\\Stemmer_";
            }
            else{
                isStemming= false;
                postingPath+="\\posting\\";
            }

            dictionary_path= postingPath+"dictionary.txt";
            docs_path =  postingPath+ "docs.txt";


            if(!dictionary_path.equals("") && Files.exists(Paths.get(dictionary_path))){
                //load Dictionary
                FileInputStream fis_dic_path = new FileInputStream(dictionary_path);
                ObjectInputStream ois_dic_path = new ObjectInputStream(fis_dic_path);
                HashMap<String, Pair<String, String>> aa = (HashMap<String, Pair<String, String>>)ois_dic_path.readObject();

                rf.getIndexer().setDictionary(aa);
                fis_dic_path.close();
                ois_dic_path.close();

                //load sorted dict
                FileInputStream fis_sorted_Dict_path = new FileInputStream(dictionary_path);
                ObjectInputStream ois_sorted_Dict_path = new ObjectInputStream(fis_sorted_Dict_path);
                rf.setSortedSictionary((HashMap<String, Pair<String, String>>)ois_sorted_Dict_path.readObject());
                fis_dic_path.close();
                ois_dic_path.close();
                alert("The Dictionary Load Successfully!");
                showDictionary_btn.setDisable(false);
                //run_btn.setDisable(false);
            }
            else{
                alertWArning("There is NOT Dictionary in the Posting File");
            }
            if(!docs_path.equals("") && Files.exists(Paths.get(docs_path))){
                //load docs.txt file to docsList
                List<String> lines = new ArrayList<>();
                BufferedReader br = new BufferedReader(new FileReader(docs_path));
                String line = br.readLine();
                while (line != null){
                    lines.add(line);
                    line = br.readLine();
                }
                rf.loadDocsList(lines);
                run_btn.setDisable(false);
            }
            else {
                alertWArning("There is NOT \"docs.txt\" file in the Posting Directory");
            }
        }
        else {
            alertWArning("You Must Enter Correct Posting Path!");
        }
    }

    /**
     * this finction add doc to option -> doc that open show its top 5 eneites
     * @param returnDocs
     */
    public void docsOptions(List<String> returnDocs){

        docs_box.getSelectionModel().clearSelection();
        docs_box.getItems().clear();

        for (String doc : returnDocs){
            docs_box.getItems().add( doc);
        }
    }

    /**
     * this function run the queries
     * @param actionEvent
     * @throws IOException
     */
    public void run(ActionEvent actionEvent) throws IOException {
        queryPath = queryPathText.getText();
        query = queryText.getText();
        if (stemming_btn.isSelected()){
            isStemming = true;
        }else {
            isStemming = false;
        }

        if(semantic_btn.isSelected()){
            semantic=true;
        }
        else {
            semantic = false;
        }
        if(onLine_btn.isSelected()){
            isOnline=true;
        }
        else {
            isOnline = false;
        }
        if(queryPath.equals("") && query.equals("")) {
            alertWArning("You Must Write a Query Or Choose a Queries Path!");
            return;
        }

        if(!queryPath.equals("") && !query.equals("")){
            alertWArning("You Can not Search Both a Single Query And a Query file. Please Choose Only One Of Them");
            return;
        }
        if(rf.stopWords.size()==0){
            loadStopWords();
        }

        //run for single query
        if(!query.equals("")) {
            searcher = new Searcher(rf.getIndexer().getDictionary(), rf.getStopWords(), rf.getDocsList(), postingPath, isStemming);
            searcher.search(query, semantic ,isOnline, isStemming,  Searcher.QUERY_STATE.singleQuery);
            showDocs();
            docsOptions(searcher.getFiftySortedDocs());
        }
        //run for query file
        else {

            //File file = new File("src/main/resources/results.txt");
            String path_res = postingPath;
            path_res = path_res.substring(0, path_res.length()-8);
            if(isStemming){
                path_res = path_res.substring(0, path_res.length()-8);

            }


            if(!path_res.equals("") && Files.exists(Paths.get(path_res))&& Files.exists(Paths.get(path_res+ "/results.txt"))){
                BufferedWriter bw = Files.newBufferedWriter(Paths.get(path_res+ "/results.txt"));
                bw.write("");
                bw.flush();
                bw.close();
            }

            //File file = new File(path_res+ "results.txt");


            //File file = new File(getClass().getResource("results.txt").getFile());

            //file.delete();

            searcher = new Searcher(rf.getIndexer().getDictionary(), rf.getStopWords(), rf.getDocsList(), postingPath, isStemming);
            // if(QueriesPath + "/queries.txt")
            if(!queryPath.equals("") && Files.exists(Paths.get(queryPath))&& Files.exists(Paths.get(queryPath + "/queries.txt"))){
                searcher.readQueries(queryPath,isStemming, semantic, isOnline);
                searcher.writeToQueryResultsFile(searcher.getListOfAllQueryResults(),path_res);

                //File f = new File("src/main/resources/results.txt");
                File f = new File(path_res+ "/results.txt");

                Desktop.getDesktop().open(f);

                docsOptions(new ArrayList<String>(searcher.getAllDocForQueriesFile()));
                save_btn.setDisable(true);
            }
            else{
                alertWArning("There isn't queries.txt File in this path");
            }

        }

        resultPath_btn.setDisable(false);
        save_btn.setDisable(false);
        showEntities_btn.setDisable(false);
    }

    /**
     * this function load the stop words
     */
    private void loadStopWords() {
        try{
//            String filename = "src/main/resources/stop_words.txt";
//            File file = new File(filename);
            String path_stop = postingPath;
            path_stop= path_stop.substring(0, path_stop.length()-8);
            if(isStemming){
                path_stop= path_stop.substring(0, path_stop.length()-8);

            }
            rf.readStopWords(path_stop+ "/stop_words.txt");
        }
         catch (IOException e) {
          //  e.printStackTrace();
        }


    }

    /**
     * this finction open new scene with top rop 50 documrnts
     */
    public void showDocs() {
        try{
            if(searcher.getFiftySortedDocs()!=null ){
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Parent root = loader.load(getClass().getClassLoader().getResource("DocumentsShow.fxml"));
                stage.setTitle("Relevant Documents");
                stage.setScene(new Scene(root, 550, 480));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            }
        }
        catch (Exception e){
            //e.printStackTrace();
        }

    }

    /**
     * shoe the entites for the chosen doc
     */
    public void showEntites(){
        if(docs_box.getSelectionModel().getSelectedItem() != null){
            String currentDoc = docs_box.getSelectionModel().getSelectedItem().toString();

            String docEntitesSTR = "";
            if(rf.getDocsList().containsKey(currentDoc)){
                TreeMap<Integer, String> docEntites = rf.getDocsList().get(currentDoc).getDocEntites();

                for ( Map.Entry<Integer, String> entity: docEntites.entrySet() ) {
                    docEntitesSTR = docEntitesSTR+ entity.getValue() + " - rank: "+ entity.getKey() + "\n";
                }

                alert(docEntitesSTR);

            }
        }
        else{
            alertWArning("You Must Select Document First");

        }
    }

    /**
     * if the user choose semantic
     * @param actionEvent
     */
    public void chooseSemantic(ActionEvent actionEvent) {
        if(semantic_btn.isSelected()){
            onLine_btn.setDisable(false);
        }
        else {
            onLine_btn.setSelected(false);
            onLine_btn.setDisable(true);
        }
    }

    /**
     * save this results file
     * @param actionEvent
     */
    public void saveing(ActionEvent actionEvent) {
        if(queryText.getText().equals("") && queryPathText.getText().equals("")) {
            alertWArning("you must write one query or choose a queries file to save the results");
            return;
        }
        String resultPath = resultPathText.getText();
        if(resultPath.equals("")){
            alertWArning("please enter a path to the place you want to save the results");
            return;
        }


        searcher.writeToQueryResultsFile(searcher.getListOfAllQueryResults(),resultPath);


        alert("the results saved");
    }
}