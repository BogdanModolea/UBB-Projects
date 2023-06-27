import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-student-login',
  templateUrl: './student-login.component.html',
  styleUrls: ['./student-login.component.css']
})
export class StudentLoginComponent {
  private backendUrl = 'http://localhost:3000/mvc/php/controller/controller.php';

  constructor(private http: HttpClient, private route: Router) { }
    
  getAllStudents(group : any) {
    const result = this.http.get<any>(
      this.backendUrl + 
      '?action=getAllStudents&group' + 
      '&group=' + group
    )
    console.log(result.subscribe(data => {
      console.log(data);
    }));
    return result;
  }

  getStudent(username: string, password: string) {
    const result = this.http.get<any>(
      this.backendUrl +
      '?action=getStudent' +
      '&username=' + username +
      '&password=' + password
    )
    result.subscribe(data => {
      if(data.id != -1){
        localStorage.setItem('student', JSON.stringify(data));
        this.route.navigate(['/student-home', data.username]);
      }
      else{
        alert("Invalid username or password");
      }
    });
  }
}
