package com.example.springmodulithexample.common.config;

// Importation des classes d'événements utilisées pour la sérialisation/désérialisation Kafka
import com.example.springmodulithexample.acheteur.events.AchatEffectueEvent;
import com.example.springmodulithexample.produit.events.StockDecrementeEvent;
// Importation des classes nécessaires à la configuration Kafka
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

// Active la gestion des listeners Kafka dans Spring
@EnableKafka
// Indique que cette classe est une configuration Spring
@Configuration
public class KafkaConfig {
    // Injection de la propriété bootstrap-servers depuis application.properties
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    // ----------- CONFIGURATION PRODUCTEUR POUR AchatEffectueEvent -----------

    // Bean ProducerFactory pour produire des messages de type AchatEffectueEvent
    @Bean
    public ProducerFactory<String, AchatEffectueEvent> achatEffectueProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        // Adresse du serveur Kafka
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // Sérialiseur de la clé (ici String)
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // Sérialiseur de la valeur (ici JSON)
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    // Bean KafkaTemplate pour envoyer des événements AchatEffectueEvent
    @Bean
    public KafkaTemplate<String, AchatEffectueEvent> achatEffectueKafkaTemplate() {
        return new KafkaTemplate<>(achatEffectueProducerFactory());
    }

    // ----------- CONFIGURATION PRODUCTEUR POUR StockDecrementeEvent -----------

    // Bean ProducerFactory pour produire des messages de type StockDecrementeEvent
    @Bean
    public ProducerFactory<String, StockDecrementeEvent> stockDecrementeProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    // Bean KafkaTemplate pour envoyer des événements StockDecrementeEvent
    @Bean
    public KafkaTemplate<String, StockDecrementeEvent> stockDecrementeKafkaTemplate() {
        return new KafkaTemplate<>(stockDecrementeProducerFactory());
    }

    // ----------- CONFIGURATION CONSOMMATEUR POUR AchatEffectueEvent -----------

    // Bean ConsumerFactory pour consommer des messages de type AchatEffectueEvent
    @Bean
    public ConsumerFactory<String, AchatEffectueEvent> achatEffectueConsumerFactory() {
        // Désérialiseur JSON pour l'événement
        JsonDeserializer<AchatEffectueEvent> deserializer = new JsonDeserializer<>(AchatEffectueEvent.class);
        deserializer.addTrustedPackages("*"); // Autorise tous les packages pour la désérialisation
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // Groupe consommateur défini dans application.properties
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "${spring.kafka.consumer.group-id}");
        // Désérialiseur de la clé (String)
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // Désérialiseur de la valeur (JSON)
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    // Bean ListenerContainerFactory pour brancher les listeners Kafka sur AchatEffectueEvent
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AchatEffectueEvent> achatEffectueKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AchatEffectueEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(achatEffectueConsumerFactory());
        return factory;
    }

    // ----------- CONFIGURATION CONSOMMATEUR POUR StockDecrementeEvent -----------

    // Bean ConsumerFactory pour consommer des messages de type StockDecrementeEvent
    @Bean
    public ConsumerFactory<String, StockDecrementeEvent> stockDecrementeConsumerFactory() {
        JsonDeserializer<StockDecrementeEvent> deserializer = new JsonDeserializer<>(StockDecrementeEvent.class);
        deserializer.addTrustedPackages("*");
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "${spring.kafka.consumer.group-id}");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    // Bean ListenerContainerFactory pour brancher les listeners Kafka sur StockDecrementeEvent
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, StockDecrementeEvent> stockDecrementeKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, StockDecrementeEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(stockDecrementeConsumerFactory());
        return factory;
    }
} 