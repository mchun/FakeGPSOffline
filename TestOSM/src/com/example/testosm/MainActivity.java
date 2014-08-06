package com.example.testosm;

import java.util.ArrayList;

import org.osmdroid.bonuspack.overlays.MapEventsOverlay;
import org.osmdroid.bonuspack.overlays.MapEventsReceiver;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements
		MapEventsReceiver {

	MapView mapView;

	// protected ArrayList<GeoPoint> viaPoints;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// mapView = new MapView(this, 256); // constructor

		mapView = (MapView) findViewById(R.id.map);

		mapView.setClickable(true);

		mapView.setBuiltInZoomControls(true);

		mapView.setMultiTouchControls(true);

		// setContentView(mapView); // displaying the MapView

		mapView.getController().setZoom(10); // set initial zoom-level, depends
												// on your need
		mapView.getController().setCenter(new GeoPoint(22.801345, 113.79949)); // This point is in HK


		mapView.setUseDataConnection(false); // keeps the mapView from loading
												// online tiles using network
												// connection.

		MapEventsOverlay evOverlay = new MapEventsOverlay(this, this);
		mapView.getOverlays().add(evOverlay);
		mapView.invalidate();


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_stop_fake) {
			Intent i = new Intent("com.lexa.fakegps.STOP");
			this.stopService(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean longPressHelper(GeoPoint loc) {
		String longitude = Double
				.toString(((double) loc.getLongitudeE6()) / 1000000);
		String latitude = Double
				.toString(((double) loc.getLatitudeE6()) / 1000000);

		Intent i = new Intent("com.lexa.fakegps.START");
		i.putExtra("lat", loc.getLatitude());
		i.putExtra("long", loc.getLongitude());

		this.startService(i);
		Toast.makeText(
				getApplicationContext(),
				"Longitude: " + longitude + " Latitude: " + latitude
						+ " sent to FakeGPS.", Toast.LENGTH_SHORT).show();
		return false;
	}

	@Override
	public boolean singleTapConfirmedHelper(GeoPoint arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
