package View;

import Model.ReadFile;
import View.Controller;
import javafx.scene.control.ListView;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * this class present the controller for the view if the Dictionary show,
 * init in the controller of the open scene view and open when the user click on the btn "show Dictionary"
 */
public class DictionaryShow {

    public ReadFile rf;
    public ListView<String> dictionaryView;

    /**
     * init of this view controller --> get the readFile
     * from that it get the sorted dictionary and foreach term in this sorted Dictionary add to the dictionaryView the terms and their frequency
     * this dictionaryView is list view that shoe all the terms in the dictionary.
     */
    public void initialize(){

        rf = Controller.getRf();
        ArrayList<String> sorted = rf.getSortedSictionary();
        for (String termInDic: sorted) {
            dictionaryView.getItems().add(termInDic + System.lineSeparator());

        }

    }
}
