using System;
using System.Data.SqlClient;
using System.Collections.Generic;
using System.Linq;
using System.Web;

using Assignment10.Models;
using MySql.Data.MySqlClient;

namespace Assignment10.DataAbstractionLayer
{
    public class DAL
    {
        public List<Grade> GetStudentGrades(string username)
        {
            MySqlConnection conn;
            string myConnectionString;

            myConnectionString = "Server=localhost;Port=3306;Database=students;Uid=root;Pwd=;";
            List<Grade> gradeList = new List<Grade>();

            try
            {
                conn = new MySqlConnection();
                conn.ConnectionString = myConnectionString;
                conn.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;
                cmd.CommandText = "SELECT * FROM grades INNER JOIN student ON grades.studentId = student.id INNER JOIN course ON grades.courseId = course.id WHERE student.username = @Username";

                cmd.Parameters.AddWithValue("@Username", username);
                
                MySqlDataReader myreader = cmd.ExecuteReader();

                while (myreader.Read())
                {
                    Grade grade = new Grade();
                    //grade.courseId = myreader.GetInt16("courseId");
                    grade.courseName = myreader.GetString("name");
                    grade.grade = myreader.GetInt16("grade");
                    gradeList.Add(grade);
                }
                myreader.Close();
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.Message);
            }
            return gradeList;
        }

