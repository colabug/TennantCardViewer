package com.colabug.tennant.viewer;
/*
 * Copyright (C) 2013 The Android Open Source Project and
 * modified by Corey Leigh Latislaw
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;

import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.timeline.TimelineManager;

public class TennantService extends Service
{
    private static final String LIVE_CARD_ID = "tennant_is_alive";

    private final TennantBinder binder = new TennantBinder();

    private TextToSpeech textToSpeech;

    private TimelineManager timelineManager;
    private TennantRenderer tennantRenderer;
    private LiveCard        liveCard;

    /**
     * A binder that gives other components access to the speech capabilities provided by the
     * service.
     *
     * TODO: Use elsewhere or remove
     */
    public class TennantBinder extends Binder
    {
        /**
         * Read the current text aloud using the text-to-speech engine.
         */
        public void readTextAloud()
        {
            String headingText = "Doctor Who? I\'m the king!";
            textToSpeech.speak( headingText, TextToSpeech.QUEUE_FLUSH, null );
        }
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        timelineManager = TimelineManager.from( this );

        // Even though the text-to-speech engine is only used in response to a menu action, we
        // initialize it when the application starts so that we avoid delays that could occur
        // if we waited until it was needed to start it up.
        textToSpeech = new TextToSpeech( this, new TextToSpeech.OnInitListener()
        {
            @Override
            public void onInit( int status )
            {
                // Do nothing.
            }
        } );
    }

    @Override
    public IBinder onBind( Intent intent )
    {
        return binder;
    }

    @Override
    public int onStartCommand( Intent intent, int flags, int startId )
    {
        if ( liveCard == null )
        {
            configureLiveCard();
            liveCard.publish();
        }

        return START_STICKY;
    }

    /**
     * Configures live card.
     */
    private void configureLiveCard()
    {
        liveCard = timelineManager.getLiveCard( LIVE_CARD_ID );
        tennantRenderer = new TennantRenderer( this );

        liveCard.enableDirectRendering( true )
                .getSurfaceHolder()
                .addCallback( tennantRenderer );
        liveCard.setNonSilent( true );

        configureMenu();
    }

    /**
     * Configures display of options menu when the live card is tapped.
     */
    private void configureMenu()
    {
        Intent menuIntent = new Intent( this, TennantMenuActivity.class );
        menuIntent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK |
                             Intent.FLAG_ACTIVITY_CLEAR_TASK );
        liveCard.setAction( PendingIntent.getActivity( this, 0, menuIntent, 0 ) );
    }

    @Override
    public void onDestroy()
    {
        if ( liveCard != null && liveCard.isPublished() )
        {
            liveCard.unpublish();
            liveCard.getSurfaceHolder().removeCallback( tennantRenderer );
            liveCard = null;
        }

        textToSpeech.shutdown();
        textToSpeech = null;

        super.onDestroy();
    }
}
