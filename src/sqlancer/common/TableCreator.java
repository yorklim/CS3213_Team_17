package sqlancer.common;

import java.io.BufferedReader;
import java.io.FileReader;

import sqlancer.GlobalState;
import sqlancer.SQLancerDBConnection;
import sqlancer.common.query.Query;

public abstract class TableCreator {

    public abstract void create() throws Exception;

    public void runQueryFromFile(String file, GlobalState<?,?,SQLancerDBConnection> globalState, Class<? extends Query<SQLancerDBConnection>> queryType) {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String cur = br.readLine();
            while (cur != null) {
                globalState.executeStatement(queryType.getDeclaredConstructor(String.class).newInstance(cur));
                cur = br.readLine();
            }
            br.close();
            fr.close();
        } catch (Exception e) {
        }
    }
}
