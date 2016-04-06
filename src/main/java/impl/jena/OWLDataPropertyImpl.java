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
 * Created on Dec 12, 2004
 */
package impl.jena;

import org.mindswap.owl.OWLDataProperty;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author unascribed
 * @version $Rev: 2530 $; $Author: thorsten $; $Date: 2010-07-30 20:46:00 +0300 (Fri, 30 Jul 2010) $
 */
public class OWLDataPropertyImpl extends OWLPropertyImpl<DatatypeProperty> implements OWLDataProperty
{
	public OWLDataPropertyImpl(final OWLOntologyImpl model, final DatatypeProperty prop)
	{
		super(model, prop);
	}

	/* @see org.mindswap.owl.OWLDataProperty#isEquivalentProperty(org.mindswap.owl.OWLDataProperty) */
	public boolean isEquivalentProperty(final OWLDataProperty p)
	{
		return isEquivalentProperty((Property) p.getImplementation());
	}

	/* @see org.mindswap.owl.OWLDataProperty#isSubPropertyOf(org.mindswap.owl.OWLDataProperty) */
	public boolean isSubPropertyOf(final OWLDataProperty p)
	{
		return isSubPropertyOf((Property) p.getImplementation());
	}

	/* @see impl.jena.OWLEntityImpl#wrap(com.hp.hpl.jena.rdf.model.Resource) */
	@Override
	protected final OWLDataPropertyImpl wrap(final Resource res)
	{
		return new OWLDataPropertyImpl(ontology, res.as(DatatypeProperty.class));
	}
}
