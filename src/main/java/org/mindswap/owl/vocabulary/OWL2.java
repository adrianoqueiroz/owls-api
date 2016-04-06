/*
 * Created on 05.08.2008
 *
 * (c) 2008 Thorsten M�ller - University of Basel Switzerland
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
package org.mindswap.owl.vocabulary;

import java.net.URI;

import org.mindswap.owl.OWLClass;
import org.mindswap.owl.OWLOntology;
import org.mindswap.utils.URIUtils;

/**
 * Specification of additional syntactical elements that were introduced
 * by OWL version 2.
 *
 * @author unascribed
 * @see <a href="http://www.w3.org/TR/owl2-primer/">OWL 2</a>
 * @version $Rev: 2395 $; $Author: thorsten $; $Date: 2010-01-15 12:28:52 +0200 (Fri, 15 Jan 2010) $
 */
public class OWL2 extends OWL
{
	public final static OWLClass AllDisjointClasses;
	public final static OWLClass AllDisjointProperties;

	public final static OWLClass ReflexiveProperty;
   public final static OWLClass IrreflexiveProperty;
   public final static OWLClass AsymmetricProperty;

   public final static OWLClass NegativePropertyAssertion;

   public final static OWLClass NamedIndividual;

   public final static URI disjointUnionOf 				= URI.create(ns + "disjointUnionOf");
   public final static URI propertyDisjointWith 	   = URI.create(ns + "propertyDisjointWith");
   public final static URI onClass 							= URI.create(ns + "onClass");
   public final static URI onDataRange 					= URI.create(ns + "onDataRange");
   public final static URI onDatatype	 					= URI.create(ns + "onDatatype");
   public final static URI datatypeComplementOf 		= URI.create(ns + "datatypeComplementOf");
   public final static URI withRestrictions				= URI.create(ns + "withRestrictions");
   public final static URI length 							= URI.create(ns + "length");
   public final static URI maxLength 						= URI.create(ns + "maxLength");
   public final static URI minLength 						= URI.create(ns + "minLength");
   public final static URI totalDigits 					= URI.create(ns + "totalDigits");
   public final static URI fractionDigits 				= URI.create(ns + "fractionDigits");
   public final static URI minInclusive 					= URI.create(ns + "minInclusive");
   public final static URI minExclusive 					= URI.create(ns + "minExclusive");
   public final static URI maxInclusive 					= URI.create(ns + "maxInclusive");
   public final static URI maxExclusive 					= URI.create(ns + "maxExclusive");
   public final static URI pattern 							= URI.create(ns + "pattern");
   public final static URI propertyChainAxiom			= URI.create(ns + "propertyChainAxiom");
   public final static URI members							= URI.create(ns + "members");
   public final static URI hasKey							= URI.create(ns + "hasKey");
   public final static URI hasSelf							= URI.create(ns + "hasSelf");
   public final static URI sourceIndividual				= URI.create(ns + "sourceIndividual");
   public final static URI assertionProperty				= URI.create(ns + "assertionProperty");
   public final static URI targetIndividual				= URI.create(ns + "targetIndividual");
   public final static URI targetValue                = URI.create(ns + "targetValue");
   public final static URI topDataProperty				= URI.create(ns + "topDataProperty");
   public final static URI bottomDataProperty			= URI.create(ns + "bottomDataProperty");
   public final static URI topObjectProperty				= URI.create(ns + "topObjectProperty");
   public final static URI bottomObjectProperty			= URI.create(ns + "bottomObjectProperty");
   public final static URI qualifiedCardinality			= URI.create(ns + "qualifiedCardinality");
   public final static URI maxQualifiedCardinality 	= URI.create(ns + "maxQualifiedCardinality");
   public final static URI minQualifiedCardinality		= URI.create(ns + "minQualifiedCardinality");


   static
   {
   	final OWLOntology ont = OWL.kb.createOntology(NS);

   	AllDisjointClasses = ont.createClass(URIUtils.createURI(ns + "AllDisjointClasses"));
   	AllDisjointProperties = ont.createClass(URIUtils.createURI(ns + "AllDisjointProperties"));
   	ReflexiveProperty = ont.createClass(URIUtils.createURI(ns + "ReflexiveProperty"));
      IrreflexiveProperty = ont.createClass(URIUtils.createURI(ns + "IrreflexiveProperty"));
      AsymmetricProperty = ont.createClass(URIUtils.createURI(ns + "AsymmetricProperty"));
      NegativePropertyAssertion = ont.createClass(URIUtils.createURI(ns + "NegativePropertyAssertion"));

      NamedIndividual = ont.createClass(URIUtils.createURI(ns + "NamedIndividual"));
   }
}
