package com.theroc.message.UI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.theroc.message.Adapter.ConversationPreviewAdapter;
import com.theroc.message.Database.Entities.ConversationMessage;
import com.theroc.message.Database.Entities.Credentials;
import com.theroc.message.Database.Entities.Message;
import com.theroc.message.Database.Entities.TrueMessage;
import com.theroc.message.UI.FirstTimeInit.WelcomeActivity;
import com.theroc.message.Interface.ItemClickListener;
import com.theroc.message.R;
import com.theroc.message.Utilities.PrefManager;
import com.theroc.message.Utilities.StringOperations;
import com.theroc.message.ViewModel.CredentialsConversationMessageViewModel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ItemClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleApiClient googleApiClient;
    private boolean permissionGranted = false;
    private Location location;
    private final List<ConversationMessage> conversationMessageList = new ArrayList<>();
    private boolean transfer = true;
    private boolean ready = false;

    private Credentials mCredentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PrefManager prefManager = new PrefManager(getApplicationContext());
        if (prefManager.isFirstTimeLaunch()) {
            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
            finish();
        }

        Locale aLocale = new Locale.Builder().setLanguage("el").setScript("Grek").setRegion("GR").build();

        //Option button
        ImageButton optionImageButton = findViewById(R.id.optionsImageButton);
        optionImageButton.setOnClickListener((View v) -> {
            PopupMenu popup = new PopupMenu(this, v);
            popup.setGravity(Gravity.END);
            popup.inflate(R.menu.menu_main);
            popup.setOnMenuItemClickListener(item -> {
                openOptionActivity();
                return true;
            });
            popup.show();
        });

        //Message setup//
        TrueMessage trueMessage = new TrueMessage();
        CredentialsConversationMessageViewModel viewModel = new ViewModelProvider(this).get(CredentialsConversationMessageViewModel.class);
        viewModel.getCredentials().observe(this, credentials -> {
            mCredentials = credentials;
            trueMessage.setLastName(credentials.getLastName());
            trueMessage.setFirstName(credentials.getFirstName());
        });

        //Time
        String time = StringOperations.milliToTimeDelay(System.currentTimeMillis(), mCredentials.getTime());

        //Reason
        String currentDate = new SimpleDateFormat("E", Locale.getDefault()).format(new Date());
        int currentHour = Integer.parseInt(time.substring(0, 2));
        if (mCredentials.getReason().equals("_auto_")) {
            if (currentHour >= 21) {
                trueMessage.setReason("1");
            } else {
                trueMessage.setReason("2");
            }
        }

        //Address
        if (mCredentials.getAddress().equals("_auto_")) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            googleApiClient.connect();
            Runnable myRunnable = () -> {
                location = receive();
                Geocoder geocoder = new Geocoder(MainActivity.this, aLocale);
                List<Address> addresses;
                try {
                    addresses = geocoder.getFromLocation(location.getLatitude() - 0.001, location.getLongitude() - 0.001, 1);
                    trueMessage.setAddress(addresses.get(0).getThoroughfare().toUpperCase() + " " + addresses.get(0).getSubThoroughfare());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
            Thread myThread = new Thread(myRunnable);
            myThread.start();
        } else {
            trueMessage.setAddress(mCredentials.getAddress());
        }

        //Message creation
        Message message = new Message(0, trueMessage.toStringTransport(), time, 0);

        RecyclerView recyclerView = findViewById(R.id.messagePreviewRecyclerView);
        ConversationPreviewAdapter adapter = new ConversationPreviewAdapter();
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getConversationMessages().observe(this, adapter::setConversationMessages);
        viewModel.getMessages().observe(this, messages -> {
            messages.add(0, message);
            adapter.setMessages(messages);
        });
    }

    @Override
    protected void onStop() {
        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    protected void onStart() {
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
        super.onStart();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 34);
            return;
        }

        LocationRequest locationRequest = LocationRequest.create()
                .setFastestInterval(1000)
                .setInterval(3000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@Nullable ConnectionResult connectionResult) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 34) {
            permissionGranted = true;
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = false;
                    break;
                }
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Runnable myRunnable = () -> send(location);
        Thread myThread = new Thread(myRunnable);
        myThread.start();
        if (!ready) {
            Toast.makeText(this, "Έτοιμο", Toast.LENGTH_LONG).show();
            ready = true;
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onClick(View view, int position) {

        openConversationActivity(position);
    }

    public synchronized void send(Location packet) {
        while (!transfer) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        transfer = false;

        this.location = packet;
        notifyAll();
    }

    public synchronized Location receive() {
        while (transfer) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        transfer = true;

        notifyAll();
        return location;
    }

    private void openConversationActivity(int position) {

        Bundle bundle = new Bundle();
        bundle.putString("conversationSender", conversationMessageList.get(position).getConversationSender());
        Intent intent = new Intent(this, ConversationActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void openOptionActivity() {

        Intent intent = new Intent(this, OptionActivity.class);
        startActivity(intent);
        finish();
    }
}