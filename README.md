# DB_SQLLITE

## SQLLITE USAGE IN MINECRAFT

### SubClass

```java
public class DataBaseManager {
    private Connection connection;
    
    //Connect
    public void connect(File databaseFile) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFile.getAbsolutePath());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //Disconnect
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //CreateTables
    public void createTables() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS players ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL,"
                + "score INTEGER DEFAULT 0"
                + ");";
        try {
            connection.createStatement().executeUpdate(createTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Insert
    public void savePlayer(String name, int score) {
        String insertQuery = "INSERT INTO players (name, score) VALUES (?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, score);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Select
    public int getPlayerScore(String name) {
        String selectQuery = "SELECT score FROM players WHERE name = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("score");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //Update
    public void updatePlayerScore(String name, int newScore) {
        String updateQuery = "UPDATE players SET score = ? WHERE name = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, newScore);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
```
# Main Class
```  
    getConfig().options().copyDefaults();
    File configFile = new File(getDataFolder(), "config.yml");
    if (!configFile.exists()) {
        saveResource("config.yml", false);
    }
    File dataFile = new File(getDataFolder(), "database.db");
    if (!dataFile.exists()) {
        try {
            dataFile.createNewFile();
            getLogger().info("Database Loaded");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    DataBaseManager db = new DataBaseManager();
    db.connect(dataFile);
    db.createTables();
}
```
#POM>XML
```
<dependency>
      <groupId>org.xerial</groupId>
      <artifactId>sqlite-jdbc</artifactId>
      <version>3.45.2.0</version>
  </dependency>
```
