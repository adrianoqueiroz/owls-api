/*
 * Created 01.03.2009
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
package impl.jena;

import org.mindswap.owl.OWLAnnotationProperty;

import com.hp.hpl.jena.ontology.AnnotationProperty;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 *
 * @author unascribed
 * @version $Rev: 2530 $; $Author: thorsten $; $Date: 2010-07-30 20:46:00 +0300 (Fri, 30 Jul 2010) $
 */
public class OWLAnnotationPropertyImpl extends OWLPropertyImpl<AnnotationProperty> implements OWLAnnotationProperty
{

	public OWLAnnotationPropertyImpl(final OWLOntologyImpl ontology, final AnnotationProperty property)
	{
		super(ontology, property);
	}

	/* @see impl.jena.OWLEntityImpl#wrap(com.hp.hpl.jena.rdf.model.Resource) */
	@Override
	protected final OWLAnnotationPropertyImpl wrap(final Resource res)
	{
		return new OWLAnnotationPropertyImpl(ontology, res.as(AnnotationProperty.class));
	}

}
