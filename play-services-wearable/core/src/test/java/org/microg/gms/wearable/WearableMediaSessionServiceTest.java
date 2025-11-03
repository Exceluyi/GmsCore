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

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.view.KeyEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class WearableMediaSessionServiceTest {

    private WearableMediaSessionService mediaSessionService;

    @Mock
    private AudioManager audioManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mediaSessionService = new WearableMediaSessionService();
        mediaSessionService.onCreate();
    }

    @Test
    public void testOnMediaButtonEvent() {
        Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE);
        intent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);

        mediaSessionService.onMediaButtonEvent(intent);

        verify(audioManager).dispatchMediaKeyEvent(keyEvent);
    }
}
