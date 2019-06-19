package com.revature.cardealership.model;

import java.util.HashMap;
import java.util.Map;

public enum OfferStatus {
	ACCEPTED(1), REJECTED(2), PENDING(3), ALL(4);

	private int value;
	private static Map<Integer, OfferStatus> map = new HashMap<>();

	public int getValue() {
		return value;
	}

	private OfferStatus(int value) {
		this.value = value;
	}

	static {
		for (OfferStatus status : OfferStatus.values()) {
			map.put(status.value, status);
		}
	}

	public static OfferStatus valueOf(int value) {
		return (OfferStatus) map.get(value);
	}

}
