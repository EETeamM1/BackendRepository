package com.ee.enigma.common;

public class EngimaException extends Exception
{
  private static final long serialVersionUID = 1L;
  private String message = null;
  
  public EngimaException(String message)
  {
    super(message);
    this.message = message;
  }

  public EngimaException()
  {
    super();
  }
  
  public EngimaException(String message,Throwable cause)
  {
    super(message,cause);
    this.message = message;
  }
  
  @Override
  public String toString() {
      return message;
  }

  @Override
  public String getMessage() {
      return message;
  }
}
