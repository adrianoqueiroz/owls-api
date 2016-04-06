/*
 * Created 27.06.2009
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
package impl.owls.process.binding;

import org.mindswap.owl.OWLIndividual;
import org.mindswap.owls.process.variable.Link;
import org.mindswap.owls.process.variable.LinkBinding;
import org.mindswap.owls.vocabulary.OWLS;

/**
 *
 * @author unascribed
 * @version $Rev: 2269 $; $Author: thorsten $; $Date: 2009-08-19 18:21:09 +0300 (Wed, 19 Aug 2009) $
 */
public class LinkBindingImpl extends BindingImpl<Link> implements LinkBinding
{
	public LinkBindingImpl(final OWLIndividual ind)
	{
		super(ind);
	}

	/* @see org.mindswap.owls.process.variable.Binding#getProcessVar() */
	public Link getProcessVar()
	{
		return getPropertyAs(OWLS.Process.toVar, Link.class);
	}

	/* @see org.mindswap.owls.process.variable.Binding#setProcessVar(org.mindswap.owls.process.variable.ProcessVar) */
	public Link setProcessVar(final Link procVar)
	{
		return setProcessVariable(procVar);
	}

	/* @see impl.owls.process.binding.BindingImpl#toString() */
	@Override
	public String toString()
	{
		return "Link binding: " + super.toString();
	}

}
