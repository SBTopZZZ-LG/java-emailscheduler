package Frames;

import Components.RoundedBorder;
import Models.Entry;
import Models.EntryHandler;
import Models.EntryTimer;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import net.miginfocom.swing.MigLayout;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilCalendarModel;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.*;

class NewEntryHeadButton extends JPanel {
    // Components
    private ImageIcon icon;
    private JLabel text;

    // Component data
    private NewEntryHead parent;
    private Point btnLocation = new Point(0, 0);
    private RoundedBorder border = new RoundedBorder();
    private boolean enabled = true;

    public NewEntryHeadButton(NewEntryHead parent, String text, String imageLocation) {
        this.parent = parent;

        setLocation(btnLocation.x + 10, btnLocation.y + 10);
        setSize(parent.getHeight(), parent.getHeight());
        setBackground(parent.getBackground());
        border.setBorderInsets(new Insets(1, 1, 1, 1));
        border.setCornerRadius(5);
        border.setBackground(Color.WHITE);
        setBorder(border);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        icon = new ImageIcon(imageLocation);
        icon = new ImageIcon(icon.getImage().getScaledInstance((int) (getWidth() / 2), (int) (getHeight() / 2), java.awt.Image.SCALE_SMOOTH));
        this.text = new JLabel(text);
        Font ft = this.text.getFont();
        ft = ft.deriveFont(Font.BOLD, 15);
        this.text.setFont(ft);
        JLabel img = new JLabel(icon);
        img.setLocation(100, 5);

        add(img);
        add(this.text);
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
class NewEntryHead extends JPanel {
    // Components
    NewEntryHeadButton backButton;
    NewEntryHeadButton saveButton;
    NewEntryHeadButton resetButton;

    // Component data
    NewEntry parent;
    int headSize = 50;
    Point headLocation = new Point(0, 0);

    public NewEntryHead(NewEntry parent) {
        this.parent = parent;

        setSize(parent.getSize().width, headSize);
        setLocation(headLocation.x, headLocation.y);
        setBackground(Color.decode("#e3e3e3"));
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.LEADING);
        setLayout(fl);

        backButton = new NewEntryHeadButton(this, "Go Back", "src/Resources/Images/back.png");
        saveButton = new NewEntryHeadButton(this, "Create entry", "src/Resources/Images/save.png");
        resetButton = new NewEntryHeadButton(this, "Reset", "src/Resources/Images/reset.png");

        backButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parent.setVisible(true);

                // Close this frame
                parent.dispose();
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

        add(backButton);
        add(saveButton);
        add(resetButton);
    }
}

class NewEntryBody extends JPanel {
    NewEntry parent;

    // Components
    JPanel recipientEmailPanel;
    JPanel subjectPanel;
    JPanel bodyPanel;
    JPanel schedulePanel;

    JLabel recipientEmail;
    JLabel subject;
    JLabel body;
    JLabel schedule;

    JTextField recipientEmailTF;
    JTextField subjectTF;
    JTextArea bodyTA;

    JDatePanelImpl scheduleDate;
    TimePicker timePicker;

    // Component data

