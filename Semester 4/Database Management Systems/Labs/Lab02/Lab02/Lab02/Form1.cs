using System;
using System.Data;
using System.Windows.Forms;
using System.Data.SqlClient;
using System.Configuration;


namespace Lab02
{
    public partial class Form1 : Form
    {
        // ChessboardCompany = Teams; Chessboard = Players
        private DataSet dataSet = new DataSet();
        private SqlConnection dbConnection;

        private SqlDataAdapter dataAdapterTeams, dataAdapterPlayers;
        private readonly BindingSource bindingTeams = new BindingSource();
        private readonly BindingSource bindingPlayers = new BindingSource();

        private void InitializeDatabase()
        {
            String connectionString = ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString;
            String database = ConfigurationManager.AppSettings["Database"];
            dbConnection = new SqlConnection(String.Format(connectionString, database));
            dataAdapterTeams = new SqlDataAdapter(ConfigurationManager.AppSettings["SelectParent"], dbConnection);
            dataAdapterPlayers = new SqlDataAdapter(ConfigurationManager.AppSettings["SelectChild"], dbConnection);

            new SqlCommandBuilder(dataAdapterPlayers);
            new SqlCommandBuilder(dataAdapterTeams).GetInsertCommand();

            dataAdapterTeams.Fill(dataSet, ConfigurationManager.AppSettings["ParentTableName"]);
            dataAdapterPlayers.Fill(dataSet, ConfigurationManager.AppSettings["ChildTableName"]);

            var dataRelation = new DataRelation(
                ConfigurationManager.AppSettings["ForeignKey"],
                dataSet.Tables[ConfigurationManager.AppSettings["ParentTableName"]].Columns[ConfigurationManager.AppSettings["ParentReferencedKey"]],
                dataSet.Tables[ConfigurationManager.AppSettings["ChildTableName"]].Columns[ConfigurationManager.AppSettings["ChildForeignKey"]]);
            dataSet.Relations.Add(dataRelation);
        }

        private void InitializeUI()
        {
            bindingTeams.DataSource = dataSet;
            bindingTeams.DataMember = ConfigurationManager.AppSettings["ParentTableName"];

            bindingPlayers.DataSource = bindingTeams;
            bindingPlayers.DataMember = ConfigurationManager.AppSettings["ForeignKey"];

            dataGridView1.DataSource = bindingTeams;
            dataGridView2.DataSource = bindingPlayers;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            // Refresh DB
            dataSet.Tables[ConfigurationManager.AppSettings["ChildTableName"]].Clear();
            dataSet.Tables[ConfigurationManager.AppSettings["ParentTableName"]].Clear();
            dataAdapterTeams.Fill(dataSet, ConfigurationManager.AppSettings["ParentTableName"]);
            dataAdapterPlayers.Fill(dataSet, ConfigurationManager.AppSettings["ChildTableName"]);
        }

        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            InitializeDatabase();
            InitializeUI();
        }
    }
}
