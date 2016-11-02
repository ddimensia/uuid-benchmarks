package com.github.cchacin;

import java.security.SecureRandom;
import java.util.UUID;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.NoArgGenerator;

/**
 * UUID generator that uses a thread-local SecureRandom instance to reduce thread blocking on the nextBytes synchronized method.
 *
 * @author Gil Markham (gil@groupon.com)
 * @since 2.9.0
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
