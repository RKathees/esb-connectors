/*
 * Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.connector.integration.test.meetup.notification;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.wso2.carbon.connector.integration.test.common.ConnectorIntegrationUtil;
import org.wso2.carbon.connector.integration.test.meetup.MeetupConnectorIntegrationTest;

import javax.activation.DataHandler;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: E Ananthaneshan
 * Date: 9/11/14
 * Time: 5:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class NotificationsIntegrationTest extends MeetupConnectorIntegrationTest {
	/**
	 * Test getDiscussionBoards API operation for Mandatory fields.
	 * Expecting Response header '200' and 'post_count' JSONObject in returned JSONArray.
	 *
	 * @throws Exception if test fails.
	 */
	@Test(enabled = false, groups = { "wso2.esb" },
	      description = "Testing getNotifications API method")
	public void testGetNotificationsMandatory() throws Exception {

		String jsonRequestFilePath =
				pathToRequestsDirectory + "notifications_getNotifications_mandatory.txt";
		String methodName = "notifications_getNotifications";

		final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
		final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
		String modifiedJsonString = String.format(jsonString,
		                                          meetupConnectorProperties.getProperty("key"),
		                                          meetupConnectorProperties
				                                          .getProperty("access_token"));
		proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

		try {

			int responseHeader = ConnectorIntegrationUtil
					.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),
					                             modifiedJsonString);
			Assert.assertTrue(responseHeader == 200);

			JSONArray jsonArray = ConnectorIntegrationUtil
					.sendRequestJSONArray(getProxyServiceURL(methodName), modifiedJsonString);
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			Assert.assertTrue(jsonObject.has("kind"));

		} finally {
			proxyAdmin.deleteProxy(methodName);
		}
	}

	/**
	 * Test getSettings API operation for Mandatory fields. this API method returns notification settings.
	 * Expecting Response header '200' and 'post_count' JSONObject in returned JSONArray.
	 *
	 * @throws Exception if test fails.
	 */
	@Test(enabled = false, groups = { "wso2.esb" },
	      description = "Testing getNotificationSettings API method")
	public void testGetSettingsMandatory() throws Exception {

		String jsonRequestFilePath =
				pathToRequestsDirectory + "notifications_getSettings_mandatory.txt";
		String methodName = "notifications_getSettings";

		final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
		final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
		String modifiedJsonString = String.format(jsonString,
		                                          meetupConnectorProperties.getProperty("key"),
		                                          meetupConnectorProperties
				                                          .getProperty("access_token"));
		proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

		try {

			int responseHeader = ConnectorIntegrationUtil
					.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),
					                             modifiedJsonString);
			Assert.assertTrue(responseHeader == 200);

			JSONObject jsonObject = ConnectorIntegrationUtil
					.sendRequest(getProxyServiceURL(methodName), modifiedJsonString);
			Assert.assertTrue(jsonObject.has("self"));

		} finally {
			proxyAdmin.deleteProxy(methodName);
		}
	}

	/**
	 * Test markRead API operation for Mandatory fields. this API method returns notification.
	 * Expecting Response header '200' JSONObject in returned JSONArray.
	 *
	 * @throws Exception if test fails.
	 */
	@Test(enabled = true, groups = { "wso2.esb" },
	      description = "Testing markRead notifications API method")
	public void testMarkReadMandatory() throws Exception {

		String jsonRequestFilePath =
				pathToRequestsDirectory + "notifications_markRead_mandatory.txt";
		String methodName = "notifications_markRead";

		final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
		final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
		String modifiedJsonString = String.format(jsonString,
		                                          meetupConnectorProperties.getProperty("key"),
		                                          meetupConnectorProperties
				                                          .getProperty("access_token"));
		proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

		try {

			int responseHeader = ConnectorIntegrationUtil
					.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),
					                             modifiedJsonString);
			Assert.assertTrue(responseHeader == 200);

            /*JSONObject jsonObject= ConnectorIntegrationUtil
		            .sendRequest(getProxyServiceURL(methodName), modifiedJsonString);
            Assert.assertTrue(jsonObject.has("self"));*/

		} finally {
			proxyAdmin.deleteProxy(methodName);
		}
	}

}