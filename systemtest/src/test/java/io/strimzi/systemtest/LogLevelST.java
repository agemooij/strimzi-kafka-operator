/*
 * Copyright 2018, Strimzi authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.strimzi.systemtest;

import io.strimzi.systemtest.timemeasuring.Operation;
import io.strimzi.systemtest.timemeasuring.TimeMeasuringSystem;
import io.strimzi.test.ClusterOperator;
import io.strimzi.test.Namespace;
import io.strimzi.test.StrimziExtension;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static io.strimzi.test.k8s.BaseKubeClient.STATEFUL_SET;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(StrimziExtension.class)
@Namespace(LogLevelST.NAMESPACE)
@ClusterOperator
class LogLevelST extends AbstractST {
    static final String NAMESPACE = "log-level-cluster-test";
    private static final Logger LOGGER = LogManager.getLogger(LogLevelST.class);
    private static final String TESTED_LOGGER = "kafka.root.logger.level";
    private static final String CONFIG_MAP_NAME = String.format("%s-%s", CLUSTER_NAME, "kafka-config");

    @Test
    void testLogLevelInfo() {
        operationID = startTimeMeasuring(Operation.TEST_EXECUTION);
        String logLevel = "INFO";
        LOGGER.info("Running testLogLevelInfo in namespace {}", NAMESPACE);
        createKafkaPods(logLevel);
        TimeMeasuringSystem.stopOperation(operationID);
        int seconds = TimeMeasuringSystem.getDurationInSecconds(testClass, testName, operationID);
        assertTrue(checkKafkaLogLevel(logLevel, seconds), "Kafka's log level is set properly");
    }

    @Test
    @Tag("release")
    void testLogLevelError() {
        operationID = startTimeMeasuring(Operation.TEST_EXECUTION);
        String logLevel = "ERROR";
        LOGGER.info("Running testLogLevelInfo in namespace {}", NAMESPACE);
        createKafkaPods(logLevel);
        TimeMeasuringSystem.stopOperation(operationID);
        int seconds = TimeMeasuringSystem.getDurationInSecconds(testClass, testName, operationID);
        assertTrue(checkKafkaLogLevel(logLevel, seconds), "Kafka's log level is set properly");
    }

    @Test
    @Tag("release")
    void testLogLevelWarn() {
        operationID = startTimeMeasuring(Operation.TEST_EXECUTION);
        String logLevel = "WARN";
        LOGGER.info("Running testLogLevelInfo in namespace {}", NAMESPACE);
        createKafkaPods(logLevel);
        TimeMeasuringSystem.stopOperation(operationID);
        int seconds = TimeMeasuringSystem.getDurationInSecconds(testClass, testName, operationID);
        assertTrue(checkKafkaLogLevel(logLevel, seconds), "Kafka's log level is set properly");
    }

    @Test
    @Tag("release")
    void testLogLevelTrace() {
        operationID = startTimeMeasuring(Operation.TEST_EXECUTION);
        String logLevel = "TRACE";
        LOGGER.info("Running testLogLevelInfo in namespace {}", NAMESPACE);
        createKafkaPods(logLevel);
        TimeMeasuringSystem.stopOperation(operationID);
        int seconds = TimeMeasuringSystem.getDurationInSecconds(testClass, testName, operationID);
        assertTrue(checkKafkaLogLevel(logLevel, seconds), "Kafka's log level is set properly");
    }

    @Test
    @Tag("release")
    void testLogLevelDebug() {
        operationID = startTimeMeasuring(Operation.TEST_EXECUTION);
        String logLevel = "DEBUG";
        LOGGER.info("Running testLogLevelInfo in namespace {}", NAMESPACE);
        createKafkaPods(logLevel);
        TimeMeasuringSystem.stopOperation(operationID);
        int seconds = TimeMeasuringSystem.getDurationInSecconds(testClass, testName, operationID);
        assertTrue(checkKafkaLogLevel(logLevel, seconds), "Kafka's log level is set properly");
    }

    @Test
    @Tag("release")
    void testLogLevelFatal() {
        operationID = startTimeMeasuring(Operation.TEST_EXECUTION);
        String logLevel = "FATAL";
        LOGGER.info("Running testLogLevelInfo in namespace {}", NAMESPACE);
        createKafkaPods(logLevel);
        TimeMeasuringSystem.stopOperation(operationID);
        int seconds = TimeMeasuringSystem.getDurationInSecconds(testClass, testName, operationID);
        assertTrue(checkKafkaLogLevel(logLevel, seconds), "Kafka's log level is set properly");
    }

    @Test
    @Tag("release")
    void testLogLevelOff() {
        operationID = startTimeMeasuring(Operation.TEST_EXECUTION);
        String logLevel = "OFF";
        LOGGER.info("Running testLogLevelInfo in namespace {}", NAMESPACE);
        createKafkaPods(logLevel);
        TimeMeasuringSystem.stopOperation(operationID);
        int seconds = TimeMeasuringSystem.getDurationInSecconds(testClass, testName, operationID);
        assertTrue(checkKafkaLogLevel(logLevel, seconds), "Kafka's log level is set properly");
    }

    private boolean checkKafkaLogLevel(String logLevel, int since) {
        LOGGER.info("Check log level setting since {} seconds. Expected: {}", since, logLevel);
        String kafkaConfigMap = kubeClient.get("configMap", CONFIG_MAP_NAME);
        String loggerConfig = String.format("%s=%s", TESTED_LOGGER, logLevel);
        boolean result = kafkaConfigMap.contains(loggerConfig);

        if (result) {
            String kafkaPodLog = kubeClient.searchInLog(STATEFUL_SET, kafkaClusterName(CLUSTER_NAME), since, "ERROR");
            result = kafkaPodLog.isEmpty();
        }

        return result;
    }

    private void createKafkaPods(String logLevel) {
        LOGGER.info("Create kafka in {} for testing logger: {}={}", CLUSTER_NAME, TESTED_LOGGER, logLevel);

        Map<String, String> map = new HashMap<>();
        map.put(TESTED_LOGGER, logLevel);

        resources().kafka(resources().defaultKafka(CLUSTER_NAME, 1)
            .editSpec()
                .editKafka().
                    withNewInlineLoggingLogging()
                        .withLoggers(map)
                    .endInlineLoggingLogging()
                .endKafka()
            .endSpec()
            .build()).done();
    }
}
