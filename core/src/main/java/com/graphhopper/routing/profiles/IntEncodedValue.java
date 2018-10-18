/*
 *  Licensed to GraphHopper GmbH under one or more contributor
 *  license agreements. See the NOTICE file distributed with this work for
 *  additional information regarding copyright ownership.
 *
 *  GraphHopper GmbH licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except in
 *  compliance with the License. You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.graphhopper.routing.profiles;

import com.graphhopper.storage.IntsRef;

/**
 * This class defines where to store an unsigned integer. It is important to note that: 1. the range of the integer is
 * highly limited (unlike the Java 32bit integer values) so that the storeable part of it fits into the
 * specified number of bits (using the internal shift value) and 2. the 'raw' default value is always 0.
 * <p>
 * To illustrate why the default is always 0 and how you can still use other defaults imagine the storage engine
 * creates a new entry. Either the engine knows the higher level logic or we assume the default value is 0 and
 * map this value to the real value on every retrieval request.
 * <p>
 * How could you then implement e.g. a 'priority' value going from [-3, 3] that maps to [0,7] but should
 * have a default value of 3 instead of 0? Either you waste space and map this to [1,7], which means that 0 and 3 both
 * refer to the same 0 value (currently the preferred method due to its simplicity) or you could create a
 * MappedIntEncodedValue class that holds an array or a Map with the raw integers similarly to what EnumEncodedValue does:
 * {0: 0, 1: -3, 2: -2, 3: -1, 4: 1, 5: 2, 6: 3}
 */
public interface IntEncodedValue extends EncodedValue {

    /**
     * This method restores the integer value from the specified 'flags' taken from the storage.
     */
    int getInt(boolean reverse, IntsRef ref);

    /**
     * This method stores the specified integer value in the specified IntsRef.
     */
    void setInt(boolean reverse, IntsRef ref, int value);
}