package com.colabug.TardisCardFlipper;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollView;

import java.util.ArrayList;
import java.util.List;

public class TardisCardActivity extends Activity
{
    private List<Card> cards;

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
        cards.add( generateCard() );
        cards.add( generateCard() );
        cards.add( generateCard() );
    }

    private Card generateCard()
    {
        Card card = new Card( this );

        card.setText( getString( R.string.CARD_TITLE ) );
        card.setInfo( getString( R.string.CARD_SUBTITLE ) );
        card.setFullScreenImages( true );
        card.addImage( R.drawable.tennant_glasses );

        return card;
    }

    private CardScrollView createCardScrollerWithList()
    {
        CardScrollView cardScrollView = new CardScrollView( this );

        cardScrollView.setAdapter( new ExampleCardScrollAdapter( cards ) );
        cardScrollView.activate();

        return cardScrollView;
    }
}
