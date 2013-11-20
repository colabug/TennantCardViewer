package com.colabug.tennant.viewer;

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
