package br.ufba.dcc.features;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.mindswap.exceptions.ExecutionException;
import org.mindswap.owl.OWLFactory;
import org.mindswap.owl.OWLKnowledgeBase;
import org.mindswap.owl.OWLValue;
import org.mindswap.owls.OWLSFactory;
import org.mindswap.owls.process.execution.DefaultProcessMonitor;
import org.mindswap.owls.process.execution.ProcessExecutionEngine;
import org.mindswap.owls.process.variable.Input;
import org.mindswap.owls.process.variable.Output;
import org.mindswap.owls.service.Service;
import org.mindswap.owls.process.Process;
import org.mindswap.query.ValueMap;

public class RunSemanticService {

	public static void main(String[] args)
			throws URISyntaxException, IOException, ExecutionException, InterruptedException {
		//URI uri = new URI("http://localhost:8888/owl_s/services/restful/owls/celsiusToKelvin.owl");
		 //URI uri = new URI("http://localhost:8888/owl_s/services/restful/owls/kelvinToFarenheith.owl");
		 URI uri = new URI("http://localhost:8888/owl_s/services/restful/owls/mycomposition.owl");

		RunSemanticService service = new RunSemanticService();
		String output = service.runService(uri, "50");

		System.out.println(output);
	}

	public String runService(URI uri, String userInput)
			throws URISyntaxException, IOException, ExecutionException, InterruptedException {
		ProcessExecutionEngine exec = OWLSFactory.createExecutionEngine();
		exec.addMonitor(new DefaultProcessMonitor());
		final OWLKnowledgeBase kb = OWLFactory.createKB();

		// Carregando ontologias que descrevem o serviço
		Service service = kb.readService(uri);
		verifyOwlsService(kb);
		
		Process process = service.getProcess();

		// Definindo entradas
		ValueMap<Input, OWLValue> inputs = new ValueMap<Input, OWLValue>();
		// inputs.setValue(process.getInput("Input_Celsius"),
		// kb.createDataValue(userInput));
		inputs.setValue(process.getInputs().get(0), kb.createDataValue(userInput));

		// Criando conjunto de saídas e executando processo
		ValueMap<Output, OWLValue> outputs = new ValueMap<Output, OWLValue>();
		outputs = exec.execute(process, inputs, kb);

		// Exibindo a saída em String
		final OWLValue outputValue = outputs.getValue(process.getOutput());

		return outputValue.toString();

	}

	public boolean verifyOwlsService(OWLKnowledgeBase kb) {
		Boolean valid = kb.isConsistent();
		System.out.println("\n########################");
		System.out.println("Validating OWL-S service");		
		if (valid) {
			System.out.println("Service is valid");
			System.out.println("########################\n");
			return true;
		} else {
			System.out.println("Service is not valid");
			System.out.println("########################\n");
			return false;
		}		
	}
	
}
