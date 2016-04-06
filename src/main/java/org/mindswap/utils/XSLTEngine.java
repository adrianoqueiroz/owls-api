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
package org.mindswap.utils;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.mindswap.exceptions.TransformationException;
import org.mindswap.owl.OWLValue;
import org.mindswap.owls.process.variable.ProcessVar;
import org.mindswap.query.ValueMap;
import org.w3c.dom.Node;

/**
 *
 * @author unascribed
 * @version $Rev: 2542 $; $Author: thorsten $; $Date: 2010-09-16 18:03:42 +0200 (Thu, 16 Sep 2010) $
 */
public class XSLTEngine
{
	private final static String header = "<?xml";
	private static TransformerFactory tFactory;

	/**
	 * @param input
	 * @param xslt
	 * @return
	 */
	public static String transform(final Node input, final String xslt) throws TransformationException
	{
		return transformToString(new DOMSource(input), xslt, ValueMap.<ProcessVar, OWLValue>emptyValueMap());
	}

	/**
	 * @param input
	 * @param xslt
	 * @return
	 */
	public static String transform(final String input, final String xslt) throws TransformationException
	{
		return transform(input, xslt, ValueMap.<ProcessVar, OWLValue>emptyValueMap());
	}

	/**
	 * @param input
	 * @param xslt
	 * @param parameters
	 * @return
	 */
	public static <V extends ProcessVar, W extends OWLValue> String transform(final String input,
		final String xslt, final ValueMap<V, W> parameters) throws TransformationException
	{
		return transformToString(new StreamSource(new StringReader(input)), xslt, parameters);
	}

	/**
	 * @param input
	 * @param xslt
	 * @param parameters
	 * @return
	 */
	public static <V extends ProcessVar, W extends OWLValue> Node transformToNode(final String input,
		final String xslt, final ValueMap<V, W> parameters) throws TransformationException
	{
		if (xslt == null) throw new TransformationException("Required XSLT stylesheet missing");

		final DOMResult result = new DOMResult();
		transform(new StreamSource(new StringReader(input)), result, xslt.trim(), parameters);
		return result.getNode();
	}

	private static <V extends ProcessVar, W extends OWLValue> String transformToString(
		final Source input, final String xslt, final ValueMap<V, W> parameters)
		throws TransformationException
	{
		if (xslt == null) throw new TransformationException("Required XSLT stylesheet missing");

		final StringWriter result = new StringWriter();
		transform(input, new StreamResult(result), xslt.trim(), parameters);
		String output = result.toString().trim();

		if (output.startsWith(header))
		{
			final int split = output.indexOf('>') + 1;
			output = output.substring(split);
		}

		return output;
	}

	private static <V extends ProcessVar, W extends OWLValue> void transform(
		final Source source, final Result result, final String xslt, final ValueMap<V, W> parameters)
		throws TransformationException
	{
		try
		{
			final Transformer transformer = getTransformerFactory().newTransformer(
				new StreamSource(new StringReader(xslt)));
			for (final V param : parameters.getVariables())
			{
				final String value = parameters.getStringValue(param);
				transformer.setParameter(param.getLocalName(), value);
				transformer.setParameter(param.getURI().toString(), value);
			}

			transformer.transform(source, result);
		}
		catch(final Exception e)
		{
			throw new TransformationException(e);
		}
	}

	private static final TransformerFactory getTransformerFactory()
	{
		if (tFactory == null)
		{
			tFactory = TransformerFactory.newInstance();
		}
		return tFactory;
	}
}
