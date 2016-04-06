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

package examples;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ProgressMonitor;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.xml.namespace.QName;

import org.mindswap.owl.OWLFactory;
import org.mindswap.owl.OWLKnowledgeBase;
import org.mindswap.owl.OWLOntology;
import org.mindswap.owl.vocabulary.OWL;
import org.mindswap.owl.vocabulary.XSD;
import org.mindswap.owls.vocabulary.OWLS;
import org.mindswap.utils.QNameProvider;
import org.mindswap.utils.URIUtils;
import org.mindswap.wsdl.WSDLConsts;
import org.mindswap.wsdl.WSDLOperation;
import org.mindswap.wsdl.WSDLParameter;
import org.mindswap.wsdl.WSDLService;
import org.mindswap.wsdl.WSDLTranslator;

/**
 * A simple to GUI to create OWL-S files from WSDL descriptions.
 *
 * @author unascribed
 * @version $Rev: 2350 $; $Author: thorsten $; $Date: 2009-11-18 17:44:31 +0200 (Wed, 18 Nov 2009) $
 */
public class WSDL2OWLS extends JPanel implements ActionListener
{
	private static final long serialVersionUID = -1485655830498597826L;

	final String[] nsColumnNames = {"Abbr", "URI"};
	final String[] columnNames = {"WSDL Parameter", "WSDL Type", "OWL-S Name", "OWL Type", "XSLT"};
	final String[][] emptyRow = new String[0][5];
	final String[] defaultFiles = {
		"http://webservices.amazon.com/AWSECommerceService/AWSECommerceService.wsdl",
		"http://api.google.com/GoogleSearch.wsdl",
		"http://lyricwiki.org/server.php?wsdl",
		"http://api.urbandictionary.com/soap?wsdl",
		"http://cheeso.members.winisp.net/books/books.asmx?WSDL",
		"http://www.mindswap.org/axis/services/TranslatorService?wsdl",
		"http://www.mindswap.org/axis/services/DictionaryService?wsdl",
		"http://www.webservicex.net/uszip.asmx?WSDL",};

//	private final List<File> savedServices = new ArrayList<File>();

	final JComboBox urls = new JComboBox(defaultFiles);
	final JList opList = new JList();
	final JTable inputTable = new JTable(emptyRow, columnNames);
	final JTable outputTable = new JTable(emptyRow, columnNames);
	final JTextField nameSpaceField = new JTextField();
	final JTextField serviceNameField = new JTextField();
	final JTextArea textDescription = new JTextArea(10, 20);
	final JTable nsTable = new JTable(0, 2);
	JButton generateButton, addNSButton, removeNSButton;

	JDialog advanced;
	JRadioButton prefixButton, manualButton;
	final JTextField prefixText = new JTextField();
	final JTextField serviceText = new JTextField();
	final JTextField profileText = new JTextField();
	final JTextField processText = new JTextField();
	final JTextField groundingText = new JTextField();

	final QNameProvider qnames = new QNameProvider();

	public WSDL2OWLS()
	{
		JPanel contentPane = new JPanel();
		JPanel addressPanel = new JPanel();
		JPanel middlePanel = new JPanel();
		JPanel operationsPanel = new JPanel();
		JPanel detailsPanel = new JPanel();
		JPanel buttonPanel = new JPanel();

		setLayout(new GridLayout(1, 1));
		add(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		contentPane.add(addressPanel);
		contentPane.add(Box.createVerticalStrut(2));
		contentPane.add(middlePanel);
		contentPane.add(Box.createVerticalStrut(2));
		contentPane.add(buttonPanel);

		JButton browseButton = new JButton("Browse Local...");
		browseButton.setActionCommand("browse");
		browseButton.addActionListener(this);

		addressPanel.setLayout(new BoxLayout(addressPanel, BoxLayout.X_AXIS));
		addressPanel.add(new JLabel("Enter URL: "));
		addressPanel.add(Box.createHorizontalStrut(2));
		addressPanel.add(urls);
		urls.setEditable(true);
		urls.setSelectedItem("");
		addressPanel.add(Box.createHorizontalStrut(2));
		addressPanel.add(browseButton);
		addressPanel.add(Box.createHorizontalStrut(2));

		urls.setActionCommand("load");
		urls.addActionListener(this);

		middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.X_AXIS));
		middlePanel.add(operationsPanel);
		middlePanel.add(detailsPanel);

