import Frames.Dashboard;
import Models.EntryHandler;

public class Main {
    public static void main(String[] args) {
        try {
            EntryHandler.initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        EntryHandler.registerTimers(null);

        Dashboard dashboard = new Dashboard();
        dashboard.display();
    }
}
