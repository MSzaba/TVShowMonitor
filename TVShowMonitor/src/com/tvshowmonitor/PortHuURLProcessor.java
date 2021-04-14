package com.tvshowmonitor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PortHuURLProcessor extends URLProcessor {
	
	private static final String NAME_PREFIX = "<title>";
	private static final String NAME_POSTFIX = "</title>";
	private static final String SHOWTIME_PREFIX = "<!-- showtime box -->";
	private static final String SHOWTIME_POSTFIX = "<!-- \\/showtime box -->";
	private static final String DIV_PREFIX = "<div class=\\\"bold\\\">";
	private static final String DIV_POSTFIX = "</div>";
	private static final String CHANNEL_PREFIX = "<a href=\".*\">";
	private static final String CHANNEL_POSTFIX = "</a>";
	
	
	@Override
	String getPlaytimes(String body, String nameOfTheFilm) {
		
		int showtimeStartIndex = getShowtimeStartIndex(body);
		if (showtimeStartIndex < 0) {
			return "The film won't be played in the near future";
		}
		int showtimeEndIndex = getShowtimeEndIndex(body);
		String showTimeBody = body.substring(showtimeStartIndex, showtimeEndIndex);
		//System.out.println(showTimeBody);
		StringBuffer sb = new StringBuffer();
		boolean firstItem = true;
		String divRegex = DIV_PREFIX + ".*" + DIV_POSTFIX;
		Pattern divPattern = Pattern.compile(divRegex);
		Matcher divMatcher = divPattern.matcher(showTimeBody);
		while(divMatcher.find()){
			if (!firstItem) {
				sb.append(", ");
				
			} else {
				firstItem = false;
			}
			int divStartIndex = divMatcher.start() + DIV_PREFIX.length() -2;
			int divEndIndex = divMatcher.end() - DIV_POSTFIX.length();
			sb.append(showTimeBody.substring(divStartIndex, divEndIndex));
			sb.append(" @ ");
			sb.append(getChannelName(showTimeBody.substring(divMatcher.end())));
		}
		if (firstItem) {
			return "The film won't be played in the near future";
		}
		
		return sb.toString();
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

	private Object getChannelName(String remaingPart) {
		String channelRegex = CHANNEL_PREFIX + ".*" + CHANNEL_POSTFIX;
		Pattern channelPattern = Pattern.compile(channelRegex);
		Matcher channelMatcher = channelPattern.matcher(remaingPart);
		if (channelMatcher.find()) {
			int matcherStart = channelMatcher.start();
			int startIndex = matcherStart + remaingPart.substring(matcherStart).indexOf('>') + 1;
			return remaingPart.substring(startIndex, channelMatcher.end() - CHANNEL_POSTFIX.length());
		}
		return "Unknown channel";
	}
	
	private int getShowtimeEndIndex(String body) {
		String showtimeRegex = SHOWTIME_POSTFIX;
		Pattern showtimePattern = Pattern.compile(showtimeRegex);
		
		Matcher showtimeMatcher = showtimePattern.matcher(body);
		if (showtimeMatcher.find()) {
			return showtimeMatcher.start() - 1;
			
		} 
		return -1;
	}

	private int getShowtimeStartIndex(String body) {
		String showtimeRegex = SHOWTIME_PREFIX;
		Pattern showtimePattern = Pattern.compile(showtimeRegex);
		
		Matcher showtimeMatcher = showtimePattern.matcher(body);
		if (showtimeMatcher.find()) {
			return showtimeMatcher.start() + SHOWTIME_PREFIX.length() +1;
			
		} 
		return -1;
	}

}