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

/**
 * A simple container for a single instance for the task (corresponds to a single row in the data)
 * with known gold label.
 *
 * @author Ivan Habernal
 */
public class LabeledInstance
        extends UnlabeledInstance
{
    private Integer correctLabelW0orW1;

    /**
     * Creates a deep copy of the given instance
     *
     * @param original original instance; remains unchanged
     */
    public LabeledInstance(UnlabeledInstance original)
    {
        super(original);
    }

    /**
     * Integrity of all fields is checked, empty fields are not allowed.
     *
     * @throws IllegalArgumentException if any field is blank or outside its allowed range
     */
    public void setPredictedLabelW0orW1(Integer correctLabelW0orW1)
            throws IllegalArgumentException
    {
        if (correctLabelW0orW1 < 0 || correctLabelW0orW1 > 1) {
            throw new IllegalArgumentException("Wrong parameter: correctLabelW0orW1");
        }

        this.correctLabelW0orW1 = correctLabelW0orW1;
    }

    /**
     * Creates an instance by parsing a single line from the TSV (txt) file provided for the task
     *
     * @param tsvLine a single line from the txt file
     */
    public LabeledInstance(String tsvLine)
    {
        super(tsvLine.split("\t")[0], tsvLine.split("\t")[1], tsvLine.split("\t")[2],
                tsvLine.split("\t")[4],
                tsvLine.split("\t")[5], tsvLine.split("\t")[6], tsvLine.split("\t")[7]);

        setPredictedLabelW0orW1(Integer.valueOf(tsvLine.split("\t")[3]));
    }

    public Integer getCorrectLabelW0orW1()
    {
        return correctLabelW0orW1;
    }
}
