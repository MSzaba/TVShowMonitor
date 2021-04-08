package com.tvshowmonitor;

public class URLProcessorFaxtory {
	
	private final static String HOSTNAME_PORT_HU = "port.hu";

	public static URLProcessor getProcessorByHomepage(String hostname) {
		String loCase = hostname.toLowerCase();
		if (loCase.contains(HOSTNAME_PORT_HU)) {
			return new PortHuURLProcessor();
		}
		return null;
	}

}