        public List<Grade> GetProfessorGrades(string group, string course)
        {
            MySqlConnection conn;
            string myConnectionString;

            myConnectionString = "Server=localhost;Port=3306;Database=students;Uid=root;Pwd=;";
            List<Grade> gradeList = new List<Grade>();

            try
            {
                conn = new MySqlConnection();
                conn.ConnectionString = myConnectionString;
                conn.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;
                cmd.CommandText = "SELECT * FROM grades INNER JOIN student ON grades.studentId = student.id INNER JOIN course ON grades.courseId = course.id WHERE student.studentGroup = @Group AND name = @Course";

                cmd.Parameters.AddWithValue("@Group", group);
                cmd.Parameters.AddWithValue("@Course", course);

                MySqlDataReader myreader = cmd.ExecuteReader();

                while (myreader.Read())
                {
                    Grade grade = new Grade();
                    //grade.courseId = myreader.GetInt16("courseId");
                    grade.studentName = myreader.GetString("firstName") + " "+ myreader.GetString("lastName");
                    grade.grade = myreader.GetInt16("grade");
                    gradeList.Add(grade);
                }
                myreader.Close();
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.Message);
            }
            return gradeList;
        }

        public bool StudentLogin(string username, string password)
        {
            MySqlConnection conn;
            string myConnectionString;

            myConnectionString = "Server=localhost;Port=3306;Database=students;Uid=root;Pwd=;";
            List<Student> studList = new List<Student>();

            try
            {
                conn = new MySqlConnection();
                conn.ConnectionString = myConnectionString;
                conn.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;
                cmd.CommandText = "SELECT * FROM student WHERE username = @Username AND password = @Password";

                cmd.Parameters.AddWithValue("@Username", username);
                cmd.Parameters.AddWithValue("@Password", password);

                MySqlDataReader myreader = cmd.ExecuteReader();

                while (myreader.Read())
                {
                    Student stud = new Student();
                    stud.username = myreader.GetString("username");
                    studList.Add(stud);
                }
                myreader.Close();
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.Message);
            }
            return studList.Count == 1;
        }

        public bool ProfessorLogin(string username, string password)
        {
            MySqlConnection conn;
            string myConnectionString;

            myConnectionString = "Server=localhost;Port=3306;Database=students;Uid=root;Pwd=;";
            List<Student> studList = new List<Student>();

            try
            {
                conn = new MySqlConnection();
                conn.ConnectionString = myConnectionString;
                conn.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;
                cmd.CommandText = "SELECT * FROM professor WHERE username = @Username AND password = @Password";

                cmd.Parameters.AddWithValue("@Username", username);
                cmd.Parameters.AddWithValue("@Password", password);
                
                MySqlDataReader myreader = cmd.ExecuteReader();

                while (myreader.Read())
                {
                    Student stud = new Student();
                    stud.username = myreader.GetString("username");
                    studList.Add(stud);
                }
                myreader.Close();
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.Message);
            }
            return studList.Count == 1;
        }

        public bool Update(string firstName, string lastName, string course, int grade)
        {
            MySqlConnection conn;
            string myConnectionString;

            myConnectionString = "Server=localhost;Port=3306;Database=students;Uid=root;Pwd=;";
            try
            {
                conn = new MySqlConnection();
                conn.ConnectionString = myConnectionString;
                conn.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;
                //UPDATE grades INNER JOIN student ON grades.studentId = student.id SET grade=10 WHERE student.firstname="name"
                cmd.CommandText = "UPDATE grades INNER JOIN student ON grades.studentId = student.id " +
                    "INNER JOIN course ON grades.courseId = course.id SET grade = @Grade" + 
                    " WHERE name = @Course AND firstName = @FirstName AND lastName = @LastName";

                cmd.Parameters.AddWithValue("@Grade", grade);
                cmd.Parameters.AddWithValue("@Course", course);
                cmd.Parameters.AddWithValue("@FirstName", firstName);
                cmd.Parameters.AddWithValue("@LastName", lastName);

                int command = cmd.ExecuteNonQuery();
                conn.Close();
                return command == 1;
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.Message);
            }
            return false;
        }

        public bool Add(int studentId, int courseId, int grade)
        {
            MySqlConnection conn;
            string myConnectionString;

            myConnectionString = "Server=localhost;Port=3306;Database=students;Uid=root;Pwd=;";
            try
            {
                conn = new MySqlConnection();
                conn.ConnectionString = myConnectionString;
                conn.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;
                cmd.CommandText = "INSERT INTO grades(studentId, courseId, grade) VALUES (@StudentId, @CourseId, @Grade)";

                cmd.Parameters.AddWithValue("@StudentId", studentId);
                cmd.Parameters.AddWithValue("@CourseId", courseId);
                cmd.Parameters.AddWithValue("@Grade", grade);

                int command = cmd.ExecuteNonQuery();
                conn.Close();
                return command == 1;
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.Message);
            }
            return false;
        }

        public int GetStudentIdByName(string firstName, string lastName)
        {
            MySqlConnection conn;
            string myConnectionString;

            myConnectionString = "Server=localhost;Port=3306;Database=students;Uid=root;Pwd=;";
            int id = 0;

            try
            {
                conn = new MySqlConnection();
                conn.ConnectionString = myConnectionString;
                conn.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;
                cmd.CommandText = "SELECT * FROM student WHERE firstName = '" + firstName + "' AND lastName = '" + lastName + "'";
                MySqlDataReader myreader = cmd.ExecuteReader();

                while (myreader.Read())
                {
                    id = myreader.GetInt16("id");
                }
                myreader.Close();
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.Message);
            }
            return id;
        }

        public int GetCourseIdByName(string name)
        {
            MySqlConnection conn;
            string myConnectionString;

            myConnectionString = "Server=localhost;Port=3306;Database=students;Uid=root;Pwd=;";
            int id = 0;

            try
            {
                conn = new MySqlConnection();
                conn.ConnectionString = myConnectionString;
                conn.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;
                cmd.CommandText = "SELECT * FROM course WHERE name = '" + name + "'";
                MySqlDataReader myreader = cmd.ExecuteReader();

                while (myreader.Read())
                {
                    id = myreader.GetInt16("id");
                }
                myreader.Close();
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.Message);
            }
            return id;
        }

        public List<Student> GetStudents (string group, int page, int size, int total)
        {
            MySqlConnection conn;
            string myConnectionString;

            myConnectionString = "Server=localhost;Port=3306;Database=students;Uid=root;Pwd=;";
            List<Student> studList = new List<Student>();
            int offset = page * size;
            int totalPages = (int)Math.Ceiling((double)total / size);

            try
            {
                conn = new MySqlConnection();
                conn.ConnectionString = myConnectionString;
                conn.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;
                cmd.CommandText = "SELECT * FROM student WHERE studentGroup = '" + group + "' LIMIT " + offset + ", " + size;
                MySqlDataReader myreader = cmd.ExecuteReader();

                while (myreader.Read())
                {
                    Student student = new Student();
                    //grade.courseId = myreader.GetInt16("courseId");
                    student.firstName = myreader.GetString("firstName");
                    student.lastName = myreader.GetString("lastName");
                    studList.Add(student);
                }
                myreader.Close();
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.Message);
            }
            return studList;
        }
    

        public List<Student> GetAllStudents ()
        {
            MySqlConnection conn;
            string myConnectionString;

            myConnectionString = "Server=localhost;Port=3306;Database=students;Uid=root;Pwd=;";
            List<Student> studList = new List<Student>();

            try
            {
                conn = new MySqlConnection();
                conn.ConnectionString = myConnectionString;
                conn.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;
                cmd.CommandText = "SELECT * FROM student";
                MySqlDataReader myreader = cmd.ExecuteReader();

                while (myreader.Read())
                {
                    Student student = new Student();
                    //grade.courseId = myreader.GetInt16("courseId");
                    student.firstName = myreader.GetString("firstName");
                    student.lastName = myreader.GetString("lastName");
                    studList.Add(student);
                }
                myreader.Close();
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.Message);
            }
            return studList;
        }

    }
}