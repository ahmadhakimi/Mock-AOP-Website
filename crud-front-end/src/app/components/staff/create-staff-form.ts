import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatStepperModule } from '@angular/material/stepper';

import { STEPPER_GLOBAL_OPTIONS } from '@angular/cdk/stepper';
import { CommonModule } from '@angular/common';
import { MatIcon } from '@angular/material/icon';
import { Terminal, Status, Role, Staff } from '../../interfaces/staff';

import { MatListModule } from '@angular/material/list';
// import { StaffService } from '../../services/staff.service';
import {
  MatSnackBar,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';
import { HttpClientModule } from '@angular/common/http';
import { AuthService } from '../../auth.service';

@Component({
  selector: 'create-staff-form',
  templateUrl: 'create-staff-form.html',
  standalone: true,
  imports: [
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    ReactiveFormsModule,
    MatStepperModule,
    CommonModule,
    MatIcon,
    MatListModule,
    HttpClientModule,
  ],

  providers: [
    {
      provide: STEPPER_GLOBAL_OPTIONS,
      useValue: { showError: true },
    },
    AuthService,
  ],
})
export class CreateStaffFormComponent implements OnInit {
  // properties

  firstFormGroup: FormGroup;

  terminals = Object.values(Terminal);
  statuses = Object.values(Status);
  roles = Object.values(Role);

  // snack bar position
  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';

  //   constructor
  constructor(
    public dialogRef: MatDialogRef<CreateStaffFormComponent>,
    private _formBuilder: FormBuilder,
    private authService: AuthService,
    private _snackBar: MatSnackBar
  ) {
    this.firstFormGroup = this._formBuilder.group({
      fullName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      terminal: ['', Validators.required],
      role: ['', Validators.required],
    });
  }

  ngOnInit(): void {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmit(): void {
    if (this.firstFormGroup.valid) {
      const staffData = this.firstFormGroup.value as Staff;
      console.log('Form Submitted', this.firstFormGroup.value);

      // Subscribe to the service route of http://localhost:8080/api/auth/register for staff registration

      this.authService.register(staffData).subscribe({
        next: (response) => {
          console.log('Staff added successfully', response);
          this._snackBar.open('New Staff created!!', 'OK', {
            horizontalPosition: this.horizontalPosition,
            verticalPosition: this.verticalPosition,
            duration: 1000 * 3,
          });
          this.dialogRef.close(true);
        },

        error: (error) => {
          console.log('Error adding staff: ', error);
        },
      });
    } else {
      console.log('The form is invalid!!');
    }
  }

  hide: boolean = true;
}
