package Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class HeadButton extends JPanel {
    // Components
    private ImageIcon icon;
    private JLabel text;

    // Component data
    private Component parent;
    private Point btnLocation = new Point(0, 0);
    private RoundedBorder border = new RoundedBorder();
    private boolean enabled = true;
    public interface InteractionListener {
        void onClick();
    }

    public HeadButton(Component parent, String text, String imageLocation, InteractionListener listener) {
        this.parent = parent;

        // Set properties
        setLocation(btnLocation.x + 10, btnLocation.y + 10);
        setSize(parent.getHeight(), parent.getHeight());
        setBackground(parent.getBackground());
        border.setBorderInsets(new Insets(1, 1, 1, 1));
        border.setCornerRadius(5);
        border.setBackground(Color.WHITE);
        setBorder(border);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Instantiate
        icon = new ImageIcon(imageLocation);
        icon = new ImageIcon(icon.getImage().getScaledInstance((int) (getWidth() / 2), (int) (getHeight() / 2), java.awt.Image.SCALE_SMOOTH));
        this.text = new JLabel(text);
        Font ft = this.text.getFont();
        ft = ft.deriveFont(Font.BOLD, 15);
        this.text.setFont(ft);
        JLabel img = new JLabel(icon);
        img.setLocation(100, 5);

        // Finalize
        add(img);
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
        img.addMouseListener(getMouseListeners()[0]);
        this.text.addMouseListener(getMouseListeners()[0]);
    }

    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        setBackground(enabled ? parent.getBackground() : Color.GRAY);
        border.setBackground(enabled ? Color.WHITE : Color.GRAY);
        setBorder(border);
        text.setForeground(enabled ? Color.BLACK : Color.darkGray);
        setCursor(enabled ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) : Cursor.getDefaultCursor());
    }
}
