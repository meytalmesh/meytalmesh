
package Model;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Pattern;


/**
 * This class is the responsible for the parsing of the docs's text in the corpus.
 * the fields of this class:
 * --> isStemming - boolean var that we use it to check if we need to send the term to the stemmer
 * --> dates - hashMap that contain the months in some variation and the value of the month is the number of the month, we wanjt to replace the month in this number
 * --> replaceTerms - hashMap of some terms that we replace in their values.
 * --> felimiters -  hash set of delimiters
 * --> numSize - hashSet of sizes
 * --> termList - hashMap of the terms
 * --> entitiesList - hashMap of the entities
 * -->stemmer -  our Stemmer, if we need to use it
 *
 * in this class we check every terms how we need to save it.
 */
public class Parse {


    private boolean isStemming;
    private HashMap<String, String> dates;
    private HashMap<String, String> replaceTerms;
    private HashSet<Character> delimiters;
    private HashSet<String> numSize;
    private HashMap<String, String> units;

    private HashMap<String, Term> termList;
    private HashMap<String, Term> entityList;
    private Stemmer stemmer;
    private static DecimalFormat df3;


    /**
     * constuctor of the parse
     * init all the hashSets and hashMaps and fill them.
     */
    public Parse() {

        replaceTerms = new HashMap<String, String>();
        dates = new HashMap<String, String>();
        delimiters = new HashSet<Character>();
        numSize = new HashSet<String>();
        termList = new HashMap<>();
        entityList = new HashMap<>();
        stemmer = new Stemmer();
        units = new HashMap<>();
        df3 = new DecimalFormat("#.###");


        insertDates();
        insertDelimiters();
        initReplaceTerms();
        insertNumSize();
        fillUnits();
    }

    /**
     * called by the constuctor
     * insert this delimiters in the hashSet
     */
    private void insertDelimiters() {
        delimiters.add('.');
        delimiters.add(',');
        delimiters.add(':');
        delimiters.add(';');
        delimiters.add('!');
        delimiters.add('?');
        delimiters.add('(');
        delimiters.add(')');
        delimiters.add('[');
        delimiters.add(']');
        delimiters.add('|');
        delimiters.add('\"');
        delimiters.add('\'');
        delimiters.add('\n');
        delimiters.add('*');
        delimiters.add('-');
        //delimiters.add('/');
        delimiters.add(' ');
        delimiters.add('+');
    }

    /**
     * called by the constuctor
     * insert the months and their values to the hashMap
     */
    private void insertDates() {
        dates.put("January", "01");
        dates.put("February", "02");
        dates.put("February", "02");
        dates.put("March", "03");
        dates.put("April", "04");
        dates.put("May", "05");
        dates.put("June", "06");
        dates.put("July", "07");
        dates.put("August", "08");
        dates.put("September", "09");
        dates.put("October", "10");
        dates.put("November", "11");
        dates.put("December", "12");

        dates.put("JANUARY", "01");
        dates.put("FEBRUARY", "02");
        dates.put("MARCH", "03");
        dates.put("APRIL", "04");
        dates.put("MAY", "05");
        dates.put("JUNE", "06");
        dates.put("JULY", "07");
        dates.put("AUGUST", "08");
        dates.put("SEPTEMBER", "09");
        dates.put("OCTOBER", "10");
        dates.put("NOVEMBER", "11");
        dates.put("DECEMBER", "12");

        dates.put("JAN", "01");
        dates.put("FEB", "02");
        dates.put("MAR", "03");
        dates.put("APR", "04");
        dates.put("MAY", "05");
        dates.put("JUN", "06");
        dates.put("JUL", "07");
        dates.put("AUG", "08");
        dates.put("SEP", "09");
        dates.put("OCT", "10");
        dates.put("NOV", "11");
        dates.put("DEC", "12");

        dates.put("Jan", "01");
        dates.put("Feb", "02");
        dates.put("Mar", "03");
        dates.put("Apr", "04");
        dates.put("May", "05");
        dates.put("Jun", "06");
        dates.put("Jul", "07");
        dates.put("Aug", "08");
        dates.put("Sep", "09");
        dates.put("Oct", "10");
        dates.put("Nov", "11");
        dates.put("Dec", "12");
    }

    /**
     * called by the constuctor
     * fille the hashSet in this sizes
     */
    private void insertNumSize() {
        numSize.add("Trillion");
        numSize.add("Million");
        numSize.add("Billion");
        numSize.add("Thousand");
        numSize.add("trillion");
        numSize.add("million");
        numSize.add("billion");
        numSize.add("thousand");
    }

    /**
     * called by the constuctor
     * insert this the delimiters that we want to replace them with their values
     */
    private void initReplaceTerms() {
        //replaceTerms.put("th", "");
        replaceTerms.put(",", "");
        replaceTerms.put("/"," ");
        replaceTerms.put("--", " ");
        replaceTerms.put("\"", " ");
        replaceTerms.put("...", " ");
        replaceTerms.put("  ", " ");
        replaceTerms.put("(", " ");
        replaceTerms.put(")", " ");
        replaceTerms.put("[", " ");
        replaceTerms.put("]", " ");
        // replaceTerms.put(": ", " ");
        replaceTerms.put(". \n", " ");
        replaceTerms.put("�", "");
        replaceTerms.put("¥", "");
        replaceTerms.put("£", "");
        replaceTerms.put("@", "");
        replaceTerms.put("Æ", "");
        replaceTerms.put("Ŭ", "");
        replaceTerms.put("⪕", "");
        replaceTerms.put("⪖", "");
        replaceTerms.put("Ω", "");
        replaceTerms.put("°", "");
        replaceTerms.put(" '", "");
        replaceTerms.put("' ", "");
    }


