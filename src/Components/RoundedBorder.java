package Components;

import javax.swing.border.Border;
import java.awt.*;

public class RoundedBorder implements Border {
    private Insets borderInsets = new Insets(5, 5, 5, 5);
    private int cornerRadius = 20, borderThickness = 1;
    private Color borderColor = Color.BLACK, background;

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;

        // Set properties to g2 before painting
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(borderThickness));

        // Draw lines
        g2.drawLine(x + (borderInsets.left + cornerRadius), y + borderInsets.top,
                x + width - (borderInsets.right + cornerRadius), y + borderInsets.top); // Top
        g2.drawLine(x + (borderInsets.left + cornerRadius), y + height - borderInsets.bottom,
                x + width - (borderInsets.right + cornerRadius), y + height - borderInsets.bottom); // Bottom
        g2.drawLine(x + borderInsets.left, y + (borderInsets.top + cornerRadius),
                x + borderInsets.left, y + height - (borderInsets.bottom + cornerRadius)); // Left
        g2.drawLine(x + width - borderInsets.right, y + (borderInsets.top + cornerRadius),
                    x + width - borderInsets.right, y + height - (borderInsets.bottom + cornerRadius)); // Right

        // Draw arcs
        g2.drawArc(borderInsets.left, borderInsets.top,
                cornerRadius * 2, cornerRadius * 2,
                90, 90); // Top Left
        g2.drawArc(x + width - (borderInsets.right + cornerRadius * 2), borderInsets.top,
                cornerRadius * 2, cornerRadius * 2,
                0, 90); // Top Right
        g2.drawArc(borderInsets.left, y + height - (borderInsets.bottom + cornerRadius * 2),
                cornerRadius * 2, cornerRadius * 2,
                180, 90); // Bottom Left
        g2.drawArc(x + width - (borderInsets.right + cornerRadius * 2), y + height - (borderInsets.bottom + cornerRadius * 2),
                cornerRadius * 2, cornerRadius * 2,
                270, 90); // Bottom Right

        // Paint background (if specified)
        if (background != null) {
            g2.setColor(background);
            g2.fillRoundRect(borderInsets.left, borderInsets.top,
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

    public int getBorderThickness() {
        return this.borderThickness;
    }
    public void setBorderThickness(int borderThickness) {
        this.borderThickness = borderThickness;
    }

    public Color getBorderColor() { return this.borderColor; }
    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
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
