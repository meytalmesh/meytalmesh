package Model;

import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.englishStemmer;
/**
 * This class present the stermming algoritem.
 */
public class Stemmer {

    SnowballStemmer stemmer;

    public Stemmer (){
        stemmer = new englishStemmer();
    }

    public String stemming (String term){
        stemmer.setCurrent(term);
        if (stemmer.stem()){
            return stemmer.getCurrent();
        }
        return term;
    }


    public SnowballStemmer getStemmer(){
        return stemmer;
    }
}
