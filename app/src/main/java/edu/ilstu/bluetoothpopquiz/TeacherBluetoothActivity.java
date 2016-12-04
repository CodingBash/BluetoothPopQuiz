package edu.ilstu.bluetoothpopquiz;

//Teacher is to be considered as the remote device. The Student is
//  client device that scans for available remote devices to connect to.

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import android.widget.Toast;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TeacherBluetoothActivity extends AppCompatActivity {

    private BluetoothAdapter BA;
    private Set<BluetoothDevice>pairedDevices;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_bluetooth);
        // TODO: Bluetooth Implementation
        Intent i = new Intent(this, TeacherQuizCreationActivity.class);
        startActivity(i);
    }

    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    final int REQUEST_ENABLE_BT = 1; //request integer must be > 1
    //use UUID generator for a distinct UUID for client to find,
    //This exact UUID must also be used in the StudentBluetoothActivity
    UUID MY_UUID = UUID.randomUUID();

    public void blueToothRemote()
    {
        enableBlueTooth();
        makeDiscoverable();
        AcceptThread acceptThread = new AcceptThread();
        startAcceptThread(acceptThread);

    }
    private BluetoothAdapter getmBluetoothAdapter() {
        return mBluetoothAdapter;
    }

    private void enableBlueTooth()
    {
        if(!mBluetoothAdapter.isEnabled()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    private void makeDiscoverable(){
        Intent discoverableIntent = new
                Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
    }

    private void startAcceptThread(AcceptThread acceptThread){
        acceptThread.run();
    }

    private void endAcceptThread(AcceptThread acceptThread){
        acceptThread.cancel();
    }

    private void manageConnectedSocket(BluetoothSocket socket){
        ConnectedThread connectedThread = new ConnectedThread(socket);
    }

    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            // Use a temporary object that is later assigned to mmServerSocket,
            // because mmServerSocket is final
            BluetoothServerSocket tmp = null;
            try {
                // MY_UUID is the app's UUID string, also used by the client code
                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("BluetoothPopQuiz", MY_UUID);
            } catch (IOException e) { }
            mmServerSocket = tmp;
        }

        public void run() {
            BluetoothSocket socket = null;
            // Keep listening until exception occurs or a socket is returned
            while (true) {
                try {
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    break;
                }
                // If a connection was accepted

                if (socket != null) {
                    // Do work to manage the connection (in a separate thread)
                    manageConnectedSocket(socket);
                    try {
                        mmServerSocket.close();
                        break;
                    } catch (IOException e) {
                        break;
                    }
                }
                // This logic is for only trying to connect one device,
                //which we will only have to test for one.
            }
        }
        /** Will cancel the listening socket, and cause the thread to finish */
        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) { }
        }
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
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public byte [] getAnswers() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    mmInStream.read(buffer);
                    return buffer;
                } catch (IOException e){break;}
            }
            return null;
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(byte[] questions) {
            try {
                mmOutStream.write(questions);
            } catch (IOException e) { }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }

}
