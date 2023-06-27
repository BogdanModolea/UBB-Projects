using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

using Assignment10.Models;
using Assignment10.DataAbstractionLayer;

namespace Assignment10.Controllers
{
    public class StudentHomeController : Controller
    {
        // GET: Main
        public ActionResult Index()
        {
            
            return View("StudentGradesView");

        }

        public string GetStudentGrades()
        {
            string username = Request.Params["username"];
            DAL dal = new DAL();
            List<Grade> gradeList = dal.GetStudentGrades(username);

            string result = "<table><thead><th>Course</th><th>Grade</th></thead>";

            foreach (Grade grade in gradeList)
            {
                result += "<tr><td>" + grade.courseName + "</td><td>" + grade.grade + "</td></tr>";
            }

            result += "</table>";
            return result;
        }
    }
}