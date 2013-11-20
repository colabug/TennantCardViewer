package com.colabug.tennant.viewer;

import android.app.Activity;
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

        for ( int imageId : imageIds )
        {
            cards.add( generateCard( imageId ) );
        }
    }

    private Card generateCard( int imageId )
    {
        Card card = new Card( this );

        card.setText( getString( R.string.CARD_TITLE ) );
        card.setInfo( getString( R.string.CARD_SUBTITLE ) );
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
}
