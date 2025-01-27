/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.aws2.kinesis;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.camel.Converter;
import software.amazon.awssdk.services.kinesis.model.Record;

// Allow to ignore this type converter if the kinesis JARs are not present on the classpath
@Converter(generateLoader = true, ignoreOnLoadError = true)
public final class RecordStringConverter {

    private RecordStringConverter() {
    }

    @Converter
    public static String toString(Record record) {
        Charset charset = StandardCharsets.UTF_8;

        ByteBuffer buffer = record.data().asByteBuffer();
        if (buffer.hasArray()) {
            byte[] bytes = record.data().asByteArray();
            return new String(bytes, charset);
        } else {
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            return new String(bytes, charset);
        }
    }

}
