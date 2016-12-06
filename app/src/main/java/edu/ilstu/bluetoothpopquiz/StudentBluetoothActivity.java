package edu.ilstu.bluetoothpopquiz;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Set;

// TODO: Connection functionality
public class StudentBluetoothActivity extends AppCompatActivity {

    private ListView bluetoothListView;
    private BluetoothAdapter bluetoothAdapter;
    private Set<BluetoothDevice> bluetoothDevices;
    private ArrayAdapter<String> btArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_bluetooth);
        // TODO: Temporary Redirect to next activity//////
        Intent i = new Intent(this, StudentQuizTakerActivity.class);
        startActivity(i);
        //////////////////////////////////////////////////
        init();
    }
    private void init(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothListView = (ListView) findViewById(R.id.bluetooth_list_view);
        btArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        bluetoothListView.setAdapter(btArrayAdapter);
        list();
    }

    private void list(){
        bluetoothDevices = bluetoothAdapter.getBondedDevices();
        for(BluetoothDevice device : bluetoothDevices)
            btArrayAdapter.add(device.getName()+ "\n" + device.getAddress());
    }
}
