package sqlancer.common;

import java.io.BufferedReader;
import java.io.FileReader;
import sqlancer.SQLGlobalState;
import sqlancer.cnosdb.CnosDBGlobalState;
import sqlancer.cnosdb.query.CnosDBOtherQuery;
import sqlancer.common.query.SQLQueryAdapter;

public abstract class FileExecutor {
    public void runQueryFromFile(String file, SQLGlobalState<?, ?> globalState) {
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

    public void runQueryFromFileCnos(String file, CnosDBGlobalState globalState) {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String cur = br.readLine();
            while (cur != null) {
                globalState.executeStatement(new CnosDBOtherQuery(cur, null));
                cur = br.readLine();
            }
            br.close();
            fr.close();
        } catch (Exception e) {
        }
    }
}
