package Frames;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EventObject;
import java.util.concurrent.Flow;

import Components.ColumnAutoSizer;
import Components.RoundedBorder;
import Models.Entry;
import Models.EntryHandler;
import Models.EntryTimer;

class DashboardHeadButton extends JPanel {
    // Components
    private ImageIcon icon;
    private JLabel text;

    // Component data
    private DashboardHead parent;
    private Point btnLocation = new Point(0, 0);
    private RoundedBorder border = new RoundedBorder();
    private boolean enabled = true;

    public DashboardHeadButton(DashboardHead parent, String text, String imageLocation) {
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
class DashboardHead extends JPanel {
    // Components
    DashboardHeadButton addButton;
    DashboardHeadButton editButton;
    DashboardHeadButton deleteButton;

    // Component data
    Dashboard parent;
    int headSize = 50;
    Point headLocation = new Point(0, 0);

    public DashboardHead(Dashboard parent) {
        this.parent = parent;

        setSize(parent.getSize().width, headSize);
        setLocation(headLocation.x, headLocation.y);
        setBackground(Color.decode("#e3e3e3"));
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.LEADING);
        setLayout(fl);

        addButton = new DashboardHeadButton(this, "New Entry", "src/Resources/Images/add.png");
        editButton = new DashboardHeadButton(this, "Edit Entry", "src/Resources/Images/edit.png");
        deleteButton = new DashboardHeadButton(this, "Delete Entry", "src/Resources/Images/delete.png");

        addButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Hide current window first
                parent.setVisible(false);

                NewEntry newEntry = new NewEntry(parent);
                newEntry.display();
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
        editButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Check if selected ?
                if (parent.body.jTable.getSelectedRow() == -1) {
                    // Not selected!

                    JOptionPane.showMessageDialog(parent,
                            "Please select an entry from the dataset to edit.",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                } else if (!EntryHandler.entries.get(parent.body.jTable.getSelectedRow()).entry.isPending()) {
                    // Entry cannot be edited as it is already completed

                    JOptionPane.showMessageDialog(parent,
                            "Selected entry is already completed. Please select any pending entry from the dataset to edit.",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Hide current window first
                parent.setVisible(false);

                EditEntry editEntry = new EditEntry(parent, EntryHandler.entries.get(parent.body.jTable.getSelectedRow()).entry.id);
                editEntry.display();
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
        deleteButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Check if selected ?
                if (parent.body.jTable.getSelectedRow() == -1) {
                    // Not selected!

                    JOptionPane.showMessageDialog(parent,
                            "Please select an entry from the dataset to delete.",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                EntryHandler.entries.remove(parent.body.jTable.getSelectedRow());

                // Save and Refresh
                try {
                    EntryHandler.save();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                parent.body.refreshData();
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

        add(addButton);
        add(editButton);
        add(deleteButton);
    }
}

class DashboardBody extends JPanel {
    Dashboard parent;

    // Components
    JTable jTable;

    // Component data
    // - jTable (JTable)
    public final String[] header = {"ID", "Recipient's Email Address", "Subject", "Body", "Scheduled Time", "Status"};

    public DashboardBody(LayoutManager lm, Dashboard parent) {
        super(lm);
        this.parent = parent;

        instantiate();
    }
    public DashboardBody(Dashboard parent) {
        this.parent = parent;

        instantiate();
    }
    public void instantiate() {
        jTable = new JTable(new String[][]{}, header);
        jTable.setFont(jTable.getFont().deriveFont(Font.BOLD, 13));
        jTable.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                ColumnAutoSizer.sizeColumnsToFit(jTable);
            }
        });
        jTable.setCellEditor(null);
        ColumnAutoSizer.sizeColumnsToFit(jTable);

        add(new JScrollPane(jTable));

        EntryHandler.callbacks.add(new EntryHandler.Callback() {
            @Override
            public void updated() {
                refreshData();
            }
        });
    }

    public void refreshData() {
        // Display data from EntryHandler to JTable if filled
        String[][] data;
        if (EntryHandler.entries.size() > 0) {
            data = new String[EntryHandler.entries.size()][6];
            int index = 0;
            for (EntryTimer entryTimer : EntryHandler.entries) {
                data[index][0] = String.valueOf(index + 1);
                data[index][1] = entryTimer.entry.getRecipientEmail();
                data[index][2] = entryTimer.entry.getSubject();
                data[index][3] = entryTimer.entry.getBody();
                data[index][4] = new SimpleDateFormat("dd-MM, hh:mm a").format(new Date(entryTimer.entry.getSchedule()));
                data[index][5] = entryTimer.entry.isPending() ? "Pending" : "Done";

                index++;
            }
        } else {
            data = new String[][]{};
        }

        DefaultTableModel model = new DefaultTableModel(header, 0);
        jTable.setModel(model);
        for (String[] entry : data) {
            model.addRow(entry);
        }
        ColumnAutoSizer.sizeColumnsToFit(jTable);
    }
}

public class Dashboard extends JFrame {
    // Components
    DashboardHead head;
    DashboardBody body;

    // Component data
    // - Frame
    Point frameSize = new Point(700, 600);
    Point startLocation;
    final String startTitle = "Email Scheduler - Dashboard";

    public Dashboard() {
        // Instantiate
        head = new DashboardHead(this);
        body = new DashboardBody(new GridLayout(), this);
        Dimension screenDims = Toolkit.getDefaultToolkit().getScreenSize();
        startLocation = new Point(screenDims.width / 2 - frameSize.x / 2, screenDims.height / 2 - frameSize.y / 2);

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setTitle(startTitle);
        pack();
        setLocationRelativeTo(null); // Sets position to center
        setSize(frameSize.x, frameSize.y);
        head.setSize(frameSize.x, head.getHeight());
        setLocation(startLocation.x, startLocation.y);
        setResizable(false);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                // Save all data before closing

                try {
                    EntryHandler.save();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
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

        // Components

        // Finalize
        add(head, BorderLayout.PAGE_START);
        add(body, BorderLayout.CENTER);
        body.refreshData();
    }

    public void display() {
        setVisible(true);
    }
    public void close() {
        setVisible(false);
    }
}