    //Fill in the units of measure dictionary
    private void fillUnits() {
        units.put("Milligram", "mg");
        units.put("Kilogram", "kg");
        units.put("Gram", "g");
        units.put("milligram", "mg");
        units.put("kilogram", "kg");
        units.put("gram", "g");
        units.put("Decimeter", "dc");
        units.put("Centimeter", "cm");
        units.put("Millimeter", "mm");
        units.put("Meter", "m");
        units.put("Kilometer", "km");
        units.put("decimeter", "dc");
        units.put("centimeter", "cm");
        units.put("millimeter", "mm");
        units.put("meter", "m");
        units.put("kilometer", "km");
        units.put("Inch", "in");
        units.put("Foot", "ft");
        units.put("Yard", "yd");
        units.put("Mile", "mile");
        units.put("inch", "in");
        units.put("foot", "ft");
        units.put("mile", "mile");
        units.put("yard", "yd");
        units.put("yards", "yd");
        units.put("Milligrams", "mg");
        units.put("Kilograms", "kg");
        units.put("Grams", "g");
        units.put("milligrams", "mg");
        units.put("kilograms", "kg");
        units.put("grams", "g");
        units.put("Decimeters", "dc");
        units.put("Centimeters", "cm");
        units.put("Millimeters", "mm");
        units.put("Meters", "m");
        units.put("Kilometers", "km");
        units.put("decimeters", "dc");
        units.put("centimeters", "cm");
        units.put("millimeters", "mm");
        units.put("meters", "m");
        units.put("kilometers", "km");
        units.put("Inchs", "in");
        units.put("Foots", "ft");
        units.put("Yards", "yd");
        units.put("Miles", "mile");
        units.put("inchs", "in");
        units.put("foots", "ft");
        units.put("miles", "mile");
    }

