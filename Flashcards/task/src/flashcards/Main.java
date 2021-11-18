package flashcards;

import java.io.*;
import java.util.*;
import java.io.FileWriter;

public class Main {
    public static ArrayList<String> inMemoryLog = new ArrayList<>();

    public static void println(String s) {
        System.out.println(s);
        inMemoryLog.add(s);
    }

    public static String scanner() {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine().replace("> ", "");
        inMemoryLog.add(s);
        return s;
    }

    public static void main(String[] args) {

        Map<String, String> cards = new LinkedHashMap<>();

        Map<String, Integer> mistakes = new LinkedHashMap<>();
        String fileNameImport;
        String fileNameExport = null;

        for (String s: args) {

            if (args.length == 4) {
                fileNameExport = args[1];
                fileNameImport = args[3];
            } else {

                fileNameImport = args[1];
                fileNameExport = args[1];
            }

            if (s.contains("import")) {
                //fileNameImport = args[0];
                BufferedReader br = null;

                Integer numCardsOriginal = 0;
                try {

                    // create file object

                    File file = new File(args[3]);

                    // create BufferedReader object from the File
                    br = new BufferedReader(new FileReader(file));

                    String numCardsStr = br.readLine();
                    Integer numCards = Integer.parseInt(numCardsStr);
                    numCardsOriginal = numCards;

                    while(numCards != 0) {
                        String term = br.readLine();
                        String definition = br.readLine();
                        Integer numMistakes = Integer.parseInt(br.readLine());

                        if (cards.containsKey(term) && cards.containsValue(definition)) {
                            for (Map.Entry<String, Integer> entry : mistakes.entrySet()) {
                                mistakes.put(entry.getKey(), numMistakes);
                                numCards -= 1;
                            }

                        } else {
                            cards.put(term, definition);
                            mistakes.put(term, numMistakes);

                            numCards -= 1;
                        }


                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    println("File not found.");
                } catch (Exception e) {
                    println("Error." + e.toString());
                } finally {

                    // Always close the BufferedReader
                    if (br != null) {
                        try {
                            br.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        
                    }
                }

                println(numCardsOriginal + " cards have been loaded.");

            } else if (s.contains("export")) {
                // fileNameExport = args[0];
                File file = new File(fileNameExport);
                BufferedWriter bf = null;
                try {

                    // create new BufferedWriter for the output file
                    bf = new BufferedWriter(new FileWriter(file));

                    bf.write(String.valueOf(cards.size()));
                    bf.newLine();

                    // iterate map entries
                    for (Map.Entry<String, String> entry : cards.entrySet()) {
                        bf.write(entry.getKey());
                        bf.newLine();
                        bf.write(entry.getValue());
                        bf.newLine();
                        bf.write(String.valueOf(mistakes.getOrDefault(entry.getKey(), 0)));
                        bf.newLine();
                    }

                    bf.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    println("Exception!!!!! while saving file");
                } catch (Exception e) {
                    println("Exception!!!!! while saving file");
                } finally {

                    try {

                        // always close the writer
                        bf.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                println(cards.size() + " cards have been saved.");
            }
        }


        boolean bool = true;

        while (bool) {

            println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");

            String input = scanner();

            switch (input) {
                case "add": {

                    println("The card:");
                    int mist = 0;
                    // boolean termExist = false;

                    // while (!termExist) {
                    String term = scanner();
                    if (cards.containsKey(term)) {
                        println("The card " + "\"" + term + "\"" + " already exists.");
                        break;

                    }

                    println("The definition of the card:");

                    String definition = scanner();

                    if (cards.containsValue(definition)) {
                        println("The definition " + "\"" + definition + "\"" + " already exists.");
                        break;
                    } else {
                        // defExist = true;
                        cards.put(term, definition);
                        mistakes.put(term, mist);
                        println("The pair (" + "\"" + term + "\"" + ":" + "\"" + definition + "\"" + ") has been added.");
                    }


                    // }
                    break;
                }

                case "remove": {

                    println("Which card?");

                    String term = scanner();

                    if (cards.containsKey(term)) {
                        cards.remove(term);
                        mistakes.remove(term);
                        println("The card has been removed.");
                    } else {
                        println("Can't remove " + "\"" + term + "\"" + " : there is no such card.");
                    }

                    break;
                }

                case "import": {

                    println("File name:");
                    String fileName = scanner();
                    BufferedReader br = null;

                    Integer numCardsOriginal = 0;
                    try {

                        // create file object

                        File file = new File(fileName);

                        // create BufferedReader object from the File
                        br = new BufferedReader(new FileReader(file));

                        String numCardsStr = br.readLine();
                        Integer numCards = Integer.parseInt(numCardsStr);
                        numCardsOriginal = numCards;

                        while (numCards != 0) {
                            String term = br.readLine();
                            String definition = br.readLine();
                            Integer numMistakes = Integer.parseInt(br.readLine());

                            if (cards.containsKey(term) && cards.containsValue(definition)) {
                                for (Map.Entry<String, Integer> entry : mistakes.entrySet()) {
                                    mistakes.put(entry.getKey(), numMistakes);
                                    numCards -= 1;
                                }

                            } else {
                                cards.put(term, definition);
                                mistakes.put(term, numMistakes);

                                numCards -= 1;
                            }


                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        println("File not found.");
                    } catch (Exception e) {
                        println("Error." + e.toString());
                    } finally {

                        // Always close the BufferedReader
                        if (br != null) {
                            try {
                                br.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    println(numCardsOriginal + " cards have been loaded.");

                    break;
                }

                case "export": {
                    println("File name:");
                    String filePath = scanner();

                    File file = new File(filePath);
                    BufferedWriter bf = null;
                    try {

                        // create new BufferedWriter for the output file
                        bf = new BufferedWriter(new FileWriter(file));

                        bf.write(String.valueOf(cards.size()));
                        bf.newLine();

                        // iterate map entries
                        for (Map.Entry<String, String> entry : cards.entrySet()) {
                            bf.write(entry.getKey());
                            bf.newLine();
                            bf.write(entry.getValue());
                            bf.newLine();
                            bf.write(String.valueOf(mistakes.getOrDefault(entry.getKey(), 0)));
                            bf.newLine();
                        }

                        bf.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                        println("Exception!!!!! while saving file");
                    } catch (Exception e) {
                        println("Exception!!!!! while saving file");
                    } finally {

                        try {

                            // always close the writer
                            bf.close();
                        } catch (Exception e) {
                        }
                    }

                    println(cards.size() + " cards have been saved.");

                    break;
                }

                case "ask": {

                    println("How many times to ask?");
                    String number = scanner();
                    int num = Integer.parseInt(number);


                    int repeat = 0;

                    while (repeat != num) {
                        for (Map.Entry<String, String> card : cards.entrySet()) {
                            println("Print the definition of " + "\"" + card.getKey() + "\"");
                            String answer = scanner();
                            if (answer.equals(card.getValue())) {
                                println("Correct!");
                            } else if (cards.containsValue(answer)) {

                                println("Wrong. The right answer is " + "\"" + card.getValue() + "\""
                                        + " but your definition is correct for " + "\"" + getKeyForValue(cards, answer) + "\"");


                                incrementValue(mistakes, card.getKey());


                            } else {

                                println("Wrong. The right answer is " + "\"" + card.getValue() + "\"");

                                incrementValue(mistakes, card.getKey());

                            }
                            repeat += 1;
                        }
                    }

                    break;
                }

                case "exit": {
                    println("Bye bye!");
                    File file = new File("fridayThe13th.txt");
                    BufferedWriter bf = null;
                    try {

                        // create new BufferedWriter for the output file
                        bf = new BufferedWriter(new FileWriter(file));

                        bf.write(String.valueOf(cards.size()));
                        bf.newLine();

                        // iterate map entries
                        for (Map.Entry<String, String> entry : cards.entrySet()) {
                            bf.write(entry.getKey());
                            bf.newLine();
                            bf.write(entry.getValue());
                            bf.newLine();
                            bf.write(String.valueOf(mistakes.getOrDefault(entry.getKey(), 0)));
                            bf.newLine();
                        }

                        bf.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                        println("Exception!!!!! while saving file");
                    } catch (Exception e) {
                        println("Exception!!!!! while saving file");
                    } finally {

                        try {

                            // always close the writer
                            bf.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    println(cards.size() + " cards have been saved.");



                    bool = false;


                    break;
                }

                case "log": {
                    println("File name:");
                    String filePath = scanner();
                  
                    try {
                        FileWriter fw = new FileWriter(filePath);
                        Writer output = new BufferedWriter(fw);

                        for (int i = 0; i < inMemoryLog.size(); i++){
                            output.write(inMemoryLog.get(i) + System.lineSeparator());
                            output.write("\n");
                        }

                        output.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    println("The log has been saved.");


                    break;
                }

                case "hardest card": {

                    int max = Integer.MIN_VALUE;
                    String name = null;

                    for (Map.Entry<String, Integer> entry : mistakes.entrySet()) {
                        if (entry.getValue() > max) {
                            max = entry.getValue();
                            name = entry.getKey();

                        }
                    }

                    List<String> keys = new ArrayList<>();
                    for (Map.Entry<String, Integer> entry : mistakes.entrySet()) {
                        if (entry.getValue() == max) {
                            keys.add(entry.getKey());

                        }
                    }



                    if (max == 0 || mistakes.isEmpty()) {
                        println("There are no cards with errors.");
                   } else if (max == 1 || keys.size() == 1){
                    println("The hardest card is " + "\"" + name + "\"" + ". You have " + max + " errors answering it.");
                    } else {
                        String formattedString = keys.toString().replace(", ", "\", \"").replace("[", "\"").replace("]", "\"").trim();
                        println("The hardest cards are " +   formattedString +  ". You have " + max + " errors answering them.");

                    }

                    break;
                }

                case "reset stats": {
                    for (Map.Entry<String, Integer> entry : mistakes.entrySet()) {
                        mistakes.put(entry.getKey(), 0);
                    }
                    println("Card statistics have been reset.");
                    break;
                }

            }


        }


    }


    static String getKeyForValue(Map<String, String> cards, String value) {
        for (Map.Entry<String, String> card : cards.entrySet()) {
            if (card.getValue().equals(value)) {
                return card.getKey();
            }
        }

        return "";
    }

    public static <String> void incrementValue(Map<String, Integer> map, String key) {
        // get value of the specified key
        Integer count = map.get(key);

        // if the map contains no mapping for the key, then
        // map the key with value of 1
        if (count == null) {
            map.put(key, 1);
        }
        // else increment the found value by 1
        else {
            map.put(key, count + 1);
        }
    }
}







