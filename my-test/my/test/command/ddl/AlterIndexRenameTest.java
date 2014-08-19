package my.test.command.ddl;

import my.test.TestBase;

public class AlterIndexRenameTest extends TestBase {
	public static void main(String[] args) throws Exception {
		// method start() run a lot of initialation and run the method overrided by this test
		System.out.println("hello");
		
		new AlterIndexRenameTest().start();
		
		System.out.println("second");
	}

	@Override
	public void startInternal() throws Exception {
		// it use jdbc
		stmt.executeUpdate("DROP TABLE IF EXISTS AlterIndexRenameTest");
		stmt.executeUpdate("CREATE TABLE IF NOT EXISTS AlterIndexRenameTest (f1 int)");
		System.out.println("stmt.executeUpdate");
		stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx0 ON AlterIndexRenameTest(f1)");

		//stmt.executeUpdate("ALTER INDEX idx0 RENAME TO idx1");

		//stmt.executeUpdate("CREATE SCHEMA IF NOT EXISTS schema0 AUTHORIZATION sa");
		//stmt.executeUpdate("ALTER INDEX mydb.public.idx0 RENAME TO schema0.idx1");

//		stmt.executeUpdate("ALTER INDEX mydb.public.idx0 RENAME TO idx1");
		System.out.println("done by startInternal()");
		
		

	}

}
