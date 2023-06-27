using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

using Assignment10.DataAbstractionLayer;

namespace Assignment10.Controllers
{
    public class ProfessorLoginController : Controller
    {
        // GET: ProfessorLogin
        public ActionResult Index()
        {
            return View();
        }

        public ActionResult Login()
        {
            string username = Request.Params["username"];
            string password = Request.Params["password"];
            DAL dal = new DAL();
            bool result = dal.ProfessorLogin(username, password);
            Console.WriteLine(result);
            return Json(new { success = result });
        }
    }
}
