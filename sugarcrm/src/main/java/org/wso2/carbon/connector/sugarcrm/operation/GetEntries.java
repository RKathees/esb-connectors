/*
 * Copyright (c) 2005-2013, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * 
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.connector.sugarcrm.operation;

import java.util.Iterator;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.synapse.MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;
import org.wso2.carbon.connector.core.Connector;
import org.wso2.carbon.connector.core.util.ConnectorUtils;
import org.wso2.carbon.connector.sugarcrm.util.SugarCRMUtil;

/**
 * Class mediator which helps to modify the pay load for <strong>get_entries</strong> method in SugarCRM SOAP
 * API.
 */
public class GetEntries extends AbstractConnector implements Connector {
    
    /** Represent the get_entries tag of the pay load in the template. */
    private static final String GET_ENTRIES_TAG = "get_entries";
    
    /**
     * Represent the soap request array element name-id tag of the pay load in the template.
     */
    private static final String IDS_TAG = "ids";
    
    /**
     * Represent the soap request array element name - select_fields tag of the pay load in the template.
     */
    private static final String SELECT_FIELDS_TAG = "select_fields";
    
    /**
     * Represent the soap request array element name - select_fields tag of the pay load in the template.
     */
    private static final String SELECTFIELDS_TAG = "selectFields";
    
    /**
     * Modify request body before sending to the end point.
     * 
     * @param messageContext MessageContext - The message context.
     * @throws ConnectException - if connection is failed.
     */
    @SuppressWarnings("unchecked")
    public void connect(MessageContext messageContext) throws ConnectException {
    
        SOAPEnvelope envelope = messageContext.getEnvelope();
        OMFactory omFactory = OMAbstractFactory.getOMFactory();
        SOAPBody body = envelope.getBody();
        
        Iterator<OMElement> omElementIterator = body.getChildrenWithLocalName(GET_ENTRIES_TAG);
        
        try {
            
            OMElement omElementList = omElementIterator.next();
            Iterator<OMElement> childElementsId = omElementList.getChildrenWithLocalName(IDS_TAG);
            
            if (childElementsId.hasNext()) {
                OMElement childElementId = childElementsId.next();
                String idListString = (String) ConnectorUtils.lookupTemplateParamater(messageContext, IDS_TAG);
                SugarCRMUtil.getItemElement(omFactory, messageContext, childElementId, idListString);
            }
            
            Iterator<OMElement> childElementsField = omElementList.getChildrenWithLocalName(SELECT_FIELDS_TAG);
            
            if (childElementsField.hasNext()) {
                OMElement childElementField = childElementsField.next();
                String strobject = (String) ConnectorUtils.lookupTemplateParamater(messageContext, SELECTFIELDS_TAG);
                SugarCRMUtil.getItemElement(omFactory, messageContext, childElementField, strobject);
            }
            
        } catch (Exception e) {
            log.error(SugarCRMUtil.EXCEPTION + SugarCRMUtil.getStackTraceAsString(e));
            throw new ConnectException(e);
        }
        
    }
    
}
