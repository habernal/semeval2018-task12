/*
 * Copyright 2017
 * Ubiquitous Knowledge Processing (UKP) Lab
 * Technische Universität Darmstadt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.tudarmstadt.ukp.argumentation.semeval2018;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Official scorer for SemEval 2018 Task 12 - The Argument Reasoning Comprehension Task.
 * <p>
 * The scorer can also run locally to test the systems outside CodaLab:
 * <pre>
 * java -jar scorer-1.0-SNAPSHOT.jar -local gold-data-only-labels.txt your-predictions.txt
 * </pre>
 *
 * @author Ivan Habernal
 */
public class Scorer
{
    private static final Charset UTF8 = Charset.forName("utf-8");

    /**
     * Reads a map of (instanceID, gold label) from a text file. Each instance is on a separate
     * line, starting with instance ID, then whitespace (space or tabs) and then 0 or 1 (the gold
     * label)
     *
     * @param file file
     * @return a map (never null)
     */
    public static Map<String, Integer> readLabelsFromFile(File file)
            throws IOException
    {
        FileInputStream inputStream = new FileInputStream(file);
        List<String> lines = IOUtils.readLines(inputStream, UTF8);
        IOUtils.closeQuietly(inputStream);

        Map<String, Integer> result = new TreeMap<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            // only for non-empty lines and non-comments
            if (!line.trim().isEmpty() && !line.trim().startsWith("#")) {
                String[] split = line.split("\\s+");

                if (split.length != 2) {
                    throw new IllegalArgumentException("Error on line " + (i + 1)
                            + ", expected two whitespace-delimited entries but got '" + line
                            + "' (file "
                            + file.getAbsolutePath() + ")");
                }

                String id = split[0].trim();
                int value;

                try {
                    value = Integer.valueOf(split[1]);
                }
                catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Error on line " + (i + 1)
                            + ", expected an integer but got '" + split[1] + "' (file "
                            + file.getAbsolutePath() + ")");
                }

                if (!(value == 0 || value == 1)) {
                    throw new IllegalArgumentException("Error on line " + (i + 1)
                            + ", expected 0 or 1 but got '" + split[1] + "' (file "
                            + file.getAbsolutePath() + ")");
                }

                result.put(id, value);
            }
        }

        return result;
    }

    public static double computeAccuracy(Map<String, Integer> gold,
            Map<String, Integer> predictions)
            throws IllegalArgumentException
    {
        if (gold == null) {
            throw new IllegalArgumentException("Parameter 'gold' is null");
        }

        if (predictions == null) {
            throw new IllegalArgumentException("Parameter 'predictions' is null");
        }

        if (gold.isEmpty()) {
            throw new IllegalArgumentException("Parameter 'gold' is an empty map");
        }

        if (predictions.isEmpty()) {
            throw new IllegalArgumentException("Parameter 'predictions' is an empty map");
        }

        if (!(gold.keySet().containsAll(predictions.keySet()) && predictions.keySet()
                .containsAll(gold.keySet()))) {
            throw new IllegalArgumentException(
                    "Gold set and predictions contain different instance IDs");
        }

        Set<String> correctPredictions = new TreeSet<>();
        Set<String> wrongPredictions = new TreeSet<>();

        for (String id : gold.keySet()) {
            int goldLabel = gold.get(id);
            int predictedLabel = predictions.get(id);

            if (goldLabel == predictedLabel) {
                correctPredictions.add(id);
            }
            else {
                wrongPredictions.add(id);
            }
        }

        return ((double) correctPredictions.size()) / ((double) correctPredictions.size()
                + wrongPredictions.size());
    }

    public static void main(String[] args)
            throws IOException
    {

        if (args.length != 3) {
            throw new IOException("Expected 3 command-line arguments, got " + args.length);
        }

        String evaluationType = args[0];

        File goldFile;
        File resultFile;
        PrintWriter resultWriter;

        if ("-codalab".equals(evaluationType)) {
            goldFile = new File(args[1] + "/ref/truth.txt");
            resultFile = new File(args[1] + "/res/answer.txt");
            resultWriter = new PrintWriter(new File(args[2] + "/scores.txt"));
        }
        else if ("-local".equals(evaluationType)) {
            goldFile = new File(args[1]);
            resultFile = new File(args[2]);
            resultWriter = new PrintWriter(System.out);
        }
        else {
            throw new IOException("Expected '-codalab' or '-local' as the first argument but got '"
                    + evaluationType + "'");
        }

        Map<String, Integer> gold = Scorer.readLabelsFromFile(goldFile);
        Map<String, Integer> predictions = Scorer.readLabelsFromFile(resultFile);

        // error rate
        double result = computeAccuracy(gold, predictions);

        // write to the output
        resultWriter.write(String.format(Locale.ENGLISH, "Accuracy: %.3f%n", result));
        resultWriter.flush();

        // close the file
        if ("-codalab".equals(evaluationType)) {
            IOUtils.closeQuietly(resultWriter);
        }
    }
}
