
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class ScrollPaneUtil {
	
	public static JLabel output = new JLabel("<html></html>");
	public static JFrame frame = makeJframe();
	public static JScrollPane jscr = new JScrollPane(output);
	
	public static JFrame makeJframe()
	{
		JFrame frame = new JFrame("JFrame Source Demo");
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.pack();
		frame.setSize(1280, 720);
		frame.setVisible(true);
		return frame;
	}
	
	public static void createUI(){
		frame.setTitle("SEAT A STUDENT");
		output.setFont(new Font("Arial", Font.BOLD, 25));
	    output.setVerticalAlignment(SwingConstants.TOP);
	    jscr.setVisible(true);
	    frame.add(jscr);
	    frame.setVisible(true);
	}
	
	public static void updateOutput(String out)
	{
		output.setText(output.getText().substring(0, output.getText().length()-7)+ out + "<br></html>");
		jscr.getVerticalScrollBar().setValue(jscr.getVerticalScrollBar().getMaximum());
	}
	
	
	
	
	
	

}
