import { Component, Inject } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import {
  FormBuilder,
  Validators,
  FormsModule,
  ReactiveFormsModule,
  FormGroup,
} from '@angular/forms';
import { STEPPER_GLOBAL_OPTIONS } from '@angular/cdk/stepper';

import { MatButtonModule } from '@angular/material/button';
import { MatStepperModule } from '@angular/material/stepper';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MAT_DATE_LOCALE, MAT_DATE_FORMATS } from '@angular/material/core';
import { provideMomentDateAdapter } from '@angular/material-moment-adapter';
import { MatIcon, MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { CompanyStatus } from '../../../interfaces/company';
import { CompanyService } from '../../../services/company.service';
import { NgFor, NgIf } from '@angular/common';
import {
  MatFormFieldControl,
  MatFormFieldModule,
} from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatDividerModule } from '@angular/material/divider';
import { MatExpansionModule } from '@angular/material/expansion';
import moment from 'moment';
import { MatDialogRef } from '@angular/material/dialog';
import {
  MatSnackBar,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';

export const MY_DATE_FORMATS = {
  parse: {
    dateInput: 'DD/MM/YYYY',
  },
  display: {
    dateInput: 'DD/MM/YYYY',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'DD/MM/YYYY',
    monthYearA11yLabel: 'MMMM YYYY',
  },
};

@Component({
  selector: 'app-create-company',
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    MatStepperModule,
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatDatepickerModule,
    MatIconModule,
    MatSelectModule,
    MatListModule,
    MatDividerModule,
    MatExpansionModule,
    NgFor,
    MatIcon,
    NgIf,
  ],
  providers: [
    {
      provide: MAT_DATE_LOCALE,
      useValue: 'en-GB',
    },
    {
      provide: STEPPER_GLOBAL_OPTIONS,
      useValue: { showError: true },
    },
    provideMomentDateAdapter(),
    {
      provide: MAT_DATE_FORMATS,
      useValue: MY_DATE_FORMATS,
    },
  ],
  templateUrl: './create-company.component.html',
  styleUrl: './create-company.component.scss',
})
export class CreateCompanyComponent {
  // create form group name createForm
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;
  attachmentError: string | null = null;

  // get enum of company status
  companyStatuses = Object.values(CompanyStatus);

  // attachment
  selectedFile: File | null = null;

  // snack bar position
  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';

  constructor(
    private formBuilder: FormBuilder,
    private companyService: CompanyService,
    private _snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<CreateCompanyComponent>,
    @Inject(MAT_DATE_LOCALE) private _locale: string
  ) {
    this.firstFormGroup = this.formBuilder.group({
      staffId: [{ value: '', disabled: true }, Validators.required],
      companyName: ['', Validators.required],
      rocExpDate: ['', Validators.required],
      telNo: ['', [Validators.required, Validators.pattern(/^\d{9,10}$/)]],
      faxNo: [
        '421015353',
        [Validators.required, Validators.pattern(/^\d{9,10}$/)],
      ],
      companyEmail: ['', [Validators.required, Validators.email]],
      companyStatus: ['ACTIVE', Validators.required],
      createdBy: ['test', Validators.required],
      updatedBy: ['test', Validators.required],
    });

    this.secondFormGroup = this.formBuilder.group({
      address: ['test', [Validators.required]],
      remarks: ['test'],
    });

    this.thirdFormGroup = this.formBuilder.group({
      attachment: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    const currentUser = sessionStorage.getItem('currentUser');
    if (currentUser) {
      const user = JSON.parse(currentUser);
      const staffId = user.staffId; // Extract the staffId

      if (staffId) {
        this.firstFormGroup.patchValue({ staffId });
        console.log('staff id retrieved from session storage: ', staffId);
      } else {
        console.error('Staff ID is missing in sessionStorage');
        // Handle the missing staffId case if necessary
      }
    } else {
      console.error('Current user is not found in sessionStorage');
      // Handle the case where `currentUser` is not found
    }
  }

  // on file change file upload
  onFileChange(event: any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      if (file.type !== 'application/pdf') {
        this.attachmentError = 'Only PDF files are allowed.';
        this.selectedFile = null;
        this.thirdFormGroup
          .get('attachment')
          ?.setErrors({ invalidFileType: true });
        this.thirdFormGroup.get('attachment')?.updateValueAndValidity();
      } else {
        this.attachmentError = null;
        this.selectedFile = file;
        this.thirdFormGroup.get('attachment')?.setValue(file);
        this.thirdFormGroup.get('attachment')?.updateValueAndValidity();
      }
    } else {
      this.attachmentError = 'No file selected.';
      this.selectedFile = null;
      this.thirdFormGroup
        .get('attachment')
        ?.setErrors({ noFileSelected: true });
      this.thirdFormGroup.get('attachment')?.updateValueAndValidity();
    }
  }

  onSubmit() {
    console.log('Submit button clicked!');

    if (
      this.firstFormGroup.valid &&
      this.secondFormGroup.valid &&
      this.thirdFormGroup.valid
    ) {
      const formData = new FormData();

      const formValues = {
        ...this.firstFormGroup.value,
        ...this.secondFormGroup.value,
        // ...this.thirdFormGroup.value,
      };

      console.log('Form Values to register company: ', formValues);

      // check if staff id is missing or not

      if (!formValues.staffId) {
        console.log('Staff ID is missing');
      }

      formValues.rocExpDate = this.formatDate(formValues.rocExpDate);
      formValues.telNo = `+60${formValues.telNo}`;
      formValues.faxNo = `+60${formValues.faxNo}`;

      formData.append(
        'company',
        JSON.stringify({
          ...formValues,
          staffId: this.firstFormGroup.get('staffId')?.value, // Ensure staffId is included
        })
      );

      if (this.selectedFile) {
        formData.append(
          'attachment',
          this.selectedFile,
          this.selectedFile.name
        );
      }

      formData.forEach((value, key) => {
        if (value instanceof File) {
          console.log(`${key}:`, value.name);
        } else {
          console.log(`${key}:`, value);
        }
      });

      this.companyService.createCompany(formData).subscribe({
        next: (response) => {
          this._snackBar.open('Company Created successfully', 'OK', {
            horizontalPosition: this.horizontalPosition,
            verticalPosition: this.verticalPosition,
            duration: 1000 * 3,
          });
          console.log('Company created successfully', response);
          this.dialogRef.close(true);
        },
        error: (err) => {
          this._snackBar.open('Error Created company', 'OK', {
            horizontalPosition: this.horizontalPosition,
            verticalPosition: this.verticalPosition,
            duration: 1000 * 3,
          });
          console.error('Error creating company', err);
        },
      });
    } else {
      this.firstFormGroup.markAllAsTouched();
      this.secondFormGroup.markAllAsTouched();
      this.thirdFormGroup.markAllAsTouched();
    }
  }

  formatDate(date: any): string {
    if (moment.isMoment(date)) {
      return date.format('DD/MM/YYYY');
    } else if (date instanceof Date) {
      return moment(date).format('DD/MM/YYYY');
    } else if (typeof date === 'string') {
      return moment(date, 'DD/MM/YYYY').format('DD/MM/YYYY');
    } else {
      console.error('Expected a Date object or string but got:', date);
      return '';
    }
  }

  // accordion expansion panel settings
  step = 0;

  setStep(index: number) {
    this.step = index;
  }

  nextStep() {
    this.step++;
  }

  prevStep() {
    this.step--;
  }
}
