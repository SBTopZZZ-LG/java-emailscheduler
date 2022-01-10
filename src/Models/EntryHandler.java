package Models;

import API.Methods;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class EntryHandler {
    public interface Callback {
        void updated();
    }

    static final String PATH = "data.json";

    public static Gson gson = new GsonBuilder().setObjectToNumberStrategy(ToNumberPolicy.BIG_DECIMAL).create();
    public static List<EntryTimer> entries = new ArrayList<>();
    public static List<Callback> callbacks = new ArrayList<>();

    public static void initialize() throws IOException {
        // Check if file exists
        if (Files.exists(Paths.get(PATH))) {
            // Load data from file
            String content = new String(Files.readAllBytes(Paths.get(PATH)));

            List entries = gson.fromJson(content, List.class);
            if (entries != null)
                for (Object entry : entries) {
                    Map<String, Object> entryMap = (Map<String, Object>) entry;

                    EntryHandler.entries.add(new EntryTimer(new Entry(entryMap)));
                }
        } else {
            // Create empty file
            Files.writeString(Paths.get(PATH), "[]");
        }
    }
    public static void registerTimers(String id) {
        for (EntryTimer entryTimer : entries) {
            if ((entryTimer.timer != null && entryTimer.entry.id.equals(id)) || !entryTimer.entry.isPending()) // Already set
                continue;

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getDefault());
            calendar.setTime(new Date());

            // Check dispose timer object
            if (entryTimer.timer != null) {
                entryTimer.timer.cancel();
                entryTimer.timer = null;
            }
            entryTimer.timer = new Timer();
            long diff = entryTimer.entry.getSchedule() - calendar.getTimeInMillis();
            if (diff < 0) {
                // Timer surpassed (mark as Done)

                entryTimer.entry.setPendingStatus(false);
            } else {
                entryTimer.timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // Send email here!

                        Entry entry = entryTimer.entry;
                        Methods.send(entry.getRecipientEmail(), entry.getSubject(), entry.getBody(),
                                new Methods.SendListener() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onFailure(Throwable t) {
                                        System.out.println(t.getMessage());
                                    }
                                });

                        entryTimer.entry.setPendingStatus(false);
                        try {
                            save();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        for (Callback callback : callbacks)
                            callback.updated();

                        entryTimer.timer = null;
                    }
                }, diff);
            }
        }
    }
    public static void save() throws IOException {
        String content = gson.toJson(map());
        Files.writeString(Paths.get(PATH), content);
    }

    public static List<Entry> map() {
        List<Entry> entries = new ArrayList<>();
        for (EntryTimer entryTimer : EntryHandler.entries)
            entries.add(entryTimer.entry);
        return entries;
    }
}
