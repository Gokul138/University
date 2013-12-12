package assignment3Redo;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import ui.CoffeesFrame;


public class UI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final static String Query = "Query";
	private final static String Table = "Table";
	
	public JPanel contentPane;
	private JTable table;
	private JTextField txtB1;
	private JTextField txtB2;
	private JTextField txtB3;
	
	JFrame referenceFrame = this;
	
	boolean rowaddEditable = false;
	
	DBMS dbms = new DBMS();
	Statement statement = null;
	Connection connection = null;
	ResultSet resultSet = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI frame = new UI();
					frame.setVisible(true);
					frame.connectToDB();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	protected void connectToDB() throws Exception {
		dbms.createConnection();
	}


	/**
	 * Create the frame.
	 */
	public UI() {

		//*Database Connections*//

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 595, 406);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnTablechooser = new JMenu("TableChooser");
		menuBar.add(mnTablechooser);
		
		JMenuItem mntmChooseTable = new JMenuItem("Choose Table");
		mntmChooseTable.addActionListener(new handelers());
		mnTablechooser.add(mntmChooseTable);
		
		JMenuItem mntmQuery = new JMenuItem("Query");
		mntmQuery.addActionListener(new handelers());
		mnTablechooser.add(mntmQuery);
		
		JMenu mnRowOperations = new JMenu("Row Operations");
		mnRowOperations.addActionListener(new handelers());
		menuBar.add(mnRowOperations);
		
		JMenuItem mntmDisplay = new JMenuItem("Display");
		mntmDisplay.addActionListener(new handelers());
		mnRowOperations.add(mntmDisplay);
		
		JMenuItem mntmDelete = new JMenuItem("Delete");
		mntmDelete.addActionListener(new handelers());
		mnRowOperations.add(mntmDelete);
		
		JMenuItem mntmAdd = new JMenuItem("Add");
		mntmAdd.addActionListener(new handelers());
		mnRowOperations.add(mntmAdd);
		
		JMenu mnPopulateDatabase = new JMenu("Populate Database");
		menuBar.add(mnPopulateDatabase);
		
		JMenuItem mntmPopulate = new JMenuItem("Populate");
		mntmPopulate.addActionListener(new handelers());
		mnPopulateDatabase.add(mntmPopulate);

		contentPane = new JPanel();
		contentPane.setLayout(new CardLayout());

		
		table = new JTable();
		table.setVisible(true);		

		JPanel card1 = new JPanel();
		card1.setName("Query");
		
		JPanel card2 = new JPanel();
		card2.setName("Table");
		
		card2.add(table);
		
		contentPane.add(card1,Query);
		card1.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(132dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(66dlu;default):grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(20dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(19dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(20dlu;default)"),}));
		
		JLabel lblB1 = new JLabel("Find Projects Employing this supplier");
		card1.add(lblB1, "2, 2, center, center");
		
		txtB1 = new JTextField();
		card1.add(txtB1, "4, 2, center, default");
		txtB1.setColumns(10);
		
		JButton btnGob1 = new JButton("Go");
		btnGob1.addActionListener(new handelers());
		card1.add(btnGob1, "6, 2");
		
		JLabel lblB2 = new JLabel("Find the parts supplied by this supplier");
		card1.add(lblB2, "2, 4, center, center");
		
		txtB2 = new JTextField();
		card1.add(txtB2, "4, 4, fill, default");
		txtB2.setColumns(10);
		
		JButton btnGob2 = new JButton("Go");
		btnGob2.addActionListener(new handelers());
		card1.add(btnGob2, "6, 4");
		
		JLabel lblB3 = new JLabel("Find the total quantity of this part ");
		card1.add(lblB3, "2, 6, right, center");
		
		txtB3 = new JTextField();
		card1.add(txtB3, "4, 6, fill, default");
		txtB3.setColumns(10);
		
		JButton btnGob3 = new JButton("Go");
		btnGob3.addActionListener(new handelers());
		card1.add(btnGob3, "6, 6");
		contentPane.add(card2,Table);
		setContentPane(contentPane);
	}
	


	String chooseTableName = "";
	DatabaseMetaData dbmd;
	ListTableModel model;
	
	class handelers implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getActionCommand().equalsIgnoreCase("Choose Table")){
				chooseTableName = (String)JOptionPane.showInputDialog(
				                    referenceFrame,
				                    "Enter Table Name:\n",
				                    "Enter Table Name",
				                    JOptionPane.PLAIN_MESSAGE);

				if (!((chooseTableName != null) && (chooseTableName.length() > 0))) {
					JOptionPane.showMessageDialog(referenceFrame, 
		                    "Please enter a valid table\n"+
		                    "No value found");
				}
				else{
					// JTable display and shit
					try {
						showTable();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}


				}
			
			else if(e.getActionCommand().equalsIgnoreCase("Query")){
				 CardLayout cl = (CardLayout)(contentPane.getLayout());
			        cl.show(contentPane, "Query");
			        System.out.println("ok");
			}
			else if(e.getActionCommand().equalsIgnoreCase("Populate")){
				try {
					dbms.uploadfile();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			else if(e.getActionCommand().equalsIgnoreCase("Add")){
				try {
					addRow();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if(e.getActionCommand().equalsIgnoreCase("Delete")){
				
			}
		}

		
		private void addRow() throws SQLException {
		String rowUserEntry = "";
		String[] rowUserEntrySplit ;
			if(!chooseTableName.isEmpty()){
				rowUserEntry = (String)JOptionPane.showInputDialog(
	                    referenceFrame,
	                    "Enter Row:\n",
	                    "Enter Row Elements with # as delimiter",
	                    JOptionPane.PLAIN_MESSAGE);
				rowUserEntrySplit = rowUserEntry.split("#");
				ArrayList<String> rowUserEntrySpliti = getTableColumnNames(chooseTableName);
					StringBuilder sb = new StringBuilder("INSERT INTO "+chooseTableName+" (");
					for(int i = 0;i<rowUserEntrySpliti.size();i++){
						sb.append(rowUserEntrySpliti.get(i));
						if(i!=rowUserEntrySpliti.size()-1){
							sb.append(",");
							}
						else{
							sb.append(") VALUES ");
						}
					}
					if(appendValues(sb,rowUserEntrySplit)){
						model.addRow(rowUserEntrySplit);
					}
					else{
						JOptionPane.showMessageDialog(referenceFrame, "Input is not right, try again");
					}

			}
			else{
				JOptionPane.showMessageDialog(referenceFrame, "Please choose a tbale first");
			}
		}


		private ArrayList<String> getTableColumnNames(String chooseTableName) throws SQLException {
			ArrayList<String> retTableColu = new ArrayList<String>();
			String sql = "select * from "+chooseTableName;
            resultSet = dbms.statement.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
			int rowCount = metaData.getColumnCount();
			for (int i = 0; i < rowCount; i++) {
                retTableColu.add(metaData.getColumnName(i + 1));
//                System.out.println(metaData.getColumnTypeName(i + 1));
            }
			return retTableColu;
		}


		private boolean appendValues(StringBuilder sb,
				String[] rowUserEntrySplit)  {
		boolean returnAvalue = true;
		for(int i = 0; i< rowUserEntrySplit.length;i++){
			if(i!=rowUserEntrySplit.length-1){
				if(i == 0){
					sb.append("(");
				}
				sb.append("'"+rowUserEntrySplit[i]+"',");
			}
			else{
				sb.append("'"+rowUserEntrySplit[i]+"')");
				}
		}
		System.out.println(sb.toString());
		try {
			dbms.statement.execute(sb.toString());
			returnAvalue = true;
		} catch (Exception e) {
			returnAvalue = false;
		}
		return returnAvalue;
		}
	}

	public void showTable() throws SQLException{
		if(isTableExist(chooseTableName)==true){
			// show the table here
			CardLayout cl = (CardLayout)(contentPane.getLayout());
			cl.show(contentPane, "Table");
			resultSet = dbms.statement.executeQuery("Select * From "+chooseTableName);
			model = ListTableModel.createModelFromResultSet( resultSet );
			table.setModel(model);
		}
		else{
			JOptionPane.showMessageDialog(referenceFrame, 
                    "Please enter a valid table\n"+
                    "Table doesn't exist");
		}
	}


	private boolean isTableExist(String tableName) {
		boolean returnVal = false;
		try{
			connection = dbms.connection;
			dbmd = connection.getMetaData();
			String[] types = {"TABLE"};
			ResultSet rs = dbmd.getTables(null, null, "%", types);
			while (rs.next()) {
				if(rs.getString("TABLE_NAME").equalsIgnoreCase(tableName)){
					returnVal = true;
				}
			}
		} 
		catch (SQLException e) {	
			e.printStackTrace();
		}
		catch(NullPointerException e){
			JOptionPane.showMessageDialog(referenceFrame, 
                    "No Populated Table found\n"+
                    "Please Populate the table first");
		}
		// get meta data and check
		return returnVal;
	}

}
