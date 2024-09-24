/* 
   Name: Sairam Nagula
   Course: CNT 4714 – Fall 2024
   Assignment title: Project 1 – An Event-driven Enterprise Simulation
   Date: Sunday September 8, 2024
*/

package NileDotCom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GUI extends JFrame {
	
	// GUI variables
	private JTextField itemIdField;
	private JTextField quantityField;
	private JTextField detailsField;
	private JTextField subtotalField;
	private JTextArea cartArea;

	private JButton addItemButton;
	private JButton viewCartButton;
	private JButton checkOutButton;
	private JButton emptyCartButton;
	private JButton quitButton;
	private JButton searchButton;

	// Instance Variables
	private int quantityEntered;
	private String description;
	private double unitPrice;
	private String itemId;
	private double finalPrice;
	private double subTotal = 0;
	private int numberOfItems = 0;
	private String cartContents = "";
	private String cartFormat = "";
	private double discount;

	public GUI() {
		// Frame setup properties
		setTitle("Nile.Com - Fall 2024");
		setSize(700, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// -------------- Top panel --------------
		JPanel entryPanel = new JPanel();
		entryPanel.setLayout(new GridBagLayout());
		entryPanel.setBackground(Color.DARK_GRAY);

		//Setting the formatting
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		//Fields from the top panel
		JLabel itemIdLabel = new JLabel("Enter item ID for Item #1:");
		itemIdLabel.setForeground(Color.YELLOW);
		gbc.gridx = 0;
		gbc.gridy = 0;
		entryPanel.add(itemIdLabel, gbc);

		itemIdField = new JTextField(20); // Kept the width of itemIdField as 20
		gbc.gridx = 1;
		gbc.gridy = 0;
		entryPanel.add(itemIdField, gbc);

		JLabel quantityLabel = new JLabel("Enter quantity for Item #1:");
		quantityLabel.setForeground(Color.YELLOW);
		gbc.gridx = 0;
		gbc.gridy = 1;
		entryPanel.add(quantityLabel, gbc);

		quantityField = new JTextField(10); // Kept the width of quantityField as 10
		gbc.gridx = 1;
		gbc.gridy = 1;
		entryPanel.add(quantityField, gbc);

		JLabel detailsLabel = new JLabel("Details for Item #1:");
		detailsLabel.setForeground(Color.ORANGE);
		gbc.gridx = 0;
		gbc.gridy = 2;
		entryPanel.add(detailsLabel, gbc);

		detailsField = new JTextField(35); // Increased the width of detailsField to 35
		detailsField.setEditable(false);
		gbc.gridx = 1;
		gbc.gridy = 2;
		entryPanel.add(detailsField, gbc);

		JLabel subtotalLabel = new JLabel("Current Subtotal for 0 item(s):");
		subtotalLabel.setForeground(Color.CYAN);
		gbc.gridx = 0;
		gbc.gridy = 3;
		entryPanel.add(subtotalLabel, gbc);

		subtotalField = new JTextField(35); // Increased the width of subtotalField to 35
		subtotalField.setEditable(false);
		gbc.gridx = 1;
		gbc.gridy = 3;
		entryPanel.add(subtotalField, gbc);

		JLabel cartStatusLabel = new JLabel("Your Shopping Cart Is Currently Empty");
		cartStatusLabel.setForeground(Color.RED);
		gbc.gridx = 1;
		gbc.gridy = 4;
		entryPanel.add(cartStatusLabel, gbc);

		cartArea = new JTextArea(10, 50); // Add proper initialization here
		cartArea.setEditable(false);

		// -------------- Center panel --------------
		JPanel cartPanel = new JPanel();
		cartPanel.setLayout(new GridLayout(9, 1, 5, 5)); 
		cartPanel.setBackground(Color.LIGHT_GRAY);

		// Filler fields to format fields
		JTextArea itemFILLER1Field = new JTextArea(); 
		itemFILLER1Field.setEditable(false); 
		itemFILLER1Field.setBackground(Color.LIGHT_GRAY);

		JTextArea itemFILLER2Field = new JTextArea();
		itemFILLER2Field.setEditable(false);
		itemFILLER2Field.setBackground(Color.LIGHT_GRAY);

		//Cart Items
		JTextField cart1Field = new JTextField();
		cart1Field.setEditable(false);
		cart1Field.setBackground(Color.WHITE);

		JTextField cart2Field = new JTextField();
		cart2Field.setEditable(false);
		cart2Field.setBackground(Color.WHITE);

		JTextField cart3Field = new JTextField();
		cart3Field.setEditable(false);
		cart3Field.setBackground(Color.WHITE);

		JTextField cart4Field = new JTextField();
		cart4Field.setEditable(false);
		cart4Field.setBackground(Color.WHITE);

		JTextField cart5Field = new JTextField();
		cart5Field.setEditable(false);
		cart5Field.setBackground(Color.WHITE);

		// Add fields to panel
		cartPanel.add(itemFILLER1Field);
		cartPanel.add(itemFILLER2Field);
		cartPanel.add(cart1Field);
		cartPanel.add(cart2Field);
		cartPanel.add(cart3Field);
		cartPanel.add(cart4Field);
		cartPanel.add(cart5Field);

		//  -------------- Bottom Panel -------------- 
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(3, 2, 10, 10));
		controlPanel.setBackground(Color.BLUE);

		// Initialize buttons
		searchButton = new JButton("Search For Item #1");
		addItemButton = new JButton("Add Item #1 To Cart");
		viewCartButton = new JButton("View Cart");
		emptyCartButton = new JButton("Empty Cart - Start A New Order");
		checkOutButton = new JButton("Check Out");
		quitButton = new JButton("Exit (Close App)");

		// Initially disable buttons
		addItemButton.setEnabled(false);
		viewCartButton.setEnabled(false);
		checkOutButton.setEnabled(false);

		// Add buttons to panel
		controlPanel.add(searchButton);
		controlPanel.add(addItemButton);
		controlPanel.add(viewCartButton);
		controlPanel.add(emptyCartButton);
		controlPanel.add(checkOutButton);
		controlPanel.add(quitButton);

		// Add panels to frame
		add(entryPanel, BorderLayout.NORTH);
		add(controlPanel, BorderLayout.SOUTH);
		add(cartPanel, BorderLayout.CENTER);

		// -------------- Button Listeners --------------

		//  -------------- Search button --------------
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Reading the entered Item ID and Quantity
				itemId = itemIdField.getText().trim();
				boolean itemFound = false;
				quantityEntered = Integer.parseInt(quantityField.getText().trim()); // Read quantity entered by the user

				// Path to your inventory.csv file
				String csvFile = "src/inventory.csv";

				//Read from file
				try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
					String line;
					while ((line = br.readLine()) != null) {
						// Split the line by commas
						String[] itemDetails = line.split(",");

						if (itemDetails[0].equals(itemId)) {
							// Item found, now display its details
							description = itemDetails[1].replace("\"", "");
							String inStock = itemDetails[2];
							int stockQuantity = Integer.parseInt(itemDetails[3]);
							unitPrice = Double.parseDouble(itemDetails[4]);

							itemFound = true;

							// Check if item in stock
							if (inStock.equals("false")) {
								JOptionPane.showMessageDialog(null,
										"Sorry... that item is out of stock, please try another item",
										"Nile Dot Com - ERROR", JOptionPane.ERROR_MESSAGE);
								addItemButton.setEnabled(false); // Disable the "Add to Cart" button
							} else {
								// Check if the requested quantity is greater than available stock
								if (quantityEntered > stockQuantity) {
									// Insufficient stock case
									JOptionPane.showMessageDialog(null,
											"Insufficient stock. Only " + stockQuantity
													+ " on hand. Please reduce the quantity.",
											"Nile Dot Com - ERROR", JOptionPane.ERROR_MESSAGE);

									// Clear quantity field
									quantityField.setText("");
									return; 
								}

								// Calculate discount
								discount = 0;
								if (quantityEntered >= 1 && quantityEntered <= 4) {
									discount = 0;
								} else if (quantityEntered >= 5 && quantityEntered <= 9) {
									discount = 0.10; 
								} else if (quantityEntered >= 10 && quantityEntered <= 14) {
									discount = 0.15; 
								} else if (quantityEntered >= 15) {
									discount = 0.20; 
								}

								// Calculate total price for item
								double totalPrice = unitPrice * quantityEntered;
								double discountAmount = totalPrice * discount;
								finalPrice = totalPrice - discountAmount;

								// Display the item details
								detailsField.setText(itemId + " \"" + description + "\" " + " $" + unitPrice + " "
										+ quantityEntered + " " + (int) (discount * 100) + "% $"
										+ String.format("%.2f", finalPrice));
								//Enable add Item button since found
								addItemButton.setEnabled(true); 
							}
							break; 
						}
					}

					// If the item ID not in file
					if (!itemFound) {
						JOptionPane.showMessageDialog(null, "Item ID " + itemId + " not in file", "Nile.com - ERROR",
								JOptionPane.ERROR_MESSAGE);
						addItemButton.setEnabled(false); // Disable the "Add to Cart" button if not found
					}

				} catch (IOException ex) {
					detailsField.setText("Error reading inventory.");
					ex.printStackTrace();
				}
			}
		});

		//  -------------- Add item button  --------------
		addItemButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Retrieve the item details
				String itemDetails = detailsField.getText().trim();

				// Increment the number of items=
				numberOfItems++; 

				// Store the cart item details 
				 cartFormat = "Item " + numberOfItems + " – SKU: " + itemId + ", Desc: \"" + description + "\", Price Ea: $" 
				            + String.format("%.2f", unitPrice) + ", Qty: " + quantityEntered + ", Total: $" 
				            + String.format("%.2f", finalPrice);
				
				//Helper for Checkout method
				 cartContents += numberOfItems + ". " + itemId + " \"" + description + "\" $" 
						    + String.format("%.2f", unitPrice) + " " + quantityEntered + " " 
						    + (int)(discount * 100) + "% $" + String.format("%.2f", finalPrice) + "\n";

				// Update the cart display
				switch (numberOfItems) {
				case 1:
					cart1Field.setText(cartFormat);
					break;
				case 2:
					cart2Field.setText(cartFormat);
					break;
				case 3:
					cart3Field.setText(cartFormat);
					break;
				case 4:
					cart4Field.setText(cartFormat);
					break;
				case 5:
					cart5Field.setText(cartFormat);
					break;
				}

				// Update the total as we proceed
				subTotal += finalPrice;
				subtotalField.setText("$" + String.format("%.2f", subTotal));
		        subtotalLabel.setText("Current Subtotal for " + numberOfItems + " item(s):");

				// Update the cart status label
				cartStatusLabel.setText("Your Shopping Cart Currently Contains " + numberOfItems + " Item(s)");

				// Clear input fields for the next item
				itemIdField.setText("");
				quantityField.setText("");

				// Check if cart is full and disable buttons accordingly, or increment number for labels
				if (numberOfItems >= 5) {
					addItemButton.setEnabled(false);
					searchButton.setEnabled(false);
					itemIdField.setEnabled(false);
					quantityField.setEnabled(false);

				} else {
					
					itemIdLabel.setText("Enter item ID for Item #" + (numberOfItems + 1) + ":");
					quantityLabel.setText("Enter quantity for Item #" + (numberOfItems + 1) + ":");
					addItemButton.setEnabled(false); 
				}

				// Enable View Cart and Checkout buttons once an item is added
				viewCartButton.setEnabled(true);
				checkOutButton.setEnabled(true);

				// Increment button labels
				searchButton.setText("Search For Item #" + (numberOfItems + 1));
				addItemButton.setText("Add Item #" + (numberOfItems + 1) + " To Cart");
			}
		});

		//  -------------- View Cart button  --------------
		viewCartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Message dialog using helper from add Item method
				JOptionPane.showMessageDialog(null, cartContents, "Nile Dot Com - Current Shopping Cart Status",
						JOptionPane.INFORMATION_MESSAGE);
			}

		});

		//  -------------- Empty Cart button  --------------
		emptyCartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Reset GUI variables fields and contents
				cartArea.setText(""); 
				subtotalField.setText(""); 
				detailsField.setText(""); 
				itemIdField.setText(""); 
				quantityField.setText(""); 

				// Reset variables
				cartContents = "";
				subTotal = 0; 
				numberOfItems = 0; 

				// Reset the cart fields
				cart1Field.setText("");
				cart2Field.setText("");
				cart3Field.setText("");
				cart4Field.setText("");
				cart5Field.setText("");

				// Update the labels for empty cart
				cartStatusLabel.setText("Your Shopping Cart Is Currently Empty");
				subtotalLabel.setText("Current Subtotal for 0 item(s):");

				// Reset buttons and labels as if new cart
				itemIdField.setEnabled(true);
				itemIdField.setEditable(true);
				quantityField.setEnabled(true);
				quantityField.setEditable(true);

				searchButton.setEnabled(true); 
				addItemButton.setEnabled(false); 
				viewCartButton.setEnabled(false); 
				checkOutButton.setEnabled(false); 

				// Reset the search and add button number
				searchButton.setText("Search For Item #1");
				addItemButton.setText("Add Item #1 To Cart");
				itemIdLabel.setText("Enter item ID for Item #1:");
				quantityLabel.setText("Enter quantity for Item #1:");
			}
		});

		// -------------- Check Out button  --------------
		checkOutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// Get  current date and time
				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
				DateTimeFormatter transactionFormatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
				String currentDateTime = now.format(dateFormatter);
				String transactionId = now.format(transactionFormatter);

				// Calculate tax and total
				double taxRate = 0.06; 
				double taxAmount = subTotal * taxRate;
				double orderTotal = subTotal + taxAmount;

				// Message box for invoice
				JOptionPane.showMessageDialog(null,
								"Date: " + currentDateTime + "EDT\n"
								+ "Number of line items: " + numberOfItems + "\n"
								+ "Item# / ID / Title / Price / Qty / Disc % / Subtotal:\n" 
								+ cartContents + "\n"
								+ "Order subtotal: $" + String.format("%.2f", subTotal) + "\n" 
								+ "Tax rate: 6%\n"
								+ "Tax amount: $" + String.format("%.2f", taxAmount) + "\n" 
								+ "Order Total: $"
								+ String.format("%.2f", orderTotal) + "\n" 
								+ "Thanks for shopping at Nile.com!",
						"Nile Dot Com - FINAL INVOICE", JOptionPane.INFORMATION_MESSAGE);

				// Append to transaction csv file
				try (FileWriter writer = new FileWriter("src/transactions.csv", true)) {
					String[] lines = cartContents.split("\n");
					for (String line : lines) {
						writer.append(transactionId).append(", ").append(line).append(", ").append(currentDateTime)
								.append("\n");
					}
					// New line after every order ends
					writer.append("\n");
					
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(null, "Error writing to transactions.csv.", "Error",
							JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}

				// Disable fields after check out is completed
				itemIdField.setEditable(false);
				itemIdField.setBackground(Color.DARK_GRAY);
				quantityField.setEditable(false);
				quantityField.setBackground(Color.DARK_GRAY);

				// Disable buttons after check out
				searchButton.setEnabled(false);
				addItemButton.setEnabled(false);
				checkOutButton.setEnabled(false);

				// Update buttons that can stay open to use
				viewCartButton.setEnabled(true);
				emptyCartButton.setEnabled(true);
				quitButton.setEnabled(true);

			}
		});

		//  -------------- Quit button  --------------
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		setVisible(true);
	}

	//  -------------- Main Function to create GUI  --------------
	public static void main(String[] args) {
		new GUI();
	}
}

