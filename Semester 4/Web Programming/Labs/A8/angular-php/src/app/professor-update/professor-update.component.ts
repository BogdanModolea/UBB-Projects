import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';


@Component({
  selector: 'app-professor-update',
  templateUrl: './professor-update.component.html',
  styleUrls: ['./professor-update.component.css']
})
export class ProfessorUpdateComponent {
  private backendUrl = 'http://localhost:3000/mvc/php/controller/controller.php';

  group = 0;
  course = '';
  grades: any[] = [];
  students: any[] = [];

  constructor(private http: HttpClient, private route: Router) { }

  ngOnInit(): void {
    this.course = this.route.url.split('/')[2];
    console.log(this.course);
    this.group = Number(this.route.url.split('/')[3]);
    console.log(this.group);
    this.getGradesProfessor();
    this.getAllStudents(this.group);
  }

  getAllStudents(group : any) {
    const result = this.http.get<any>(
      this.backendUrl + 
      '?action=getAllStudents&group' + 
      '&group=' + group
    )
    result.subscribe(data => {
      data.forEach((student: any) => {
        this.students.push([student.id, student.username]);
        console.log(student);
      });
    });
    return result;
  }

  getNameByUsername(username: string) {
    const result = this.http.get<string[]>(
      this.backendUrl +
      '?action=getNameByUsername' +
      '&username=' + username
    )
    result.subscribe(data => {
      // console.log(data);
      return data;
    });
  }

  getGradesProfessor() {
    const result = this.http.get<any[]>(
      this.backendUrl +
      '?action=getGradesProfessor' +
      '&group=' + this.group + 
      '&course=' + this.course
    )
    result.subscribe(data => {
      data.forEach(grade => {
        this.grades.push([grade.studentId, grade.username, grade.grade]);
        console.log(grade);
      }
      );
    });
    // console.log(this.grades);
  }

  updateGrade(studentId: string, grade: string) {

    console.log(studentId);
    console.log(grade);

    this.http.get<any>(
      this.backendUrl +
      '?action=updateGrade' +
      '&studentId=' + studentId +
      '&course=' + this.course +
      '&grade=' + grade
    ).subscribe(data => {
      console.log(data);
      window.location.reload();
    });
  }
}
