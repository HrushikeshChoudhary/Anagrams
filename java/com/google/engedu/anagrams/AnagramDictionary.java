/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 5;
    private Random random = new Random();

    ArrayList<String> wordList = new ArrayList<String>();
    HashSet<String> wordSet = new HashSet<String>();
    HashMap<String,ArrayList> lettersToWord = new HashMap<>();
    String key;

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);
            ArrayList<String> al = new ArrayList<String>();
            key = sortLetters(word);
            if(!lettersToWord.containsKey(key)){
                al.add(word);
                lettersToWord.put(key,al);
            }
            else{
                al = (ArrayList) lettersToWord.get(key);
                al.add(word);
                lettersToWord.put(key, al);
            }
        }
    }

    public boolean isGoodWord(String word, String base) {

        for (String dictionaryWord: wordSet) {
            if (dictionaryWord.equals(word)) {
                if (!word.toLowerCase().contains(base.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        for (String words: wordList) {
            if (words.length() == targetWord.length()) {
                if (sortLetters(words).equals(sortLetters(targetWord))) {
                    result.add(targetWord);
                }
            }
        }
        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<String> resultant;
        for (char extraLetter = 'a';extraLetter<='z';extraLetter++) {
            String extendedWord = word + extraLetter;
            String extendedKey = sortLetters(extendedWord);
            if (lettersToWord.containsKey(extendedKey)) {
                resultant = new ArrayList<String>();
                resultant= (ArrayList<String>) lettersToWord.get(extendedKey);
                for(int i = 0;i< resultant.size();i++)
                    result.add(String.valueOf(resultant.get(i)));
            }
        }
        return result;
    }

    public String pickGoodStarterWord() {

        int num;
        String randomWord = null;
        ArrayList res;
        boolean goOn = true;
        while(goOn) {
            num = random.nextInt(9000) + 1;
            randomWord = (String) wordList.get(num);
            res = new ArrayList();
            res = (ArrayList) lettersToWord.get(sortLetters(randomWord));
            if(res.size() >= MIN_NUM_ANAGRAMS && randomWord.length() > MAX_WORD_LENGTH){
                goOn = false;

            }
        }
        return randomWord;
    }


    public String sortLetters(String orignalString) {

        char[] orignalCharString = orignalString.toCharArray();
        Arrays.sort(orignalCharString);
        String sortedString = new String(orignalCharString);
        return sortedString;
    }
}
