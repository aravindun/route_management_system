import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        CreateDataBase.createStaticTables();
        JourneyMapper journeyMapper = new JourneyMapper(new MapGenerator(),
                                                        new MapGenerator(),
                                                        new MapGenerator(),
                                                        new CreateDataBase());
        journeyMapper.readCsvFileAndPopulateDB();

        journeyMapper.getJourney("thoraipakam", "taramani", JourneyMapper.KPI.TIME);
    }
}
