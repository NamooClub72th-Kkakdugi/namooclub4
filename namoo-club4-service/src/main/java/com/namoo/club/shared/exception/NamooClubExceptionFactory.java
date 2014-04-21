package com.namoo.club.shared.exception;

public class NamooClubExceptionFactory {

	private NamooClubExceptionFactory() {
		//
	}

	public static NamooClubException createRuntime(String msg) {
		//
		return new NamooClubException(msg);
	}
}
