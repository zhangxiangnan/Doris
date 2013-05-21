/*
 * Copyright(C) 1999-2010 Alibaba Group Holding Limited All rights reserved. Licensed under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the
 * License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing permissions and limitations
 * under the License.
 */
package com.alibaba.doris.client.operation.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.doris.client.AccessException;
import com.alibaba.doris.client.cn.OperationDataConverter;
import com.alibaba.doris.client.net.Connection;
import com.alibaba.doris.client.net.DataSource;
import com.alibaba.doris.client.net.NetException;
import com.alibaba.doris.client.operation.AbstractOperation;
import com.alibaba.doris.client.operation.OperationData;
import com.alibaba.doris.client.operation.OperationDataSourceGroup;
import com.alibaba.doris.client.operation.failover.PeerCallback;
import com.alibaba.doris.client.operation.failover.impl.DefaultPeerCallback;
import com.alibaba.doris.client.operation.result.DataSourceOpResult;
import com.alibaba.doris.common.data.Key;
import com.alibaba.doris.common.operation.OperationEnum;
import com.alibaba.doris.common.route.DorisRouterException;

/**
 * PutOperation
 * 
 * @author Kun He (Raymond He), kun.hek@alibaba-inc.com
 * @since 1.0 2011-4-22
 */
public class DeleteOperation extends AbstractOperation {

    private static final Logger logger = LoggerFactory.getLogger(GetOperation.class);

    /**
     * @see com.alibaba.doris.framework.operation.AbstractOperation#getName()
     */
    public String getName() {
        return "delete";
    }

    public OperationEnum getOperationType(OperationData operationData) {
        return OperationEnum.WRITE;
    }

    @Override
    public int getOperationCount(OperationData operationData) {
        return operationData.getNamespace().getCopyCount();
    }

    @Override
    protected PeerCallback generatePeerCallback(DataSource dataSource, OperationData operationData) {
        return new DefaultPeerCallback(dataSource, operationData) {

            @Override
            public PeerCallback execute() throws AccessException {
                try {
                    Connection connection = dataSource.getConnection();
                    Key commuKey = (Key) operationData.getKey();

                    this.future = connection.delete(commuKey);
                } catch (NetException e) {
                    throw new AccessException("delete connection exception.", e);
                } catch (Throwable t) {
                    throw new AccessException("delete operation exception." + t, t);
                }
                return this;
            }
        };
    }

    @Override
    protected void buildLogicParam(List<OperationDataSourceGroup> operationDataSourceGroups,
                                   List<OperationData> operationDatas, OperationData operationData,
                                   OperationDataConverter operationDataConverter, long routeVersion)
                                                                                                    throws DorisRouterException {

        buildSimpleLogicParam(operationDataSourceGroups, operationDatas, operationData, operationDataConverter,
                              routeVersion);

    }

    @Override
    protected void mergeOperationResult(List<List<DataSourceOpResult>> dsOpResults, List<OperationData> operationDatas) {
        super.needAllNodeSuccess(dsOpResults, operationDatas);
    }
}
