/*
 * Created 24.11.2008
 *
 * (c) 2008 Thorsten M�ller - University of Basel Switzerland
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
package org.mindswap.owls.profile;

/**
 *
 * @author unascribed
 * @version $Rev: 2269 $; $Author: thorsten $; $Date: 2009-08-19 18:21:09 +0300 (Wed, 19 Aug 2009) $
 */
public interface Retriability extends ServiceParameter
{
	/**
	 * This method corresponds to {@link ServiceParameter#getParameter()}
	 * while narrowing the data type to boolean values as defined by the backing
	 * ontology that defines {@link Retriability}.
	 *
	 * @return <code>true</code> if the service&mdash;represented by its profile&mdash;
	 * 	is guaranteed to terminate successfully after a finite number of invocations.
	 */
	public boolean isRetriable();

	/**
	 * This method corresponds to {@link ServiceParameter#setParameter(org.mindswap.owl.OWLIndividual)}
	 * while narrowing the data type to boolean values as defined by the backing
	 * ontology that defines {@link Retriability}.
	 *
	 * @param isRetriable Whether the service&mdash;represented by its profile&mdash;
	 * 	is guaranteed to terminate successfully after a finite number of invocations.
	 */
	public void setRetriable(boolean isRetriable);

}
