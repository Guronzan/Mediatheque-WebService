package fr.guronzan.mediatheque;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.search.TablesDependencyHelper;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.Test;

public class DBUnitTools {
	@Test
	public void backupDB() throws FileNotFoundException, IOException,
			DatabaseUnitException, SQLException {
		// database connection
		try (final Connection jdbcConnection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/mediatheque", "root", "")) {
			final IDatabaseConnection connection = new DatabaseConnection(
					jdbcConnection);

			// full database export
			try (final FileOutputStream os = new FileOutputStream("full.xml")) {
				final IDataSet fullDataSet = connection.createDataSet();
				FlatXmlDataSet.write(fullDataSet, os);
			}

			// dependent tables database export: export table X and all tables
			// that have a PK which is a FK on X, in the right order for
			// insertion
			try (final FileOutputStream os = new FileOutputStream(
					"dependents.xml")) {
				final String[] depTableNames = TablesDependencyHelper
						.getAllDependentTables(connection, "user");
				final IDataSet depDataset = connection
						.createDataSet(depTableNames);
				FlatXmlDataSet.write(depDataset, os);
			}
		}
	}

	@Test
	public void getDTD() throws FileNotFoundException, IOException,
			DatabaseUnitException, SQLException {
		// database connection
		try (final Connection jdbcConnection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/mediatheque", "root", "")) {
			final IDatabaseConnection connection = new DatabaseConnection(
					jdbcConnection);

			try (FileOutputStream fos = new FileOutputStream("test.dtd")) {
				// write DTD file
				FlatDtdDataSet.write(connection.createDataSet(), fos);
			}
		}
	}
}
