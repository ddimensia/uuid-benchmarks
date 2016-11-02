/**
 * Copyright 2016 Groupon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */package com.github.cchacin;

import java.security.SecureRandom;
import java.util.UUID;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.NoArgGenerator;

/**
 * UUID generator that uses a thread-local SecureRandom instance to reduce thread blocking on the nextBytes synchronized method.
 *
 * @author Gil Markham (gil@groupon.com)
 */
public final class ThreadLocalUUIDGenerator {
    private static final ThreadLocal<NoArgGenerator> THREAD_LOCAL_GENERATOR = new ThreadLocal<NoArgGenerator>() {
        @Override
        protected NoArgGenerator initialValue() {
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(new byte[1]); // Force seeding
            return Generators.randomBasedGenerator(secureRandom);
        }
    };

    private ThreadLocalUUIDGenerator() {
        // Utility class, should not be constructed
    }

    public static UUID generateUUID() {
        return THREAD_LOCAL_GENERATOR.get().generate();
    }
}
