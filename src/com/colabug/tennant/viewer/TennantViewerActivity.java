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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollView;

import java.util.ArrayList;
import java.util.List;

public class TennantViewerActivity extends Activity
{
    private List<Card> cards;

    private int[] imageIds = { R.drawable.tennant_glasses,
                               R.drawable.tenannt_longing,
                               R.drawable.space_tennant,
                               R.drawable.tennant_snow,
                               R.drawable.tennant_bars,
                               R.drawable.clock_tennant,
                               R.drawable.king_tennant
    };

    private int[] imageTitles = { R.string.GLASSES_TITLE,
                                  R.string.LONGING_TITLE,
                                  R.string.SPACE_TITLE,
                                  R.string.SNOW_TITLE,
                                  R.string.BARS_TITLE,
                                  R.string.CLOCK_TITLE,
                                  R.string.KING_TITLE
    };

    private int[] imageSubTitles = { R.string.GLASSES_SUBTITLE,
                                     R.string.LONGING_SUBTITLE,
                                     R.string.SPACE_SUBTITLE,
                                     R.string.SNOW_SUBTITLE,
                                     R.string.BARS_SUBTITLE,
                                     R.string.CLOCK_SUBTITLE,
                                     R.string.KING_SUBTITLE
    };

    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        createCardList();
        setContentView( createCardScrollerWithList() );
    }

    private void createCardList()
    {
        cards = new ArrayList<Card>();

        for ( int i = 0; i < imageIds.length; i++ )
        {
            cards.add( generateCard( imageIds[i],
                                     imageTitles[i],
                                     imageSubTitles[i] ) );
        }
    }

    private Card generateCard( int imageId, int titleId, int subTitleId )
    {
        Card card = new Card( this );

        card.setText( getString( titleId ) );
        card.setInfo( getString( subTitleId ) );
        card.setFullScreenImages( true );
        card.addImage( imageId );

        return card;
    }

    private CardScrollView createCardScrollerWithList()
    {
        CardScrollView cardScrollView = new CardScrollView( this );

        cardScrollView.setAdapter( new TennantCardScrollAdapter( cards ) );
        cardScrollView.activate();

        return cardScrollView;
    }

    public static Intent createIntent( Context context )
    {
        return new Intent( context, TennantViewerActivity.class );
    }
}
