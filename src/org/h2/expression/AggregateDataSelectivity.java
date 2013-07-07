/*
 * Copyright 2004-2013 H2 Group. Multiple-Licensed under the H2 License,
 * Version 1.0, and under the Eclipse Public License, Version 1.0
 * (http://h2database.com/html/license.html).
 * Initial Developer: H2 Group
 */
package org.h2.expression;

import org.h2.engine.Constants;
import org.h2.engine.Database;
import org.h2.util.IntIntHashMap;
import org.h2.value.Value;
import org.h2.value.ValueInt;

/**
 * Data stored while calculating a SELECTIVITY aggregate.
 */
class AggregateDataSelectivity extends AggregateData {
    private long count;
    private IntIntHashMap distinctHashes;
    private double m2;

    @Override
    void add(Database database, int dataType, boolean distinct, Value v) {
        //是基于某个表达式(多数是单个字段)算不重复的记录数所占总记录数的百分比
        //org.h2.engine.Constants.SELECTIVITY_DISTINCT_COUNT默认是1万，这个值不能改，
        //对统计值影响很大。通常这个值越大，统计越精确，但是会使用更多内存。
        //SELECTIVITY越大，说明重复的记录越少，在选择索引时更有利。
        count++;
        if (distinctHashes == null) {
            distinctHashes = new IntIntHashMap();
        }
        int size = distinctHashes.size();
        if (size > Constants.SELECTIVITY_DISTINCT_COUNT) {
            distinctHashes = new IntIntHashMap();
            m2 += size;
        }
        int hash = v.hashCode();
        // the value -1 is not supported
        distinctHashes.put(hash, 1);
    }

    @Override
    Value getValue(Database database, int dataType, boolean distinct) {
        if (distinct) {
            count = 0;
        }
        Value v = null;
        int s = 0;
        if (count == 0) {
            s = 0;
        } else {
            m2 += distinctHashes.size();
            m2 = 100 * m2 / count;
            s = (int) m2;
            s = s <= 0 ? 1 : s > 100 ? 100 : s;
        }
        v = ValueInt.get(s);
        return v.convertTo(dataType);
    }
}
