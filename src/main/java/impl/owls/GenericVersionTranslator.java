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
 * Created on Dec 27, 2003
 *
 */
package impl.owls;

import org.mindswap.owl.OWLOntology;
import org.mindswap.owls.OWLSVersionTranslator;

/**
 *
 * @author unascribed
 * @version $Rev: 2269 $; $Author: thorsten $; $Date: 2009-08-19 18:21:09 +0300 (Wed, 19 Aug 2009) $
 */
public class GenericVersionTranslator implements OWLSVersionTranslator
{

	private static final OWLSVersionTranslator[] translators = {
		new OWLSTranslator_1_1(),
		new OWLSTranslator_1_0()
	};

	/* @see org.mindswap.owls.OWLSVersionTranslator#getVersion() */
	public String getVersion()
	{
		return "X.X";
	}

	/* @see org.mindswap.owls.OWLSVersionTranslator#canTranslate(org.mindswap.owl.OWLOntology) */
	public boolean canTranslate(final OWLOntology ontology)
	{
		return findTranslator(ontology) != null;
	}

	/* @see org.mindswap.owls.OWLSVersionTranslator#translate(org.mindswap.owl.OWLOntology, org.mindswap.owl.OWLKnowledgeBase) */
	public OWLOntology translate(final OWLOntology inputOnt, final OWLOntology outputOnt)
	{
		OWLSVersionTranslator translator = findTranslator(inputOnt);
		return translator.translate(inputOnt, outputOnt);
	}

	private OWLSVersionTranslator findTranslator(final OWLOntology ont)
	{
		for (int i = 0; i < translators.length; i++)
		{
			if (translators[i].canTranslate(ont)) return translators[i];
		}

		// by default do not translate
		return translators[0];
	}

}