    public NewEntryBody(NewEntry parent) {
        this.parent = parent;

        // Instantiate
        recipientEmailPanel = new JPanel(new MigLayout("fillx"));
        recipientEmail = new JLabel("Recipient's Email Address");
        recipientEmail.setFont(recipientEmail.getFont().deriveFont(Font.BOLD));
        recipientEmailTF = new JTextField();
        recipientEmailTF.setBorder(new LineBorder(Color.BLACK, 1, false));
        recipientEmailPanel.add(recipientEmail, "wrap");
        recipientEmailPanel.add(recipientEmailTF, "span, width 100%");

        subjectPanel = new JPanel(new MigLayout("fillx"));
        subject = new JLabel("Subject");
        subject.setFont(subject.getFont().deriveFont(Font.BOLD));
        subjectTF = new JTextField();
        subjectTF.setBorder(new LineBorder(Color.BLACK, 1, false));
        subjectPanel.add(subject, "wrap");
        subjectPanel.add(subjectTF, "span, width 100%");

        bodyPanel = new JPanel(new MigLayout("fillx"));
        body = new JLabel("Body");
        body.setFont(body.getFont().deriveFont(Font.BOLD));
        bodyTA = new JTextArea();
        bodyTA.setRows(10);
        bodyTA.setBorder(new LineBorder(Color.BLACK, 1, false));
        bodyPanel.add(body, "wrap");
        bodyPanel.add(bodyTA, "span, width 100%");

        schedulePanel = new JPanel(new MigLayout("fillx"));
        schedule = new JLabel("Schedule at");
        schedule.setFont(schedule.getFont().deriveFont(Font.BOLD));
        schedulePanel.add(schedule, "wrap");
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        scheduleDate = new JDatePanelImpl(new UtilDateModel(), p);
        schedulePanel.add(scheduleDate, "width 100%, wrap");
        TimePickerSettings timeSettings = new TimePickerSettings();
        timeSettings.setColor(TimePickerSettings.TimeArea.TimePickerTextValidTime, Color.black);
        timeSettings.initialTime = LocalTime.now();
        timePicker = new TimePicker(timeSettings);
        schedulePanel.add(timePicker, "width 100%");

        setLayout(new MigLayout("fillx"));

        // Add items
        add(recipientEmailPanel, "width 100%, wrap");
        add(subjectPanel, "width 100%, wrap");
        add(bodyPanel, "width 100%, wrap");
        add(schedulePanel, "width 100%");
    }
}

public class NewEntry extends JFrame {
    Dashboard previous;
    // Components
    NewEntryHead head;
    NewEntryBody body;

    // Component data
    // - Frame
    Point frameSize = new Point(500, 700);
    Point startLocation;
    final String startTitle = "Email Scheduler - New Entry";

    public NewEntry(Dashboard previous) {
        this.previous = previous;

        // Instantiate
        head = new NewEntryHead(this);
        body = new NewEntryBody(this);

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

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {
                previous.setVisible(true);
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
        head.resetButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                body.recipientEmailTF.setText("");
                body.subjectTF.setText("");
                body.bodyTA.setText("");
                body.scheduleDate.getModel().setSelected(false);
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        head.saveButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (body.recipientEmailTF.getText().length() == 0 || body.subjectTF.getText().length() == 0 ||
                    body.bodyTA.getText().length() == 0 || !body.scheduleDate.getModel().isSelected()) {
                    JOptionPane.showMessageDialog(NewEntry.this,
                            "Please fill all the fields before creating the entry",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Calendar scheduleDateSelected = Calendar.getInstance();
                scheduleDateSelected.setTimeZone(TimeZone.getDefault());
                scheduleDateSelected.setTime(((UtilDateModel) body.scheduleDate.getModel()).getValue());
                scheduleDateSelected.set(Calendar.HOUR_OF_DAY, body.timePicker.getTime().getHour());
                scheduleDateSelected.set(Calendar.MINUTE, body.timePicker.getTime().getMinute());
                scheduleDateSelected.set(Calendar.SECOND, 0);

                Calendar temp = Calendar.getInstance();
                temp.setTimeZone(TimeZone.getDefault());
                temp.setTime(new Date());

                if (scheduleDateSelected.before(temp)) {
                    // Date cannot be old

                    JOptionPane.showMessageDialog(NewEntry.this,
                            "Please choose a valid date",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Entry entry = new Entry();
                entry.setRecipientEmail(body.recipientEmailTF.getText());
                entry.setSubject(body.subjectTF.getText());
                entry.setBody(body.bodyTA.getText());
                entry.setSchedule(scheduleDateSelected.getTimeInMillis());
                entry.setPendingStatus(true);

                EntryHandler.entries.add(new EntryTimer(entry));
                try {
                    EntryHandler.save();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                EntryHandler.registerTimers(null);

                JOptionPane.showMessageDialog(NewEntry.this,
                        "Entry created",
                        "Alert",
                        JOptionPane.INFORMATION_MESSAGE);

                // Refresh and Close this JFrame
                previous.body.refreshData();
                NewEntry.this.dispose();
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

        add(head, BorderLayout.PAGE_START);
        add(body, BorderLayout.CENTER);
    }

    public void display() {
        setVisible(true);
    }
    public void close() {
        setVisible(false);
    }
}
