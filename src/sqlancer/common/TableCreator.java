package sqlancer.common;

import sqlancer.SQLGlobalState;
import sqlancer.common.query.SQLQueryAdapter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public abstract class TableCreator {

    public abstract void create() throws Exception;

    public static void runQueryFromFile(String file, SQLGlobalState<?,?> globalState) {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String cur = br.readLine();
            while (cur != null) {
                globalState.executeStatement(new SQLQueryAdapter(cur));
                cur = br.readLine();
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        } catch (Exception e) {

        }
    }
}
