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

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * A simple container for a single instance for the task (corresponds to a single row in the data)
 * with unknown gold label. Since IDs unique across the data, they are solely used for compare(),
 * hash(), and equal() methods.
 *
 * @author Ivan Habernal
 */
public class UnlabeledInstance
        implements Comparable<UnlabeledInstance>
{
    private final String id;
    private final String warrant0;
    private final String warrant1;
    private final String reason;
    private final String claim;
    private final String debateTitle;
    private final String debateInfo;

    /**
     * Creates a deep copy of the given instance
     *
     * @param original original instance; remains unchanged
     */
    public UnlabeledInstance(UnlabeledInstance original)
    {
        this.id = original.id;
        this.warrant0 = original.warrant0;
        this.warrant1 = original.warrant1;
        this.reason = original.reason;
        this.claim = original.claim;
        this.debateTitle = original.debateTitle;
        this.debateInfo = original.debateInfo;
    }

    /**
     * Integrity of all fields is checked, empty fields are not allowed.
     *
     * @throws IllegalArgumentException if any field is blank or outside its allowed range
     */
    public UnlabeledInstance(String id, String warrant0, String warrant1, String reason,
            String claim, String debateTitle, String debateInfo)
            throws IllegalArgumentException
    {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("Blank parameter: id");
        }
        if (StringUtils.isBlank(warrant0)) {
            throw new IllegalArgumentException("Blank parameter: warrant0");
        }
        if (StringUtils.isBlank(warrant1)) {
            throw new IllegalArgumentException("Blank parameter: warrant1");
        }
        if (StringUtils.isBlank(reason)) {
            throw new IllegalArgumentException("Blank parameter: reason");
        }
        if (StringUtils.isBlank(claim)) {
            throw new IllegalArgumentException("Blank parameter: claim");
        }
        if (StringUtils.isBlank(debateTitle)) {
            throw new IllegalArgumentException("Blank parameter: debateTitle");
        }
        if (StringUtils.isBlank(debateInfo)) {
            throw new IllegalArgumentException("Blank parameter: debateInfo");
        }

        this.id = id;
        this.warrant0 = warrant0;
        this.warrant1 = warrant1;
        this.reason = reason;
        this.claim = claim;
        this.debateTitle = debateTitle;
        this.debateInfo = debateInfo;
    }

    /**
     * Creates an instance by parsing a single line from the TSV (txt) file provided for the task
     *
     * @param tsvLine a single line from the txt file
     */
    public UnlabeledInstance(String tsvLine)
    {
        this(tsvLine.split("\t")[0], tsvLine.split("\t")[1], tsvLine.split("\t")[2],
                tsvLine.split("\t")[3],
                tsvLine.split("\t")[4], tsvLine.split("\t")[5], tsvLine.split("\t")[6]);
    }

    public String getId()
    {
        return id;
    }

    public String getWarrant0()
    {
        return warrant0;
    }

    public String getWarrant1()
    {
        return warrant1;
    }

    public String getReason()
    {
        return reason;
    }

    public String getClaim()
    {
        return claim;
    }

    public String getDebateTitle()
    {
        return debateTitle;
    }

    public String getDebateInfo()
    {
        return debateInfo;
    }

    @Override
    public int compareTo(UnlabeledInstance o)
    {
        return this.id.compareTo(o.id);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LabeledInstance)) {
            return false;
        }
        LabeledInstance that = (LabeledInstance) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getId());
    }
}
