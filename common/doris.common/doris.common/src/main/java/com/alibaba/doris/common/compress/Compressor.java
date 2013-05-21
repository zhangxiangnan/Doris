/*
Copyright(C) 2010-2011 Alibaba Group Holding Limited
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
package com.alibaba.doris.common.compress;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Compressor
 * @author Kun He (Raymond He), kun.hek@alibaba-inc.com
 * @since 1.0 2011-6-7
 */
public interface Compressor {
	
	byte[] compress(byte[] bytes);
	
	void compressStream(InputStream is,OutputStream os) throws IOException;
	
	byte[] decompress(byte[] bytes);
	
	void decompressStream(InputStream is, OutputStream os) throws IOException;
}
