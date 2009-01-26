import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

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

	private JTextField addressField, userField, portField;
	private JLabel address = new JLabel("Enter Address");
	private JLabel port = new JLabel("Enter Port");
	private JLabel users = new JLabel("Enter amount of users");
	private JButton submit = new JButton("submit");

	public MyFrame() {
		addressField = new JTextField();
		portField = new JTextField();
		userField = new JTextField();
		setLayout(new GridLayout(4, 2));
		add(address);
		add(addressField);
		add(port);
		add(portField);
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
		else if(portField.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "Vul aub een aantal gebruikers in!");
		}
		else if(userField.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "Vul aub een aantal gebruikers in!");
		}
		else {
			try {
				int users = Integer.parseInt(userField.getText());
				int port = Integer.parseInt(portField.getText());
				String host = addressField.getText();
				Socket s = new Socket(host, port);
				
				for(int i = 0; i< users; i++) {
					
				}
			}
			catch(Exception ee) {
				JOptionPane.showMessageDialog(null, "Vul AUB numerieke waarden in!");
			}
		}
	}
}
