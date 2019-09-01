package com.example.instahappy.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import com.example.instahappy.R;
import com.example.instahappy.model.Constants;
import com.example.instahappy.ui.CollectionsActivity;

public class AppWidgetProvider extends android.appwidget.AppWidgetProvider {
    private PendingIntent service;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Intent activityIntent = new Intent(context, CollectionsActivity.class);
        activityIntent.putExtra("btnClickedId", Constants.kidsCollectionId);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,activityIntent, 0);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        remoteViews.setOnClickPendingIntent(R.id.appWidgetbutton, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);

    }

}
