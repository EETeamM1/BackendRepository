package com.ee.enigma.response;


public class EnigmaResponse {
	private ResponseResult result;
	private ResponseCode responseCode;
	private String returnResponse;
	
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
  public String getReturnResponse()
  {
    return returnResponse;
  }
  public void setReturnResponse(String returnResponse)
  {
    this.returnResponse = returnResponse;
  }	
	
}
