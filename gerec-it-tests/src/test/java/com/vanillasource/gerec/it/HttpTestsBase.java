/**
 * Copyright (C) 2016 VanillaSource
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.vanillasource.gerec.it;

import com.vanillasource.gerec.*;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import static org.mockito.Mockito.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import java.net.URI;
import com.github.tomakehurst.wiremock.client.WireMock;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import com.vanillasource.gerec.httpclient.HttpClientResourceReference;

public class HttpTestsBase {
   private HttpClientResourceReference reference;
   private PoolingHttpClientConnectionManager connectionManager;
   private CloseableHttpClient httpClient;
   private WireMockServer wireMock = new WireMockServer(wireMockConfig().port(8091));

   protected ResourceReference reference() {
      return reference;
   }

   protected PoolingHttpClientConnectionManager connectionManager() {
      return connectionManager;
   }

   @BeforeMethod
   protected void setUp() {
      connectionManager = new PoolingHttpClientConnectionManager();
      httpClient = HttpClientBuilder
         .create()
         .setConnectionManager(connectionManager)
         .build();
      reference = new HttpClientResourceReference(() -> httpClient, URI.create("http://localhost:8091/"));
      WireMock.reset();
   }

   @AfterMethod
   protected void tearDown() throws Exception {
      httpClient.close();
   }

   @BeforeClass
   protected void startTests() {
      wireMock.start();
      WireMock.configureFor("localhost", 8091);
   }

   @AfterClass
   protected void stopTests() {
      wireMock.stop();
   }
}