    /**
     * this function is the main function in this class
     * it is responsible to the parsing of each terms
     * @param doc
     * @param docText - the text of the doc
     * @param stopWords - hashset ot the stop words
     * @param stemming - boolean for the stemming
     */
    public void parser(Doc doc, String docText, HashSet<String> stopWords, Boolean stemming) {
        this.isStemming = stemming;
        try {
            // dont parse empty string
            if (docText.equals(""))
                return;
            // delete the ',' from the text
            docText = docText.replace(",", "");

            //replace the delimiters by their value
            for (String delimiter : replaceTerms.keySet()) {
                docText = docText.replace(delimiter, replaceTerms.get(delimiter));
            }

            //replace delimiters and symbols that don't have meaning
            docText =docText.replaceAll("[\n\t\r]"," ");
            docText =docText.replaceAll("[?',_;<>(){}|`~#&^*!\"]","");
            docText =docText.replaceAll("\\[","");
            //docText =docText.replaceAll("[\\Q..\\E]", "");
            docText =docText.replaceAll("[\\Q//\\E]", "");
            docText =docText.replaceAll("[\\Q=\\E]", "");
            docText =docText.replaceAll("[\\Q+\\E]", "");



            // split the text by ' '
            //so text is array of the terms before parsing in the docs text
            String[] text = docText.split(" ");

            //loop for the text array and parse evey text[i]
            for (int i = 0; i < text.length; i++) {
                while (i < text.length && text[i].equals("")) {
                    i = i + 1;
                    if (i == text.length)
                        return;
                }

                if(text[i].charAt(text[i].length()-1)!='.'){
                    text[i] = deleteDelimiters(text[i]);
                }
                if (text[i].equals(""))
                    continue;


                ///recognize entities
                if(!Character.isDigit(text[i].charAt(0)) && Character.isUpperCase(text[i].charAt(0))){

                    if(i-1>=0 && !text[i-1].equals("") && !Character.isUpperCase(text[i-1].charAt(0))){

                        // 3 words of entity
                        if(i+2< text.length && !text[i+1].equals("") && !text[i+2].equals("") &&
                                !Character.isDigit(text[i+1].charAt(0)) && !Character.isDigit(text[i+2].charAt(0))&&
                                Character.isUpperCase(text[i+1].charAt(0)) &&Character.isUpperCase(text[i+2].charAt(0))){
                            String entity = deleteDelimiters(text[i]).toUpperCase()+" "+ deleteDelimiters(text[i+1]).toUpperCase()+" "+deleteDelimiters(text[i+2]).toUpperCase();
                            addTermToEntitiesList(entity, doc);
                        }
                        // 2 words of entity
                        else if(i+1< text.length && !text[i+1].equals("") && !Character.isDigit(text[i+1].charAt(0))&&
                                Character.isUpperCase(text[i+1].charAt(0))){
                            String entity = deleteDelimiters(text[i]).toUpperCase()+" "+ deleteDelimiters(text[i+1]).toUpperCase();
                            addTermToEntitiesList(entity, doc);
                        }
//                        // 1 word
//                        else if(!stopWords.contains(text[i].toLowerCase())) {
//                            String entity = deleteDelimiters(text[i]).toUpperCase();
//                            addTermToEntitiesList(entity, doc);
//                        }

                    }
                    else if( i==0 && !text[i].equals("") && !Character.isUpperCase(text[i].charAt(0))){
                        // 3 words
                        if(i+2< text.length && !text[i+1].equals("") && !text[i+2].equals("") &&
                                !Character.isDigit(text[i+1].charAt(0)) && !Character.isDigit(text[i+2].charAt(0))&&
                                Character.isUpperCase(text[i+1].charAt(0)) &&Character.isUpperCase(text[i+2].charAt(0))){
                            String entity = deleteDelimiters(text[i]).toUpperCase()+" "+ deleteDelimiters(text[i+1]).toUpperCase()+" "+deleteDelimiters(text[i+2]).toUpperCase();
                            addTermToEntitiesList(entity, doc);
                        }
                        // 2 words
                        else if(i+1< text.length && !text[i+1].equals("") && !Character.isDigit(text[i+1].charAt(0))&&
                                Character.isUpperCase(text[i+1].charAt(0))){
                            String entity = deleteDelimiters(text[i]).toUpperCase()+" "+ deleteDelimiters(text[i+1]).toUpperCase();
                            addTermToEntitiesList(entity, doc);
                        }
//                        // 1 word
//                        else if(!stopWords.contains(text[i].toLowerCase())) {
//                            String entity = deleteDelimiters(text[i]).toUpperCase();
//                            addTermToEntitiesList(entity, doc);
//                        }
                    }

                }



                // number month
                if (isNumber(text[i]) && i + 1 < text.length && dates.containsKey(text[i + 1])) {
                    String withoutDelimeters = deleteDelimiters(text[i]);
                    if (!withoutDelimeters.contains(".") &&!withoutDelimeters.equals("")) {
                        // day month
                        if (text[i].length() <= 2 && Integer.parseInt(withoutDelimeters) > 0 && Integer.parseInt(withoutDelimeters) < 32) {
                            String month = dates.get(text[i + 1]);
                            String day = withoutDelimeters;
                            String date = DateFormatDayMonth(month, day);
                            addTermToList(date, doc);
                            i = i + 2;
                            continue;
                        }

                        // year month
                        if (text[i].length() >= 2 && Integer.parseInt(withoutDelimeters) > 31 && Integer.parseInt(withoutDelimeters) < 2999) {
                            String month = dates.get(text[i + 1]);
                            String year = withoutDelimeters;
                            String date = DateFormatMonthYear(month, year);
                            addTermToList(date, doc);
                            i = i + 2;
                            continue;

                        }
                    }


                }

                // month number
                if (dates.containsKey(text[i]) && i + 1 < text.length && isNumber(text[i + 1])) {

                    // month year
                    String withoutDelimeters = deleteDelimiters(text[i + 1]);
                    if (!withoutDelimeters.contains(".") && !text[i + 1].equals("") && !withoutDelimeters.equals("")) {
                        if (text[i + 1].length() >= 2 && Long.parseLong(withoutDelimeters) > 31 && Long.parseLong(withoutDelimeters) < 2999) {
                            String month = dates.get(text[i]);
                            String year = withoutDelimeters;
                            String date = DateFormatMonthYear(month, year);
                            addTermToList(date, doc);
                            i = i + 1;
                            continue;
                        }

                        // month day
                        if (text[i + 1].length() <= 2 && Integer.parseInt(withoutDelimeters) > 0 && Integer.parseInt(withoutDelimeters) < 32) {
                            String month = dates.get(text[i]);
                            String day = withoutDelimeters;
                            String date = DateFormatDayMonth(month, day);
                            addTermToList(date, doc);
                            i = i + 1;
                            continue;
                        }
                    }


                }


                //percent
                if (text[i].contains("%") && text[i].length()>1  ) {

                    // NUM%
                    if (text[i].charAt(text[i].length()-1)=='%'){
                        String num = text[i].substring(0, text[i].length()-1);
                        if (isNumber(num) ) {
                            String abc = deleteDelimiters(num);
                            double a = Double.parseDouble(abc);


                            addTermToList(df3.format(a)+"%", doc);
                            continue;
                        }
                        else{
                            text[i]=text[i].replace("%","");

                        }
                    }
                    else {
                        text[i]=text[i].replace("%","");
                    }

                }else if(text[i].equals("%")){
                    continue;
                }


                //phrases
                if (text[i].contains("-")) {
                    String[] phraseText = text[i].split("-");
                    // word-word-word
                    if (phraseText.length == 3 && !isNumber(phraseText[0]) && !isNumber(phraseText[1]) && !isNumber(phraseText[2])) {
                        if( numSize.contains(phraseText[1]) && phraseText[2].equals("dollar") ){
                            phraseText[0] = phraseText[0].replace("$","");
                            if( isNumber(phraseText[0]) && phraseText[1].equals("million") || phraseText[1].equals("Million")){
                                String abc = deleteDelimiters(phraseText[0]);
                                double a = Double.parseDouble(abc);
                                addTermToList(df3.format(a)+ " M Dollars", doc);
                                continue;
                            } else if( isNumber(phraseText[0]) && phraseText[1].equals("billion") || phraseText[1].equals("Billion")){
                                String abc = deleteDelimiters(phraseText[0]);
                                double a = Double.parseDouble(abc);
                                addTermToList(df3.format(a)+ " B Dollars", doc);
                                continue;
                            } else if( isNumber(phraseText[0]) && phraseText[1].equals("thousand") || phraseText[1].equals("Thousand")){
                                String abc = deleteDelimiters(phraseText[0]);
                                double a = Double.parseDouble(abc);
                                addTermToList(df3.format(a)+ " K Dollars", doc);
                                continue;
                            }
                        }

                    }
                    if (phraseText.length == 2) {
                        // num-num
                        if (isNumber(phraseText[0]) && isNumber(phraseText[1])) {
                            if(containDelimiters(phraseText[0])|| containDelimiters(phraseText[1])){
                                if((phraseText[0].length()>1 && delimiters.contains(phraseText[0].charAt(phraseText.length-1))&&phraseText[0].charAt(phraseText[0].length()-1)!='.' ) ||
                                        delimiters.contains(phraseText[1].charAt(0))){
                                    addTermToList(deleteDelimiters(phraseText[0]), doc);
                                    addTermToList(deleteDelimiters(phraseText[1]), doc);
                                    continue;
                                }
                            }else {
                                addTermToList(deleteDelimiters(phraseText[0]), doc);
                                addTermToList(deleteDelimiters(phraseText[1]), doc);
                                addTermToList(text[i], doc);
                                continue;
                            }
                        } else if(!isNumber(phraseText[0]) && !isNumber(phraseText[1])){
                            //word-word
                            if(containDelimiters(phraseText[0])|| containDelimiters(phraseText[1])){
                                if((phraseText[0].length()>1 && delimiters.contains(phraseText[0].charAt(phraseText.length-1))&&phraseText[0].charAt(phraseText[0].length()-1)!='.' ) ||
                                        delimiters.contains(phraseText[1].charAt(0))){
                                    addTermToList(deleteDelimiters(phraseText[0]), doc);
                                    addTermToList(deleteDelimiters(phraseText[1]), doc);
                                    continue;
                                }
                            }else if(!text[i].contains("$")) {
                                addTermToList(deleteDelimiters(text[i]), doc);
                                addTermToList(deleteDelimiters(phraseText[0]), doc);
                                addTermToList(deleteDelimiters(phraseText[1]), doc);
                                continue;
                            } else if(text[i].contains("$") && (phraseText[1].equals("dollar") || phraseText[1].equals("Dollar") || phraseText[1].equals("dollars") || phraseText[1].equals("Dollars") )){
                                phraseText[0] = phraseText[0].replace("$", "");
                                if(isNumber(phraseText[0])){
                                    String abc = deleteDelimiters(phraseText[0]);
                                    double a = Double.parseDouble(abc);
                                    addTermToList(df3.format(a)+ " Dollars", doc);
                                    continue;
                                }


                            }
                            //num-percent
                        }else if(isNumber( phraseText[0]) && (phraseText[1].equals("percent") ||phraseText[1].equals("percentage") ||phraseText[1].equals("Percent")) ){
                            //String num = text[i].substring(0, text[i].length()-1);

                            String abc = deleteDelimiters(phraseText[0]);
                            double a = Double.parseDouble(abc);
                            addTermToList(df3.format(a)+"%", doc);
                            continue;
                        }
                        // num-word
                        else{
                            addTermToList(deleteDelimiters(phraseText[0]), doc);
                            addTermToList(deleteDelimiters(phraseText[1]), doc);
                            addTermToList(deleteDelimiters(text[i]), doc);
                            continue;
                        }
                    }

                }
                //between NUM and NUM
                if (i + 3 < text.length && (text[i].equals("Between") || text[i].equals("between")) && text[i + 2].equals("and")) {

                    if(!isNumber(text[i+1]) && !isNumber(text[i+3])){
                        if(!stopWords.contains(text[i+1].toLowerCase())){
                            addTermToList(text[i+1], doc);
                        }
                        if(!stopWords.contains(text[i+3].toLowerCase())){
                            addTermToList(text[i+3], doc);
                        }
                        i = i + 3;
                        continue;
                    }
                    else {
                        String word = text[i].toLowerCase() + " " + text[i + 1] + " " + text[i + 2].toLowerCase() + " " + text[i + 3];
                        addTermToList(word, doc);

                        String rang = text[i + 1] + "-" + text[i + 3];
                        addTermToList(rang, doc);
                        i = i + 3;
                        continue;
                    }


                }




                // NUM percent     or    NUM percentage
                String percent = "";
                if (isNumber(text[i]) && i + 1 < text.length && (deleteDelimiters(text[i + 1]).equals("percent") || deleteDelimiters(text[i + 1]).equals("percentage"))) {

                    String abc = deleteDelimiters(text[i]);
                    if(!abc.equals("")){
                        double a = Double.parseDouble(abc);
                        percent = df3.format(a) + "%";
                        addTermToList(percent, doc);
                        i = i + 1;
                        continue;
                    }

                }

                if(Character.isDigit(text[i].charAt(0)) && (text[i].charAt(text[i].length()-1)=='k' || text[i].charAt(text[i].length()-1)=='K' ||
                        text[i].charAt(text[i].length()-1)=='m' || text[i].charAt(text[i].length()-1)=='M' || text[i].charAt(text[i].length()-1)=='b' || text[i].charAt(text[i].length()-1)=='B')){
                    String test ="";
                    String word = "";
                    if(text[i].charAt(text[i].length()-1)=='k'){
                        test = text[i].substring(0, text[i].length()-1);
                        if(isNumber(test)){
                            test = deleteDelimiters(test);
                            if (!test.equals("")){
                                double a = Double.parseDouble(test);
                                word = df3.format(a) + "K";
                                addTermToList(word, doc);
                                continue;
                            }
                        }
                    }
                    else if(text[i].charAt(text[i].length()-1)=='m'){
                        test = text[i].substring(0, text[i].length()-1);
                        if(isNumber(test)){
                            test = deleteDelimiters(test);
                            if (!test.equals("")){
                                double a = Double.parseDouble(test);
                                word = df3.format(a) + "M";
                                addTermToList(word, doc);
                                continue;
                            }
                        }
                    }
                    else if(text[i].charAt(text[i].length()-1)=='b'){
                        test = text[i].substring(0, text[i].length()-1);
                        if(isNumber(test)){
                            test = deleteDelimiters(test);
                            if (!test.equals("")){
                                double a = Double.parseDouble(test);
                                word = df3.format(a) + "B";
                                addTermToList(word, doc);
                                continue;
                            }
                        }
                    }
                }



                String unit = "";
                //units
                // number UNITS ---> 50 yard
                // we add this law every unit replaced in its value from the unit hashMap
                if (i + 1 < text.length &&text[i+1]!=null && !(text[i + 1].equals("")) && units.containsKey(text[i + 1]) &&isNumber(text[i])) {

                    String abc = deleteDelimiters(text[i]);
                    if(!abc.equals("")){
                        double a = Double.parseDouble(abc);
                        unit = df3.format(a) + " " + units.get(text[i+1])  ;
                        addTermToList(unit, doc);
                        i = i + 1;
                        continue;
                    }
                }

                //time
                // 13:45 AM/PM || 13:45 || 10:12 a.m./p.m. ------> NUM:NUM {AM/PM/a.m./p.m.}
                // we add this law for time, we save numbers that meaning time in same format
                if (text[i].contains(":")) {

                    String[] toSplite = text[i].split(":");
                    if(toSplite.length == 2 && isNumber(toSplite[0]) && isNumber(toSplite[1]) &&
                            !toSplite[0].contains(".") &&!toSplite[1].contains(".") &&
                            !toSplite[0].contains(",") &&!toSplite[1].contains(",") && toSplite[0].length()<3 && toSplite[1].length()<3 ){
                        int hour = Integer.parseInt(toSplite[0]);
                        int mint = Integer.parseInt(toSplite[1]);
                        String min_str="";
                        String hour_str="";
                        if (i + 1 < text.length && !(text[i + 1].equals("")) ) {

                            if (text[i + 1].equals("a.m") || text[i + 1].equals("AM")){
                                String compHour= toTimeFormat(hour,mint,"a.m.");
                                addTermToList(compHour,doc);
                                i++;
                                continue;
                            }
                            else if((text[i + 1].equals("p.m") || text[i + 1].equals("PM"))){
                                String compHour= toTimeFormat(hour,mint,"p.m.");
                                addTermToList(compHour,doc);
                                i++;
                                continue;
                            }


                        }
                        if (hour<12 && mint<60){
                            String compHour= toTimeFormat(hour,mint,"a.m.");
                            addTermToList(compHour,doc);
                            continue;
                        }
                        else if(hour>=12 && mint<60){
                            String compHour= toTimeFormat(hour,mint,"p.m.");
                            addTermToList(compHour,doc);
                            continue;
                        }

                    }


                }

                //if <million && price  ---- example: 20.6m Dollars
                if ((text[i].contains("m") || text[i].contains("M")) && i + 1 < text.length &&
                        (text[i + 1].equals("Dollars") || text[i + 1].equals("dollars"))) {
                    if (text[i].charAt(text[i].length() - 1) == 'm' || text[i].charAt(text[i].length() - 1) == 'M') {
                        String numWithoutsign = text[i].substring(0, text[i].length() - 1);
                        if (isNumber(deleteDelimiters(numWithoutsign)) && Double.parseDouble(deleteDelimiters(numWithoutsign)) < 1000000) {
                            double a =Double.parseDouble(deleteDelimiters(numWithoutsign));
                            numWithoutsign = df3.format(a);
                            numWithoutsign += "M Dollars";
                            addTermToList(numWithoutsign, doc);
                            i = i + 1;
                            continue;
                        }
                    }
                }



                //if <million && price  ---- 20.6bn Dollars -----billion
                if ((text[i].contains("bn") || text[i].contains("B")) && i + 1 < text.length &&
                        (text[i + 1].equals("Dollars") || text[i + 1].equals("dollars"))) {
                    if (text[i].length()>=2 && (text[i].charAt(text[i].length() - 2) == 'b' && text[i].charAt(text[i].length() - 1) == 'n') ||
                            text[i].charAt(text[i].length() - 1) == 'B') {
                        //if end with "bn"
                        if (text[i].length()>=2 && text[i].charAt(text[i].length() - 2) == 'b' && text[i].charAt(text[i].length() - 1) == 'n') {
                            String numWithoutsign = text[i].substring(0, text[i].length() - 2);
                            if (isNumber(numWithoutsign) && Double.parseDouble(numWithoutsign) < 1000000) {
                                Double num = Double.parseDouble(deleteDelimiters(numWithoutsign));
                                num *= 1000;
                                numWithoutsign = df3.format(num);
                                numWithoutsign += "M Dollars";
                                addTermToList(numWithoutsign, doc);
                                i = i + 2;
                                continue;
                            }
                        }
                        //if end with "B"
                        else if (text[i].charAt(text[i].length() - 1) == 'B') {
                            String numWithoutsign = text[i].substring(0, text[i].length() - 1);
                            if (isNumber(numWithoutsign) && Double.parseDouble(numWithoutsign) < 1000000) {
                                Double num = Double.parseDouble(numWithoutsign);
                                num *= 1000;
                                numWithoutsign = df3.format(num.toString());
                                numWithoutsign += "M Dollars";
                                addTermToList(numWithoutsign, doc);
                                i = i + 2;
                                continue;
                            }
                        }

                    }
                }


                // if <million && price  ---- 20.6 m Dollars ---- million or billion
                if (isNumber(text[i]) && i + 2 < text.length && (text[i + 1].equals("m") || text[i + 1].equals("M") ||
                        text[i + 1].equals("bn") || text[i + 1].equals("b") || text[i + 1].equals("B")) &&
                        (text[i + 2].equals("dollars") || text[i + 2].equals("Dollars"))) {
                    Double num = Double.parseDouble(text[i]);
                    String numberAsTerm = "";
                    if (num < 1000000) {
                        // if <million && price  ---- 20.6 m Dollars -----million
                        if (text[i + 1].equals("m") || text[i + 1].equals("M")) {
                            double a = Double.parseDouble(deleteDelimiters(text[i]));
                            String ab = df3.format(a);
                            // System.out.println(ab);
                            numberAsTerm += ab + " M Dollars";
                            addTermToList(numberAsTerm, doc);
                            i += 2;
                            continue;
                        }
                        // if <million && price  ---- 20.6 b Dollars -----billion
                        if (text[i + 1].equals("bn") || text[i + 1].equals("b") || text[i + 1].equals("B")) {
                            num *= 1000;

                            numberAsTerm += df3.format(num) + " M Dollars";
                            addTermToList(numberAsTerm, doc);
                            i += 2;
                            continue;
                        }
                    }

                }


                // if number million/billion/trillion U.S. dollars
                if (isNumber(text[i]) && i + 3 < text.length && numSize.contains(text[i + 1]) &&
                        text[i + 2].equals("U.S.") && (text[i + 3].equals("dollars") || text[i + 3].equals("Dollars"))) {
                    Double num = Double.parseDouble(text[i]);
                    String numberAsTerm = "";
                    //if million
                    if (text[i + 1].equals("million") || text[i + 1].equals("Million")) {
                        //
                        //    System.out.println(text[i]);
                        String ab = deleteDelimiters(text[i]);
                        double a= Double.parseDouble(ab);
                        numberAsTerm = df3.format(a) + " M Dollars";
                        addTermToList(numberAsTerm, doc);
                        i += 3;
                        continue;
                    }
                    // if billion
                    else if (text[i + 1].equals("billion") || text[i + 1].equals("Billion")) {


                        num *= 1000;
                        String ab = df3.format(num);
                        numberAsTerm = ab + " M Dollars";
                        addTermToList(numberAsTerm, doc);
                        i += 3;
                        continue;
                    }
                    // if trillion
                    else if (text[i + 1].equals("trillion") || text[i + 1].equals("Trillion")) {
                        num *= 1000000;
                        numberAsTerm = df3.format(num) + " M Dollars";
                        addTermToList(numberAsTerm, doc);
                        i += 3;
                        continue;
                    }

                }


                // $number - price
                if (text[i].contains("$") && isPrice(text[i])) {
                    String abc = deleteDelimiters(text[i]);
                    if(abc.contains("%"))
                        abc = abc.replace("%","");
                    abc = abc.substring(1);
                    if (abc != "") {
                        double a = Double.parseDouble(abc);
                        String numWithoutsign = df3.format(a);

                        if (Double.parseDouble(numWithoutsign) < 1000000) {

                            if (i + 1 < text.length && numSize.contains(text[i + 1])) {
                                // if million after
                                if (text[i + 1].equals("million") || text[i + 1].equals("Million")) {
                                    numWithoutsign += " M Dollars";
                                    addTermToList(numWithoutsign, doc);
                                    i++;
                                    continue;
                                }
                                //else if billion after
                                else if (text[i + 1].equals("billion") || text[i + 1].equals("Billion")) {
                                    Double num = Double.parseDouble(numWithoutsign);
                                    num = num * 1000;
                                    numWithoutsign = df3.format(num) + " M Dollars";
                                    addTermToList(numWithoutsign, doc);
                                    i++;
                                    continue;
                                }
                            }
                            //else nothing after
                            else {
                                numWithoutsign += " Dollars";
                                addTermToList(numWithoutsign, doc);
                                continue;
                            }


                        } else {
                            double num = Double.parseDouble(numWithoutsign);
                            num = num / 1000000;

                            numWithoutsign = df3.format(num);
                            numWithoutsign += " M Dollars";
                            addTermToList(numWithoutsign, doc);
                            continue;
                        }
                    }
                }
                else if(text[i].contains("$") /* && !isPrice(text[i]) */){
                    text[i]= text[i].replace("$"," ");
                }

                // number >=1000
                if (isNumber(deleteDelimiters(text[i])) && Double.parseDouble(deleteDelimiters(text[i])) >= 1000) {
                    String n = deleteDelimiters(text[i]);
                    Double number = Double.parseDouble(n);
                    String numStr = "";


                    if (number >= 1000 && number < 1000000) {
                        number = number / 1000;
                        String abc = deleteDelimiters(number.toString());
                        double a = Double.parseDouble(abc);
                        numStr = df3.format(a) + "K";
                        addTermToList(numStr, doc);
                        continue;

                    }

                    if (number >= 1000000 && number < 1000000000) {
                        number = number / 1000000;


                        //dollar
                        if (i + 1 < text.length && (text[i + 1].equals("dollars") || text[i + 1].equals("Dollars"))) {
                            numStr = df3.format(number) + " M Dollars";
                            addTermToList(numStr, doc);
                            i++;
                            continue;
                        } else {
                            double num = Double.parseDouble(number.toString());
                            numStr = df3.format(num) + "M";
                            addTermToList(numStr, doc);
                            continue;
                        }


                    }

                    if (number >= 1000000000) {
                        number = number / 1000000000;

                        //dollar
                        if (i + 1 < text.length && (text[i + 1].equals("dollars") || text[i + 1].equals("Dollars"))) {
                            number = number * 1000;
                            numStr = df3.format(number) + " M Dollars";
                            addTermToList(numStr, doc);
                            i++;
                            continue;
                        } else {
                            numStr = df3.format(number) + "B";
                            addTermToList(numStr, doc);
                            continue;
                        }
                    }

                } else if (isNumber(deleteDelimiters(text[i])) && Double.parseDouble(deleteDelimiters(text[i])) < 1000) {
                    //number numSize
                    String newNum = "";

                    if (i + 1 < text.length && numSize.contains(text[i + 1])) {
                        //thousand ->k
                        if (text[i + 1].equals("thousand") || text[i + 1].equals("Thousand")) {
                            if (i + 2 < text.length && (text[i + 1].equals("dollars") || text[i + 1].equals("Dollars"))) {
                                addTermToList(df3.format(text[i]) + " Dollars", doc);
                                i = i + 2;
                                continue;
                            } else {
                                double a= Double.parseDouble(deleteDelimiters(text[i]));

                                newNum = df3.format(a) + "K";
                                addTermToList(newNum, doc);
                                i++;
                                continue;
                            }
                        }
                        //million->M
                        if (text[i + 1].equals("million") || text[i + 1].equals("Million")) {

                            String abc = deleteDelimiters(text[i]);
                            double a = Double.parseDouble(abc);
                            String add = df3.format(a) + "M";
                            addTermToList(add, doc);

                            i++;
                            continue;
                        }
                        //billion->B
                        if (text[i + 1].equals("billion") || text[i + 1].equals("Billion")) {
                            double num = Double.parseDouble(deleteDelimiters(text[i]));
                            newNum = df3.format(num) + "B";
                            addTermToList(newNum, doc);
                            i++;
                            continue;
                        }
                        //Trillion-> 000B
                        if (text[i + 1].equals("trillion") || text[i + 1].equals("Trillion")) {
                            String a = deleteDelimiters(text[i]);
                            double ab = Double.parseDouble(a);
                            Double num = ab * 1000;
                            newNum = df3.format(num) + "B";
                            addTermToList(newNum, doc);
                            i++;
                            continue;
                        }
                    }


                    //shever
                    if ( i+1<text.length && isShever(deleteDelimiters(text[i + 1]))) {
                        String shever = text[i] + " " + text[i + 1];
                        //shever dollars
                        if (i + 2 < text.length && (text[i + 2].equals("Dollars") || text[i + 2].equals("dollars"))) {
                            shever += " Dollars";
                            addTermToList(shever, doc);
                            i = i + 2;
                            continue;
                        }
                        //regular shever
                        else {
                            addTermToList(shever, doc);
                            continue;
                        }
                    }


                    // number Dollar
                    if (i + 1 < text.length && (text[i + 1].equals("dollars") || text[i + 1].equals("Dollars"))) {


                        String abc = deleteDelimiters(text[i]);
                        double a = Double.parseDouble(abc);
                        String add = df3.format(a) + "Dollars";
                        addTermToList(add, doc);

                        i = i + 1;
                        continue;
                    } else {

                        String abc = deleteDelimiters(text[i]);
                        double a = Double.parseDouble(abc);
                        addTermToList(df3.format(a), doc);
                        continue;

                    }

                }

                // word1:word2 -----> word1  ,  word
                if (text[i].contains(":")) {
                    String[] toSplite = text[i].split(":");
                    if(toSplite.length>1){
                        addTermToList(deleteDelimiters(toSplite[0]), doc);
                        addTermToList(deleteDelimiters(toSplite[1]), doc);
                        continue;
                    }

                }

                // word1/word2 -----> word1  ,  word
                if (text[i].contains("/")){
                    String[] toSplite = text[i].split("/");
                    if(toSplite.length>1){
                        addTermToList(deleteDelimiters(toSplite[0]), doc);
                        addTermToList(deleteDelimiters(toSplite[1]), doc);
                        continue;
                    }

                }

                // word1(word2 -----> word1  ,  word
                if (text[i].contains("(")){
                    text[i]= text[i].replace("(",",");
                    String[] toSplite = text[i].split(Pattern.quote(","));
                    if(toSplite.length>1){
                        addTermToList(deleteDelimiters(toSplite[0]), doc);
                        addTermToList(deleteDelimiters(toSplite[1]), doc);
                        continue;
                    }

                }

                // word1.word2 -----> word1  ,  word
                if (text[i].contains(".") && !text[i].equals(".")){
                    int idxOfPointend = text[i].lastIndexOf(".");
                    if(idxOfPointend-2>0 && text[i].charAt(idxOfPointend-2)=='.'){
                        addTermToList(text[i], doc);
                        continue;
                    }
                    else{
                        String[] toSplite = text[i].split(Pattern.quote("."));
                        if(toSplite.length>1){
                            addTermToList(deleteDelimiters(toSplite[0]), doc);
                            addTermToList(deleteDelimiters(toSplite[1]), doc);
                            continue;
                        }
                    }


                }

                //just words
                if (!stopWords.contains(text[i].toLowerCase()) && !isNumber(text[i]) && !text[i].equals("%")) {
                    addTermToList(deleteDelimiters(text[i]), doc);
                }

            } //for


        } catch (Exception e) {

           // e.printStackTrace();
        }

    }

