import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class TestScript {
	public static void main(String[] arg) {
		MyFrame mf = new MyFrame();
	}
}

class MyFrame extends JFrame implements ActionListener {

	private JTextField addressField, userField;
	private JLabel address = new JLabel("Enter Address");
	private JLabel users = new JLabel("Enter amount of users");
	private JButton submit = new JButton("submit");

	public MyFrame() {
		addressField = new JTextField();
		userField = new JTextField();
		setLayout(new GridLayout(3, 2));
		add(address);
		add(addressField);
		add(users);
		add(userField);
		submit.addActionListener(this);
		add(submit);

		setSize(400, 200);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e) {
		if(addressField.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "Vul aub een adres in!");
		}
		else if(userField.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "Vul aub een aantal gebruikers in!");
		}
		else {
			try {
				Integer.parseInt(userField.getText());
				
				
			}
			catch(Exception ee) {
				JOptionPane.showMessageDialog(null, "Aantal gebruikers moet numeriek zijn");
			}
		}
	}
}
