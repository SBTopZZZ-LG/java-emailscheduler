package Components;

import javax.swing.border.Border;
import java.awt.*;

public class RoundedBorder implements Border {
    private Insets borderInsets = new Insets(5, 5, 5, 5);
    private int cornerRadius = 20;
    private Color background;

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        // Draw lines
        g.drawLine(x + (borderInsets.left + cornerRadius), y + borderInsets.top,
                x + width - (borderInsets.right + cornerRadius), y + borderInsets.top); // Top
        g.drawLine(x + (borderInsets.left + cornerRadius), y + height - borderInsets.bottom,
                x + width - (borderInsets.right + cornerRadius), y + height - borderInsets.bottom); // Bottom
        g.drawLine(x + borderInsets.left, y + (borderInsets.top + cornerRadius),
                x + borderInsets.left, y + height - (borderInsets.bottom + cornerRadius)); // Left
        g.drawLine(x + width - borderInsets.right, y + (borderInsets.top + cornerRadius),
                    x + width - borderInsets.right, y + height - (borderInsets.bottom + cornerRadius)); // Right

        // Draw arcs
        g.drawArc(borderInsets.left, borderInsets.top,
                cornerRadius * 2, cornerRadius * 2,
                90, 90); // Top Left
        g.drawArc(x + width - (borderInsets.right + cornerRadius * 2), borderInsets.top,
                cornerRadius * 2, cornerRadius * 2,
                0, 90); // Top Right
        g.drawArc(borderInsets.left, y + height - (borderInsets.bottom + cornerRadius * 2),
                cornerRadius * 2, cornerRadius * 2,
                180, 90); // Bottom Left
        g.drawArc(x + width - (borderInsets.right + cornerRadius * 2), y + height - (borderInsets.bottom + cornerRadius * 2),
                cornerRadius * 2, cornerRadius * 2,
                270, 90); // Bottom Right

        // Paint background (if specified)
        if (background != null) {
            g.setColor(background);
            g.fillRoundRect(borderInsets.left, borderInsets.top,
                    width - (borderInsets.left + borderInsets.right), height - (borderInsets.top + borderInsets.bottom),
                    cornerRadius * 2, cornerRadius * 2);
        }
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return borderInsets;
    }
    public void setBorderInsets(Insets borderInsets) {
        this.borderInsets = borderInsets;
    }

    public int getCornerRadius() {
        return cornerRadius;
    }
    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public Color getBackground() {
        return this.background;
    }
    public void setBackground(Color background) {
        this.background = background;
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }
}
