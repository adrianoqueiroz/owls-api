// The MIT License
//
// Copyright (c) 2004 Evren Sirin
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to
// deal in the Software without restriction, including without limitation the
// rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
// sell copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
// FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
// IN THE SOFTWARE.

/*
 * Created on Mar 11, 2004
 */
package impl.owls.profile;

import impl.owl.WrappedIndividual;

import org.mindswap.owl.OWLIndividual;
import org.mindswap.owls.profile.ServiceParameter;
import org.mindswap.owls.vocabulary.OWLS;

/**
 * @author unascribed
 * @version $Rev: 2269 $; $Author: thorsten $; $Date: 2009-08-19 18:21:09 +0300 (Wed, 19 Aug 2009) $
 */
public class ServiceParameterImpl extends WrappedIndividual	implements ServiceParameter
{
	public ServiceParameterImpl(final OWLIndividual ind)
	{
		super(ind);
	}

	/* @see org.mindswap.owls.profile.ServiceParameter#getName() */
	public String getName()
	{
		return getPropertyAsString(OWLS.ServiceParameter.serviceParameterName);
	}

	/* @see org.mindswap.owls.profile.ServiceParameter#getParameter() */
	public OWLIndividual getParameter()
	{
		return getProperty(OWLS.ServiceParameter.sParameter);
	}

	/* @see org.mindswap.owls.profile.ServiceParameter#setName(java.lang.String) */
	public void setName(final String name)
	{
		setProperty(OWLS.ServiceParameter.serviceParameterName, name);
	}

	/* @see org.mindswap.owls.profile.ServiceParameter#setParameter(org.mindswap.owl.OWLIndividual) */
	public void setParameter(final OWLIndividual value)
	{
		setProperty(OWLS.ServiceParameter.sParameter, value);
	}
}
