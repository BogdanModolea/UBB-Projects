import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-professor-home',
  templateUrl: './professor-home.component.html',
  styleUrls: ['./professor-home.component.css']
})
export class ProfessorHomeComponent implements OnInit{
  private backendUrl = 'http://localhost:3000/mvc/php/controller/controller.php';

  username = '';

  constructor(private http: HttpClient, private route: Router) { }

  ngOnInit(): void {
    this.username = this.route.url.split('/')[2];
    console.log(this.username);
  }

  addGrade(course: string, group: string) {
    if(localStorage.getItem('professor') !== null){
      this.route.navigate(['/professor-add', course, group]);
    }
  }

  updateGrade(course: string, group: string) {
    if(localStorage.getItem('professor') !== null){
      this.route.navigate(['/professor-update', course, group]);
    }
  }

  logout() {
    localStorage.removeItem('professor');
    this.route.navigate(['/']);
  }

}
