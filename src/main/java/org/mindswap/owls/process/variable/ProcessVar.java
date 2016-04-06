/*
 * Created 17.06.2009
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
package org.mindswap.owls.process.variable;

import java.net.URI;

import org.mindswap.common.Variable;
import org.mindswap.owl.OWLIndividual;
import org.mindswap.owl.OWLType;
import org.mindswap.owl.OWLValue;
import org.mindswap.owls.process.Process;
import org.mindswap.owls.profile.Profile;
import org.mindswap.owls.service.Service;

/**
 * Represents the different kinds of OWL-S process variables, see sub interfaces.
 * <p>
 * Corresponding OWL-S concept: {@link org.mindswap.owls.vocabulary.OWLS.Process#ProcessVar}
 *
 * @author unascribed
 * @version $Rev: 2571 $; $Author: thorsten $; $Date: 2011-02-03 13:01:18 +0200 (Thu, 03 Feb 2011) $
 */
public interface ProcessVar extends OWLIndividual, Variable
{
	/**
	 * @return The type of this process variable.
	 */
	public OWLType getParamType();

	/**
	 * This method is equivalent to invoking <code>setParamType(type.getURI())</code>.
	 *
	 * @param type The type for this process variable.
	 * @throws IllegalArgumentException In case the type is anonymous, i.e.,
	 * 	{@link OWLType#getURI()} returns <code>null</code>.
	 * @see #setParamType(URI)
	 */
	public void setParamType(OWLType type);

	/**
	 * @param type The type for this process variable.
	 * @throws IllegalArgumentException If parameter value is <code>null</code>.
	 */
	public void setParamType(URI type);

	/**
	 * @return The process object where this variable occurs.
	 */
	public Process getProcess();

	/**
	 * @return The service object where this variable indirectly occurs.
	 */
	public Service getService();

	/**
	 * @return The profile object where this variable indirectly occurs.
	 */
	public Profile getProfile();

	/**
	 * @param process The process for this variable.
	 */
	public void setProcess(Process process);

	/**
	 * If this process variable has a constant value associated with it (specified
	 * using the {@link org.mindswap.owls.vocabulary.OWLS.Process#parameterValue
	 * parameterValue} property return that value. There is no restriction as to
	 * the contents of the value except that it is supposed to be a 
	 * {@link org.mindswap.owl.vocabulary.RDF#XMLLiteral XML literal}.
	 * <p>
	 * This method tries to get the constant value (if any) as an OWL individual
	 * if possible, that is, if the literal syntactically represents some OWL
	 * individual. This has to be achieved using some simple heuristic that is
	 * able to analyze and decide whether this is the case, and eventually
	 * transform the literal into an individual.
	 *
	 * @return The constant value associated with this process variable or
	 * 	<code>null</code> if there is no such value. If possible, the constant
	 * 	value is transformed to an OWLIndividual.
	 */
	public OWLValue getConstantValue();

	/**
	 * @param value The constant value for this variable. If it is an
	 * 	{@link OWLIndividual} it will be serialized using the backing
	 * 	serialization syntax (e.g. RDF/XML).
	 */
	public void setConstantValue(OWLValue value);

}