    /**
     * this function return string in our format for time
     * the function receive int for hour, int for minute and string for the AM or PM
     * @param hour
     * @param mint
     * @param s
     * @return
     */
    private String toTimeFormat(int hour, int mint, String s) {
        String min_str="";
        String hour_str="";

        if(mint==0){
            min_str ="00";
        }
        else
            min_str =""+mint;
        if(hour==0){
            hour_str="00";
        }
        else
            hour_str=hour+"";

        String compHour = hour_str+":"+min_str+" "+s;
        return compHour;
    }


    /**
     * this function return true or false if the given word contain at least one delimiter
     * @param word
     * @return
     */
    private boolean containDelimiters(String word) {
        for (int i=0;  i<word.length(); i++){
            if(delimiters.contains(word.charAt(i)))
                return true;
        }
        return false;
    }

    /**
     * check if the string is in a pattern of price ( $NUM ) and return true if yes
     * and return false if not.
     * @param price
     * @return
     */
    private boolean isPrice(String price) {
        if (price.contains("$") && price.charAt(0) == '$') {
            if (price.substring(1).equals("")) {
                return false;
            }
            for (int i = 1; i < price.length(); i++) {
                if (!Character.isDigit(price.charAt(i)))
                    return false;
            }
            return true;
        }
        return false;
    }


