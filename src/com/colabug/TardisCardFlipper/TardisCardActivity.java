package com.colabug.TardisCardFlipper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.google.android.glass.app.Card;

public class TardisCardActivity extends Activity
{
    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        Card card1 = new Card( this );
        card1.setText( getString( R.string.CARD_TITLE ) );
        card1.setInfo( getString( R.string.CARD_SUBTITLE ) );
        card1.setFullScreenImages( true );
        card1.addImage( R.drawable.tennant_glasses );
        View card1View = card1.toView();
        setContentView( card1View );
    }
}
