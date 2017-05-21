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
package org.keyboardplaying.demo.people;

import de.flapdoodle.embed.mongo.MongoImportExecutable;
import de.flapdoodle.embed.mongo.MongoImportProcess;
import de.flapdoodle.embed.mongo.MongoImportStarter;
import de.flapdoodle.embed.mongo.config.*;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * Configuration for the testing embedded Mongo repository.
 *
 * @author Cyrille Chopelet
 */
@Configuration
public class PeopleRepositoryTestConfiguration {

    @Value("${flapdoodle.database}")
    private String testDbName;

    @Value("${flapdoodle.host}")
    private String testDbHost;

    @Value("${flapdoodle.port}")
    private int testDbPort;

    @Bean
    public IMongodConfig mongodConfig() throws IOException {
        return new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(testDbHost, testDbPort, Network.localhostIsIPv6()))
                .build();
    }

    @Autowired
    public void importMockData(PeopleRepository repository) {
        List<Person> people = new ArrayList<>();
        people.add(new Person("CCH", "Cyril", "Chopelet", Person.Gender.MALE, LocalDate.of(1985, Month.OCTOBER, 24)));
        people.add(new Person("JDO", "John", "Doe", Person.Gender.MALE, LocalDate.of(1960, Month.AUGUST, 26)));
        people.add(new Person("SCA", "Samantha", "Carter", Person.Gender.FEMALE, null));
        repository.save(people);
    }

    // FIXME import mock data here
    //@Bean(destroyMethod = "stop")
    public MongoImportProcess mongoImportProcess() throws URISyntaxException, IOException {
        return startMongoImport(getResourceFileUri("mongo-test-people.json"), "people", true, true, true);
    }

    private String getResourceFileUri(String fileName) throws URISyntaxException {
        return this.getClass().getResource("/" + fileName).toString();
    }

    private MongoImportProcess startMongoImport(String jsonFile, String collection, Boolean jsonArray, Boolean upsert, Boolean drop)
            throws IOException {
        IMongoImportConfig mongoImportConfig = new MongoImportConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(testDbHost, testDbPort, Network.localhostIsIPv6()))
                .db(testDbName)
                .collection(collection)
                .upsert(upsert)
                .dropCollection(drop)
                .jsonArray(jsonArray)
                .importFile(jsonFile)
                .build();

        MongoImportExecutable mongoImportExecutable = MongoImportStarter.getDefaultInstance().prepare(mongoImportConfig);
        return mongoImportExecutable.start();
    }
}