    /**
     * check if the string is in pattern of shever ( NUM/NUM )
     * if yes, return true
     * if not, return false
     * @param num
     * @return
     */
    private boolean isShever(String num) {
        if (!num.equals("") && num.contains("/")){
            String[] nomeMechane = num.split("/");
            if (nomeMechane.length == 2) {
                if (isNumber(nomeMechane[0]) && isNumber(nomeMechane[1]))
                    return true;
            }
        }
        return false;
    }

    /**
     * this function get month anf year and return string in the format that we want to save this date
     * @param month
     * @param year
     * @return
     */
    private String DateFormatMonthYear(String month, String year) {

        String date;
        if (Integer.parseInt(deleteDelimiters(year)) > 32 && Integer.parseInt(deleteDelimiters(year)) < 100)
            date = "19" + deleteDelimiters(year) + "-";
        else
            date = year + "-";
        date += month;
        return date;
    }


    /**
     * this function add term to the termList
     * we get string that is the term name and create term with its fields (doc that contain the terms)
     * if the term already exist we add the doc to the doc list and upp the time that the term appear in doc
     * @param term
     * @param doc
     */
    private void addTermToList(String term, Doc doc) {
        if(!term.equals("")){
            if (isStemming) {
                stemmer.getStemmer().setCurrent(term);
                if (stemmer.getStemmer().stem()){
                    String sTerm = stemmer.getStemmer().getCurrent();
                    if( !sTerm.equals(""))
                        term = sTerm;
                }
            }
            if (Character.isUpperCase(term.charAt(0)) || Character.isUpperCase(term.charAt(term.length()-1)) ) {
                term = term.toUpperCase();
            }
//            boolean p =  !term.contains("Dollars");
//            boolean pa = !( Character.isDigit(term.charAt(0)) && ( term.charAt(term.length()-1) == 'M' ||term.charAt(term.length()-1) == 'K' || term.charAt(term.length()-1) == 'B'));

            else if(!term.contains("Dollars") && !( Character.isDigit(term.charAt(0)) && ( term.charAt(term.length()-1) == 'M' ||term.charAt(term.length()-1) == 'K' || term.charAt(term.length()-1) == 'B'))){
                term = term.toLowerCase();
            }

            //exist in the list in lower letters
            if (termList.containsKey(term.toLowerCase())) {
                termList.get(term.toLowerCase()).setTerm(doc.getDocNo(), term.toLowerCase());
            }
            //exist in the list in upperletters
            else if (termList.containsKey(term.toUpperCase())){
                Term t = termList.get(term.toUpperCase());
                termList.remove(term.toUpperCase());
                termList.put(term, t);
                t.setTerm(doc.getDocNo(),term);

            }
            //new term
            else {
                Term newTerm = new Term(term, doc.getDocNo());
                termList.put(term, newTerm);
            }

            // if is new term
            if(!doc.getDocTerms().containsKey(term.toLowerCase())&& !doc.getDocTerms().containsKey(term.toUpperCase())){
                doc.addTermToDoc(term,1);
            }
            // if the term exist in lowerCase
            else if(doc.getDocTerms().containsKey(term.toLowerCase())){
                int count= doc.getDocTerms().get(term.toLowerCase());
                doc.addTermToDoc(term.toLowerCase(),count+1 );
            }
            // if the term exist in upperCase
            else if(doc.getDocTerms().containsKey(term.toUpperCase())){
                int count= doc.getDocTerms().get(term.toUpperCase());
                doc.getDocTerms().remove(term.toUpperCase());
                doc.addTermToDoc(term,count+1 );
            }

        }
    }

