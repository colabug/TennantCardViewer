package com.colabug.tennant.viewer;

import android.content.Context;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageView;

import java.util.concurrent.TimeUnit;

public class TennantRenderer implements SurfaceHolder.Callback
{
    private static final String TAG = TennantRenderer.class.getSimpleName();

    /**
     * The refresh rate, in frames per second.
     */
    private static final int REFRESH_RATE_FPS = 45;

    /**
     * The duration, in milliseconds, of one frame.
     */
    private static final long FRAME_TIME_MILLIS = TimeUnit.SECONDS.toMillis( 1 ) / REFRESH_RATE_FPS;

    private final ImageView layout;

    private SurfaceHolder surfaceHolder;
    private RenderThread  renderThread;

    private int surfaceWidth;
    private int surfaceHeight;

    /**
     * Creates a new instance of the {@code TennantRenderer}.
     */
    public TennantRenderer( Context context )
    {
        LayoutInflater inflater = LayoutInflater.from( context );
        layout = (ImageView) inflater.inflate( R.layout.live_tennant, null );
        layout.setWillNotDraw( false );
    }

    @Override
    public void surfaceChanged( SurfaceHolder holder,
                                int format,
                                int width,
                                int height )
    {
        surfaceWidth = width;
        surfaceHeight = height;
        doLayout();
    }

    @Override
    public void surfaceCreated( SurfaceHolder holder )
    {
        surfaceHolder = holder;
        renderThread = new RenderThread();
        renderThread.start();
    }

    @Override
    public void surfaceDestroyed( SurfaceHolder holder )
    {
        renderThread.quit();
    }

    /**
     * Requests that the views redo their layout. This must be called manually every time the
     * tips view's text is updated because this layout doesn't exist in a GUI thread where those
     * requests will be enqueued automatically.
     */
    private void doLayout()
    {
        // Measure and update the layout so that it will take up the entire surface space
        // when it is drawn.
        int measuredWidth = View.MeasureSpec.makeMeasureSpec( surfaceWidth,
                                                              View.MeasureSpec.EXACTLY );
        int measuredHeight = View.MeasureSpec.makeMeasureSpec( surfaceHeight,
                                                               View.MeasureSpec.EXACTLY );

        layout.measure( measuredWidth, measuredHeight );
        layout.layout( 0, 0, layout.getMeasuredWidth(), layout.getMeasuredHeight() );
    }

    /**
     * Repaints the view.
     */
    private synchronized void repaint()
    {
        Canvas canvas = null;

        try
        {
            canvas = surfaceHolder.lockCanvas();
        }
        catch ( RuntimeException e )
        {
            Log.d( TAG, "lockCanvas failed", e );
        }

        if ( canvas != null )
        {
            layout.draw( canvas );

            try
            {
                surfaceHolder.unlockCanvasAndPost( canvas );
            }
            catch ( RuntimeException e )
            {
                Log.d( TAG, "unlockCanvasAndPost failed", e );
            }
        }
    }

    /**
     * Redraws the view in the background.
     */
    private class RenderThread extends Thread
    {
        private boolean shouldRun;

        /**
         * Initializes the background rendering thread.
         */
        public RenderThread()
        {
            shouldRun = true;
        }

        /**
         * Returns true if the rendering thread should continue to run.
         *
         * @return true if the rendering thread should continue to run
         */
        private synchronized boolean shouldRun()
        {
            return shouldRun;
        }

        /**
         * Requests that the rendering thread exit at the next opportunity.
         */
        public synchronized void quit()
        {
            shouldRun = false;
        }

        @Override
        public void run()
        {
            while ( shouldRun() )
            {
                long frameStart = SystemClock.elapsedRealtime();
                repaint();
                long frameLength = SystemClock.elapsedRealtime() - frameStart;

                long sleepTime = FRAME_TIME_MILLIS - frameLength;
                if ( sleepTime > 0 )
                {
                    SystemClock.sleep( sleepTime );
                }
            }
        }
    }
}
