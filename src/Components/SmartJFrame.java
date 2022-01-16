package Components;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public abstract class SmartJFrame extends JFrame {
    protected SmartJFrame previous;
    public SmartJFrame(SmartJFrame previous) {
        this.previous = previous;
        setVisible(false);

        if (previous == null)
            // If previous is null then exit application on closing
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        else
            // Manual control
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Add listener
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                if (onClose(e))
                    dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    public void pushNext(SmartJFrame jFrame) {
        pushNext(jFrame, false);
    }
    public void pushNext(SmartJFrame jFrame, boolean stackWindows) {
        if (stackWindows) {
            // Don't hide, simply display jFrame and set focus

            jFrame.setAlwaysOnTop(true);
            jFrame.display();
        } else {
            // Hide, and display jFrame

            close();
            jFrame.display();
        }
    }

    public boolean onClose(WindowEvent windowEvent) {
        // Dispose and display previous frame

        previous.display();
        return true;
    }

    public void display() {
        setVisible(true);
    }
    public void close() {
        setVisible(false);
    }
}
