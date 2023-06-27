using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

using Assignment10.DataAbstractionLayer;
using Assignment10.Models;

namespace Assignment10.Controllers
{
    public class ProfessorHomeController : Controller
    {
        // GET: ProfessorHome
        public ActionResult Index()
        {
            return View();
        }

        public string GetGrades()
        {
            string group = Request.Params["group"];
            string course = Request.Params["course"];
            DAL dal = new DAL();
            List<Grade> gradeList = dal.GetProfessorGrades(group, course);

            string result = "<table><th>Student</th><th>Grade</th>";

            foreach (Grade grade in gradeList)
            {
                result += "<tr><td>" + grade.studentName + "</td><td>" + grade.grade + "</td></tr>";
            }

            result += "</table>";
            return result;
        }

        public string GetStudents()
        {
            string group = Request.Params["group"];
            int page = int.Parse(Request["pageNr"]);
            int size = int.Parse(Request["pageSize"]);

            DAL dal = new DAL();
            int total = dal.GetAllStudents().Count();
            List<Student> studList = dal.GetStudents(group, page, size, total);

            string result = "<table><th>First Name</th><th>Last Name</th>";

            foreach (Student student in studList)
            {
                result += "<tr><td>" + student.firstName + "</td><td>" + student.lastName + "</td></tr>";
            }

            result += "</table>";
            return result;
        }

    }
}