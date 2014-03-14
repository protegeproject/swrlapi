package org.swrlapi.exceptions;

public class InvalidBuiltInMethodsImplementationClass extends BuiltInException
{
	private static final long serialVersionUID = -3805466029948851976L;

	public InvalidBuiltInMethodsImplementationClass(String className)
	{
		super("Class " + className + " does not implement the interface SWRLBuiltInMethods");
	}
}
