import java.sql.*;
import java.util.Scanner;


public class Java3Project2 {


    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement pstmt;


    public static void main(String[] args) {

        try {
            connect();

 /*           stmt.execute("CREATE TABLE tovar (\n" +
                    "                    id     INTEGER PRIMARY KEY,\n" +
                    "                    prodid INTEGER UNIQUE,\n" +
                    "                    title  STRING,\n" +
                    "                    cost   INTEGER\n" +
                    "            );");

*/
        stmt.execute("Delete from tovar");
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement("INSERT INTO tovar ( id, prodid , title,  cost)\n" +
                    "VALUES  (?,?,?,?)");

            for (int i = 0; i < 10000; i++) {
                pstmt.setInt(1, i);
                pstmt.setInt(2, i);
                pstmt.setString(3, "name" + i);
                pstmt.setInt(4, i + 3);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            connection.setAutoCommit(true);


            Scanner sc = new Scanner(System.in);
            String stroka = sc.nextLine();

            String[] mas = stroka.split(" ");



            if (mas[0].equals("цена")) // 3-5 часть
                {
                    ResultSet rs = stmt.executeQuery("select cost from tovar where title = '" + mas[1] + "'");
                    if (!rs.next())
                    {
                        System.out.println("нет совпадений");
                    }
                    else
                    System.out.println(rs.getInt("cost")  );
                }
                if (mas[0].equals("сменитьцену"))
                {
                    int rez = stmt.executeUpdate("update tovar set cost = " + mas[2] + " where title = '" + mas[1] + "'");
                }

                if (mas[0].equals("товарыпоцене"))
                {
                    ResultSet rs = stmt.executeQuery("select * from tovar where cost > " + mas[1] + " and cost < " + mas[2]);

                   if (!rs.next())
                    {
                        System.out.println("нет совпадений");
                    }
                    else
                    {
                        while(rs.next()){
                            System.out.print(rs.getInt(1) + " ");
                            System.out.print(rs.getString(3) + " ");
                            System.out.print(rs.getInt(4) + " ");
                            System.out.println();
                        }
                    }
                }





            sc.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        finally {
            try {
                disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:main.db");
        stmt = connection.createStatement();

    }

    public static void disconnect() throws SQLException {
        connection.close();
    }
}
