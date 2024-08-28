import { Component, Inject } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { Terminal, Status, Role, Staff } from '../../../interfaces/staff';
import {
  ReactiveFormsModule,
  FormBuilder,
  FormControl,
  Validators,
  FormGroup,
} from '@angular/forms';
import { Dialog } from '@angular/cdk/dialog';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NgFor, NgIf } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import {
  MatSnackBar,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';

@Component({
  selector: 'app-edit-staff',
  standalone: true,
  imports: [
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    ReactiveFormsModule,
    NgFor,
    NgIf,
    MatButtonModule,
  ],
  templateUrl: './edit-staff.component.html',
  styleUrl: './edit-staff.component.scss',
})
export class EditStaffComponent {
  editForm: FormGroup;

  terminals = Object.values(Terminal);
  statuses = Object.values(Status);
  roles = Object.values(Role);

  // snack bar position
  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';

  constructor(
    private formBuilder: FormBuilder,
    private _snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<EditStaffComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Staff
  ) {
    this.editForm = this.formBuilder.group({
      fullName: [data.fullName],
      email: [data.email],
      terminal: [data.terminal],
      role: [data.role],
      status: [data.status],
      createdBy: [data.createdBy],
      updatedBy: [data.updatedBy],
    });
  }

  onNoClick(): void {
    this._snackBar.open('Cancel Update', 'OK', {
      duration: 1000 * 3,
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
    });
    this.dialogRef.close();
    console.log('Update Staff Operation Cancelled.');
  }

  onSaveClick(): void {
    if (this.editForm.valid) {
      console.log('Form Data: ', this.editForm.value);
      this._snackBar.open('Staff successfully edited', 'OK', {
        duration: 1000 * 3,
        horizontalPosition: this.horizontalPosition,
        verticalPosition: this.verticalPosition,
      });
      this.dialogRef.close(this.editForm.value);
    } else {
      console.log('Form invalid');
    }
  }
}
