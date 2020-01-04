package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class histogram {
    public static void main(String[] args) {
//  Bring in the file
        File file = new File("input.txt");

        generateMap(file);
    }

    private static void generateMap(File file) {
// Map to hold the words and counts
        HashMap<String, Integer> wordCounts = new HashMap<>();

        try{

            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine()){

                String[] currentLine = scanner.nextLine().toLowerCase().split(" ");


                for(String word : currentLine){

// TODO: instead of checking for special chars, check if isLetter so that this will work for all                         inputs not just this one
//      Get rid of special characters at the end of words that will cause a problem when comparing words
                    if(word.contains(",") || word.contains(".") || word.contains("!") || word.contains("?")){
                        word = word.substring(0, word.length()-1);
                    }
//      If the word is not i the map, add it and add the value 1
                    if(!wordCounts.keySet().contains(word)){
                        wordCounts.put(word, 1);
                    } else {
//      If the word is in the map increment the value
                        Integer incrementedValue = wordCounts.get(word) + 1;
                        wordCounts.replace(word, incrementedValue);
                    }
                }
            }

//      Format the output
            formatHistogram(wordCounts);

        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e);
        }
    }

    private static void formatHistogram(HashMap<String, Integer> wordCounts){

//        Sort in descending order by value
        List<Map.Entry<String, Integer>> convertedWordCounts = new LinkedList<>(wordCounts.entrySet());

        Collections.sort(convertedWordCounts, new Comparator<>() {
                    public int compare(Map.Entry<String, Integer> o1,
                                       Map.Entry<String, Integer> o2) {
                        return (o2.getValue()).compareTo(o1.getValue());
                    }
                });

//        Get the longest word for formatting the output below
        String longestWordForFormatting = "";

        for(Map.Entry entry : convertedWordCounts) {
            String key = entry.getKey().toString();
            if(key.length() > longestWordForFormatting.length()) {
                longestWordForFormatting = key;
            }
        }

        int width = longestWordForFormatting.length() + 1;

//       Iterate through the convertedWordCounts to set the number of = and spaces needed for the formatted output
        for(Map.Entry entry : convertedWordCounts){
            String key = entry.getKey().toString();
            Integer value = (Integer) entry.getValue();
            String equals = "";
            for(int i = 0; i< value; i++){
                equals = equals + "=";
            }
            String keyFormatted = "";
            if(key.length() < width){
                int spacesToAdd = width - key.length();
                String extraSpaces = "";
                for(int i = 0; i<spacesToAdd; i++){
                    extraSpaces = extraSpaces + " ";
                }
                keyFormatted = extraSpaces + key;
            }
//            Print out the formatted output
            System.out.printf("%s | %s (%s)%n", keyFormatted, equals, value);
        }
    }
}
