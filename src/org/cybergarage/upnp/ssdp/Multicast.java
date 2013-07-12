package org.cybergarage.upnp.ssdp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.UUID;

import android.content.Context;
import android.net.wifi.WifiManager;

public class Multicast {
	public static final int PORT = 1900;
	public static final String ADDRESS = "239.255.255.250";

	private DatagramSocket dSocket1;
	private boolean useIPv6Address;

	MulticastSocket MultiSocket = null;
	DatagramSocket serverSocket = null;
	InetAddress serverAddr = null;
	DatagramPacket dpacket = null;

	InetSocketAddress ssdpMultiGroup = null;
	NetworkInterface ssdpMultiIf = null;

	String uuid = null;

	public Multicast() {
		uuid = UUID.randomUUID().toString();
	}

	public boolean Start() {

		return true;
	}

	public void Receive() {
		try {
			useIPv6Address = false;

			InetAddress addr = InetAddress.getByName(ADDRESS);
			String addrstr;
			if (addr instanceof Inet6Address) {
				addrstr = "FF02::C"; // IPV6_LINK_LOCAL_ADDRESS;
										// //SSDP.getIPv6Address();
				useIPv6Address = true;
				System.out.println("C: IPv6");
			} else
				addrstr = ADDRESS;

			serverAddr = InetAddress.getByName("239.255.255.250");

			System.out.println("C: MulticastSocket Create");

			MultiSocket = new MulticastSocket(null);
			MultiSocket.setReuseAddress(true);

			InetSocketAddress bindSockAddr = new InetSocketAddress(PORT);
			MultiSocket.bind(bindSockAddr);
			ssdpMultiGroup = new InetSocketAddress(
					InetAddress.getByName(addrstr), PORT);
			ssdpMultiIf = NetworkInterface.getByInetAddress(serverAddr);

			// MultiSocket.setSoTimeout(5000);
			MultiSocket.setTimeToLive(2);
			MultiSocket.joinGroup(ssdpMultiGroup, ssdpMultiIf);

			System.out.println("C: Join Group");

			byte ssdvRecvBuf[] = new byte[1024];

			dpacket = new DatagramPacket(ssdvRecvBuf, 1024);
			/*
			 * serverAddr = InetAddress.getByName("239.255.255.250");
			 * MultiSocket = new MulticastSocket(PORT);
			 * 
			 * byte b = 64; MultiSocket.setTTL(b);
			 * MultiSocket.joinGroup(serverAddr);
			 * 
			 * byte ssdvRecvBuf[] = new byte[1024]; dpacket = new
			 * DatagramPacket(ssdvRecvBuf, 1024);
			 */
		} catch (Exception e) {
			System.out.println("C: Error3");
		} finally {

		}
		while (true) {
			try {
				System.out.println("C: wait to receive~");
				MultiSocket.receive(dpacket);

				SSDPMessage ssdpMsg = new SSDPMessage();
				byte[] DiscoveryData = dpacket.getData();
				ssdpMsg.process(DiscoveryData);

				String SendData1 = "HTTP/1.1 200 OK\n"
						+ "CACHE-CONTROL : max-age = 180\n"
						+ "LOCATION: http://192.168.1.1:5431/dyndev/uuid:0014-bf09\n"
						+ "ST: upnp:rootdevice\n"
						+ "USN: uuid:0014-bf09::upnp:rootdevice\n";
				String SendData2 = "NOTIFY * HTTP/1.1\n"
						+ "HOST: 239.255.255.250:1900\n"
						+ "CACHE-CONTROL: max-age=180\n" + "Location:"
						+ serverAddr + "/dyndev/uuid:" + uuid + "\n"
						+ "NT: upnp:rootdevice\n" + "NTS: ssdp:alive\n"
						+ "SERVER:Android/2.1 UPnP/1.0 BRCM400/1.0\n"
						+ "USN: uuid:" + uuid + "::upnp:rootdevice\n";
				dpacket.setData(SendData2.getBytes());
				MultiSocket.send(dpacket);
			} catch (Exception e) {
				System.out.println("C: Error4 " + e.toString());
				return;
			} finally {

			}

			InetAddress ia = dpacket.getAddress();
			String str = new String(dpacket.getData()).trim();

			System.out.println("C: " + ia.getHostName() + " receive ==> \n"
					+ str + "\n\n");

			return;
		}
	}
}
