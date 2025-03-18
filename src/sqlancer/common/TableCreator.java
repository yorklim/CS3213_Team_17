package sqlancer.common;

import java.io.BufferedReader;
import java.io.FileReader;

import sqlancer.GlobalState;
import sqlancer.common.query.SQLQueryAdapter;

public abstract class TableCreator {

    public abstract void create() throws Exception;

    public void runQueryFromFile(String file, GlobalState globalState) {
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
        } catch (Exception e) {
        }
    }
}
