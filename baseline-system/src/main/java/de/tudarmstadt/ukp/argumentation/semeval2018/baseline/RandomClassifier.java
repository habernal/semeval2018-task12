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

package de.tudarmstadt.ukp.argumentation.semeval2018.baseline;

import de.tudarmstadt.ukp.argumentation.semeval2018.AbstractClassifier;
import de.tudarmstadt.ukp.argumentation.semeval2018.LabeledInstance;
import de.tudarmstadt.ukp.argumentation.semeval2018.UnlabeledInstance;

import java.util.Random;
import java.util.Set;

/**
 * @author Ivan Habernal
 */
public class RandomClassifier
        extends AbstractClassifier
{
    private final Random random = new Random(123456);

    @Override
    protected LabeledInstance makePrediction(UnlabeledInstance instance)
    {
        // copy unlabeled instance
        LabeledInstance predictedInstance = new LabeledInstance(instance);

        // make a random prediction -- 0 or 1
        int correctLabelW0orW1 = random.nextInt(2);

        // set the the resulting instance
        predictedInstance.setPredictedLabelW0orW1(correctLabelW0orW1);

        return predictedInstance;
    }

    @Override
    protected void train(Set<LabeledInstance> trainingData)
    {
        // no training
    }
}
