using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

using Assignment10.DataAbstractionLayer;

namespace Assignment10.Controllers
{
    public class UpdateController : Controller
    {
        // GET: Update
        public ActionResult Index()
        {
            return View();
        }

        public bool Update()
        {
            string firstname = Request["firstName"];
            string lastname = Request["lastName"];
            string course = Request["course"];
            int grade = int.Parse(Request["newGrade"]);

            DAL dal = new DAL();

            bool result = dal.Update(firstname, lastname, course, grade);
            return result;
        }
    }
}