package com.conference.management.exception;

/**
 * Custom Exception for using within the application
 *
 */
public class ConferenceException extends Exception {

	public ConferenceException() {
		super();
	}

	public ConferenceException(String message) {
		super(message);
	}

	public ConferenceException(Throwable cause) {
		super(cause);
	}

	public ConferenceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConferenceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	

}
