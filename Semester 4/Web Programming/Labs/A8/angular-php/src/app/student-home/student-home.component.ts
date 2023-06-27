import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-student-home',
  templateUrl: './student-home.component.html',
  styleUrls: ['./student-home.component.css']
})
export class StudentHomeComponent implements OnInit {
  private backendUrl = 'http://localhost:3000/mvc/php/controller/controller.php';
  username = '';
  grades: any[] = [];

  constructor(private http: HttpClient, private route: Router) { }

  ngOnInit(): void {
    this.username = this.route.url.split('/')[2];
    console.log(this.username);
    this.getGrades();
  }

  getGrades() {
    const result = this.http.get<any[]>(
      this.backendUrl +
      '?action=getGradesStudent' +
      '&username=' + this.username
    )
    result.subscribe(data => {
      data.forEach(grade => {
        this.grades.push([grade.courseName, grade.grade]);
      }
      );
    });
    console.log(this.grades);
  }

  isNull(): boolean {
    return localStorage.getItem('student') !== null;
  }

  logout() {
    localStorage.clear();
    this.route.navigate(['/']);
  }
}
