import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatePassFormComponent } from './create-pass-form.component';

describe('CreatePassFormComponent', () => {
  let component: CreatePassFormComponent;
  let fixture: ComponentFixture<CreatePassFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreatePassFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CreatePassFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
