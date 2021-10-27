
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * A class to provide a simple GUI to add and remove planets and moons in a solar system.
 * @see SolarSystemController
 * @author Joe Finney
 */
public class SolarSystemGUI implements ActionListener
{
	private JFrame window = new JFrame("Solar System GUI");
    private String[] attributes = {"Name", "Orbital Distance", "Orbital Angle", "Size", "Speed", "Colour", "Orbits"};
    private JLabel[] attributeLabels;
    private JTextField[] values = new JTextField[attributes.length];
    private JPanel dataPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JPanel panel = new JPanel();
    private JButton addButton = new JButton("Add");
	private JButton removeButton = new JButton("Remove");
    private SolarSystemController controller;

    /**
     * Creates a new SolarSystemGUI
     * 
     * A simple graphical user interface is displayed, which prompts the user to
     * enter data relating to new planets and moons. These solar object are identified
     * by name, and can be dynamically added and removed by the user.
     */
	public SolarSystemGUI()
	{
		window.setContentPane(panel);
		window.setSize(300,300);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set sane minimum sizing, allow resizing.
        window.setMinimumSize(window.getSize());
        window.setResizable(true);

        //Configure the data panel with a grid layout (and guttering).
        GridLayout dataPanelLayout = new GridLayout(attributes.length,2);
        dataPanelLayout.setHgap(5);
        dataPanel.setBorder(new EmptyBorder(0,5,0,0));
        dataPanel.setLayout(dataPanelLayout);

        //Add the attribute labels to the grid.
        attributeLabels = new JLabel[attributes.length];
        for (int i=0; i<attributes.length; i++)
        {
            JLabel label = new JLabel(attributes[i]);
            attributeLabels[i] = label;
            dataPanel.add(label);
            values[i] = new JTextField("");
            dataPanel.add(values[i]);
        }

        buttonPanel.setLayout(new GridLayout(1,2));
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);

        panel.setLayout(new BorderLayout());
		panel.add("Center", dataPanel);
		panel.add("South", buttonPanel);

		addButton.addActionListener(this);
		removeButton.addActionListener(this);

		window.setVisible(true);
	}

    /**
     * Register a given SolarSystemController with this GUI.
     * Once registered, this controller will then receive add and remove method calls
     * when the user interacts with the user interface.
     * 
     * @param controller an object satisfying the SolarSystemController interface which
     * will subsequently receive add/remove method invocations.
     */
    public void addSolarSystemController(SolarSystemController controller)
    {
        this.controller = controller;
    }

    /***
     * Reports that invalid data has been entered to the user with a popup, along with a reason.
     * @param reason The reason the data is invalid.
     */
    private void reportInvalidData(String reason)
    {
        JOptionPane.showMessageDialog(null, "Invalid data entered: " + reason, "Solar System: Invalid Information!", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Event handler. Called by the Swing package when a button related to this GUI is clicked.
     */
	public void actionPerformed(ActionEvent e)
	{
        boolean valid = true;

        //Clear colouring from previous actions.
        clearValidationStyling();

		if (e.getSource() == addButton)
		{
            // Validate the data fields...
            try
            {
                String name = validateString(0);
                double distance = validateDouble(1);
                double angle = validateDouble(2);
                double size = validateDouble(3);
                double speed = validateDouble(4);
                String colour = validateString(5);
                String orbits = values[6].getText();

                //Ensure the size is valid.
                if (size <= 0.0)
                {
                    this.reportInvalidData("Invalid size (below zero).");
                    return;
                }

                //If we don't have a controller, don't need to add anything.
                if (controller != null)
                {
                    if (orbits != null && !orbits.equals(""))
                        controller.add(name, distance, angle, size, speed, colour, orbits);
                    else
                        controller.add(name, distance, angle, size, speed, colour);
                }
                else
                {
                    System.out.println(" --- No SolarSystemController Registered ---");
                }
    
            } catch (Exception ex)
            {
                this.reportInvalidData("Could not parse fields,  '" + ex.getMessage() + "' (are some fields invalid or empty?)");
                return;
            }
        }

        if (e.getSource() == removeButton)
		{
		    //Validate the name.
            String name = null;
            try {
                name = validateString(0);
            } catch (Exception exception) {
                this.reportInvalidData("Name is null or empty, cannot remove.");
            }

            //Remove from controller (if we have one).
            if (controller != null)
            {
                controller.remove(name);
            }
        }

	}

    /**
     * Clears all colour styling caused by validation functions (validateDouble & validateString).
     */
	private void clearValidationStyling() {
        for (JLabel label : attributeLabels)
            label.setForeground(Color.BLACK);
        for (JTextField field : values)
            field.setForeground(Color.BLACK);
    }

    /**
     * Verifies that a field has a valid double, and returns it if so.
     * If not, throws an exception on a failed parse.
     * @param index The index of the field to validate.
     * @return The double value from the text field.
     */
	private double validateDouble(int index) throws Exception {

	    //Get the field.
        String fieldName = attributes[index];
        JTextField field = values[index];

        //Get the double out from the field (if valid).
	    double out;
        try {
            out = Double.parseDouble(field.getText());
            field.setForeground(Color.BLACK);
        }
        catch(Exception e) {
            attributeLabels[index].setForeground(Color.RED);
            field.setForeground(Color.RED);
            throw new Exception("Invalid double in field " + fieldName + ".");
        }

        //Return the double.
        return out;
    }

    /**
     * Verifies that a field has a valid string (non-null, non-empty) and returns it if so.
     * If not, throws an exception on a failed validation.
     * @param index The index of the text field to validate.
     * @return The string value from the text field.
     * @throws Exception
     */
    private String validateString(int index) throws Exception {
        //Get the field.
        String fieldName = attributes[index];
        JTextField field = values[index];

        //Is the string null or empty?
        String fieldText = field.getText();
        if (fieldText == null || fieldText.equals("")) {
            attributeLabels[index].setForeground(Color.RED);
            field.setForeground(Color.RED);
            throw new Exception("Empty or null string in field " + fieldName + ".");
        }

        field.setForeground(Color.BLACK);
        return fieldText;
    }
}
