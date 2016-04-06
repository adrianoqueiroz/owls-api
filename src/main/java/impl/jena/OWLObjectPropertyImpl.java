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

import org.mindswap.owl.OWLObjectProperty;

import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.OWL;

/**
 *
 * @author unascribed
 * @version $Rev: 2530 $; $Author: thorsten $; $Date: 2010-07-30 20:46:00 +0300 (Fri, 30 Jul 2010) $
 */
public class OWLObjectPropertyImpl extends OWLPropertyImpl<ObjectProperty> implements OWLObjectProperty
{
	public OWLObjectPropertyImpl(final OWLOntologyImpl model, final ObjectProperty prop)
	{
		super(model, prop);
	}

	/* @see org.mindswap.owl.OWLObjectProperty#isEquivalentProperty(org.mindswap.owl.OWLObjectProperty) */
	public boolean isEquivalentProperty(final OWLObjectProperty p)
	{
		return isEquivalentProperty((Property) p.getImplementation());
	}

	/* @see org.mindswap.owl.OWLObjectProperty#isInverseOf(org.mindswap.owl.OWLObjectProperty) */
	public boolean isInverseOf(final OWLObjectProperty p)
	{
		return ontology.getKB().ontModel.contains(resource, OWL.inverseOf, (Property) p.getImplementation());
	}

	/* @see org.mindswap.owl.OWLObjectProperty#isSubPropertyOf(org.mindswap.owl.OWLObjectProperty) */
	public boolean isSubPropertyOf(final OWLObjectProperty p)
	{
		return isSubPropertyOf((Property) p.getImplementation());
	}

	/* @see impl.jena.OWLEntityImpl#wrap(com.hp.hpl.jena.rdf.model.Resource) */
	@Override
	protected final OWLObjectPropertyImpl wrap(final Resource res)
	{
		return new OWLObjectPropertyImpl(ontology, res.as(ObjectProperty.class));
	}
}
