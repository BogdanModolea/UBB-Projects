using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

using Assignment10.DataAbstractionLayer;

namespace Assignment10.Controllers 
{
    public class AddController : Controller
    {
        // GET: Add
        public ActionResult Index()
        {
            return View();
        }

        public bool Add()
        {
            string firstname = Request["firstName"];
            string lastname = Request["lastName"];
            string course = Request["course"];
            int grade = int.Parse(Request["newGrade"]);

            DAL dal = new DAL();

            int studentId = dal.GetStudentIdByName(firstname, lastname);
            int courseId = dal.GetCourseIdByName(course);

            bool result = dal.Add(studentId, courseId, grade);
            return result;
        }
    }
}