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

import android.app.Notification;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class WearableNotificationListenerServiceTest {

    private WearableNotificationListenerService notificationListenerService;

    @Mock
    private WearableImpl wearable;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        notificationListenerService = new WearableNotificationListenerService();
        notificationListenerService.wearable = wearable;
        notificationListenerService.gson = new Gson();
    }

    @Test
    public void testOnNotificationPosted() {
        Notification notification = new Notification.Builder(null)
                .setContentTitle("Test Title")
                .setContentText("Test Text")
                .build();
        StatusBarNotification sbn = new StatusBarNotification("com.test.app", "com.test.app", 1, null, 0, 0, 0, notification, null, 0);

        notificationListenerService.onNotificationPosted(sbn);

        NotificationHolder holder = new NotificationHolder("com.test.app", 1, null, notification);
        String json = new Gson().toJson(holder);
        verify(wearable).sendMessage(eq("com.test.app"), any(), eq("/notification"), eq(json.getBytes()));
    }
}
