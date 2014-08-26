package org.swrlapi.core.visitors;

import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.swrlapi.core.SWRLAPIRule;

/**
 * SWRLAPI OWL axiom visitor customized to deal with {@link org.swrlapi.core.SWRLAPIRule}s instead of
 * OWLAPI-based {@link org.swrlapi.core.SWRLAPIRule}s.
 *
 * @see org.swrlapi.core.SWRLAPIRule
 * @see org.semanticweb.owlapi.model.OWLAxiom
 * @see org.semanticweb.owlapi.model.OWLAxiomVisitor
 */
public interface SWRLAPIOWLAxiomVisitor
{
	void visit(SWRLAPIRule swrlapiRule);

	void visit(OWLDeclarationAxiom axiom);

	void visit(OWLSubClassOfAxiom owlSubClassOfAxiom);

	void visit(OWLNegativeObjectPropertyAssertionAxiom owlNegativeObjectPropertyAssertionAxiom);

	void visit(OWLAsymmetricObjectPropertyAxiom owlAsymmetricObjectPropertyAxiom);

	void visit(OWLReflexiveObjectPropertyAxiom owlReflexiveObjectPropertyAxiom);

	void visit(OWLDisjointClassesAxiom owlDisjointClassesAxiom);

	void visit(OWLDataPropertyDomainAxiom owlDataPropertyDomainAxiom);

	void visit(OWLObjectPropertyDomainAxiom owlObjectPropertyDomainAxiom);

	void visit(OWLEquivalentObjectPropertiesAxiom owlEquivalentObjectPropertiesAxiom);

	void visit(OWLNegativeDataPropertyAssertionAxiom owlNegativeDataPropertyAssertionAxiom);

	void visit(OWLDifferentIndividualsAxiom owlDifferentIndividualsAxiom);

	void visit(OWLDisjointDataPropertiesAxiom owlDisjointDataPropertiesAxiom);

	void visit(OWLDisjointObjectPropertiesAxiom owlDisjointObjectPropertiesAxiom);

	void visit(OWLObjectPropertyRangeAxiom owlObjectPropertyRangeAxiom);

	void visit(OWLObjectPropertyAssertionAxiom owlObjectPropertyAssertionAxiom);

	void visit(OWLFunctionalObjectPropertyAxiom owlFunctionalObjectPropertyAxiom);

	void visit(OWLSubObjectPropertyOfAxiom owlSubObjectPropertyOfAxiom);

	void visit(OWLDisjointUnionAxiom owlDisjointUnionAxiom);

	void visit(OWLSymmetricObjectPropertyAxiom owlSymmetricObjectPropertyAxiom);

	void visit(OWLDataPropertyRangeAxiom owlDataPropertyRangeAxiom);

	void visit(OWLFunctionalDataPropertyAxiom owlFunctionalDataPropertyAxiom);

	void visit(OWLEquivalentDataPropertiesAxiom owlEquivalentDataPropertiesAxiom);

	void visit(OWLClassAssertionAxiom owlClassAssertionAxiom);

	void visit(OWLEquivalentClassesAxiom owlEquivalentClassesAxiom);

	void visit(OWLDataPropertyAssertionAxiom owlDataPropertyAssertionAxiom);

	void visit(OWLTransitiveObjectPropertyAxiom owlTransitiveObjectPropertyAxiom);

	void visit(OWLIrreflexiveObjectPropertyAxiom owlIrreflexiveObjectPropertyAxiom);

	void visit(OWLSubDataPropertyOfAxiom owlSubDataPropertyOfAxiom);

	void visit(OWLInverseFunctionalObjectPropertyAxiom owlInverseFunctionalObjectPropertyAxiom);

	void visit(OWLSameIndividualAxiom owlSameIndividualAxiom);

	void visit(OWLSubPropertyChainOfAxiom owlSubPropertyChainOfAxiom);

	void visit(OWLInverseObjectPropertiesAxiom owlInverseObjectPropertiesAxiom);

	void visit(OWLHasKeyAxiom owlHasKeyAxiom);

	void visit(OWLDatatypeDefinitionAxiom owlDatatypeDefinitionAxiom);
}
