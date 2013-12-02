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

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;

public class TennantMenuActivity extends Activity
{
    private boolean resumed;

    private TennantService.TennantBinder tennantService;

    private ServiceConnection connection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected( ComponentName name, IBinder service )
        {
            if ( service instanceof TennantService.TennantBinder )
            {
                tennantService = (TennantService.TennantBinder) service;
                openOptionsMenu();
            }
        }

        @Override
        public void onServiceDisconnected( ComponentName name )
        {
            // Do nothing.
        }
    };

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        bindService( new Intent( this, TennantService.class ), connection, 0 );
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        resumed = true;
        openOptionsMenu();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        resumed = false;
    }

    @Override
    public void openOptionsMenu()
    {
        if ( resumed && tennantService != null )
        {
            super.openOptionsMenu();
        }
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        getMenuInflater().inflate( R.menu.live_menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item )
    {
        switch ( item.getItemId() )
        {
            case R.id.start_viewer:
                startActivity( TennantViewerActivity.createIntent(this) );
                return true;

            case R.id.stop:
                stopService( new Intent( this, TennantService.class ) );
                return true;

            default:
                return super.onOptionsItemSelected( item );
        }
    }

    @Override
    public void onOptionsMenuClosed( Menu menu )
    {
        super.onOptionsMenuClosed( menu );

        unbindService( connection );

        // We must call finish() from this method to ensure that the activity ends either when an
        // item is selected from the menu or when the menu is dismissed by swiping down.
        finish();
    }
}
