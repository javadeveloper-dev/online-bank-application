package com.banking_app.util;

public class LoginUtil {
	public static String getBaseUrl(String fullUrl) {
		if (fullUrl.contains("/admin/")) {
			return fullUrl.substring(0, fullUrl.lastIndexOf("admin"));
		} else if (fullUrl.contains("/user/")) {
			return fullUrl.substring(0, fullUrl.lastIndexOf("user"));
		} else if (fullUrl.contains("/welcome")) {
			return fullUrl.substring(0, fullUrl.lastIndexOf("welcome"));
		} else if (fullUrl.contains("/logout")) {
			return fullUrl.substring(0, fullUrl.lastIndexOf("logout"));
		} else if (fullUrl.contains("/error")) {
			return fullUrl.substring(0, fullUrl.lastIndexOf("error")) + "onlinebankapplication/";
		}
		return fullUrl.substring(0, fullUrl.lastIndexOf("login"));
	}

	public static String getBaseUrlFromLastSlash(String fullUrl) {
		if (fullUrl.contains("admin")) {
			if (fullUrl.contains("login")) {
				fullUrl = fullUrl.substring(0, fullUrl.lastIndexOf("adminLogin"));
			} else {
				fullUrl = fullUrl.substring(0, fullUrl.indexOf("/admin/") + "/admin/".length());
			}
		} else if (fullUrl.contains("user")) {
			if (fullUrl.contains("login")) {
				fullUrl = fullUrl.substring(0, fullUrl.lastIndexOf("userLogin"));
			} else {
				fullUrl = fullUrl.substring(0, fullUrl.indexOf("/user/") + "/user/".length());
			}
		}
		return fullUrl.substring(0, fullUrl.lastIndexOf("/") + 1);
	}

}
