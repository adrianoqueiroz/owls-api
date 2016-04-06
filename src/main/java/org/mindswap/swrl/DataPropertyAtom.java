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
 * Created on Oct 26, 2004
 */
package org.mindswap.swrl;

import org.mindswap.owl.OWLDataProperty;
import org.mindswap.query.ValueMap;

/**
 * Evaluation ({@link #evaluate(ValueMap)}) of a datatype property atom will
 * add the following statement to the backing {@link #getOntology() ontology}:
 * ({@link #getArgument1() subject} {@link #getPropertyPredicate() predicate}
 * {@link #getArgument2() object}).
 *
 * @author unascribed
 * @version $Rev: 2269 $; $Author: thorsten $; $Date: 2009-08-19 18:21:09 +0300 (Wed, 19 Aug 2009) $
 */
public interface DataPropertyAtom extends Atom {

	/**
	 * Returns the data property predicate for this SWRL datavaluedProperty atom
	 *
	 * @return the data property predicate for this this SWRL datavaluedProperty atom
	 */
    public OWLDataProperty getPropertyPredicate();

    /**
     * Sets the data property predicate for this SWRL datavaluedProperty atom
     * @param p  the data property predicate for this SWRL datavaluedProperty atom
     */
    public void setPropertyPredicate(OWLDataProperty p);

    /**
     * Retuns the subject of the property of this SWRL datavaluedProperty atom.
     * It is of one of the explicit or inferred types of the domain of the OWL data property
     * @return the subject of the property of this SWRL datavaluedProperty atom.
     */
    public SWRLIndividualObject getArgument1();
    /**
     * Sets the subject of the property of this SWRL datavaluedProperty atom.
     * It must be of one of the explicit or inferred types of the domain of the OWL data property
     *
     * @param obj the subject of the property of this SWRL datavaluedProperty atom.
     */
    public void setArgument1(SWRLIndividualObject obj);

    /**
     * Returns the object of the property of this SWRL datavaluedProperty atom.
     * It is of one of the explicit or inferred types of the range of the OWL data property
     * @return the object of the property of this SWRL datavaluedProperty atom.
     */
    public SWRLDataObject getArgument2();

    /**
     * Sets the object of the property of this SWRL datavaluedProperty atom.
     * It must be of one of the explicit or inferred types of the range of the OWL data property
     *
     * @param obj the object of the property of this SWRL datavaluedProperty atom.
     */
    public void setArgument2(SWRLDataObject obj);

}
