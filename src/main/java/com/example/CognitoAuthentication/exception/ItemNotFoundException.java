package com.example.CognitoAuthentication.exception;

public class ItemNotFoundException extends RuntimeException {
	public ItemNotFoundException(Long itemId) {
		super("Item ID:" + itemId + "is not found.");
	}
}
