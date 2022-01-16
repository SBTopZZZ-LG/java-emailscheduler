package Frames;

import Components.ColumnAutoSizer;
import Components.HeadButton;
import Components.SmartJFrame;
import Models.EntryHandler;
import Models.EntryTimer;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

class DashboardHead extends JPanel {
    // Components
    private HeadButton addButton;
    private HeadButton editButton;
    private HeadButton deleteButton;
    private HeadButton sourceButton;
    private HeadButton aboutButton;

    // Component data
    private Dashboard parent;
    private int headSize = 50;
    private Point headLocation = new Point(0, 0);
    public interface InteractionListener {
        void onAdd(DashboardHead head);
        void onEdit(DashboardHead head);
        void onDelete(DashboardHead head);
        void onSource(DashboardHead head);
        void onAbout(DashboardHead head);
    }

    public DashboardHead(Dashboard parent, InteractionListener listener) {
        this.parent = parent;

        // Set properties
        setSize(parent.getSize().width, headSize);
        setLocation(headLocation.x, headLocation.y);
        setBackground(Color.decode("#e3e3e3"));
        setLayout(new MigLayout("insets 5 10 5 10, fillx"));

        // Setup listener
        addButton = new HeadButton(this, "New Entry", "src/Resources/Images/add.png",
                new HeadButton.InteractionListener() {
                    @Override
                    public void onClick() {
                        listener.onAdd(DashboardHead.this);
                    }
                });
        editButton = new HeadButton(this, "Edit Entry", "src/Resources/Images/edit.png",
                new HeadButton.InteractionListener() {
                    @Override
                    public void onClick() {
                        listener.onEdit(DashboardHead.this);
                    }
                });
        deleteButton = new HeadButton(this, "Delete Entry", "src/Resources/Images/delete.png",
                new HeadButton.InteractionListener() {
                    @Override
                    public void onClick() {
                        listener.onDelete(DashboardHead.this);
                    }
                });
        sourceButton = new HeadButton(this, "Source Code", "src/Resources/Images/github.png",
                new HeadButton.InteractionListener() {
                    @Override
                    public void onClick() {
                        listener.onSource(DashboardHead.this);
                    }
                });
        aboutButton = new HeadButton(this, "About Us", "src/Resources/Images/about.png",
                new HeadButton.InteractionListener() {
                    @Override
                    public void onClick() {
                        listener.onAbout(DashboardHead.this);
                    }
                });

        // Finalize
        add(addButton, "width 80%");
        add(editButton, "width 80%");
        add(deleteButton, "width 80%");
        add(sourceButton, "width 80%");
        add(aboutButton, "width 80%");
    }
}

class DashboardBody extends JPanel {
    // Components
    JTable jTable;

    // Component data
    private Dashboard parent;
    // - jTable (JTable)
    public final String[] header = {"ID", "Recipient's Email Address", "Subject", "Body", "Scheduled Time", "Status"};

    public DashboardBody(Dashboard parent) {
        super(new GridLayout());
        this.parent = parent;

        instantiate();
    }
    public void instantiate() {
        jTable = new JTable(new String[][]{}, header);
        jTable.getTableHeader().setFont(jTable.getFont().deriveFont(Font.PLAIN, 13));
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
                // Display updated data into jTable

                refreshDataset();
            }
        });

        refreshDataset();
    }

    private void refreshDataset() {
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

public class Dashboard extends SmartJFrame {
    // Components
    private DashboardHead head;
    private DashboardBody body;

    // Component data
    private Dimension frameSize = new Dimension(700, 600);
    private Point startLocation;
    private final String startTitle = "Email Scheduler - Dashboard";

    public Dashboard() {
        super(null);

        // Instantiate
        head = new DashboardHead(this, new DashboardHead.InteractionListener() {
            @Override
            public void onAdd(DashboardHead head) {
                NewEntry newEntry = new NewEntry(Dashboard.this);
                pushNext(newEntry, true);
            }

            @Override
            public void onEdit(DashboardHead head) {
                // Check if selected ?
                if (body.jTable.getSelectedRow() == -1) {
                    // Not selected!

                    JOptionPane.showMessageDialog(Dashboard.this,
                            "Please select an entry from the dataset to edit.",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                } else if (!EntryHandler.entries.get(body.jTable.getSelectedRow()).entry.isPending()) {
                    // Entry cannot be edited as it is already completed

                    JOptionPane.showMessageDialog(Dashboard.this,
                            "Selected entry is already completed. Please select any pending entry from the dataset to edit.",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                EditEntry editEntry = new EditEntry(Dashboard.this, EntryHandler.entries.get(body.jTable.getSelectedRow()).entry.id);
                pushNext(editEntry, true);
            }

            @Override
            public void onDelete(DashboardHead head) {
                // Check if selected ?
                if (body.jTable.getSelectedRow() == -1) {
                    // Not selected!

                    JOptionPane.showMessageDialog(Dashboard.this,
                            "Please select an entry from the dataset to delete.",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                EntryTimer entryTimer = EntryHandler.entries.get(body.jTable.getSelectedRow());
                // Check if timer is enabled, if yes then cancel first
                if (entryTimer.timer != null) {
                    entryTimer.timer.cancel();
                    entryTimer.timer = null;
                }
                EntryHandler.entries.remove(entryTimer);

                // Save and Refresh
                try {
                    EntryHandler.save();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                EntryHandler.notifyChange();
            }

            @Override
            public void onSource(DashboardHead head) {
                try {
                    String url = "https://github.com/SBTopZZZ-LG/java-emailscheduler.git";
                    java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
                } catch (java.io.IOException e) {
                    System.out.println(e.getMessage());
                }
            }

            @Override
            public void onAbout(DashboardHead head) {
                AboutUs aboutUs = new AboutUs(Dashboard.this);
                pushNext(aboutUs, false);
            }
        });
        body = new DashboardBody(this);
        Dimension screenDims = Toolkit.getDefaultToolkit().getScreenSize();
        startLocation = new Point(screenDims.width / 2 - frameSize.width / 2, screenDims.height / 2 - frameSize.width / 2);

        // Set frame properties
        setLayout(new BorderLayout());
        setTitle(startTitle);
        pack();
        setLocationRelativeTo(null); // Sets position to center
        setSize(frameSize);
        head.setSize(frameSize.width, head.getHeight());
        setLocation(startLocation.x, startLocation.y);

        // Finalize
        add(head, BorderLayout.PAGE_START);
        add(body, BorderLayout.CENTER);
    }

    @Override
    public boolean onClose(WindowEvent windowEvent) {
        // Save all data before disposing

        try {
            EntryHandler.save();
        } catch (Exception e2) {
            e2.printStackTrace();
        }

        return true;
    }
}