		operationsPanel.setBorder(BorderFactory.createTitledBorder("Operations"));
		operationsPanel.setLayout(new BoxLayout(operationsPanel, BoxLayout.X_AXIS));
		operationsPanel.add(new JScrollPane(opList));
		opList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		opList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(final ListSelectionEvent e)
			{
				if (e.getValueIsAdjusting()) return;
				doSelect();
			}
		});

		JPanel servicePanel = new JPanel();
		JPanel inputsPanel = new JPanel();
		JPanel outputsPanel = new JPanel();
		JPanel nsPanel = new JPanel();
		JPanel nsButtons = new JPanel();

		JScrollPane textDescriptionPane = new JScrollPane(textDescription);
		textDescriptionPane.setPreferredSize(new Dimension(textDescriptionPane.getPreferredSize().width, 50));
		textDescriptionPane.setMinimumSize(new Dimension(textDescriptionPane.getPreferredSize().width, 50));

		JButton advancedButton = new JButton("Advanced...");
		advancedButton.setActionCommand("advanced");
		advancedButton.addActionListener(this);

		servicePanel = createTableLayout(
			new JComponent[] {new JLabel("Service Name"), new JLabel("Text description"), new JLabel("Logical URI")},
			new JComponent[] {serviceNameField, textDescriptionPane, nameSpaceField});
		servicePanel.setBorder(BorderFactory.createTitledBorder("Service information"));

		textDescription.setLineWrap(true);
		textDescription.setWrapStyleWord(true);

		nsTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		nsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(final ListSelectionEvent e)
			{
				if (e.getValueIsAdjusting()) return;

				removeNSButton.setEnabled(true);
			}
		});

		addNSButton = new JButton("Add");
		removeNSButton = new JButton("Remove");

		addNSButton.setActionCommand("addNS");
		addNSButton.addActionListener(this);

		removeNSButton.setActionCommand("removeNS");
		removeNSButton.addActionListener(this);

		nsButtons.setLayout(new BoxLayout(nsButtons, BoxLayout.X_AXIS));
		nsButtons.add(Box.createHorizontalGlue());
		nsButtons.add(addNSButton);
		nsButtons.add(Box.createHorizontalStrut(5));
		nsButtons.add(removeNSButton);

		nsPanel.setLayout(new BoxLayout(nsPanel, BoxLayout.Y_AXIS));
		nsPanel.setBorder(BorderFactory.createTitledBorder("Namespaces"));
		nsPanel.add(new JScrollPane(nsTable));
		nsPanel.add(Box.createVerticalStrut(2));
		nsPanel.add(nsButtons);

		inputsPanel.setLayout(new BoxLayout(inputsPanel, BoxLayout.Y_AXIS));
		inputsPanel.setBorder(BorderFactory.createTitledBorder("Inputs"));
		inputsPanel.add(new JScrollPane(inputTable));

		outputsPanel.setLayout(new BoxLayout(outputsPanel, BoxLayout.Y_AXIS));
		outputsPanel.setBorder(BorderFactory.createTitledBorder("Outputs"));
		outputsPanel.add(new JScrollPane(outputTable));

		generateButton = new JButton("Generate OWL-S");
		generateButton.setEnabled(false);
		generateButton.setActionCommand("generate");
		generateButton.addActionListener(this);

		JButton closeButton = new JButton("Close");
		closeButton.setActionCommand("close");
		closeButton.addActionListener(this);

		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(generateButton);
		buttonPanel.add(Box.createHorizontalStrut(10));
		buttonPanel.add(closeButton);

		detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
		detailsPanel.add(servicePanel);
		detailsPanel.add(inputsPanel);
		detailsPanel.add(outputsPanel);
		detailsPanel.add(nsPanel);

		qnames.setMapping("soapEnc", WSDLConsts.soapEnc + "#");
		updateNS();
	}

	public void actionPerformed(final ActionEvent e)
	{
		if (e.getActionCommand().equals("browse")) doBrowse();
		else if (e.getActionCommand().equals("load")) doLoad();
		else if (e.getActionCommand().equals("advanced")) showAdvanced();
		else if (e.getActionCommand().equals("addNS")) addNS();
		else if (e.getActionCommand().equals("removeNS")) removeNS();
		else if (e.getActionCommand().equals("generate")) doGenerate();
		else if (e.getActionCommand().equals("close"))
		{
			Window window = SwingUtilities.getWindowAncestor(this);
			if (window != null) window.dispose();
			else System.exit(0);
		}
		else if (e.getActionCommand().equals("load")) doLoad();
	}

	JLabel createLabel(final String text)
	{
		JLabel label = new JLabel(text);
		label.setAlignmentX(Component.LEFT_ALIGNMENT);
		label.setVerticalAlignment(SwingConstants.TOP);

		int labelWidth = 100;
		label.setPreferredSize(new Dimension(labelWidth, label.getPreferredSize().height));
		label.setMaximumSize(new Dimension(labelWidth, label.getMaximumSize().height));
		label.setMinimumSize(new Dimension(labelWidth, label.getMinimumSize().height));

		return label;
	}

	public void addNS()
	{
		JDialog info = new JDialog((JFrame) null, "Add namespace", true);

		JTextField t1 = new JTextField(50);
		JTextField t2 = new JTextField(5);
		JLabel[] labels = {new JLabel("Enter URL: "), new JLabel("Abbreviation:")};
		JComponent[] textFields = {t1, t2};
		JButton ok = new JButton("Ok");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e)
			{
				JComponent c = (JComponent) e.getSource();
				Window w = (Window) c.getTopLevelAncestor();
				w.dispose();
			}
		});
		info.getContentPane().setLayout(new BoxLayout(info.getContentPane(), BoxLayout.Y_AXIS));
		info.getContentPane().add(createTableLayout(labels, textFields));
		info.getContentPane().add(ok);
		ok.setAlignmentX(Component.CENTER_ALIGNMENT);
		info.pack();
		info.setResizable(false);
		centerFrame(info);
		// info.show();
		info.setVisible(true);

		System.out.println(t1.getText() + " " + t2.getText());

		String uri = t1.getText();
		String prefix = t2.getText();

		qnames.setMapping(prefix, uri);

		updateNS();
	}

	public void removeNS()
	{
		int row = nsTable.getSelectedRow();

		if (row == -1)
		{
			JOptionPane.showMessageDialog(null, "Please first select an entry, then click remove!", "Error",
				JOptionPane.ERROR_MESSAGE);
			return;
		}

		String prefix = (String) nsTable.getModel().getValueAt(row, 0);

		qnames.removePrefix(prefix);

		updateNS();
	}

	private JPanel createTableLayout(final JComponent[] labels, final JComponent[] textFields)
	{
		JPanel textControlsPane = new JPanel();
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		textControlsPane.setLayout(gridbag);

		c.anchor = GridBagConstraints.WEST;
		int numLabels = labels.length;

		for (int i = 0; i < numLabels; i++)
		{
			c.gridwidth = GridBagConstraints.RELATIVE; // next-to-last
			c.fill = GridBagConstraints.NONE; // reset to default
			c.weightx = 0.0; // reset to default
			c.insets = new Insets(2, 2, 2, 2);
			gridbag.setConstraints(labels[i], c);
			textControlsPane.add(labels[i]);

			c.gridwidth = GridBagConstraints.REMAINDER; // end row
			if (i == numLabels - 1) c.gridheight = GridBagConstraints.REMAINDER; // end
																										// row
			c.fill = GridBagConstraints.BOTH;
			c.weightx = 1.0;
			c.weighty = 1.0;
			c.insets = new Insets(2, 2, 2, 2);
			gridbag.setConstraints(textFields[i], c);
			textControlsPane.add(textFields[i]);
		}

		return textControlsPane;
	}

	void doLoad()
	{
		final String url = urls.getSelectedItem().toString().replaceAll(" ", "%20");
		try
		{
			WSDLService s = WSDLService.createService(url);
			List<WSDLOperation> ops = s.getOperations();
			opList.setListData(ops.toArray());
		}
		catch (final Exception e)
		{
			opList.setListData(new Vector<WSDLOperation>());
			SwingUtilities.invokeLater(new Runnable() {
				public void run()
				{
					JOptionPane.showMessageDialog(null, "Cannot load service description from file\n " + url
						+ "\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
				}
			});
		}
	}

	void doBrowse()
	{
		JFileChooser fc = new JFileChooser();

		// In response to a button click:
		int returnVal = fc.showOpenDialog(null);

		if (returnVal != JFileChooser.APPROVE_OPTION) return;

		File file = fc.getSelectedFile();
		if (!file.exists())
		{
			JOptionPane.showMessageDialog(null, file.getAbsolutePath() + "does not exist!", "Error",
				JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			try
			{
				urls.setSelectedItem(file.toURL().toExternalForm());
			}
			catch (MalformedURLException e)
			{
				JOptionPane.showMessageDialog(null, "Not a valid file path " + file.getAbsolutePath() + "\n" + e,
					"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	void doSelect()
	{
		WSDLOperation op = (WSDLOperation) opList.getSelectedValue();

		if (op == null)
		{
			// fileNameField.setText("");
			nameSpaceField.setText("");
			serviceNameField.setText("");
			textDescription.setText("");

			addParams(new Vector<WSDLParameter>(), inputTable);
			addParams(new Vector<WSDLParameter>(), outputTable);

			generateButton.setEnabled(false);
		}
		else
		{
			// fileNameField.setText(op.getName() + ".owl");

			nameSpaceField.setText("http://www.example.org/service.owl");
			serviceNameField.setText(op.getName());
			if (op.getDocumentation() == null)
				textDescription.setText("Auto generated from " + op.getService().getFileURI());
			else textDescription.setText(op.getDocumentation());
			textDescription.setCaretPosition(0);

			addParams(op.getInputs(), inputTable);
			addParams(op.getOutputs(), outputTable);

			generateButton.setEnabled(true);
		}

		updateNS();
	}

	void prepareAdvanced()
	{
		advanced = new JDialog((JFrame) null, "Advanced Settings", true);

		JPanel localNamePanel = new JPanel();

		prefixButton = new JRadioButton("Use a prefix to genearate local names");
		prefixButton.setSelected(true);

		manualButton = new JRadioButton("Manually enter each local name");

		ButtonGroup group = new ButtonGroup();
		group.add(prefixButton);
		group.add(manualButton);

		localNamePanel.setBorder(BorderFactory.createTitledBorder("Local name settings for URI's"));
		localNamePanel.setLayout(new BoxLayout(localNamePanel, BoxLayout.Y_AXIS));
		localNamePanel.add(prefixButton);
		localNamePanel.add(prefixText);
		localNamePanel.add(createTableLayout(new JComponent[] {new JLabel("Prefix")},
			new JComponent[] {prefixText}));
		localNamePanel.add(manualButton);
		localNamePanel.add(createTableLayout(
			new JComponent[] {new JLabel("Service"), new JLabel("Profile"), new JLabel("Process"), new JLabel("Grounding")},
			new JComponent[] {serviceText, profileText, processText, groundingText}));

		JButton okButton = new JButton("Ok");
		JButton cancelButton = new JButton("Cancel");

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(okButton);
		buttonPanel.add(Box.createHorizontalStrut(10));
		buttonPanel.add(cancelButton);
		buttonPanel.add(Box.createHorizontalStrut(5));

		advanced.getContentPane().add(localNamePanel, "Center");
		advanced.getContentPane().add(buttonPanel, "South");

		advanced.pack();
		advanced.setResizable(false);
	}

	void showAdvanced()
	{
		centerFrame(advanced);
		// advanced.show();
		advanced.setVisible(true);
	}

	private void addParams(final Vector<WSDLParameter> params, final JTable table)
	{
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);

		for (WSDLParameter p : params)
		{
			String[] row = new String[columnNames.length];
			QName paramType = (p.getType() == null)? new QName(WSDLConsts.xsdURI, "any") : p.getType();
			String wsdlType = paramType.getNamespaceURI() + "#" + paramType.getLocalPart();

			// By default use owl:Thing as param type
			String type = OWL.Thing.toString();

			if (paramType.getNamespaceURI().equals(WSDLConsts.soapEnc)
				|| (paramType.getNamespaceURI().equals(WSDLConsts.xsdURI) && !paramType.getLocalPart().equals(
					"any"))) type = XSD.ns + paramType.getLocalPart();

			row[0] = URIUtils.getLocalName(p.getName());
			row[1] = qnames.shortForm(wsdlType);
			row[2] = row[0];
			row[3] = qnames.shortForm(type);
			// row[4] = "None";

			model.addRow(row);
		}

		table.setModel(model);
	}

	void doGenerate()
	{
		JFileChooser fileChooser = new JFileChooser();

		String serviceName = serviceNameField.getText().trim();
		String name = serviceName.replaceAll(" ", "_");

		String fileName = fileChooser.getCurrentDirectory().getAbsolutePath() + File.separator + name + ".owl";

		fileChooser.setSelectedFile(new File(fileName));

		int retVal = fileChooser.showSaveDialog(this);
		if (retVal != JFileChooser.APPROVE_OPTION) return;

		File file = fileChooser.getSelectedFile();

		if (file.exists())
		{
			int option = JOptionPane.showConfirmDialog(this, file.getAbsolutePath() + " already exists.\n"
				+ "Do you want to replace it?", "Save File", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
			if (option == JOptionPane.NO_OPTION) return;
		}

		WSDLOperation op = (WSDLOperation) opList.getSelectedValue();
		URI ontURI;
		try
		{
			ontURI = new URI(nameSpaceField.getText().trim());
		}
		catch (URISyntaxException e)
		{
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		final ProgressMonitor progMon = new ProgressMonitor(this, "Generation Progress",
			"Generating OWL-S service from WSDL", 1, 5);

		OWLOntology ont = OWLFactory.createKB().createOntology(ontURI);
		OWLS.addOWLSImports(ont);

		WSDLTranslator t = new WSDLTranslator(ont, op, name);
		t.setServiceName(serviceNameField.getText());
		t.setTextDescription(textDescription.getText());

		Set<String> usedNames = new HashSet<String>();
		if (!addParams(op, t, usedNames, inputTable.getModel(), true)) return;
		if (!addParams(op, t, usedNames, outputTable.getModel(), false)) return;
		progMon.setProgress(2);

		progMon.setNote("Writing OWL-S service to file");
		try
		{
			FileOutputStream fos = new FileOutputStream(file);
			t.writeOWLS(fos);
			fos.close();
			progMon.setProgress(3);
			System.out.println("Saved to " + file.toURI());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			progMon.close();
			JOptionPane.showMessageDialog(this, "Cannot create OWL-S file!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		progMon.setNote("Validating generated OWL-S service");
		OWLSValidator validator = new OWLSValidator();
		boolean valid = false;
		try
		{
			valid = validator.validate(file.toURI().toString());
			progMon.setProgress(4);
			if (valid)
			{
				progMon.setNote("Checking consistency of generated file");
				OWLKnowledgeBase kb = OWLFactory.createKB();
				kb.setReasoner("Pellet");
				kb.read(file.toURI());
				valid = kb.isConsistent();
			}
			progMon.setProgress(5);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			progMon.close();
		}

		if (valid)
		{
			JOptionPane.showMessageDialog(this, "Service " + file.getAbsolutePath() + " was saved succesfully",
				"Success", JOptionPane.INFORMATION_MESSAGE);

//			savedServices.add(file);
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Saved file " + file.getAbsolutePath()
				+ " is not valid", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private boolean addParams(final WSDLOperation op, final WSDLTranslator translator, final Set<String> usedNames,
		final TableModel parameters, final boolean input)
	{
		for (int i = 0; i < parameters.getRowCount(); i++)
		{
			WSDLParameter param;
			if (input) param = op.getInput(i);
			else param = op.getOutput(i);

			String paramName = (String) parameters.getValueAt(i, 2);
			String paramType = (String) parameters.getValueAt(i, 3);
			String xsltTransformation = (String) parameters.getValueAt(i, 4);

			String prefix = paramName;
			for (int count = 1; usedNames.contains(paramName); count++)
				paramName = prefix + count;

			usedNames.add(paramName);

			URI paramTypeURI;
			try
			{
				paramType = qnames.longForm(paramType);
				paramTypeURI = new URI(paramType);
			}
			catch (IllegalArgumentException e)
			{
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			catch (URISyntaxException e)
			{
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}

			if (input) translator.addInput(param, paramName, paramTypeURI, xsltTransformation);
			else translator.addOutput(param, paramName, paramTypeURI, xsltTransformation);
		}
		return true;
	}

	void updateNS()
	{
		DefaultTableModel model = new DefaultTableModel(nsColumnNames, 0);

		for (String prefix : qnames.getPrefixSet())
		{
			String uri = qnames.getURI(prefix);
			model.addRow(new String[] {prefix, uri});
		}

		nsTable.setModel(model);
		nsTable.getColumnModel().getColumn(0).setMaxWidth(75);
	}

	static void centerFrame(final Window frame)
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = frame.getSize();
		screenSize.height = screenSize.height/2;
		screenSize.width = screenSize.width/2;
		size.height = size.height/2;
		size.width = size.width/2;
		int y = screenSize.height - size.height;
		int x = screenSize.width - size.width;

		frame.setLocation(x, y);
	}

	public static void main(final String[] args) throws Exception
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (final Exception e) // CNFE, Inst'E, IAE, ULAFE
		{
			e.printStackTrace();
		}

		JFrame test = new JFrame("WSDL2OWL-S Converter");

		test.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		test.setSize(800, 600);
		centerFrame(test);
		test.getContentPane().add(new WSDL2OWLS());
		// test.show();
		test.setVisible(true);
	}

}
