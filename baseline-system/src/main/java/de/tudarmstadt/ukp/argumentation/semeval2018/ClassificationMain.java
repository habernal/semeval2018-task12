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

import de.tudarmstadt.ukp.argumentation.semeval2018.baseline.RandomClassifier;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * (c) 2017 Ivan Habernal
 */
public class ClassificationMain
{
    public static Set<LabeledInstance> loadTrainingData(InputStream inputStream)
            throws IOException
    {
        Set<LabeledInstance> result = new HashSet<>();

        List<String> lines = IOUtils.readLines(inputStream, "utf-8");
        IOUtils.closeQuietly(inputStream);

        for (String line : lines) {
            if (!line.startsWith("#")) {
                result.add(new LabeledInstance(line));
            }
        }

        return result;
    }

    public static Set<UnlabeledInstance> loadTestData(InputStream inputStream)
            throws IOException
    {
        Set<UnlabeledInstance> result = new HashSet<>();

        List<String> lines = IOUtils.readLines(inputStream, "utf-8");
        IOUtils.closeQuietly(inputStream);

        for (String line : lines) {
            if (!line.startsWith("#")) {
                result.add(new UnlabeledInstance(line));
            }
        }

        return result;
    }

    public static void writeTestResultsToFile(OutputStream outputStream,
            Set<LabeledInstance> labeledInstances)
            throws IOException
    {
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(outputStream, "utf-8"));
        pw.println("#id\tcorrectLabelW0orW1");
        for (LabeledInstance instance : labeledInstances) {
            String id = instance.getId();
            Integer label = instance.getCorrectLabelW0orW1();

            if (label == null) {
                throw new IllegalStateException("Instance " + id + " has null predicted label");
            }

            pw.printf(Locale.ENGLISH, "%s\t%d%n", id, label);
        }

        pw.flush();
        IOUtils.closeQuietly(pw);
    }

    public static void runExperiment(AbstractClassifier classifier, File trainingData,
            File testData, File outputFile)
            throws IOException
    {
        // load training data
        Set<LabeledInstance> training = loadTrainingData(new FileInputStream(trainingData));
        Set<UnlabeledInstance> test = loadTestData(new FileInputStream(testData));

        // train
        classifier.train(training);

        // test
        Set<LabeledInstance> predictedInstances = classifier.predict(test);

        // write output to file
        writeTestResultsToFile(new FileOutputStream(outputFile), predictedInstances);
    }

    public static void main(String[] args)
            throws IOException
    {
        File trainingData = new File(args[0]); // train/train-full.txt
        File testData = new File(args[1]);     // dev/dev-only-data.txt
        File outputFile = new File(args[2]);   // /tmp/output-predictions.txt

        AbstractClassifier classifier = new RandomClassifier();

        runExperiment(classifier, trainingData, testData, outputFile);
    }
}
