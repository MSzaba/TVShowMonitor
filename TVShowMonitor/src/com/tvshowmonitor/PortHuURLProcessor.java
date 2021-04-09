package com.tvshowmonitor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PortHuURLProcessor extends URLProcessor {
	
	private static final String NAME_PREFIX = "<title>";
	private static final String NAME_POSTFIX = "</title>";
	private static final String SHOWTIME_PREFIX = "/<!-- showtime box -->";
	private static final String SHOWTIME_POSTFIX = "<!-- \\/showtime box -->/gm";
	
	
	@Override
	String getPlaytimes(String body, String nameOfTheFilm) {
		String showtimeRegex = SHOWTIME_PREFIX + "[\\s\\S]*" + SHOWTIME_POSTFIX;
		String showtimeBody = "";
		
		Pattern showtimePattern = Pattern.compile(showtimeRegex);
		Matcher showtimeMatcher = showtimePattern.matcher(body);
		if (showtimeMatcher.find()) {
			showtimeBody = body.substring(showtimeMatcher.start() + SHOWTIME_PREFIX.length(), showtimeMatcher.end() - SHOWTIME_POSTFIX.length());
			System.out.println(showtimeBody);
		} else {
			return "Unable to find show time for film " + nameOfTheFilm;
		}
		return null;
	}

	@Override
	String getNameOfTheFilm(String body) {
		String nameOfTheFilmRegex = NAME_PREFIX + ".*" + NAME_POSTFIX;
		Pattern namePattern = Pattern.compile(nameOfTheFilmRegex);
		Matcher nameMatcher = namePattern.matcher(body);
		if (nameMatcher.find()) {
			return body.substring(nameMatcher.start() + NAME_PREFIX.length(), nameMatcher.end() - NAME_POSTFIX.length());
		} 
		return  null;
	}

}