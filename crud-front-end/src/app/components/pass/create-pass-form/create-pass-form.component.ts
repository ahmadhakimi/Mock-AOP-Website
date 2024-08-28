import {
  Component,
  ChangeDetectionStrategy,
  computed,
  inject,
  model,
  signal,
  Inject,
} from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatRadioModule } from '@angular/material/radio';
import {
  FormBuilder,
  FormsModule,
  ReactiveFormsModule,
  FormGroup,
  Validators,
} from '@angular/forms';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { PassService } from '../../../services/pass.service';
import { MatButtonModule } from '@angular/material/button';
import { NgFor, NgIf } from '@angular/common';
import { MatIcon } from '@angular/material/icon';
import { ApplicantType, ApplicationStatus } from '../../../interfaces/pass';
import { MatSelectModule } from '@angular/material/select';
import { Role, Terminal } from '../../../interfaces/staff';
import { ScrollingModule } from '@angular/cdk/scrolling';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { provideMomentDateAdapter } from '@angular/material-moment-adapter';
import moment from 'moment';

import {
  MatAutocompleteModule,
  MatAutocompleteSelectedEvent,
} from '@angular/material/autocomplete';
import { MatChipInputEvent, MatChipsModule } from '@angular/material/chips';
import { MAT_DATE_LOCALE, MAT_DATE_FORMATS } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { Dialog } from '@angular/cdk/dialog';
import { MatDialogRef } from '@angular/material/dialog';

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
  selector: 'app-create-pass-form',
  standalone: true,
  imports: [
    MatDatepickerModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatCheckboxModule,
    FormsModule,
    MatRadioModule,
    ReactiveFormsModule,
    MatButtonModule,
    NgFor,
    NgIf,
    MatIcon,
    MatSelectModule,
    ScrollingModule,
    MatChipsModule,
    MatAutocompleteModule,
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
  templateUrl: './create-pass-form.component.html',
  styleUrl: './create-pass-form.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CreatePassFormComponent {
  // properties

  passForm: FormGroup;
  readonly separatorKeysCodes: number[] = [ENTER, COMMA];
  // readonly currentArea = signal('');
  readonly areas = signal<string[]>([]);
  readonly allAreas: string[] = [
    'Area 1',
    'Area 2',
    'Area 3',
    'Area 4',
    'Area 5',
    'Area 6',
  ];

  readonly filteredArea = () => {
    const selectedAreas = this.areas();
    return this.allAreas.filter((area) => !selectedAreas.includes(area));
  };

  readonly announcer = inject(LiveAnnouncer);

  // enums
  ApplicationStatuses = Object.values(ApplicationStatus);
  ApplicantTypes = Object.values(ApplicantType);
  Terminals = Object.values(Terminal);
  Roles = Object.values(Role);

  // file property
  selectedFile: File | null = null;

  // CONSTRUCTOR
  constructor(
    private _formBuilder: FormBuilder,
    private passService: PassService,
    public dialogRef: MatDialogRef<CreatePassFormComponent>,
    @Inject(MAT_DATE_LOCALE) private _locale: string
  ) {
    this.passForm = this._formBuilder.group({
      purpose: ['', Validators.required],
      applicationNo: [''],
      applicationName: [''],
      icNo: [''],
      passportNo: [''],
      areas: [[]], // Initialize with an empty array
      terminal: [''],
      applicantType: [''],
      status: [''],
      submittedBy: [''],
      submittedDate: [''],
      role: [''],
    });
  }

  add(event: MatChipInputEvent): void {
    event.value = '';
  }

  remove(area: string): void {
    this.areas.update((areas) => {
      const index = areas.indexOf(area);
      if (index >= 0) {
        areas.splice(index, 1);
        this.announcer.announce(`Removed ${area}`);
      }
      return [...areas];
    });
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    const selectedValue = event.option.viewValue;
    const currentAreas = this.areas();

    if (!currentAreas.includes(selectedValue)) {
      this.areas.update((areas) => [...areas, selectedValue]);
      event.option.deselect();
    } else {
      // Optionally announce the duplicate selection attempt
      this.announcer.announce(`${selectedValue} is already selected.`);
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
}
