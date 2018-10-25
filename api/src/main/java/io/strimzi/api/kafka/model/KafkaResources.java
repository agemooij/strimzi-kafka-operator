/*
 * Copyright 2018, Strimzi authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.strimzi.api.kafka.model;

public class KafkaResources {
    private KafkaResources() { }

    /**
     * Returns the name of the Zookeeper {@code StatefulSet} for a {@code Kafka} cluster of the given name.
     * @param clusterName  The {@code metadata.name} of the {@code Kafka} resource.
     * @return The name of the corresponding Zookeeper {@code StatefulSet}.
     */
    public static String zookeeperStatefulSetName(String clusterName) {
        return clusterName + "-zookeeper";
    }

    /**
     * Returns the name of the Kafka {@code StatefulSet} for a {@code Kafka} cluster of the given name.
     * @param clusterName  The {@code metadata.name} of the {@code Kafka} resource.
     * @return The name of the corresponding Kafka {@code StatefulSet}.
     */
    public static String kafkaStatefulSetName(String clusterName) {
        return clusterName + "-kafka";
    }

    /**
     * Returns the name of the Entity Operator {@code Deployment} for a {@code Kafka} cluster of the given name.
     * This {@code Deployment} will only exist if {@code Kafka.spec.entityOperator} is configured in the
     * {@code Kafka} resource with the given name.
     * @param clusterName  The {@code metadata.name} of the {@code Kafka} resource.
     * @return The name of the corresponding Entity Operator {@code Deployment}.
     */
    public static String entityOperatorDeploymentName(String clusterName) {
        return clusterName + "-entity-operator";
    }

    /**
     * Returns the name of the Cluster CA certificate {@code Secret} for a {@code Kafka} cluster of the given name.
     * @param clusterName  The {@code metadata.name} of the {@code Kafka} resource.
     * @return The name of the corresponding Cluster CA certificate {@code Secret}.
     */
    public static String clusterCaCertificateSecretName(String clusterName) {
        return clusterName + "-cluster-ca-cert";
    }

    /**
     * Returns the name of the Cluster CA key {@code Secret} for a {@code Kafka} cluster of the given name.
     * @param clusterName  The {@code metadata.name} of the {@code Kafka} resource.
     * @return The name of the corresponding Cluster CA key {@code Secret}.
     */
    public static String clusterCaKeySecretName(String clusterName) {
        return clusterName + "-cluster-ca";
    }

    /**
     * Returns the name of the Clients CA certificate {@code Secret} for a {@code Kafka} cluster of the given name.
     * @param clusterName  The {@code metadata.name} of the {@code Kafka} resource.
     * @return The name of the corresponding Clients CA certificate {@code Secret}.
     */
    public static String clientsCaCertificateSecretName(String clusterName) {
        return clusterName + "-clients-ca-cert";
    }

    /**
     * Returns the name of the Clients CA key {@code Secret} for a {@code Kafka} cluster of the given name.
     * @param clusterName  The {@code metadata.name} of the {@code Kafka} resource.
     * @return The name of the corresponding Clients CA key {@code Secret}.
     */
    public static String clientsCaKeySecretName(String clusterName) {
        return clusterName + "-clients-ca";
    }

    /**
     * Returns the name of the external bootstrap {@code Service} for a {@code Kafka} cluster of the given name.
     * This {@code Service} will only exist if {@code Kafka.spec.kafka.listeners.external} is configured for a
     * loadbalancer or NodePort in the {@code Kafka} resource with the given name.
     * @param clusterName  The {@code metadata.name} of the {@code Kafka} resource.
     * @return The name of the corresponding bootstrap {@code Service}.
     */
    public static String externalBootstrapServiceName(String clusterName) {
        return clusterName + "-kafka-external-bootstrap";
    }
}
