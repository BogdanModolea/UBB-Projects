using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Lab01
{
    public partial class Form1 : Form
    {
        private readonly string TEAMS_TABLE = "Teams";
        private readonly string PLAYERS_TABLE = "Players";
        private readonly string FK_TEAMS_PLAYERS = "FK__Players__teamID__3B75D760";

        private DataSet dataSet = new DataSet();
        private SqlConnection dbConnection;

        private SqlDataAdapter dataAdapterTeams, dataAdapterPlayers;
        private BindingSource bindingTeams = new BindingSource();
        private BindingSource bindingPlayers = new BindingSource();

        private void InitializeDatabase()
        {
            dbConnection = new SqlConnection("Data Source=DESKTOP-TH52825;Initial Catalog=esportLeague;Integrated Security=True");

            dataAdapterTeams = new SqlDataAdapter($"SELECT * FROM {TEAMS_TABLE}", dbConnection);
            dataAdapterPlayers = new SqlDataAdapter($"SELECT * FROM {PLAYERS_TABLE}", dbConnection);

            new SqlCommandBuilder(dataAdapterPlayers);

            dataAdapterTeams.Fill(dataSet, TEAMS_TABLE);
            dataAdapterPlayers.Fill(dataSet, PLAYERS_TABLE);

            var dataRelation = new DataRelation(
                FK_TEAMS_PLAYERS,
                dataSet.Tables[TEAMS_TABLE].Columns["teamID"],
                dataSet.Tables[PLAYERS_TABLE].Columns["teamID"]
            );
            dataSet.Relations.Add(dataRelation);
        }

        private void InitializeUI()
        {
            bindingTeams.DataSource = dataSet;
            bindingTeams.DataMember = TEAMS_TABLE;

            bindingPlayers.DataSource = bindingTeams;
            bindingPlayers.DataMember = FK_TEAMS_PLAYERS;

            dataGridView1.DataSource = bindingTeams;
            dataGridView2.DataSource = bindingPlayers;
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

        private void button1_Click(object sender, EventArgs e)
        {
            SqlCommandBuilder builder = new SqlCommandBuilder(dataAdapterTeams);
            builder.GetInsertCommand();
            dataAdapterPlayers.Update(dataSet, PLAYERS_TABLE);
            dataAdapterTeams.Update(dataSet, TEAMS_TABLE);
        }
    }
}
