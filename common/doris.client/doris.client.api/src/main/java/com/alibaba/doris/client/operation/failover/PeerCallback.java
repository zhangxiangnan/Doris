/*
Copyright(C) 1999-2010 Alibaba Group Holding Limited
All rights reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.alibaba.doris.client.operation.failover;

import com.alibaba.doris.client.AccessException;
import com.alibaba.doris.client.net.DataSource;
import com.alibaba.doris.client.net.OperationFuture;
import com.alibaba.doris.client.operation.ExecutionStatus;
import com.alibaba.doris.client.operation.OperationData;

/**
 * Callback
 * @author Kun He (Raymond He), kun.hek@alibaba-inc.com
 * @since 1.0 2011-4-25
 */
public interface PeerCallback {
	
	public OperationData getOperationData();
	
	public ExecutionStatus getExecutionStatus();
	
	public OperationFuture<?> getOperationFuture();
	
	PeerCallback execute() throws AccessException;
	
	void setDataSource(DataSource dataSource);
	
	DataSource getDataSource();
}
