/*
 * Created 09.08.2009
 *
 * (c) 2009 Thorsten M�ller - University of Basel Switzerland
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
package org.mindswap.owls.grounding;

import java.net.URI;

import org.mindswap.common.ServiceFinder;
import org.mindswap.owl.OWLModel;

/**
 *
 * @author unascribed
 * @version $Rev: 2298 $; $Author: thorsten $; $Date: 2009-08-26 16:46:02 +0300 (Wed, 26 Aug 2009) $
 */
public class UPnPGroundingFactory
{
	private static final UPnPGroundingProvider provider = ServiceFinder.instance().loadImplementation(
		UPnPGroundingProvider.class, UPnPGroundingProvider.DEFAULT_UPNP_GROUNDING_PROVIDER);

	public static final UPnPGrounding createUPnPGrounding(final URI uri, final OWLModel model)
	{
		return provider.createUPnPGrounding(uri, model);
	}

	public static final UPnPAtomicGrounding createUPnPAtomicGrounding(final URI uri, final OWLModel model)
	{
		return provider.createUPnPAtomicGrounding(uri, model);
	}

	public static final MessageMap<String> createUPnPMessageMap(final URI uri, final OWLModel model)
	{
		return provider.createUPnPMessageMap(uri, model);
	}

}
