package edu.ilstu.bluetoothpopquiz;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Set;

import java.util.List;
import java.util.UUID;

// TODO: Connection functionality
public class StudentBluetoothActivity extends AppCompatActivity {

    private ListView bluetoothListView;
    private BluetoothAdapter bluetoothAdapter;
    private Set<BluetoothDevice> bluetoothDevices;
    private ArrayAdapter<String> btArrayAdapter;
    UUID MY_UUID = UUID.fromString("49091324-bb5f-11e6-a4a6-cec0c932ce01");
    final int REQUEST_ENABLE_BT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_bluetooth);
        init();
    }

    private void init() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        enableBluetooth();
        startDiscovery();
        bluetoothListView = (ListView) findViewById(R.id.bluetooth_list_view);
        btArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        bluetoothListView.setAdapter(btArrayAdapter);
        bluetoothListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startPollingActivity();
            }
        });
        list();
    }

    private void startPollingActivity() {
        Intent i = new Intent(this, StudentPollingActivity.class);
        startActivity(i);
    }

    public void startDiscovery(){
        bluetoothAdapter.startDiscovery();
    }

    private void enableBluetooth() {
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }


    private void list() {
        bluetoothDevices = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : bluetoothDevices) {
            btArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            ConnectThread connectThread = new ConnectThread(device);
        }
        btArrayAdapter.add("Xoom" + "\n" + "4C:60:D7:3B:21:11");

    }

    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket,
            // because mmSocket is final
            BluetoothSocket tmp = null;
            mmDevice = device;

            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try {
                // MY_UUID is the app's UUID string, also used by the server code
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
            }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it will slow down the connection
            bluetoothAdapter.cancelDiscovery();

            try {
                // Connect the device through the socket. This will block
                // until it succeeds or throws an exception
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and get out
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                }
                return;
            }

            // Do work to manage the connection (in a separate thread)
            manageConnectedSocket(mmSocket);
        }

        /**
         * Will cancel an in-progress connection, and close the socket
         */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
            }
        }
    }

    public void manageConnectedSocket(BluetoothSocket socket) {
        ConnectedThread connectedThread = new ConnectedThread(socket);
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public Object getAnswers() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    mmInStream.read(buffer);
                    return toObject(buffer);
                } catch (IOException e) {
                    break;
                }
            }
            return null;
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(Object questions) {
            try {
                mmOutStream.write(toByteArray(questions));
            } catch (IOException e) {
            }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
            }
        }
    }

    public static byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ObjectOutputStream oos = null;

        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(b);
            oos.writeObject(obj);

            return b.toByteArray();
        } catch (Exception e) {
            Log.e("Bluetooth", "Cast exception at sending end ...");
        }

        return bytes;
    }

    public static Object toObject(byte[] bytes) {
        Object obj = null;
        ObjectInputStream ois = null;

        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return ois.readObject();
        } catch (Exception ex) {
            Log.e("Bluetooth", "Cast exception at receiving end ...");
        }
        return obj;
    }
}