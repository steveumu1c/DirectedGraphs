package directedGraph;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/*A program that behaves like the Java command line
compiler. Whenever we request that the Java compiler recompile a particular class, it not only
recompiles that class but every other class that depends upon it, directly or indirectly, and in a
particular order. To make the determination about which classes need recompilation, the Java
compiler maintains a directed graph of class dependencies. Any relationship in a UML class diagram
of a Java program such as inheritance relationships, composition relationships and aggregations
relationships indicate a class dependency.*/
public class DriverGUI extends JFrame{
    private JTextField fileNameTxtFld, classNameTxtFld;
    private JTextPane outputTxtPne;
    private DirectedGraphMethods<String> graph;

    public DriverGUI() {
        super("Class Dependency Graph");

        //set look and feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        //setup panels

        //setup input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setMaximumSize(new Dimension(610,110));
        inputPanel.setPreferredSize(new Dimension(600,110));
        inputPanel.setBorder(BorderFactory.createTitledBorder(" "));
        inputPanel.setLayout(null);
        //setup labels
        JLabel lbl1 = new JLabel("Input file name:");
        inputPanel.add(lbl1);
        lbl1.setBounds(50,25,133,20);
        JLabel lbl2  = new JLabel("Class to recompile:");
        inputPanel.add(lbl2);
        lbl2.setBounds(50,68,134,20);
        //setup textfields
        inputPanel.add(fileNameTxtFld = new JTextField("",20));
        fileNameTxtFld.setBounds(205,30,190,20);
        inputPanel.add(classNameTxtFld = new JTextField("",20));
        classNameTxtFld.setBounds(205,70,190,20);
        //setup buttons
        JButton buildGraphBtn = new JButton("Build Directed Graph");
        inputPanel.add(buildGraphBtn);
        buildGraphBtn.setBounds(415,25,170,28);
        buildGraphBtn.addActionListener(this::buildGraphBtnClicked);
        JButton topoOrderBtn = new JButton("Topological Order");
        inputPanel.add(topoOrderBtn);
        topoOrderBtn.setBounds(423,68,155,28);
        topoOrderBtn.addActionListener(this::topologicalOrderBtnClicked);

        //setup output panel
        JPanel outputPanel = new JPanel();
        outputPanel.setBackground(Color.WHITE);
        outputPanel.setBorder(BorderFactory.createTitledBorder("Recompilation Order"));
        outputPanel.setLayout(new BorderLayout());
        //Setup textpane Style (centers text)
        StyleContext context = new StyleContext();
        StyledDocument document = new DefaultStyledDocument(context);
        Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
        StyleConstants.setAlignment(style, StyleConstants.ALIGN_CENTER);
        outputPanel.add(outputTxtPne = new JTextPane(document));

        //setup layout and add panels
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        add(inputPanel);
        add(outputPanel);

        //setup frame attr.
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(605,325);
        setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    ///Reads file from project directory in eclipse or D:\eclipseworkspace\DirectedGraphProject4 on author's computer
//    Pressing the Build Directed Graph button should cause the specified input file that contains the class
//    dependency information to be read in and the directed graph represented by those dependencies to be
//    built. The input file associated with the above example is shown below:
//    ClassA ClassC ClassE
//    ClassB ClassD ClassG
//    ClassE ClassB ClassF ClassH
//    ClassI ClassC
    private void buildGraphBtnClicked(ActionEvent event) {
        try {
            graph = DirectedGraphMethods.initializeGraph(fileNameTxtFld.getText());
            //System.out.println(graph); //For Debugging
            JOptionPane.showMessageDialog(this, "Directed Graph Built Successfully");

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "File Did Not Open");
        }
    }
//    Once the graph has been built, the name of a class to be recompiled can be specified and the
//    Topological Order button can be pressed. Provided a valid class name has been supplied, the list of
//    classes that need to be recompiled should be listed in the order they are to be recompiled in the text
//    area at the bottom of the window. An invalid class name should generate an appropriate error message
    private void topologicalOrderBtnClicked(ActionEvent event) {
        try {
            outputTxtPne.setText(graph.topologicalOrder(classNameTxtFld.getText()));
        } catch (CycleDiscoveredException | ClassNameException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(this, "Please Build Directed Graph");

        }
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> { DriverGUI view = new DriverGUI(); });
    }

}