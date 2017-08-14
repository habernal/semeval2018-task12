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

import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author Ivan Habernal
 */
public class ScorerTest
{
    @Test
    public void readLabelsFromFile1()
            throws Exception
    {
        Scorer.readLabelsFromFile(getFileFromResources("test-gold1.txt"));
    }

    @Test
    public void readLabelsFromFile2()
            throws Exception
    {
        Scorer.readLabelsFromFile(getFileFromResources("test-gold2.txt"));
    }


    @Test(expected = IllegalArgumentException.class)
    public void readLabelsFromFileWrong1()
            throws Exception
    {
        Scorer.readLabelsFromFile(getFileFromResources("test-wrong1.txt"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void readLabelsFromFileWrong2()
            throws Exception
    {
        Scorer.readLabelsFromFile(getFileFromResources("test-wrong2.txt"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void readLabelsFromFileWrong3()
            throws Exception
    {
        Scorer.readLabelsFromFile(getFileFromResources("test-wrong3.txt"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void computeAccuracyWrong()
            throws Exception
    {
        Map<String, Integer> gold = Scorer
                .readLabelsFromFile(getFileFromResources("test-gold1.txt"));

        Map<String, Integer> predictions = Scorer
                .readLabelsFromFile(getFileFromResources("test-predictions-wrong1.txt"));

        Scorer.computeAccuracy(gold, predictions);
    }

    @Test
    public void computeAccuracy()
            throws Exception
    {
        Map<String, Integer> gold = Scorer
                .readLabelsFromFile(getFileFromResources("test-gold1.txt"));

        // 60% accuracy
        Map<String, Integer> predictions1 = Scorer
                .readLabelsFromFile(getFileFromResources("test-predictions1.txt"));
        assertEquals(0.6, Scorer.computeAccuracy(gold, predictions1), 0.01);

        // 100% accuracy
        Map<String, Integer> predictions2 = Scorer
                .readLabelsFromFile(getFileFromResources("test-predictions2.txt"));
        assertEquals(1.0, Scorer.computeAccuracy(gold, predictions2), 0.01);

        // 0% accuracy
        Map<String, Integer> predictions3 = Scorer
                .readLabelsFromFile(getFileFromResources("test-predictions3.txt"));
        assertEquals(0.0, Scorer.computeAccuracy(gold, predictions3), 0.01);

    }

    private File getFileFromResources(String name)
    {
        URL resource = this.getClass().getClassLoader().getResource(name);

        if (resource == null) {
            throw new IllegalArgumentException("Resource " + name + " not found on the classpath");
        }

        return new File(resource.getFile());
    }

}