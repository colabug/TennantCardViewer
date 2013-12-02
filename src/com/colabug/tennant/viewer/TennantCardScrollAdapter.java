package com.colabug.tennant.viewer;
/*
 * Copyright (C) 2013 Corey Leigh Latislaw
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

import android.view.View;
import android.view.ViewGroup;
import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;

import java.util.List;

public class TennantCardScrollAdapter extends CardScrollAdapter
{
    private List<Card> cards;

    public TennantCardScrollAdapter( List<Card> cards )
    {
        this.cards = cards;
    }

    @Override
    public int findIdPosition( Object id )
    {
        return -1;
    }

    @Override
    public int findItemPosition( Object item )
    {
        return cards.indexOf( item );
    }

    @Override
    public int getCount()
    {
        return cards.size();
    }

    @Override
    public Object getItem( int position )
    {
        return cards.get( position );
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent )
    {
        return cards.get( position ).toView();
    }
}
