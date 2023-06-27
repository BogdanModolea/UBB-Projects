using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Assignment10.Models
{
    public class Grade
    {
        public int studentId { get; set; }
        public string studentName { get; set; }
        public int courseId { get; set; }
        public string courseName { get; set; }
        public int grade { get; set; }
    }
}