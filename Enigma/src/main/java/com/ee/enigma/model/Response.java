package com.ee.enigma.model;


public class Response {
	private ResponseResult result;
	private ResponseCode responseCode;
	
	
	public ResponseResult getResult() {
		return result;
	}
	public void setResult(ResponseResult result) {
		this.result = result;
	}
	public ResponseCode getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(ResponseCode responseCode) {
		this.responseCode = responseCode;
	}	
}
