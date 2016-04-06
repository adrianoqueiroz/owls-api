/*
 * Created 10.02.2010
 *
 * (c) 2010 Thorsten M�ller - University of Basel Switzerland
 *
 * The MIT License
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */
package org.mindswap.exceptions;

import org.mindswap.owls.process.Process;

/**
 * Thrown if invocation of a service failed (because it is offline or crashed)
 * or if the service returned an error.
 *
 * @author unascribed
 * @version $Rev: 2535 $; $Author: thorsten $; $Date: 2010-08-18 15:19:53 +0300 (Wed, 18 Aug 2010) $
 */
public class ServiceFailureException extends HealableExecutionException
{
	private static final long serialVersionUID = -7154150820783370881L;

	/**
	 * @see ExecutionException#ExecutionException()
	 */
	public ServiceFailureException()
	{
		super();
	}

	/**
	 * @see ExecutionException#ExecutionException(String)
	 */
	public ServiceFailureException(final String message)
	{
		super(message);
	}

	/**
	 * @see ExecutionException#ExecutionException(String, Process)
	 */
	public ServiceFailureException(final String message, final Process process)
	{
		super(message, process);
	}

}