    /**
     * this function add term as entities that the parse recognize in th entitiesList
     * we get string that is the term name and create term with its fields (doc that contain the terms)
     * if the term already exist we add the doc to the doc list and upp the time that the term appear in doc
     * @param term
     * @param doc
     */
    private void addTermToEntitiesList(String term, Doc doc) {
        if(!term.equals("")){
            if (isStemming) {
                stemmer.getStemmer().setCurrent(term);
                if (stemmer.getStemmer().stem()){
                    String sTerm = stemmer.getStemmer().getCurrent();
                    if( !sTerm.equals(""))
                        term = sTerm;
                }
            }
            term = term.toUpperCase();


            //exist in the list
            if (entityList.containsKey(term)) {
                entityList.get(term).setTerm(doc.getDocNo(), term);
            }

            //new term
            else {
                Term newTerm = new Term(term, doc.getDocNo());
                entityList.put(term, newTerm);
            }

            // if the term not exist in the docList (new term)
            if(!doc.getDocTerms().containsKey(term)){
                doc.addTermToDoc(term,1);
            }
            // if the term already exist in the docList
            if(doc.getDocTerms().containsKey(term)){
                int count= doc.getDocTerms().get(term);
                doc.addTermToDoc(term,count+1 );
            }

        }
    }

    /**
     * this function given month and day and save the date in the correct format as string and return this string
     * @param month
     * @param day
     * @return
     */
    private String DateFormatDayMonth(String month, String day) {

        String date = month + "-";
        if (day.length() == 1)
            date += "0" + day;
        else
            date += day;

        return date;
    }

