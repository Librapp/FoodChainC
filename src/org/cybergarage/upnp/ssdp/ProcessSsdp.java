package org.cybergarage.upnp.ssdp;

public class ProcessSsdp implements Runnable {
	Multicast mc1 = null;
	
	  public ProcessSsdp(){
		  System.out.println("C: Start ProcessSSDP");
		  mc1 = new Multicast();
		  mc1.Start();
	  }
	  public void run() {  
	        mc1.Receive();
	  }
}
