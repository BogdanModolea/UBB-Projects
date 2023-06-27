import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfessonLoginComponent } from './professon-login.component';

describe('ProfessonLoginComponent', () => {
  let component: ProfessonLoginComponent;
  let fixture: ComponentFixture<ProfessonLoginComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProfessonLoginComponent]
    });
    fixture = TestBed.createComponent(ProfessonLoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
