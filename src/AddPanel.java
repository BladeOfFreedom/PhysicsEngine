import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Vector2d.Vector2D;
import objects.Circle;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddPanel extends JFrame {
	private JTextField radiusTF;
	private JTextField positionTF;
	private JTextField velocityTF;
	private JTextField restitutionTF;
	private JTextField massTF;
	private JTextField accelerationTF;
	
	
	
	public AddPanel() {
		setBounds(100, 100, 706, 454);
		getContentPane().setLayout(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		JLabel lblNewLabel = new JLabel("Radius");
		lblNewLabel.setBounds(10, 45, 49, 21);
		getContentPane().add(lblNewLabel);
		
		JLabel infoLB = new JLabel("");
		infoLB.setBounds(109, 259, 261, 14);
		getContentPane().add(infoLB);
		
		radiusTF = new JTextField();
		radiusTF.setBounds(136, 48, 96, 20);
		getContentPane().add(radiusTF);
		radiusTF.setColumns(10);
		
		positionTF = new JTextField();
		positionTF.setBounds(136, 17, 96, 20);
		getContentPane().add(positionTF);
		positionTF.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Position(x,y)");
		lblNewLabel_1.setBounds(10, 17, 62, 14);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Enter x and y seperated by \",\" eg. 1500,200. (0 <= x <= 1200 ) && (0 <= y <= 800)");
		lblNewLabel_2.setBounds(266, 17, 482, 14);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Velocity(x,y)");
		lblNewLabel_3.setBounds(10, 79, 62, 14);
		getContentPane().add(lblNewLabel_3);
		
		velocityTF = new JTextField();
		velocityTF.setBounds(136, 79, 96, 20);
		getContentPane().add(velocityTF);
		velocityTF.setColumns(10);
		
		restitutionTF = new JTextField();
		restitutionTF.setColumns(10);
		restitutionTF.setBounds(136, 172, 96, 20);
		getContentPane().add(restitutionTF);
		
		massTF = new JTextField();
		massTF.setColumns(10);
		massTF.setBounds(136, 141, 96, 20);
		getContentPane().add(massTF);
		
		accelerationTF = new JTextField();
		accelerationTF.setColumns(10);
		accelerationTF.setBounds(136, 110, 96, 20);
		getContentPane().add(accelerationTF);
		
		JLabel lblNewLabel_3_1 = new JLabel("Resititution");
		lblNewLabel_3_1.setBounds(10, 172, 62, 14);
		getContentPane().add(lblNewLabel_3_1);
		
		JLabel lblMass = new JLabel("Mass");
		lblMass.setBounds(10, 138, 49, 21);
		getContentPane().add(lblMass);
		
		JLabel lblNewLabel_1_1 = new JLabel("Acceleration(x,y)");
		lblNewLabel_1_1.setBounds(10, 110, 96, 14);
		getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_2_1 = new JLabel("Enter x and y seperated by \",\" eg. 10,30.");
		lblNewLabel_2_1.setBounds(266, 79, 232, 14);
		getContentPane().add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_2 = new JLabel("Enter x and y seperated by \",\" eg. 10,0.");
		lblNewLabel_2_2.setBounds(266, 113, 232, 14);
		getContentPane().add(lblNewLabel_2_2);
		
		JLabel lblNewLabel_4 = new JLabel("Converted to real life (somewhat) so values can be entered using m/s!");
		lblNewLabel_4.setBounds(10, 203, 360, 21);
		getContentPane().add(lblNewLabel_4);
		
		JButton btnNewButton = new JButton("Add Body");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//check if any of the text boxes are empty
				if(	positionTF.getText().equals("") ||
					radiusTF.getText().equals("") ||
					velocityTF.getText().equals("") ||
					accelerationTF.getText().equals("") ||
					massTF.getText().equals("") ||
					restitutionTF.getText().equals("")) {
					
					infoLB.setText("Please fill all the textBoxes!!!");
					return;
				}
				
				//get the values from the text boxes
				String[] positionS = positionTF.getText().split(",");
				String radiusS = radiusTF.getText();
				String[] velocityS = velocityTF.getText().split(",");
				String[] accelerationS = accelerationTF.getText().split(",");
				String massS = massTF.getText();
				String restitutionS = restitutionTF.getText();
				
				
				
				Vector2D position = new Vector2D(Integer.parseInt(positionS[0]), Integer.parseInt(positionS[1]));
				double radius = Double.parseDouble(radiusS);
				Vector2D velocity = new Vector2D(Integer.parseInt(velocityS[0]), Integer.parseInt(velocityS[1]));
				Vector2D acceleration = new Vector2D(Integer.parseInt(accelerationS[0]), Integer.parseInt(accelerationS[1]));
				double mass = Double.parseDouble(massS);
				double restitution = Double.parseDouble(restitutionS);
				
				Circle addCircle = new Circle(position.x, position.y, velocity, acceleration, new Vector2D(0,0), mass, restitution, radius);
				
				Panel.getBodies().add(addCircle);
				
				
				
			}
		});
		btnNewButton.setBounds(10, 255, 89, 23);
		getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_5 = new JLabel("Mass == 0 => Static Body.");
		lblNewLabel_5.setBounds(266, 144, 232, 14);
		getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("between 0 and 1. Determines how elastic the body is.");
		lblNewLabel_6.setBounds(266, 175, 279, 14);
		getContentPane().add(lblNewLabel_6);
		
		JLabel lblNewLabel_8 = new JLabel("Beware that (0,0) is top left corner and y axis is flipped ( (-) is up)!");
		lblNewLabel_8.setBounds(10, 230, 360, 14);
		getContentPane().add(lblNewLabel_8);
		
		
		
		

	}
}
