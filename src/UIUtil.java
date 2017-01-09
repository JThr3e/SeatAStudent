
import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyledDocument;

public class UIUtil {
	
	public static JTextPane textpane = new JTextPane();
	public static StyledDocument doc = textpane.getStyledDocument();
	public static JFrame frame = makeJframe();
	public static JScrollPane jscr = new JScrollPane(textpane);
	
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
	
	public static void changeJOP()
	{
		UIManager.put("OptionPane.messageForeground",
				new Color(74,186,101));
		UIManager.put("TextField.background",
				new Color(106, 79, 52));
		UIManager.put("TextField.font", new FontUIResource(new Font
				("Dialog", Font.ITALIC, 45)));
		UIManager.put("Button.font", new FontUIResource	(new Font
				("Tempus Sans ITC", Font.BOLD, 45)));
		UIManager.put("Label.font", new FontUIResource (new Font
				("Tempus Sans ITC", Font.BOLD, 45)));
		UIManager.put("TextField.foreground", Color.green);
		UIManager.put("Panel.background",new Color(46,112,62));
		UIManager.put("OptionPane.background",new Color(106,79,52));
		UIManager.put("Button.background",new Color(74,137,63));
		UIManager.put("Button.foreground", new Color(72,61,139));
	}
	
	public static void createUI(){
		frame.setTitle("SEAT A STUDENT");
		textpane.setFont(new Font("Arial", Font.BOLD, 30));
	    jscr.setVisible(true);
	    frame.add(jscr);
	    frame.setVisible(true);
	}
	
	public static void updateOutput(String out)
	{
		textpane.setText(textpane.getText()+ out + "\n");
		jscr.getVerticalScrollBar().setValue(jscr.getVerticalScrollBar().getMaximum());
	}
	
	public static String askUser(String str)
	{
		return JOptionPane.showInputDialog(str);
	}
	
	public static void showOutput(String str)
	{
		JOptionPane.showMessageDialog(null, str);
	}
	
	public static void joke(long startTime)
	{
		String joke = "";
		int i = (int)(Math.random()*13);
		switch(i){
		case(0): joke = "42"; break;
		case(1): joke = "Hi I'm JThr3e, the friendly AI"; break;
		case(2): joke = "Steel Ppl"; break;
		case(3): joke = "Please don't hate me, I'm not perfect. ;("; break;
		case(4): joke = ";)"; break;
		case(5): joke = "WARNING: **Explodes when divided by zero**"; break;
		case(7): joke = "At least I'm better than O(n!)"; break;
		case(8): joke = "Does P=NP yet?"; break;
		case(9): joke = "(╯°□°）╯︵ ┻━┻"; break;
		case(10): joke = "I'm Sorry Dave, I'm afraid I can't do that."; break;
		case(11): joke = "There will be cake."; break;
		case(12): joke = "NO DISSASSEMBLE!!"; break;
		default: joke = "Why?"; break;
		}
		updateOutput("["+(System.currentTimeMillis()-startTime)+"ms]: "+joke);
	}
}
