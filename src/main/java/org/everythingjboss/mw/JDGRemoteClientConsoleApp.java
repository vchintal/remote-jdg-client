package org.everythingjboss.mw;

import java.util.stream.IntStream;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.Configuration;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.configuration.ClientIntelligence;
import org.infinispan.client.hotrod.configuration.SaslQop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDGRemoteClientConsoleApp {

	private static final Logger logger = LoggerFactory.getLogger(JDGRemoteClientConsoleApp.class);
	private static final String HOST = "datagrid-service-external-datagrid-demo.apps.ocp4.example.com";

	public static void main(String[] args) {
		Configuration configuration = new ConfigurationBuilder().addServer().host(HOST).port(443)
		        .clientIntelligence(ClientIntelligence.BASIC).security().authentication().enable().username("developer")
		        .password("openshift").realm("default").serverName("infinispan").saslMechanism("DIGEST-MD5")
		        .saslQop(SaslQop.AUTH).ssl().enable().sniHostName(HOST)
		        .trustStoreFileName("/home/vchintal/git/remote-jdg-client/src/main/resources/truststore")
		        .trustStorePassword("openshift".toCharArray()).build();

		RemoteCacheManager cacheManager = new RemoteCacheManager(configuration);

		// Get a cache instance using cacheManager
		RemoteCache<String, Integer> remoteCache = cacheManager.getCache("default");

		// Perform some cache operations
		IntStream.range(1, 1000).parallel().forEach(i -> {
			remoteCache.put("" + i, i);
		});

		// Log any stats onto the console
		logger.info("The size of the cache : {}", remoteCache.size());

		remoteCache.entrySet().stream()
		        .forEach(entry -> System.out.printf("%s = %s\n", entry.getKey(), entry.getValue()));

		// Stop the cache manager
		cacheManager.stop();
	}
}
