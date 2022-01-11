package Frames;

import Components.HeadButton;
import Components.SmartJFrame;
import Models.Entry;
import Models.EntryHandler;
import Models.EntryTimer;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import net.miginfocom.swing.MigLayout;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

class NewEntryHead extends JPanel {
    // Components
    private HeadButton backButton;
    private HeadButton saveButton;
    private HeadButton resetButton;

    // Component data
    private NewEntry parent;
    private int headSize = 50;
    private Point headLocation = new Point(0, 0);
    public interface InteractionListener {
        void onBack(NewEntryHead head);
        void onSave(NewEntryHead head);
        void onReset(NewEntryHead head);
    }

    public NewEntryHead(NewEntry parent, InteractionListener listener) {
        this.parent = parent;

        // Set properties
        setSize(parent.getSize().width, headSize);
        setLocation(headLocation.x, headLocation.y);
        setBackground(Color.decode("#e3e3e3"));
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.LEADING);
        setLayout(fl);

        // Instantiate
        backButton = new HeadButton(this, "Go Back", "src/Resources/Images/back.png",
                new HeadButton.InteractionListener() {
                    @Override
                    public void onClick() {
                        listener.onBack(NewEntryHead.this);
                    }
                });
        saveButton = new HeadButton(this, "Create entry", "src/Resources/Images/save.png",
                new HeadButton.InteractionListener() {
                    @Override
                    public void onClick() {
                        listener.onSave(NewEntryHead.this);
                    }
                });
        resetButton = new HeadButton(this, "Reset", "src/Resources/Images/reset.png",
                new HeadButton.InteractionListener() {
                    @Override
                    public void onClick() {
                        listener.onReset(NewEntryHead.this);
                    }
                });

        // Finalize
        add(backButton);
        add(saveButton);
        add(resetButton);
    }
}

class NewEntryBody extends JPanel {
    // Components
    private JPanel recipientEmailPanel;
    private JPanel subjectPanel;
    private JPanel bodyPanel;
    private JPanel schedulePanel;

    private JScrollPane bodyScroll;

    private JLabel recipientEmail;
    private JLabel subject;
    private JLabel body;
    private JLabel schedule;

    JTextField recipientEmailTF;
    JTextField subjectTF;
    JTextArea bodyTA;

    JDatePanelImpl scheduleDate;
    TimePicker timePicker;

    // Component data
    private NewEntry parent;

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
        bodyScroll = new JScrollPane(bodyTA, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        bodyPanel.add(body, "wrap");
        bodyPanel.add(bodyScroll, "span, width 100%");

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

        // Set properties
        setLayout(new MigLayout("fillx"));

        // Add items
        add(recipientEmailPanel, "width 100%, wrap");
        add(subjectPanel, "width 100%, wrap");
        add(bodyPanel, "width 100%, wrap");
        add(schedulePanel, "width 100%");
    }
}

public class NewEntry extends SmartJFrame {
    // Components
    private NewEntryHead head;
    private NewEntryBody body;

    // Component data
    private Point frameSize = new Point(500, 700);
    private Point startLocation;
    private final String startTitle = "Email Scheduler - New Entry";

    public NewEntry(Dashboard previous) {
        super(previous);

        // Instantiate
        head = new NewEntryHead(this, new NewEntryHead.InteractionListener() {
            @Override
            public void onBack(NewEntryHead head) {
                // Display previous window then dispose current one
                previous.display();
                dispose();
            }

            @Override
            public void onSave(NewEntryHead head) {
                // Validate input
                if (body.recipientEmailTF.getText().length() == 0 || body.subjectTF.getText().length() == 0 ||
                        body.bodyTA.getText().length() == 0 || !body.scheduleDate.getModel().isSelected()) {
                    JOptionPane.showMessageDialog(NewEntry.this,
                            "Please fill all the fields before creating the entry",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!body.recipientEmailTF.getText().matches("[a-z0-9._]+@[a-z0-9._]+")) {
                    JOptionPane.showMessageDialog(NewEntry.this,
                            "Enter a valid email address",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Create entry

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
                NewEntry.this.dispose();
            }

            @Override
            public void onReset(NewEntryHead head) {
                body.recipientEmailTF.setText("");
                body.subjectTF.setText("");
                body.bodyTA.setText("");
                body.scheduleDate.getModel().setSelected(false);
                body.timePicker.setTime(LocalTime.now());
            }
        });
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

        // Finalize
        add(head, BorderLayout.PAGE_START);
        add(body, BorderLayout.CENTER);
    }
}