package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class histogram {
    public static void main(String[] args) {

        File file = new File("input.txt");

        generateMap(file);
    }

//    TODO: return type, private or public
    private static void generateMap(File file) {

        HashMap<String, Integer> wordCounts = new HashMap<>();

        try{

            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine()){

                String[] currentLine = scanner.nextLine().toLowerCase().split(" ");


                for(String word : currentLine){

//                    TODO: instead of checking for special chars, check if isLetter so that this will work for all                         inputs not just this one
                    if(word.contains(",") || word.contains(".") || word.contains("!") || word.contains("?")){
                        word = word.substring(0, word.length()-1);
                    }

                    if(!wordCounts.keySet().contains(word)){
                        wordCounts.put(word, 1);
                    } else {
                        Integer incrementedValue = (int) wordCounts.get(word) + 1;
                        wordCounts.replace(word, incrementedValue);
                    }
                }
            }

            //Format the output
            formatHistogram(wordCounts);

        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e);
        }
    }

    private static void formatHistogram(HashMap<String, Integer> wordCounts){

        System.out.println(wordCounts);


        List<Map.Entry<String, Integer>> convertedWordCounts = new LinkedList<>(wordCounts.entrySet());


        Collections.sort(convertedWordCounts, new Comparator<>() {
                    public int compare(Map.Entry<String, Integer> o1,
                                       Map.Entry<String, Integer> o2) {
                        return (o2.getValue()).compareTo(o1.getValue());
                    }
                });

        System.out.println(convertedWordCounts);

        for(Map.Entry entry : convertedWordCounts){
            String key = entry.getKey().toString();
            Integer value = (Integer) entry.getValue();
//            TODO: change to StringBuilder
            String equals = "";
            for(int i = 0; i< value; i++){
                equals = equals + "=";
            }
            System.out.println(key + " | " + equals + " (" + value + ")");
        }

    }
}
