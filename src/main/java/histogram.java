package main.java;

import java.io.*;
import java.util.*;

public class histogram {
    public static void main(String[] args) {
//  Bring in the file
        final File file = new File("input.txt");

        generateMap(file);
    }

    private static void generateMap(File file) {
// Map to hold the words and counts
        HashMap<String, Integer> wordCounts = new HashMap<>();

        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String[] currentLine = scanner.nextLine().toLowerCase().split(" ");

//      Get rid of special characters in words so that words can be compared without punctuation
//      Note: this will also remove hyphens from hyphenated words
                for(String word : currentLine){
                    for(int i = 0; i<word.length(); i++){
                        if(!Character.isLetter(word.charAt(i)) && !Character.isDigit(word.charAt(i))){
                            String beginning = "";
                            String end = "";
                            if(i != 0) {
                                beginning = word.substring(0, i);
                            }
                            if(i != word.length()-1){
                                end = word.substring(i+1);
                            }
                            word = beginning + end;
                        }
                    }
//      If the word is not i the map, add it and add the value 1
                    if(!wordCounts.keySet().contains(word)){
                        wordCounts.put(word.trim(), 1);
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
                    public int compare(Map.Entry<String, Integer> a,
                                       Map.Entry<String, Integer> b) {
                        return (b.getValue()).compareTo(a.getValue());
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

        int longestWordLength = longestWordForFormatting.length() + 1;

//       Create output.txt file
        try (FileWriter file = new FileWriter("output.txt")){
//       Iterate through the convertedWordCounts to set the number of = and spaces needed for the formatted output
            for(Map.Entry entry : convertedWordCounts){
                String key = entry.getKey().toString();
                Integer value = (Integer) entry.getValue();
                String equals = "";
                for(int i = 0; i< value; i++){
                    equals = equals + "=";
                }
                String keyFormatted = "";
                if(key.length() < longestWordLength){
                    int spacesToAdd = longestWordLength - key.length();
                    String extraSpaces = "";
                    for(int i = 0; i<spacesToAdd; i++){
                        extraSpaces = extraSpaces + " ";
                    }
                    keyFormatted = extraSpaces + key;
                }
//          Write to the file
//          checks empty lines in the input file and were counted as words, and doesn't write them to the file
                if(key.length()>0){
                    file.write(keyFormatted + " | " + equals + " (" + value + ")\n");
                }
            }

        } catch(IOException e){
            System.out.println(e);
        }
    }
}
