/*
 * Copyright (C) 2025 microG Project Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.microg.gms.wearable;

import android.net.Uri;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class ChannelImplTest {

    private ChannelImpl channel;

    @Before
    public void setUp() {
        channel = new ChannelImpl("/test_path", "test_node");
    }

    @Test
    public void testSendAndReceiveFile() throws Exception {
        File inputFile = new File("test_input.txt");
        FileOutputStream fos = new FileOutputStream(inputFile);
        fos.write("test data".getBytes());
        fos.close();

        File outputFile = new File("test_output.txt");

        channel.sendFile(null, Uri.fromFile(inputFile));
        channel.receiveFile(null, Uri.fromFile(outputFile), false);

        TimeUnit.SECONDS.sleep(1);

        InputStream is = new java.io.FileInputStream(outputFile);
        byte[] buffer = new byte[1024];
        int len = is.read(buffer);
        is.close();

        assertEquals("test data", new String(buffer, 0, len));

        inputFile.delete();
        outputFile.delete();
    }
}
