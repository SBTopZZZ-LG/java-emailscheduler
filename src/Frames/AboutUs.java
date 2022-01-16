package Frames;

import Components.HeadButton;
import Components.RoundedBorder;
import Components.SmartJFrame;
import Models.User;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class AboutUsHead extends JPanel {
    // Components
    private HeadButton backButton;

    // Component data
    private AboutUs parent;
    private int headSize = 50;
    private Point headLocation = new Point(0, 0);
    public interface InteractionListener {
        void onBack(AboutUsHead head);
    }

    public AboutUsHead(AboutUs parent, InteractionListener listener) {
        this.parent = parent;

        // Set properties
        setSize(parent.getSize().width, headSize);
        setLocation(headLocation.x, headLocation.y);
        setBackground(Color.decode("#e3e3e3"));
        setLayout(new MigLayout("insets 5 10 5 10, fillx"));

        // Instantiate
        backButton = new HeadButton(this, "Go Back", "src/Resources/Images/back.png",
                new HeadButton.InteractionListener() {
                    @Override
                    public void onClick() {
                        listener.onBack(AboutUsHead.this);
                    }
                });

        // Finalize
        add(backButton, "width 100%");
    }
}

class AboutUsBodyDetailPanel extends JPanel {
    // Components
    private JPanel dataPanel;
    private JPanel socialsPanel;

    private JLabel avatar, linkedin, instagram;
    private JLabel name;
    private JLabel usn;

    // Component data
    private AboutUsBody parent;
    private User user;

    public AboutUsBodyDetailPanel(AboutUsBody parent, User user) {
        this.parent = parent;
        this.user = user;

        // Instantiate
        ImageIcon icon = new ImageIcon(user.getImageLocation());
        icon = new ImageIcon(icon.getImage().getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH));
        avatar = new JLabel(icon);
        avatar.setBorder(new LineBorder(Color.BLACK, 1, false));

        name = new JLabel(user.getFullName());
        name.setFont(name.getFont().deriveFont(Font.BOLD, 20));

        ImageIcon linkedInIcon = new ImageIcon("src/Resources/Images/linkedin.png");
        linkedInIcon = new ImageIcon(linkedInIcon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH));
        linkedin = new JLabel(linkedInIcon);
        linkedin.setBackground(Color.WHITE);
        linkedin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        linkedin.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    java.awt.Desktop.getDesktop().browse(java.net.URI.create(user.getSocials().getLinkedIn()));
                } catch (java.io.IOException e2) {
                    System.out.println(e2.getMessage());
                }
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

        ImageIcon instagramIcon = new ImageIcon("src/Resources/Images/instagram.png");
        instagramIcon = new ImageIcon(instagramIcon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH));
        instagram = new JLabel(instagramIcon);
        instagram.setBackground(Color.WHITE);
        instagram.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        instagram.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    java.awt.Desktop.getDesktop().browse(java.net.URI.create(user.getSocials().getInstagram()));
                } catch (java.io.IOException e2) {
                    System.out.println(e2.getMessage());
                }
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

        socialsPanel = new JPanel(new MigLayout("insets 0 0 5 0"));
        socialsPanel.setBackground(Color.WHITE);
        socialsPanel.add(linkedin);
        socialsPanel.add(instagram, "gap 10");

        usn = new JLabel(user.getUsn());
        usn.setFont(usn.getFont().deriveFont(Font.BOLD, 13));

        dataPanel = new JPanel();
        dataPanel.setLayout(new MigLayout("insets 2 2 2 2"));
        dataPanel.add(name, "width 100%, wrap");
        dataPanel.add(socialsPanel, "width 100%, wrap");
        dataPanel.add(usn, "width 100%");

        // Set properties
        setLayout(new MigLayout("insets 5 5 5 5, fillx"));
        RoundedBorder border = new RoundedBorder();
        border.setBorderInsets(new Insets(3, 3, 3, 3));
        border.setBackground(Color.WHITE);
        border.setCornerRadius(5);
        border.setBorderThickness(2);
        border.setBorderColor(Color.decode("#dddddd"));
        setBorder(border);
        dataPanel.setBackground(Color.WHITE);

        // Finalize
        add(avatar);
        add(dataPanel, "gap 10, width 100%");
    }
}

class AboutUsBody extends JPanel {
    // Components
    private JLabel heading;
    private AboutUsBodyDetailPanel user1, user2, user3, user4;

    // Component data
    private AboutUs parent;

    public AboutUsBody(AboutUs parent) {
        this.parent = parent;

        // Instantiate
        heading = new JLabel("A project by");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 20));
        user1 = new AboutUsBodyDetailPanel(this,
                new User("Saumitra Topinkatti",
                        "2GI20CS133",
                        "src/Resources/Images/person1_2.jpeg",
                        new User.Socials("https://linkedin.com", "https://instagram.com")));
        user2 = new AboutUsBodyDetailPanel(this,
                new User("Shamant Myageri",
                        "2GI20CS134",
                        "src/Resources/Images/person2.jpg",
                        new User.Socials("https://linkedin.com", "https://instagram.com")));
        user3 = new AboutUsBodyDetailPanel(this,
                new User("Shivani Patil",
                        "2GI20CS139",
                        "src/Resources/Images/person3.jpeg",
                        new User.Socials("https://linkedin.com", "https://instagram.com")));
        user4 = new AboutUsBodyDetailPanel(this,
                new User("Shivam Karamudi",
                        "2GI20CS137",
                        "src/Resources/Images/person4.png",
                        new User.Socials("https://linkedin.com", "https://instagram.com")));

        // Set properties
        setLayout(new MigLayout("insets 5 5 5 5"));

        // Finalize
        add(heading, "wrap, width 100%");
        add(user1, "wrap, width 100%");
        add(user2, "wrap, width 100%");
        add(user3, "wrap, width 100%");
        add(user4, "width 100%");
    }
}

public class AboutUs extends SmartJFrame {
    // Components
    private AboutUsHead head;
    private AboutUsBody body;

    // Component data
    private Point frameSize = new Point(500, 675);
    private Point startLocation;
    private final String startTitle = "Email Scheduler - About Us";

    public AboutUs(SmartJFrame previous) {
        super(previous);

        // Set frame properties
        setLayout(new BorderLayout());
        setTitle(startTitle);
        pack();
        setLocationRelativeTo(null); // Sets position to center
        setSize(frameSize.x, frameSize.y);
        Dimension screenDims = Toolkit.getDefaultToolkit().getScreenSize();
        startLocation = new Point(screenDims.width / 2 - frameSize.x / 2, screenDims.height / 2 - frameSize.y / 2);
        setLocation(startLocation.x, startLocation.y);
        setResizable(false);

        // Instantiate
        head = new AboutUsHead(this, new AboutUsHead.InteractionListener() {
            @Override
            public void onBack(AboutUsHead head) {
                // Display previous window then dispose current one
                previous.display();
                dispose();
            }
        });
        body = new AboutUsBody(this);

        // Finalize
        add(head, BorderLayout.PAGE_START);
        add(body, BorderLayout.CENTER);
    }
}
