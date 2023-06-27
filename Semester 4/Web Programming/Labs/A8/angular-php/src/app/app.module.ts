import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { StudentLoginComponent } from './student-login/student-login.component';
import { StudentHomeComponent } from './student-home/student-home.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ProfessonLoginComponent } from './professon-login/professon-login.component';
import { ProfessorHomeComponent } from './professor-home/professor-home.component';
import { ProfessorAddComponent } from './professor-add/professor-add.component';
import { ProfessorUpdateComponent } from './professor-update/professor-update.component';

@NgModule({
  declarations: [
    AppComponent,
    StudentLoginComponent,
    StudentHomeComponent,
    ProfessonLoginComponent,
    ProfessorHomeComponent,
    ProfessorAddComponent,
    ProfessorUpdateComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    CommonModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
