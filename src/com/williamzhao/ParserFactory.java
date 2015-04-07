package com.williamzhao;

import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class ParserFactory {
	private static XmlPullParserFactory parserFactory = null;
	
	public static XmlPullParserFactory getParserFactory() {
		if ( ParserFactory.parserFactory == null ) {
			try {
				ParserFactory.parserFactory = XmlPullParserFactory.newInstance();
			} catch ( final XmlPullParserException e ) {
				logParserFactoryError(e);
			}
		}
		return ParserFactory.parserFactory;
	}
	
	private static void logParserFactoryError(XmlPullParserException e) {
		Log.e( "nba", "ParseFactory:getParserFactory: Caught exception making parser factory: " + e.getLocalizedMessage() );
		e.printStackTrace();
	}
}
