import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-professon-login',
  templateUrl: './professon-login.component.html',
  styleUrls: ['./professon-login.component.css']
})
export class ProfessonLoginComponent {
  private backendUrl = 'http://localhost:3000/mvc/php/controller/controller.php';

  constructor(private http: HttpClient, private route: Router) { }

  getProfessor(username: string, password: string) {
    const result = this.http.get<any>(
      this.backendUrl +
      '?action=getProfessor' +
      '&username=' + username +
      '&password=' + password
    )
    result.subscribe(data => {
      if(data.id != -1){
        console.log(data);
        localStorage.setItem('professor', JSON.stringify(data));
        this.route.navigate(['/professor-home', data.username]);
      }
      else{
        alert("Invalid username or password");
      }
    });
  }
}
