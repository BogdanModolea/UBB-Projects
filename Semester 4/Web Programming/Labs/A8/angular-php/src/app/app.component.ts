import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angular-php';

  isProfessorNull(): boolean {
    return localStorage.getItem('professor') === null && localStorage.getItem("student") === null;
  }
}
