package View;

import Model.Searcher;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;
/**
 * this class present the controller for the view if the documents show,
 * init in the controller of the open scene view and open when the user search single query.
 * it show 50 relevant documents by them rank for the query
 */
public class DocumentsShow {
    public Searcher search;
    public ListView<String> docsView;


    /**
     * init of this view controller --> get the searcher
     * from that it get the fifty sorted dictionary and foreach term in this fifty sorted dictionary add to the documentView the terms
     * this documentView is list view that show all the documents that  relevant.
     */
    public void initialize(){
        search = Controller.getSearcher();
        List<String> sortedDocs = search.getFiftySortedDocs();
        int counter =1;
        for (String doc: sortedDocs) {
            docsView.getItems().add(counter + ". " + doc + System.lineSeparator());
            counter++;
        }
    }

}
