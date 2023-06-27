import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { StudentHomeComponent } from './student-home/student-home.component';
import { StudentLoginComponent } from './student-login/student-login.component';
import { ProfessonLoginComponent } from './professon-login/professon-login.component';
import { ProfessorHomeComponent } from './professor-home/professor-home.component';
import { ProfessorAddComponent } from './professor-add/professor-add.component';
import { ProfessorUpdateComponent } from './professor-update/professor-update.component';

const routes: Routes = [
  {path: 'student-home/:username', component: StudentHomeComponent},
  {path: 'student-login', component: StudentLoginComponent},
  {path: 'professor-login', component: ProfessonLoginComponent},
  {path: 'professor-home/:username', component: ProfessorHomeComponent},
  {path: 'professor-add/:course/:group', component: ProfessorAddComponent},
  {path: 'professor-update/:course/:group', component: ProfessorUpdateComponent}
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
