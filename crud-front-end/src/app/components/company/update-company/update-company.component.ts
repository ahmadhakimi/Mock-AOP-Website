import { Component, Inject } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
} from '@angular/forms';
import {
  MAT_DIALOG_DATA,
  MatDialogModule,
  MatDialogRef,
} from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { CompanyService } from '../../../services/company.service';
import { Company, CompanyStatus } from '../../../interfaces/company';
import {
  MAT_DATE_LOCALE,
  MAT_DATE_FORMATS,
  MatNativeDateModule,
} from '@angular/material/core';
import {
  MatMomentDateModule,
  provideMomentDateAdapter,
} from '@angular/material-moment-adapter';
import moment from 'moment';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { NgFor } from '@angular/common';

// custom date format

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
  selector: 'app-update-company',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    FormsModule,
    MatFormFieldModule,
    MatDialogModule,
    MatDatepickerModule,
    MatInputModule,
    MatNativeDateModule,
    MatMomentDateModule,
    MatButtonModule,
    MatIcon,
    MatSelectModule,
    NgFor,
  ],
  providers: [
    {
      provide: MAT_DATE_LOCALE,
      useValue: 'en-GB',
    },
    provideMomentDateAdapter(),
    {
      provide: MAT_DATE_FORMATS,
      useValue: MY_DATE_FORMATS,
    },
  ],
  templateUrl: './update-company.component.html',
  styleUrl: './update-company.component.scss',
})
export class UpdateCompanyComponent {
  // declare update form
  updateForm: FormGroup;
  companyStatusOptions = Object.values(CompanyStatus);
  fileInput: File | null = null;

  constructor(
    private formBuilder: FormBuilder,
    private companyService: CompanyService,
    public dialogRef: MatDialogRef<UpdateCompanyComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Company,
    @Inject(MAT_DATE_LOCALE) public locale: string
  ) {
    this.updateForm = this.formBuilder.group({
      companyName: [data.companyName],
      rocExpDate: [data.rocExpDate ? new Date(data.rocExpDate) : null], // Ensure it's a Date object
      telNo: [data.telNo],
      faxNo: [data.faxNo],
      companyEmail: [data.companyEmail],
      companyStatus: [data.companyStatus],
      createdBy: [data.createdBy],
      updatedBy: [data.updatedBy],
      address: [data.address],
      remarks: [data.remarks],
      attachment: [null],
    });
  }

  // Handle file change event
  onFileChange(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.fileInput = input.files[0];
    }
  }

  onSubmit() {
    console.log('Form submitted');
    console.log('Form values:', this.updateForm.value);
    console.log('Form validity:', this.updateForm.valid);

    if (this.updateForm.valid) {
      const formData = new FormData();
      console.log('Update form is valid. Creating FormData object');

      // Append form fields to FormData object
      Object.keys(this.updateForm.value).forEach((key) => {
        const value = this.updateForm.value[key];
        if (key === 'rocExpDate' && value) {
          // Format date if it exists
          formData.append('rocExpDate', moment(value).format('DD-MM-YYYY'));
        } else if (
          key !== 'attachment' &&
          value !== null &&
          value !== undefined &&
          value !== ''
        ) {
          formData.append(key, value);
        }
      });

      // Append the file if it's selected
      if (this.fileInput) {
        formData.append('attachment', this.fileInput, this.fileInput.name);
      }

      // Call the service with FormData object
      this.companyService.updateCompany(formData, this.data.id).subscribe({
        next: () => {
          this.dialogRef.close(true);
        },
        error(err) {
          console.error('Error updating company details', err);
        },
      });
    } else {
      console.error('Form is invalid');
    }
  }

  onCancel() {
    this.dialogRef.close(true);
    console.log('click cancel button');
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
}