    /**
     * this function get token and delete the delimiters from the start of the token and from the end of the token
     * @param token
     * @return string of the token without the delimiters from the start and the end
     */
    private String deleteDelimiters(String token) {
        int firstChar = 0;
        if ((isNumber(token) && token.length() > 2 && (token.substring(token.length() - 2).equals("th"))) || (token.length() > 2 && token.substring(token.length() - 2).equals("'s"))
                || (token.length() > 2 && token.substring(token.length() - 2).equals("'S")) ||(token.length() > 2 && token.substring(token.length() - 2).equals("0)"))||(token.length() > 2 && token.substring(token.length() - 2).equals("5.")))
            token = token.substring(0, token.length() - 2);
        while (token.length() > 0 && delimiters.contains(token.charAt(firstChar))) {
            token = token.substring(firstChar + 1);  //cut the first char
        }
        int lastChar = token.length() - 1;
        while (token.length() > 0 && delimiters.contains(token.charAt(lastChar))) {
            token = token.substring(0, lastChar);
            lastChar--;
        }
        return token;
    }

    /**
     * this function return false and true if the token is number or not
     * we consider number as string that every char is or digit or '.' or ','
     * and the total count of point is maximum 1
     * @param token
     * @return
     */
    private boolean isNumber(String token) {
        if (token.equals(""))
            return false;
        int countPoints=0;
        for (char c : token.toCharArray()) {
            if(c == '.'){
                countPoints++;
            }
            if(countPoints>1)
                return false;

            if ((!Character.isDigit(c) && c != ',' && c != '.' )) {
                return false;
            }
        }
        return true;
    }

    /**
     * getter gor the termsList
     * @return
     */
    public HashMap<String, Term> getTerms() {
        return termList;
    }


    /**
     * this function is init the termList, create new HashMap
     */
    public void clearTermList() {
        termList.clear();
    }

    /**
     * getter for the entitiesList
     * @return
     */
    public HashMap<String, Term> getEntities() {
        return entityList;
    }

    /**
     * this function is init the entitiesList, create new HashMap
     */
    public void clearEntitiesList() {
        entityList.clear();
    }
}
