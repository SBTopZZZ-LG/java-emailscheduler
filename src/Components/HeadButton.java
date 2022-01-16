package Components;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class HeadButton extends JPanel {
    // Components
    private JLabel icon;
    private JLabel text;

    // Component data
    private Component parent;
    private Point btnLocation = new Point(0, 0);
    public interface InteractionListener {
        void onClick();
    }

    public HeadButton(Component parent, String text, String imageLocation, InteractionListener listener) {
        this.parent = parent;

        // Set properties
        setLocation(btnLocation.x + 10, btnLocation.y + 10);
        setSize(parent.getHeight(), parent.getHeight());
        setBackground(Color.WHITE);
        setBorder(new LineBorder(Color.BLACK, 1, true));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Instantiate
        this.text = new JLabel(text);
        this.text.setFont(this.text.getFont().deriveFont(Font.BOLD, 12));
        ImageIcon icon = new ImageIcon(imageLocation);
        icon = new ImageIcon(icon.getImage().getScaledInstance((int) (getWidth() / 2), (int) (getHeight() / 2), java.awt.Image.SCALE_SMOOTH));
        this.icon = new JLabel(icon);

        // Finalize
        add(this.icon);
        add(this.text);

        // Setup listener
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                listener.onClick();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        this.icon.addMouseListener(getMouseListeners()[0]);
        this.text.addMouseListener(getMouseListeners()[0]);
    }
}
