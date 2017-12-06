package net;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class LocalIP{
	private static final void printIp() {   
		   
		        try {   
		            for (Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces(); e.hasMoreElements();) {   
		                NetworkInterface item = e.nextElement();   
		   
		                System.out.println(item.toString());   
		                System.out.println("MTU --"+item.getMTU() + " isLoopBack--" + item.isLoopback() + " isPointToPoint --" + item.isPointToPoint() + " isUp--" + item.isUp() + " isVirture--" + item.isVirtual());   
		   
		                for (InterfaceAddress address : item.getInterfaceAddresses()) {   
		                    if (address.getAddress() instanceof Inet4Address) {   
		                        Inet4Address inet4Address = (Inet4Address) address.getAddress();   
		                        System.out.println("hostAddress--"+inet4Address.getHostAddress());   
		                        System.out.println("isLinkLocalAddress--"+inet4Address.isLinkLocalAddress() + " isLoopbackAddress--" + inet4Address.isLoopbackAddress() + " isMCGlobal--" + inet4Address.isMCGlobal() + " isMulticastAddress--" + inet4Address.isMulticastAddress());   
		                    }   
		                }   
		            }   
		        } catch (IOException ex) {   
		   
		        }   
		    }   
		    public static void main(String[] args) {   
		        printIp();   
		    }   
}
