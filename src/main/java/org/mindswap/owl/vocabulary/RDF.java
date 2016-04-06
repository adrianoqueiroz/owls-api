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
 * Created on Dec 28, 2003
 *
 */
package org.mindswap.owl.vocabulary;

import java.net.URI;

import org.mindswap.owl.OWLValue;
import org.mindswap.owl.list.ListVocabulary;

/**
 *
 * @author unascribed
 * @see <a href="http://www.w3.org/TR/rdf-primer/">RDF Primer</a>
 * @version $Rev: 2269 $; $Author: thorsten $; $Date: 2009-08-19 18:21:09 +0300 (Wed, 19 Aug 2009) $
 */
public class RDF {
	public final static String ns = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";

	public final static URI getURI() { return URI.create(ns); }

	public final static URI Property   = URI.create(ns + "Property");
	public final static URI Resource   = URI.create(ns + "Resource");

	public final static URI type       = URI.create(ns + "type");

	public final static URI XMLLiteral = URI.create(ns + "XMLLiteral");

	public final static URI List       = URI.create(ns + "List");
	public final static URI first      = URI.create(ns + "first");
	public final static URI rest       = URI.create(ns + "rest");
	public final static URI nil        = URI.create(ns + "nil");

	public final static ListVocabulary<OWLValue> ListVocabulary =
		new ListVocabulary<OWLValue>(RDF.List, RDF.first, RDF.rest, RDF.nil, OWLValue.class);

}
