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

package org.apache.camel.kamelets.utils.format.converter.google.storage;

import org.apache.camel.Message;
import org.apache.camel.component.aws2.s3.AWS2S3Constants;
import org.apache.camel.component.google.storage.GoogleCloudStorageConstants;
import org.apache.camel.kamelets.utils.format.converter.utils.CloudEvents;
import org.apache.camel.spi.DataType;
import org.apache.camel.spi.DataTypeTransformer;
import org.apache.camel.spi.Transformer;

import java.util.Map;

/**
 * Output data type represents Google Storage downloadTo response as CloudEvent V1. The data type sets Camel specific
 * CloudEvent headers on the exchange.
 */
@DataTypeTransformer(name = "google-storage:application-cloudevents")
public class GoogleStorageCloudEventOutputType extends Transformer {

    @Override
    public void transform(Message message, DataType fromType, DataType toType) {
        final Map<String, Object> headers = message.getHeaders();

        headers.put(CloudEvents.CAMEL_CLOUD_EVENT_ID, message.getExchange().getExchangeId());
        headers.put(CloudEvents.CAMEL_CLOUD_EVENT_TYPE, "org.apache.camel.event.google.storage.downloadTo");
        headers.put(CloudEvents.CAMEL_CLOUD_EVENT_SOURCE, "google.storage.bucket." + message.getHeader(GoogleCloudStorageConstants.BUCKET_NAME, String.class));
        headers.put(CloudEvents.CAMEL_CLOUD_EVENT_SUBJECT, message.getHeader(GoogleCloudStorageConstants.OBJECT_NAME, String.class));
        headers.put(CloudEvents.CAMEL_CLOUD_EVENT_TIME, CloudEvents.getEventTime(message.getExchange()));
    }
}